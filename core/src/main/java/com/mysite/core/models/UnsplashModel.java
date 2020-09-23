package com.mysite.core.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Sling Model for Unsplash Integration. This model is responsible for fetching all image related information that
 * author has configured via dialog.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class UnsplashModel {

    /**
     * List of Image Configurations
     */
    private final List<ImageConfigs> items = new ArrayList<>();

    public List<ImageConfigs> getItems() {
        return items;
    }

    @SlingObject
    private Resource currentResource;

    @SlingObject
    private SlingHttpServletRequest request;

    @PostConstruct
    public void activate() {

        final Object fileReference = currentResource.getValueMap().get("fileReference");
        String author = StringUtils.EMPTY;
        if (fileReference != null) {
            author = String.valueOf(fileReference).split("author=")[1];
        }
        final Resource resource = currentResource.getChild("unsplash");
        if (resource != null) {
            final Iterable<Resource> resourceChildren = resource.getChildren();
            for(Resource res: resourceChildren) {
                final ValueMap valueMap = res.getValueMap();
                ImageConfigs config = new ImageConfigs();
                config.setDevice(String.valueOf(valueMap.get("device")));
                config.setFormat(String.valueOf(valueMap.get("format")));
                config.setHeight(String.valueOf(valueMap.get("height")));
                config.setFit(String.valueOf(valueMap.get("fit")));
                config.setCrop(String.valueOf(valueMap.get("crop")));
                config.setWidth(String.valueOf(valueMap.get("width")));
                config.setQuality(String.valueOf(valueMap.get("quality")));
                config.setAuthor(author);
                items.add(config);
            }
        }
    }

    /**
     * Bean class for holding Image Configuration data
     */
    public class ImageConfigs {

        // Device (Can be either of Mobile, Tablet and Desktop)
        private String device;

        // Image Format (JPG, PNG etc)
        private String format;

        // Height of Image
        private String height;

        // Image Fit
        private String fit;

        // Image Crop
        private String crop;

        // Width of Image
        private String width;

        // Image quality
        private String quality;

        // Image author name from Unsplash
        private String author;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getFit() {
            return fit;
        }

        public void setFit(String fit) {
            this.fit = fit;
        }

        public String getCrop() {
            return crop;
        }

        public void setCrop(String crop) {
            this.crop = crop;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }
    }
}