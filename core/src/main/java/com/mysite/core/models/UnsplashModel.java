package com.mysite.core.models;

import com.mysite.core.service.UnsplashService;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Sling Model for Unsplash Integration. This model is responsible for fetching all image related information that
 * author has configured via dialog.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class UnsplashModel {

    /**
     * Custom Object which holds the image related configured data.
     */
    private final Item item = new Item();

    public Item getItem() {
        return item;
    }

    @SlingObject
    private Resource currentResource;

    @SlingObject
    private SlingHttpServletRequest request;

    @OSGiService
    private UnsplashService service;

    @PostConstruct
    public void activate() throws URISyntaxException, UnsupportedEncodingException {

        final String fileReference = String.valueOf(currentResource.getValueMap().get("fileReference"));
        final String uri = fileReference.split("\\?")[0];
        final Map<String, String> queryParams = getQueryParamsMap(fileReference);
        final Resource resource = currentResource.getChild("unsplash");
        if (!queryParams.isEmpty() && queryParams.get("author") != null) {
            URLDecoder.decode(queryParams.get("author"), StandardCharsets.UTF_8.name());
            item.setAuthor(URLDecoder.decode(queryParams.get("author"), StandardCharsets.UTF_8.name()));
        }
        item.setProfileUrl(queryParams.get("profile"));
        item.setAppName(service.getAppName());
        item.setUri(uri);
        final Map<Integer, String> urls = new TreeMap<>(Collections.reverseOrder());
        if (resource != null) {
            final Iterable<Resource> resourceChildren = resource.getChildren();
            for(Resource res: resourceChildren) {
                final ValueMap valueMap = res.getValueMap();
                item.setAltText(String.valueOf(valueMap.get("alttext")));
                queryParams.put("fm", String.valueOf(valueMap.get("format")));
                queryParams.put("h", String.valueOf(valueMap.get("height")));
                queryParams.put("fit", String.valueOf(valueMap.get("fit")));
                queryParams.put("crop", String.valueOf(valueMap.get("crop")));
                queryParams.put("w", String.valueOf(valueMap.get("width")));
                queryParams.put("q", String.valueOf(valueMap.get("quality")));
                final URI buildUrl = buildUrl(uri, queryParams);
                urls.put(Integer.valueOf(queryParams.get("w")), buildUrl.toString());
            }
            item.setUrls(urls);
        }
    }

    /**
     * This method builds URL based on actual url and the new query params value from dialog
     * @param uri original url
     * @param queryParams updated query params
     * @return new url
     * @throws URISyntaxException
     */
    private URI buildUrl(final String uri, final Map<String, String> queryParams) throws URISyntaxException {
        final URIBuilder uriBuilder = new URIBuilder(uri);
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (Map.Entry<String,String> query : queryParams.entrySet()) //using map.entrySet() for iteration
        {
            BasicNameValuePair nameValuePair = new BasicNameValuePair(query.getKey(), query.getValue());
            nameValuePairs.add(nameValuePair);
        }
        return uriBuilder.addParameters(nameValuePairs).build();
    }

    /**
     * Extracts queryParams from the original url
     * @param fileReference url value persisted in JCR
     * @return queryparams map
     * @throws URISyntaxException
     */
    private Map<String, String> getQueryParamsMap(String fileReference) throws URISyntaxException {
        List<NameValuePair> params = URLEncodedUtils.parse(new URI(fileReference), Charset.forName("UTF-8"));
        final Map<String, String> queryParams = new HashMap<>();
        for (NameValuePair param : params) {
            queryParams.put(param.getName(), param.getValue());
        }
        return queryParams;
    }

    public class Item {

        /**
         *  Map containing the new urls(value) corresponding to their width(key).
         */
        private Map<Integer, String> urls = new HashMap<>();
        /**
         * Name of Photographer who owns the image.
         */
        private String author;
        /**
         * profile url of photographer.
         */
        private String profileUrl;
        /**
         * App Name using which app key was generated.
         */
        private String appName;
        /**
         * Url without query params
         */
        private String uri;
        /**
         * Alternate Text
         */
        private String altText;

        public String getAltText() {
            return altText;
        }

        public void setAltText(String altText) {
            this.altText = altText;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getProfileUrl() {
            return profileUrl;
        }

        public void setProfileUrl(String profileUrl) {
            this.profileUrl = profileUrl;
        }

        public Map<Integer, String> getUrls() {
            return urls;
        }

        public void setUrls(Map<Integer, String> urls) {
            this.urls = urls;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}