package com.mykytenko.rest.images.blocking.model;

import org.hibernate.validator.constraints.URL;

/**
 * The class describes location of resource
 */
public class ResourceLocation {

    @URL
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
