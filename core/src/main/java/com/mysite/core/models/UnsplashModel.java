package com.mysite.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import java.util.*;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class UnsplashModel {

    private final List<ImageConfigs> items = new ArrayList<>();

    public List<ImageConfigs> getItems() {
        return items;
    }

    @SlingObject
    private Resource currentResource;

    @PostConstruct
    public void activate() {
        final Iterable<Resource> res = currentResource.getChild("unsplash").getChildren();
        for(Resource resource: res) {
            final ValueMap valueMap = resource.getValueMap();
            ImageConfigs config = new ImageConfigs();
            config.setDevice(String.valueOf(valueMap.get("device")));
            config.setFormat(String.valueOf(valueMap.get("format")));
            config.setHeight(String.valueOf(valueMap.get("height")));
            config.setFit(String.valueOf(valueMap.get("fit")));
            config.setCrop(String.valueOf(valueMap.get("crop")));
            config.setWidth(String.valueOf(valueMap.get("width")));
            config.setQuality(String.valueOf(valueMap.get("quality")));
            items.add(config);
        }
    }

    public class ImageConfigs {

        private String device;
        private String format;
        private String height;
        private String fit;
        private String crop;
        private String width;
        private String quality;

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