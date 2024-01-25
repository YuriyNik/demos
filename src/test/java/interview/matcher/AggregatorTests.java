package interview.matcher;
import interview.matcher.Aggregator;
import interview.matcher.Offset;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AggregatorTests {

    @Test
    void aggregateAndPrint_ShouldAggregateResults() {
        // Arrange
        Aggregator aggregator = new Aggregator();
        List<Map<String, List<Offset>>> matcherResults = createMatcherResults();

        // Act
        aggregator.aggregate(matcherResults);

        // Assert
        Map<String, List<Offset>> expectedAggregatedResults = createExpectedAggregatedResults();
        assertEquals(expectedAggregatedResults, aggregator.getAggregatedResults());
    }

    private List<Map<String, List<Offset>>> createMatcherResults() {
        List<Map<String, List<Offset>>> matcherResults = new ArrayList<>();

        Map<String, List<Offset>> result1 = new HashMap<>();
        result1.put("keyword1", createOffsetList(1, 2, 3));
        result1.put("keyword2", createOffsetList(4, 5, 6));
        matcherResults.add(result1);

        Map<String, List<Offset>> result2 = new HashMap<>();
        result2.put("keyword1", createOffsetList(7, 8, 9));
        result2.put("keyword3", createOffsetList(10, 11, 12));
        matcherResults.add(result2);

        return matcherResults;
    }

    private List<Offset> createOffsetList(int... charOffsets) {
        List<Offset> offsets = new ArrayList<>();
        for (int charOffset : charOffsets) {
            offsets.add(new Offset(1, charOffset));
        }
        return offsets;
    }

    private Map<String, List<Offset>> createExpectedAggregatedResults() {
        Map<String, List<Offset>> expectedAggregatedResults = new HashMap<>();

        expectedAggregatedResults.put("keyword1", createOffsetList(1, 2, 3, 7, 8, 9));
        expectedAggregatedResults.put("keyword2", createOffsetList(4, 5, 6));
        expectedAggregatedResults.put("keyword3", createOffsetList(10, 11, 12));

        return expectedAggregatedResults;
    }
}

