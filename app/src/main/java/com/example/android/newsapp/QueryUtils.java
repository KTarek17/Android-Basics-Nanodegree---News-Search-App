package com.example.android.newsapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    //Log Tag
    private static final String TAG = "QueryUtils";

    //URL is used to query The Guardians API
    private static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search";
    //Registered API Key given to the developer of this app
    private static final String GUARDIAN_API_KEY = "28a18d57-54b5-4f0e-af60-0c3abc118e2c";

    //Query parameter values used in building the final query URL
    private static String query = "debate"; //search queries separated by AND/OR/NOT, with %20 instead of spaces
    private static String queryOrderBy = "relevance"; //valid orders: newest, oldest or relevance
    private static int queryPageSize = 10; //valid number of items: 1-50

    /**
     * Utility method used to build the query URL based on the user preferences
     *
     * @return String representing the URL used to query The Guardian API
     */
    public static String getQueryUrlString() {
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);

        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", query);
        uriBuilder.appendQueryParameter("show-blocks", "body");
        uriBuilder.appendQueryParameter("page-size", Integer.toString(queryPageSize));
        uriBuilder.appendQueryParameter("order-by", queryOrderBy);
        uriBuilder.appendQueryParameter("api-key", GUARDIAN_API_KEY);

        String finalUrl = uriBuilder.toString();

        Log.i(TAG, "Final Url = " + finalUrl);

        return finalUrl;
    }

    /**
     * Accepts a JSON Response in the form of a string, then parses it into a list of
     * {@link NewsItem}s
     * @param jsonResponse The JSON Response provided by the URL query
     * @return A list of {@link NewsItem}s if the JSON Response is valid,
     *         or null otherwise
     */
    public static List<NewsItem> parseJsonResponse(String jsonResponse) throws JSONException {
        //TODO remove this line
        Log.i(TAG, "Parsing JSON Response: " + jsonResponse);

        //Checks if the response is null or empty, and returns early if it's true
        if(jsonResponse == null || jsonResponse.isEmpty()){
            Log.e(TAG, "JSON Response is null or empty");
            return null;
        }

        JSONObject response = new JSONObject(jsonResponse).getJSONObject("response");

        //Checks the status of the response and returns early if it's not "ok"
        String status = response.getString("status");
        if(!status.equals("ok")){
            Log.e(TAG, "Status not OK: " + status +
                             "\n Message: " + response.getString("message"));
            return null;
        }

        List<NewsItem> newsItems = new ArrayList<>(queryPageSize);
        JSONArray results = response.getJSONArray("results");
        for(int i = 0; i < results.length(); i++)
            newsItems.add(new NewsItem(results.getJSONObject(i)));

        return newsItems;
    }

    public static void setQuery(String query) {
        QueryUtils.query = query;
    }

    public static void setQueryOrderBy(String queryOrderBy) {
        QueryUtils.queryOrderBy = queryOrderBy;
    }

    public static void setQueryPageSize(int queryPageSize) {
        QueryUtils.queryPageSize = queryPageSize;
    }

}
