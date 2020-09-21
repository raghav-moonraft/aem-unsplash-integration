package com.mysite.core.service.impl;

import com.mysite.core.service.UnsplashService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Component(service = UnsplashService.class)
@Designate(ocd = UnsplashServiceImpl.UnsplashServiceConfig.class)
public class UnsplashServiceImpl implements UnsplashService {

    private String unsplashUrl;
    private String unsplashSearchUrl;
    private String clientId;
    private String limitPerPage;

    @ObjectClassDefinition(name = "Unsplash OSGi Configuration", description = "OSGi Configuration For UnsplashService")
    @interface UnsplashServiceConfig {
        /**
         * unsplashUrl
         *
         * @return String unsplashUrl
         */
        @AttributeDefinition(name = "Unsplash Url", description = "Unsplash Url", type = AttributeType.STRING)
        String unsplashUrl();

        /**
         * unsplashSearchUrl
         * @return String unsplashSearchUrl
         */
        @AttributeDefinition(name = "Unsplash Search Url", description = "Unsplash Search Url", type = AttributeType.STRING)
        String unsplashSearchUrl();
        /**
         * clientId
         * @return String clientId
         */
        @AttributeDefinition(name = "Client Id", description = "Client Id", type = AttributeType.STRING)
        String clientId();
        /**
         * limitPerPage
         * @return String limitPerPage
         */
        @AttributeDefinition(name = "Limit Per Page", description = "Limit Per Page", type = AttributeType.STRING)
        String limitPerPage();
    }

    @Activate
    protected void activate(UnsplashServiceConfig config) {

        this.unsplashUrl = config.unsplashUrl();
        this.unsplashSearchUrl = config.unsplashSearchUrl();
        this.clientId = config.clientId();
        this.limitPerPage = config.limitPerPage();

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
}
