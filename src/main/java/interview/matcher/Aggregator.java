package interview.matcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Aggregator {
    private final Map<String, List<Offset>> aggregatedResults = new HashMap<>();

    public void aggregate(List<Map<String, List<Offset>>> matcherResults) {
        matcherResults.forEach(result ->
                result.forEach((keyword, offsets) ->
                        aggregatedResults.merge(keyword, new ArrayList<>(offsets), (list1, list2) -> {
                            list1.addAll(list2);
                            return list1;
                        })
                )
        );
    }

    public Map<String, List<Offset>> getAggregatedResults() {
        return aggregatedResults;
    }

    public void print(){
        System.out.println("Aggregated Results:");
        aggregatedResults.forEach((keyword, offsets) -> {
            System.out.print(keyword + " --> ");
            System.out.println(offsets);
        });
    }

}
