package zergtel.core.searcher;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Simon on 1/10/2017.
 */
public class SearcherTest {
    @DataProvider(name = "queries")
    public static Object[][] getQueries() {
        return new Object[][] {
            {"we are number one"},
            {"yee"}
        };
    }

    @Test(dataProvider = "queries", groups = {"searcher"})
    public void testSearch(String query) throws Exception {
        System.out.println("Searcher test: " + query);
        ArrayList<Map<String, String>> results = Searcher.search(query);
        int counter = 1;
        for (Map<String, String> result : results) {
            System.out.println("Result # " + counter++);
            for(Map.Entry<String, String> entry : result.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            System.out.println("-------");
        }
        Assert.assertTrue(true);

        System.out.println("Test success");
    }
}