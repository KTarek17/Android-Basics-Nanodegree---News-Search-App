# Android Basics Nanodegree - News Search App
News Search App created as the last project in the Android Basics Nanodegree

## App Description
This app communicates with [The Guardian API](https://open-platform.theguardian.com/documentation/) to get basic news items and show thgem to the user.
It has a default search query, but the user can edit the query based on date ranges, keywords, and news items per query

## Android skills implemented:
* __RecyclerView__: To display the news items
* __Adapter__ (for the RecyclerView)
* __Loaders & Loader Manager__: To load data from The Guardian API independantly from the Main Activity.
* __RESTful calls__: To handle communicating with The Guardian API through HTTP URL Connections
* __Preference Fragment__: To allow the user to change some of the query attributes such as "from_date", "to_date", "page_size", etc...
* __Dark Mode__: Implemented a dark mode for the app, which can be changed in the preferences fragment, or if the system is dark mode compatible (Android 10+) 
