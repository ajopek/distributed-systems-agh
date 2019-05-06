#ifndef BANK_ICE
#define BANK_ICE

module Bank {
    enum Currency { PLN, EUR, USD }
    enum AccountType { STANDARD, PREMIUM }

    struct LoanPeriod {
        int numOfDays;
    }

    struct Money {
        int baseUnitAmount;
        Currency currency;
    }

    interface Account {
        // idempotent informs that two (or more) successive invocations have the same result
        idempotent Money getBalance();
	idempotent AccountType getType();
    }

    struct CreditCost {
        Money base;
        Money currency;
    }

    interface PremiumAccount extends Account {
            CreditCost requestLoan(Money ammount, LoanPeriod period);
        }

    struct AccountCreationResult {
        Account* account;
        AccountType type;
        string key;
    }

    interface BankAccounts {
        AccountCreationResult createAccount(string firstName, string lastName, string PESEL, Money monthlyIncome);
    }


}

#endif
