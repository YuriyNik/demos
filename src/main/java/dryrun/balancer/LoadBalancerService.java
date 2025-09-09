package dryrun.balancer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoadBalancerService implements LoadBalancer{
   private List<String> servers = new ArrayList<>();
   private Integer counter=0;
   Random random = new Random();
   public void register(String server){
       if (!server.isEmpty()){
           servers.add(server);
       }
   }
   public String get(Strategy strategy){
       switch (strategy){
           case ROUND_ROBIN -> {
               return servers.get(counter++%getSize());
           }
           case RANDOM -> {
                return servers.get(random.nextInt(getSize()));
           }
           default -> {
               return "";
           }
       }
   }
   public int getSize(){
       return servers.size();
   }
}
