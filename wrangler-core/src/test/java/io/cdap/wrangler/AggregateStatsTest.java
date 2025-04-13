package io.cdap.wrangler;

import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.executor.TestingRig;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AggregateStatsTest {

    @Test
    public void testAggregateStats() throws Exception {
       
        List<Row> inputRows = new ArrayList<>();
        inputRows.add(new Row("data_transfer_size", "10MB").add("response_time", "150ms"));
        inputRows.add(new Row("data_transfer_size", "5MB").add("response_time", "50ms"));
        inputRows.add(new Row("data_transfer_size", "15MB").add("response_time", "300ms"));

       
        String[] recipe = {
            "aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec"
        };

  
        List<Row> results = TestingRig.execute(recipe, inputRows);

       
        Assert.assertEquals(1, results.size());

        Row resultRow = results.get(0);
        double totalSizeMB = (Double) resultRow.getValue("total_size_mb");
        double totalTimeSec = (Double) resultRow.getValue("total_time_sec");

        Assert.assertEquals(30.0, totalSizeMB, 0.001); // 10+5+15 MB
        Assert.assertEquals(0.5, totalTimeSec, 0.001); // 150+50+300 ms = 500 ms = 0.5 sec
    }
}
