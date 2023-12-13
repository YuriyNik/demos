package interview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Aggregator {
    public void aggregateAndPrint(List<Map<String, List<Offset>>> matcherResults) {

        Map<String, List<Offset>> aggregatedResults = new HashMap<>();

        matcherResults.forEach(result ->
                result.forEach((keyword, offsets) ->
                        aggregatedResults.merge(keyword, new ArrayList<>(offsets), (list1, list2) -> {
                            list1.addAll(list2);
                            return list1;
                        })
                )
        );

        System.out.println("Aggregated Results:");
        aggregatedResults.forEach((keyword, offsets) -> {
            System.out.print(keyword + " --> ");
            System.out.println(offsets);
        });

    }
}
