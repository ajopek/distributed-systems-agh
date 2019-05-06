package Bank;

import com.zeroc.Ice.*;
import com.zeroc.Ice.Object;
import exchange_rate.proto.gen.CurrencyType;

import java.util.HashMap;

public class BankAccountsI implements BankAccounts {
    private final static Currency NATIVE_CURRENCY = Currency.PLN;
    private final static int PREMIUM_LOWER_BOUND = 10000;
    private final HashMap<CurrencyType, Double> rates;

    public BankAccountsI(HashMap<CurrencyType, Double> rates) {
        this.rates = rates;
    }

    @Override
    public AccountCreationResult createAccount(String firstName, String lastName, String PESEL,
                                               Money monthlyIncome, Current current) {
        System.out.println("New account request:");
        System.out.println("PESEL: " + PESEL);
        System.out.println(firstName);
        System.out.println(lastName);
        System.out.println(monthlyIncome.baseUnitAmount);

        if(PESEL.isEmpty()) {
            throw new IllegalArgumentException("PESEL cannot be empty");
        }
        if(monthlyIncome.currency != NATIVE_CURRENCY) {
            throw new IllegalArgumentException("Monthly income must be in native currency");
        }
        if(monthlyIncome.baseUnitAmount < 0) {
            throw new IllegalArgumentException("Income must be non negative");
        }

        AccountCreationResult creationResult = new AccountCreationResult();

        creationResult.type = monthlyIncome.baseUnitAmount >= PREMIUM_LOWER_BOUND ?
                                    AccountType.PREMIUM : AccountType.STANDARD;

        AccountI account = null;
        Money balance = new Money();
        balance.currency = NATIVE_CURRENCY;
        balance.baseUnitAmount = monthlyIncome.baseUnitAmount * 2;
        switch (creationResult.type) {
            case PREMIUM:
                account = new PremiumAccountI(PESEL, firstName, lastName, balance, rates);
                break;
            case STANDARD:
                account = new AccountI(PESEL, firstName, lastName, balance);
                break;
            default:
                throw new IllegalStateException("Encountered unknown account type");
        }

        creationResult.account = AccountPrx.uncheckedCast(
                                    current.adapter.add(account,
                                                        new Identity(PESEL, creationResult.type.toString())));
        creationResult.key = account.key;
        return  creationResult;
    }
}
