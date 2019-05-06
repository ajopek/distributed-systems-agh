package Bank;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.Util;
import com.zeroc.Ice.ObjectAdapter;

import exchange_rate.proto.gen.Currency;
import exchange_rate.proto.gen.ExchangeRatesProviderGrpc;
import exchange_rate.proto.gen.CurrencyType;
import exchange_rate.proto.gen.ExchangeRate;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Server
{
    private final String PORT;
    private final HashMap<CurrencyType, Double> exchangeRateValue = new HashMap<>();

    // Waluty
    private final ManagedChannel channel;
    private final ExchangeRatesProviderGrpc.ExchangeRatesProviderStub exchangeRatesProviderStub;

    public Server(String host, int port, String PORT) {
        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
        exchangeRatesProviderStub = ExchangeRatesProviderGrpc.newStub(channel);
        this.PORT = PORT;
        exchangeRateValue.put(CurrencyType.EUR, 1.0);
        exchangeRateValue.put(CurrencyType.PLN, 1.0);
        exchangeRateValue.put(CurrencyType.USD, 1.0);
    }

    private void subscribeOnRates() {
        exchangeRatesProviderStub.getExchangeRates(
                Currency.newBuilder().addAllCurrency(exchangeRateValue.keySet()).build(),
                new StreamObserver<ExchangeRate>() {
                    @Override
                    public void onNext(ExchangeRate exchangeRate) {
                        exchangeRateValue.replace(exchangeRate.getCurrency(), exchangeRate.getRate());
                        System.out.println("New rate for " + exchangeRate.getCurrency().toString() + " : " + exchangeRate.getRate());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("Error in rates: " + throwable.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Rates stream complete");
                    }
                }
        );
    }

    private void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(6, TimeUnit.SECONDS);
    }

    public void t1(String[] args)
    {


        int status = 0;
        Communicator communicator = null;

        try
        {
            // 1. Inicjalizacja ICE - utworzenie communicatora
            communicator = Util.initialize(args);

            // 2. Konfiguracja adaptera
            // METODA 1 (polecana produkcyjnie): Konfiguracja adaptera Adapter1 jest w pliku konfiguracyjnym podanym jako parametr uruchomienia serwera
            //Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Adapter1");

            // METODA 2 (niepolecana, dopuszczalna testowo): Konfiguracja adaptera Adapter1 jest w kodzie �r�d�owym
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h localhost -p " + PORT + ":udp -h localhost -p " + PORT);

            // 3. Stworzenie serwanta/serwant�w
            //CalcI calcServant1 = new CalcI();
            //CalcI calcServant2 = new CalcI();

            BankAccountsI accountsI = new BankAccountsI(exchangeRateValue);
            adapter.add(accountsI, new Identity("bankaccounts" + PORT, "acc"));


            // 4. Dodanie wpis�w do tablicy ASM
            //adapter.add(calcServant1, new Identity("calc11", "calc"));
            //adapter.add(calcServant2, new Identity("calc22", "calc"));


            // 5. Aktywacja adaptera i przej�cie w p�tl� przetwarzania ��da�
            adapter.activate();

            System.out.println("Entering event processing loop...");

            communicator.waitForShutdown();

        }
        catch (Exception e)
        {
            System.err.println(e);
            status = 1;
        }
        if (communicator != null)
        {
            // Clean up
            //
            try
            {
                communicator.destroy();
            }
            catch (Exception e)
            {
                System.err.println(e);
                status = 1;
            }
        }
        System.exit(status);
    }


    public static void main(String[] args)
    {
        Server app = new Server("localhost", 50051, args.length > 0 ? args[0] : "10000");
        app.subscribeOnRates();
        app.t1(args);
    }
}
