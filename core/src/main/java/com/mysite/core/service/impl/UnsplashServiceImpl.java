package com.mysite.core.service.impl;

import com.mysite.core.service.UnsplashService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Implementation of UnsplashService and UnsplashServiceConfig OSGI Configuration
 */
@Component(service = UnsplashService.class)
@Designate(ocd = UnsplashServiceImpl.UnsplashServiceConfig.class)
public class UnsplashServiceImpl implements UnsplashService {

    private String unsplashUrl;
    private String unsplashSearchUrl;
    private String clientId;
    private String limitPerPage;
    private String appName;

    /**
     * OSGI Config for Unsplash Service
     */
    @ObjectClassDefinition(name = "Unsplash OSGi Configuration", description = "OSGi Configuration For UnsplashService")
    @interface UnsplashServiceConfig {
        /**
         * unsplashUrl - list photos url
         *
         * @return String unsplashUrl
         */
        @AttributeDefinition(name = "Unsplash List Photos Url", description = "Unsplash List Photos Url", type = AttributeType.STRING)
        String unsplashUrl();

        /**
         * unsplashSearchUrl search photos url
         * @return String unsplashSearchUrl
         */
        @AttributeDefinition(name = "Unsplash Search Url", description = "Unsplash Search Url", type = AttributeType.STRING)
        String unsplashSearchUrl();
        /**
         * clientId ClientId (API Key) generated from unsplash site
         * @return String clientId
         */
        @AttributeDefinition(name = "Client Id", description = "Client Id", type = AttributeType.STRING)
        String clientId();
        /**
         * limitPerPage per page limit for search/list photos results
         * @return String limitPerPage
         */
        @AttributeDefinition(name = "Limit Per Page", description = "Limit Per Page", type = AttributeType.STRING)
        String limitPerPage();

        /**
         * App Name for your account used at the time og app key creation on unsplash site
         * @return String app Name
         */
        @AttributeDefinition(name = "App Name", description = "App Name", type = AttributeType.STRING)
        String appName();
    }

    @Activate
    protected void activate(UnsplashServiceConfig config) {

        this.unsplashUrl = config.unsplashUrl();
        this.unsplashSearchUrl = config.unsplashSearchUrl();
        this.clientId = config.clientId();
        this.limitPerPage = config.limitPerPage();
        this.appName = config.appName();

    }

    @Override
    public String getUnsplashUrl() {
        return unsplashUrl;
    }

    @Override
    public String getUnsplashSearchUrl() {
        return unsplashSearchUrl;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getLimitPerPage() {
        return limitPerPage;
    }

    @Override
    public String getAppName() {
        return appName;
    }
}
