package dryrun.transfer;

public class AccountPersonal implements Account{
    private Double balance;

    public AccountPersonal(Double balance){
        this.balance=balance;
    }

    @Override
    public void deposit(Double amount){
       balance = balance + amount;
    }
    @Override
    public void withdraw(Double amount){
        balance = balance - amount;
    }

    @Override
    public Double getBalance() {
        return balance;
    }
}
