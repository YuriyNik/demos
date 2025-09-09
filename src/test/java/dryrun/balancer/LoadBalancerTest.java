package dryrun.balancer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class LoadBalancerTest {

    @Test
    void loadBalancerService_concurrent() throws InterruptedException, ExecutionException {
        LoadBalancer loadBalancer = new LoadBalancerService();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            loadBalancer.register("Srv"+i);
        }
        for (int i = 0; i < 100 ; i++) {
            tasks.add(()-> loadBalancer.get(LoadBalancer.Strategy.ROUND_ROBIN));
        }
        List<Future<String>> futures = executorService.invokeAll(tasks);
        Map<String, Integer> stats = new HashMap<>();
        for (Future<String> result:futures) {
            String srv = result.get();
//       Opt1 getOrDefault
//            stats.put(srv, stats.getOrDefault(srv,0)+1);
//       Opt2 merge
            stats.merge(srv, 1, Integer::sum);
        }
        System.out.println(stats);
        executorService.shutdown();
        executorService.awaitTermination(1,TimeUnit.SECONDS);


    }

    @Test
    void loadBalancerService_test(){
        LoadBalancer loadBalancer = new LoadBalancerService();
        loadBalancer.register("Server1");
        loadBalancer.register("Server2");
        loadBalancer.register("Server3");
        loadBalancer.register("Server4");
        loadBalancer.register("Server5");

        System.out.println("Strategy.ROUND_ROBIN");
        for (int i = 0; i < 20; i++) {
            System.out.println(loadBalancer.get(LoadBalancer.Strategy.ROUND_ROBIN));
        }
        System.out.println("Strategy.RANDOM");
        for (int i = 0; i < 20; i++) {
            System.out.println(loadBalancer.get(LoadBalancer.Strategy.RANDOM));
        }
    }
}
