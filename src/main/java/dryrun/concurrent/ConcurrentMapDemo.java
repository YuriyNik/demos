package dryrun.concurrent;

import java.util.concurrent.*;

public class ConcurrentMapDemo {

    public static void main(String[] args) throws InterruptedException {

        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        int tasks = 1_000_000;

        ExecutorService executor = Executors.newFixedThreadPool(8);

//        List<Future<Integer>> futures = new ArrayList<>();

            for (int i = 0; i < tasks; i++) {
                int id = i;
                executor.submit(() -> {
                    String key = "key" + (id % 10); // 10 ключей
                    // безопасное увеличение значения
                    map.merge(key, 1, Integer::sum);
                });
            }

            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);

        // проверяем результат
        map.forEach((k, v) -> System.out.println(k + " -> " + v));
    }

}
