package dryrun.transfer;

public interface TransferService {
    void transferMoney(Account from, Account to, Double amount);
}