import sys
import Ice
import click

Ice.loadSlice('../slice/bank.ice')
import Bank

currencies = {
    'PLN': Bank.Currency.PLN,
    'EUR': Bank.Currency.EUR,
    'USD': Bank.Currency.USD
}

with Ice.initialize(sys.argv) as communicator:



    @click.group()
    def cli():
        pass


    @cli.command()
    @click.argument('port')
    @click.argument('pesel')
    @click.argument('key')
    def get_account(port, pesel, key):
        bank_prx = communicator.stringToProxy(
            'acc/bankaccounts{0}:tcp -h localhost -p {0}:udp -h localhost -p {0}'.format(port))
        bank = Bank.BankAccountsPrx.checkedCast(bank_prx)
        print("Is your account premium?")
        if input() == "yes":
            type = "PREMIUM"
        else:
            type = "STANDARD"
        try:
            obj = communicator.stringToProxy('{0}/{1}:tcp -h localhost -p {2}:udp -h localhost -p {2}'.format(type, pesel, port))
        except Ice.ObjectNotExistException as ex:
            print("Error: no such account")
            sys.exit(-17)
        result = Bank.AccountCreationResult()
        result.account = Bank.AccountPrx.checkedCast(obj)
        result.type = result.account.getType()
        result.key = key
        main_loop(result)


    @cli.command()
    @click.argument('port')
    @click.argument('pesel')
    @click.argument('firstname')
    @click.argument('lastname')
    @click.argument('income')
    def add_account(port, pesel, firstname, lastname, income):
        print('acc/bankaccounts{0}:tcp -h localhost -p {0}:udp -h localhost -p {0}'.format(port))
        bank_prx = communicator.stringToProxy(
            'acc/bankaccounts{0}:tcp -h localhost -p {0}:udp -h localhost -p {0}'.format(port))
        bank = Bank.BankAccountsPrx.checkedCast(bank_prx)
        try:
            income = Bank.Money(int(income), Bank.Currency.PLN)
            result = bank.createAccount(pesel, firstname, lastname, income)
        except Ice.UnknownLocalException:
            print("Account for such person already exists")
            sys.exit(-5)
        except ValueError:
            print("Wrong arguments")
            sys.exit(-6)
        main_loop(result)

    def main_loop(result):
        print(result)
        context = {"key": result.key}
        print(result.account)
        print("Entering main loop:")
        while(True):
            command = input()
            if command == "balance":
                balance = result.account.getBalance(context=context)
                print("Your balance is :" + str(balance.baseUnitAmount) + " " + str(balance.currency))
            elif command == "exit":
                break
            elif command == "loan":
                if result.type != Bank.AccountType.PREMIUM:
                    print("Your account is not premium")
                    continue
                premium_account = Bank.PremiumAccountPrx.checkedCast(result.account)
                print("Enter amount:")
                amount = int(input())
                print("Enter currency")
                currency = input()
                if currency in currencies.keys():
                    currency = currencies[currency]
                else:
                    print("This currency is not available.")
                money_amount = Bank.Money()
                money_amount.currency = currency
                money_amount.baseUnitAmount = amount
                print("Enter period in days:")
                days = Bank.LoanPeriod()
                days.numOfDays = int(input())
                print(premium_account.requestLoan(money_amount, days, context=context))
            else:
                print("Unknown command. 'balance' for balance, 'loan' for loan, 'exit' for exit")

    cli();