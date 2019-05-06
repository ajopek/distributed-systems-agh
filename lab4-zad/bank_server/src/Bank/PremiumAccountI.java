package Bank;

import com.zeroc.Ice.Current;
import exchange_rate.proto.gen.CurrencyType;

import java.util.HashMap;

public class PremiumAccountI extends AccountI implements PremiumAccount{
    private final HashMap<CurrencyType, Double> rates;
    public PremiumAccountI(String PESEL, String firstName, String lastName, Money balance, HashMap<CurrencyType, Double> rates) {
        super(PESEL, firstName, lastName, balance);
        this.rates = rates;
    }

    @Override
    public CreditCost requestLoan(Money ammount, LoanPeriod period, com.zeroc.Ice.Current current) {
        Money loanCost = new Money();
        CurrencyType type = CurrencyType.forNumber(ammount.currency.value());
        double rate = rates.get(type)/rates.get(CurrencyType.PLN);
        balance.baseUnitAmount = balance.baseUnitAmount + (int)(ammount.baseUnitAmount * rate);
        loanCost.baseUnitAmount = (int)(ammount.baseUnitAmount * rate + (ammount.baseUnitAmount * rate * 0.002 * period.numOfDays));
        loanCost.currency = Currency.PLN;
        CreditCost cost = new CreditCost();
        cost.base = loanCost;
        Money currCost = new Money();
        currCost.currency = ammount.currency;
        currCost.baseUnitAmount = (int)(loanCost.baseUnitAmount / rate);
        cost.currency = currCost;
        return cost;
    };

    public AccountType getType(Current current) {
        return AccountType.PREMIUM;
    }
}
