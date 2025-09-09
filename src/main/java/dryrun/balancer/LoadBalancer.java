package dryrun.balancer;

public interface LoadBalancer {
    enum Strategy{
        ROUND_ROBIN,
        RANDOM
    }
    void register(String server);
    String get(Strategy strategy);
    int getSize();

}
