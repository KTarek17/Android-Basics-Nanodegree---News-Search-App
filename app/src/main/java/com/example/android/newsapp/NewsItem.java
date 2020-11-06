package com.example.android.newsapp;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsItem {
    private static final String TAG = "NewsItem";

    final private String title;
    final private String author;
    final private String section;
    final private String webUrlString;
    final private String datePublished;
    final private String trailText;

    /**
     * Standard constructor
     *
     * @param title         Title of the {@link NewsItem}
     * @param author        Name of the author of the {@link NewsItem}
     * @param section       Name of the section, the {@link NewsItem} is related to
     * @param webURL        String representation of the URL leading to the {@link NewsItem}
     * @param datePublished A string representing the date {@link NewsItem} was published
     * @param trailText     The trail text of the {@link NewsItem}
     */
    public NewsItem(String title, String author, String section, String webURL, String datePublished, String trailText) {
        this.title = title;
        this.author = author;
        this.section = section;
        this.webUrlString = webURL;
        this.datePublished = datePublished;
        this.trailText = trailText;
    }

    /**
     * This constructor accepts a {@link JSONObject} that is parsed into a {@link NewsItem}.
     * This constructor assumes that the given JSON object is valid
     * @param jsonObject {@link JSONObject} representation of the {@link NewsItem}
     * @throws JSONException JSONException
     */
    public NewsItem(JSONObject jsonObject) throws JSONException {

        /* In the Guardian API, author names are sometimes added at the end of the title,
         * separated by "|". The following section of code checks the title to see if the author's
         * name is provided, splitting the string and assigning it to the correct field, or
         * assigns the whole string to the title field if no author name is provided.
         */
        String titleAuthorString = jsonObject.getString("webTitle");
        if (titleAuthorString.contains("|")) {
            this.title = titleAuthorString.substring(0, titleAuthorString.indexOf("|"));
            this.author = titleAuthorString.substring(titleAuthorString.indexOf("|") + 2);
        } else {
            this.title = titleAuthorString;
            this.author = "";
        }

        this.section = jsonObject.getString("sectionName");
        this.webUrlString = jsonObject.getString("webUrl");
        this.datePublished = parseDate(jsonObject.getString("webPublicationDate"));
        this.trailText = jsonObject.getJSONObject("fields").getString("trailText");

    }

    /**
     * Parses a given ISO8601 formatted date into a "MMM dd, yyyy" date format
     * @param webPublicationDate ISO8601 date string
     * @return "MMM dd, yyyy" date string
     */
    private String parseDate(String webPublicationDate) {
        String date = "";

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");
        try {
            @SuppressLint("SimpleDateFormat")
            Date dateObject = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(webPublicationDate);
            date = dateFormatter.format(dateObject);
        } catch (ParseException e) {
            Log.e(TAG, "Data Error Occurred!", e);
            e.printStackTrace();
        }

        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSection() {
        return section;
    }

    public String getWebUrlString() {
        return webUrlString;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public String getTrailText() {
        return trailText;
    }
}
