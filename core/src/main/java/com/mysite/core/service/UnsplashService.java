package com.mysite.core.service;

/**
 * Unsplash Service Interface
 */
public interface UnsplashService {

    /**
     * Returns url for list photos rest api from unsplash
     * @return list photos url
     */
    String getUnsplashUrl();

    /**
     * Returns url for search photos rest api from unsplash
     * @return search photos url
     */
    String getUnsplashSearchUrl();

    /**
     * Returns the Client Id (API Key)
     * @return client id
     */
    String getClientId();

    /**
     * Returns the Limit per page for search/list photo results
     * @return per page limit
     */
    String getLimitPerPage();

    /**
     * Return the App Name used at time of app key creation
     * @return app name
     */
    String getAppName();

}
