package zergtel.core.searcher;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.testng.Assert.*;

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
        ArrayList<Map> results = Searcher.search(query);
        Map result;
        for (int i = 0; i < 5; i++) {
            result = results.get(i);
            System.out.println("Result #" + (i + 1));
            System.out.println("Title: " + result.get("title"));
            System.out.println("Url: " + result.get("url"));
            System.out.println("Thumbnail: " + result.get("thumbnail"));
            System.out.println("-------");
        }

        Assert.assertTrue(true);

        System.out.println("Test success");
    }
}