package dryrun.balancer;

import org.junit.jupiter.api.Test;

public class LoadBalancerTest {
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
