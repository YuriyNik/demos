package dryrun.concurrent;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureSumExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("availableProcessors="+Runtime.getRuntime().availableProcessors());
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        List<Integer> numbers = new java.util.ArrayList<>(List.of(10, 20, 30, 40, 50));
        for (int i = 0; i < 100; i++) {
            numbers.add(i);
        }

        List<CompletableFuture<Integer>> futures = numbers.stream()
                .map(num -> CompletableFuture.supplyAsync(()-> num,executorService))
                .toList();

        CompletableFuture<Integer> sumFuture = futures.stream()
                .reduce(
                        CompletableFuture.completedFuture(0),
                        (f1,f2) -> f1.thenCombine(f2, Integer::sum)
                );

        System.out.println("Sum="+sumFuture.get());

        executorService.shutdown();


        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("Step 1: загрузка числа...");
                    return 10;
                })
                .thenApply(n -> {
                    System.out.println("Step 2: умножаем на 2");
                    return n * 2;
                })
                .thenApply(n -> {
                    System.out.println("Step 3: преобразуем в строку");
                    return "Result: " + n;
                })
                .exceptionally(ex -> {
                    System.out.println("Step ERR: ошибка " + ex.getMessage());
                    return "Default value";
                });

        System.out.println("Final result = " + future.join());





    }
}
