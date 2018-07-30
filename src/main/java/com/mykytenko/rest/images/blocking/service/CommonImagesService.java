package com.mykytenko.rest.images.blocking.service;

import com.mykytenko.rest.images.blocking.client.ResourceDownload;
import com.mykytenko.rest.images.blocking.exception.BadUrlException;
import com.mykytenko.rest.images.blocking.model.ResourceItem;
import com.mykytenko.rest.images.blocking.model.ResourceItem.Type;
import com.mykytenko.rest.images.blocking.model.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

/**
 * An implementation of ResourceService interface
 */
@Service
public class CommonImagesService implements ResourceService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ResourceDownload client;

    public CommonImagesService(@Autowired ResourceDownload client) {
        this.client = client;
    }

    @Override
    public ResourceItem fromLocation(ResourceLocation location) {
        URL url;
        try {
            url = new URL(location.getUrl());
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            throw new BadUrlException(e);
        }

        ResponseEntity<byte[]> response = client.downloadResource(url);
        checkResponse(response, url.toString());
        ResourceItem imageItem = new ResourceItem();
        imageItem.setBody(response.getBody());
        imageItem.setName(UUID.randomUUID().toString());
        imageItem.setType(Type.fromMediaType(response.getHeaders().getContentType()));
        return imageItem;

    }

    private void checkResponse(ResponseEntity<byte[]> response, String url) {
        if (response.getStatusCode() != HttpStatus.OK) {
            String message = "Resource not found at this location: ";
            log.error(message);
            throw new BadUrlException(message + url);
        }
        if (Type.fromMediaType(response.getHeaders().getContentType()) == Type.NONE) {
            String message = "Content is not of any supported types: " +
                    Arrays.stream(Type.values()).filter(x -> x != Type.NONE).collect(toList());
            log.error(message);
            throw new BadUrlException(message);
        }
    }
}
