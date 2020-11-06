package com.example.android.newsapp;

import org.json.JSONException;
import org.json.JSONObject;

public class NewsItem {
    private static final String TAG = "NewsItem";

    final private String title;
    final private String author;
    final private String section;
    final private String webUrlString;
    final private String datePublished;
    final private String bodyTextSummary;

    /**
     * Standard constructor
     *
     * @param title           Title of the {@link NewsItem}
     * @param author          Name of the author of the {@link NewsItem}
     * @param section         Name of the section, the {@link NewsItem} is related to
     * @param webURL          String representation of the URL leading to the {@link NewsItem}
     * @param datePublished   A string representing the date {@link NewsItem} was published
     * @param bodyTextSummary The textual summary of the {@link NewsItem}
     */
    public NewsItem(String title, String author, String section, String webURL, String datePublished, String bodyTextSummary) {
        this.title = title;
        this.author = author;
        this.section = section;
        this.webUrlString = webURL;
        this.datePublished = datePublished;
        this.bodyTextSummary = bodyTextSummary;
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
        this.datePublished = jsonObject.getString("webPublicationDate");

        JSONObject blocks = jsonObject.optJSONObject("blocks");

        if (blocks != null) {
            String bodyTextStr = blocks.getJSONArray("body").getJSONObject(0)
                    .getString("bodyTextSummary");
            this.bodyTextSummary = reduceText(bodyTextStr);
        } else
            this.bodyTextSummary = "";
    }

    /**
     * Utility method that reduces given string to fit only only 1 sentence after 256 characters
     *
     * @param str The string being reduced
     * @return Reduced string
     */
    private String reduceText(String str) {
        int firstDotAfter256Characters = str.indexOf(".", 256);

        //If the string is less than 256 characters, or doesn't contain a dot after 256 characters
        //then return early with the same string
        if (firstDotAfter256Characters == -1)
            return str;

        return str.substring(0, firstDotAfter256Characters + 1);
    }

    //TODO Implement parseDate method
//    private String parseDate(String webPublicationDate) {
//        String date = "";
//
//
//
//        return date;
//    }

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

    public String getBodyTextSummary() {
        return bodyTextSummary;
    }
}
