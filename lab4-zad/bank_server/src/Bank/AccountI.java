package Bank;

import com.zeroc.Ice.Current;

public class AccountI implements Account {
    public final String PESEL;
    public final String firstName;
    public final String lastName;
    public Money balance;
    public final String key;

    public AccountI(String PESEL, String firstName, String lastName, Money balance) {
        this.PESEL = PESEL;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.key = getNewKey();
    }

    public AccountType getType(Current current) {
        return AccountType.STANDARD;
    }

    @Override
    public Money getBalance(com.zeroc.Ice.Current current) {
        if(this.isAuthorized(current.ctx.get("key"))) {
            return this.balance;
        }
        Money fakeBalance = new Money();
        fakeBalance.currency = Currency.PLN;
        fakeBalance.baseUnitAmount = -1;
        return fakeBalance;
    }

    public boolean isAuthorized(String compare) {
        System.out.println("Key: " + compare);
        return key.equals(compare);
    }

    private String getNewKey() {
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }
}
