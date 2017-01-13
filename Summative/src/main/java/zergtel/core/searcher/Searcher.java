package zergtel.core.searcher;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.SearchResultSnippet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Simon on 1/9/2017.
 * Based largely off of Google's searcher example class
 * This file contains some structures designed to include support for pages of searching,
 * but that feature hasn't been implemented yet as of 2017-01-10
 *
 * For an example on how to use this searcher, please see SearcherTest.java in test/java/zergtel/core/searcher
 */
public class Searcher {
    private static final String PROPERTIES_FILENAME = "youtube.properties";
    private static final int NUMBER_OF_VIDEOS_RETURNED = 25;
    private static YouTube youtube;

    public static ArrayList<Map> search(String query) {
        Properties properties = getProperties(PROPERTIES_FILENAME);
        return search(query, 0, Integer.parseInt(properties.getProperty("youtube.numreturns")), properties);
    }

    public static ArrayList<Map> search(String query, int beginIndex, int endIndex) {
        return search(query, beginIndex, endIndex, getProperties(PROPERTIES_FILENAME));
    }

    public static ArrayList<Map> search(String query, int beginIndex, int endIndex, Properties properties) {
        try {
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("ZTVDC").build();

            YouTube.Search.List search = youtube.search().list("id,snippet");

            String apiKey = properties.getProperty("youtube.apikey");
            search.setKey(apiKey);
            search.setQ(query);

            search.setType("video");

            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults((long)endIndex);

            SearchListResponse searchResponse = search.execute();

            return parse(searchResponse);
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.err.println("Search error - no results found");
        return null;
    }

    private static Properties getProperties(String fileName) {
        Properties properties = new Properties();
        try {
            InputStream in = Searcher.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);

        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }

        return properties;
    }

    private static ArrayList<Map> parse(SearchListResponse response) {
        ResourceId rId;
        ArrayList<Map> output = new ArrayList<>();

        for (SearchResult result : response.getItems()) {
            SearchResultSnippet snippet = result.getSnippet();
            rId = result.getId();
            Map element = new HashMap<String, String>();
            element.put("title", snippet.getTitle());
            element.put("url", "http://www.youtube.com/embed/" + rId.getVideoId());
            element.put("thumbnail", snippet.getThumbnails().getDefault().getUrl());
            output.add(element);
        }
        return output;
    }


}