package dryrun.transfer;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TransferServiceRegular implements TransferService{
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    @Override
    public void transferMoney(Account from, Account to, Double amount){
        reentrantReadWriteLock.writeLock().lock();
        from.withdraw(amount);
        to.deposit(amount);
        reentrantReadWriteLock.writeLock().unlock();
    }
}
