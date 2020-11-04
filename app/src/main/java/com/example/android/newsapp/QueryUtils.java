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

    //region Long String
    //String used to test the parseJsonResponse before adding the HTTPS requests
    public static final String TEST_JSON_RESPONSE = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":8652,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1,\"pages\":866,\"orderBy\":\"relevance\",\"results\":[{\"id\":\"technology/2020/sep/28/samsung-galaxy-tab-s7-review-android-tablet-to-rival-the-ipad-pro\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2020-09-28T06:00:06Z\",\"webTitle\":\"Samsung Galaxy Tab S7+ review: Android tablet to rival the iPad Pro\",\"webUrl\":\"https://www.theguardian.com/technology/2020/sep/28/samsung-galaxy-tab-s7-review-android-tablet-to-rival-the-ipad-pro\",\"apiUrl\":\"https://content.guardianapis.com/technology/2020/sep/28/samsung-galaxy-tab-s7-review-android-tablet-to-rival-the-ipad-pro\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"technology/2020/sep/01/samsung-galaxy-watch-3-review-the-new-king-of-android-smartwatches-screen-battery\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2020-09-01T06:00:29Z\",\"webTitle\":\"Samsung Galaxy Watch 3 review: the new king of Android smartwatches\",\"webUrl\":\"https://www.theguardian.com/technology/2020/sep/01/samsung-galaxy-watch-3-review-the-new-king-of-android-smartwatches-screen-battery\",\"apiUrl\":\"https://content.guardianapis.com/technology/2020/sep/01/samsung-galaxy-watch-3-review-the-new-king-of-android-smartwatches-screen-battery\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"technology/2020/sep/09/android-11-release-everything-you-need-to-know-about-google-update\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2020-09-09T11:11:12Z\",\"webTitle\":\"Android 11 release: everything you need to know about Google's update\",\"webUrl\":\"https://www.theguardian.com/technology/2020/sep/09/android-11-release-everything-you-need-to-know-about-google-update\",\"apiUrl\":\"https://content.guardianapis.com/technology/2020/sep/09/android-11-release-everything-you-need-to-know-about-google-update\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"technology/2020/may/13/samsung-galaxy-watch-active-2-review-best-smartwatch-for-android\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2020-05-13T06:00:32Z\",\"webTitle\":\"Samsung Galaxy Watch Active 2 review: the best smartwatch for Android\",\"webUrl\":\"https://www.theguardian.com/technology/2020/may/13/samsung-galaxy-watch-active-2-review-best-smartwatch-for-android\",\"apiUrl\":\"https://content.guardianapis.com/technology/2020/may/13/samsung-galaxy-watch-active-2-review-best-smartwatch-for-android\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"world/2020/may/06/critical-mass-of-android-users-needed-for-success-of-nhs-coronavirus-contact-tracing-app\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2020-05-06T14:57:36Z\",\"webTitle\":\"Critical mass of Android users crucial for NHS contact-tracing app\",\"webUrl\":\"https://www.theguardian.com/world/2020/may/06/critical-mass-of-android-users-needed-for-success-of-nhs-coronavirus-contact-tracing-app\",\"apiUrl\":\"https://content.guardianapis.com/world/2020/may/06/critical-mass-of-android-users-needed-for-success-of-nhs-coronavirus-contact-tracing-app\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"world/2020/sep/24/users-report-issues-as-covid-19-app-launches-in-england-and-wales\",\"type\":\"article\",\"sectionId\":\"world\",\"sectionName\":\"World news\",\"webPublicationDate\":\"2020-09-24T11:13:49Z\",\"webTitle\":\"Users report issues as Covid-19 app launches in England and Wales\",\"webUrl\":\"https://www.theguardian.com/world/2020/sep/24/users-report-issues-as-covid-19-app-launches-in-england-and-wales\",\"apiUrl\":\"https://content.guardianapis.com/world/2020/sep/24/users-report-issues-as-covid-19-app-launches-in-england-and-wales\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"technology/2020/feb/26/nvidia-shield-tv-review-the-best-android-tv-box-with-brilliant-ai-upscaling\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2020-02-26T07:00:12Z\",\"webTitle\":\"Nvidia Shield TV review: the best Android TV box with brilliant AI upscaling\",\"webUrl\":\"https://www.theguardian.com/technology/2020/feb/26/nvidia-shield-tv-review-the-best-android-tv-box-with-brilliant-ai-upscaling\",\"apiUrl\":\"https://content.guardianapis.com/technology/2020/feb/26/nvidia-shield-tv-review-the-best-android-tv-box-with-brilliant-ai-upscaling\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"technology/2020/sep/24/how-to-download-the-nhs-covid-19-contact-tracing-app\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2020-09-24T09:55:39Z\",\"webTitle\":\"How to download the NHS Covid-19 contact-tracing app\",\"webUrl\":\"https://www.theguardian.com/technology/2020/sep/24/how-to-download-the-nhs-covid-19-contact-tracing-app\",\"apiUrl\":\"https://content.guardianapis.com/technology/2020/sep/24/how-to-download-the-nhs-covid-19-contact-tracing-app\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"technology/2020/sep/27/20-best-apps-to-get-you-organised\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2020-09-27T10:00:41Z\",\"webTitle\":\"20 best apps to get you organised\",\"webUrl\":\"https://www.theguardian.com/technology/2020/sep/27/20-best-apps-to-get-you-organised\",\"apiUrl\":\"https://content.guardianapis.com/technology/2020/sep/27/20-best-apps-to-get-you-organised\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"technology/2020/jul/27/oneplus-nord-review-top-quality-phone-is-true-bargain-android-battery-camera\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2020-07-27T08:00:48Z\",\"webTitle\":\"OnePlus Nord review: top-quality phone is true bargain at £379\",\"webUrl\":\"https://www.theguardian.com/technology/2020/jul/27/oneplus-nord-review-top-quality-phone-is-true-bargain-android-battery-camera\",\"apiUrl\":\"https://content.guardianapis.com/technology/2020/jul/27/oneplus-nord-review-top-quality-phone-is-true-bargain-android-battery-camera\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"}]}}";
    //Query parameter values used in building the final query URL
    private static String query = "android"; //search queries separated by AND/OR/NOT, with %20 instead of spaces
    private static String queryOrderBy = "relevance"; //valid orders: newest, oldest or relevance
    private static int queryPageSize = 10; //valid number of items: 1-50
    //endregion

    /**
     * Utility method used to build the query URL based on the user preferences
     *
     * @return String representing the URL used to query The Guardian API
     */
    public static String getQueryUrlString() {
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);

        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", query);
        //uriBuilder.appendQueryParameter("show-blocks", "body");
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
        Log.v(TAG, "Parsing JSON Response: " + jsonResponse);

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
