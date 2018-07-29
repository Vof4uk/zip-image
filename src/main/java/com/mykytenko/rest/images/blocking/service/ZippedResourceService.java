package com.mykytenko.rest.images.blocking.service;

import com.mykytenko.rest.images.blocking.model.ResourceItem;
import com.mykytenko.rest.images.blocking.model.ResourceLocation;

/**
 * Service which provides client with zipped resources
 */
public interface ZippedResourceService extends ResourceService{
    /**
     * Provides client with resource compressed to zip. Resource is downloaded from  defined location.
     * @param location location which contains resource
     * @return compressed resource with properties
     * @throws RuntimeException if anything fails during compressing or getting resource.
     */
    ResourceItem fromLocation(ResourceLocation location);
}
