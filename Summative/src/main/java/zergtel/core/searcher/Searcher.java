package zergtel.core.searcher;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Created by Simon on 1/9/2017.
 * Based largely off of Google's searcher example class
 * This file contains some structures designed to include support for pages of searching,
 * but that feature hasn't been implemented yet as of 2017-01-10
 */
public class Searcher {
    private static final String PROPERTIES_FILENAME = "youtube.properties";
    private static final int NUMBER_OF_VIDEOS_RETURNED = 25;
    private static YouTube youtube;

    public static ArrayList<String> search(String query) {
        Properties properties = getProperties(PROPERTIES_FILENAME);
        return search(query, 0, Integer.parseInt(properties.getProperty("youtube.numReturns")), properties);
    }

    public static ArrayList<String> search(String query, int beginIndex, int endIndex) {
        return search(query, beginIndex, endIndex, getProperties(PROPERTIES_FILENAME));
    }

    public static ArrayList<String> search(String query, int beginIndex, int endIndex, Properties properties) {
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

//            return parse(searchResponse.getItems());
//            make sure to write the parse function tomorrow!
            return null;

        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return new ArrayList<String>();
    }

    private static Properties getProperties(String fileName) {
        Properties properties = new Properties();
        try {
            InputStream in = SearcherExample.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);

        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }

        return properties;
    }


}
