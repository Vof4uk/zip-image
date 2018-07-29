package com.mykytenko.rest.images.blocking.service;

import com.mykytenko.rest.images.blocking.model.ResourceItem;
import com.mykytenko.rest.images.blocking.model.ResourceLocation;

/**
 * Provides client with resources
 */
public interface ResourceService {
    /**
     * @param location location of resource resource
     * @return Returns ResourceItem with resource from url;
     * @throws RuntimeException if resource is unreachable.
     */
    ResourceItem fromLocation(ResourceLocation location);
}
