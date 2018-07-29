package com.mykytenko.rest.images.blocking.client;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.URL;

/**
 *  The class is designed to provide client with resources by http address.
 */
public interface ResourceDownload {

    /**
     * Performs GET request to the url provided to get response with body defined in types.
     *
     * @param url   expected url of the resource
     * @param types MIME types provided and expected by the method client
     * @return response to request with body presented as byte[]
     */
    ResponseEntity<byte[]> downloadResource(URL url, MediaType... types);
}
