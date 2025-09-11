package dryrun.transfer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferServiceTest {

    @Test
    void transferService_transferMoney() throws ExecutionException, InterruptedException {
        Account account_from = new AccountPersonal(100.0);
        Account account_to = new AccountPersonal(0.0);
        TransferService transferService = new TransferServiceRegular();
//        transferService.transferMoney(account_from,account_to, 10.0);
//        assertEquals( 10.0,account_to.getBalance());

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        List<Future<?>> results = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            results.add(executorService.submit(() -> transferService.transferMoney(account_from,account_to,1.0)));
        }
        for (Future<?> result:results){
            result.get();
        }
        executorService.shutdown();
        executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
        System.out.println("Account_from balance="+account_from.getBalance()+";Account_to balance="+account_to.getBalance());

    }

}
