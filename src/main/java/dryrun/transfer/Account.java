package dryrun.transfer;

public interface Account {
    void deposit(Double amount);
    void withdraw(Double amount);
    Double getBalance();
}
