package com.mykytenko.rest.images.blocking.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.Arrays;

/**
 * A RestTemplate based implementation of ResourceDownload interface.
 * @see ResourceDownload
 */
@Component
public class ResourceDownloadImpl implements ResourceDownload {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final RestTemplate restTemplate;

    public ResourceDownloadImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * @throws RuntimeException if http request fails
     */
    @Override
    public ResponseEntity<byte[]> downloadResource(URL url, MediaType... types) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(types));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            return restTemplate.exchange(
                    url.toString(),
                    HttpMethod.GET, entity, byte[].class, "1");
        } catch (RestClientException e) {
            log.error("Download failed for: " + url.toString(), e);
            throw new RuntimeException(e);
        }

    }
}
