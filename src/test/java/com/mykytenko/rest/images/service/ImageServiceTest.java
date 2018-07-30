package com.mykytenko.rest.images.service;

import com.mykytenko.rest.images.blocking.client.ResourceDownloadImpl;
import com.mykytenko.rest.images.blocking.exception.BadUrlException;
import com.mykytenko.rest.images.blocking.model.ResourceItem;
import com.mykytenko.rest.images.blocking.model.ResourceLocation;
import com.mykytenko.rest.images.blocking.service.CommonImagesService;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;

@RunWith(JMockit.class)
public class ImageServiceTest {
    @Injectable
    private ResourceDownloadImpl httpClient;
    @Tested
    private CommonImagesService imageService;


    private URL validUrl;
    private byte[] body;

    @Before
    public void setup() throws MalformedURLException {
        this.validUrl = new URL("http://any.com");
        this.body = new byte[]{1};
    }

    @Test(expected = BadUrlException.class)
    public void notFound404Handle() {
        new Expectations() {{
            httpClient.downloadResource(withAny(validUrl));
            result = ResponseEntity.notFound().build();
        }};

        ResourceLocation resourceLocation = new ResourceLocation();
        resourceLocation.setUrl(validUrl.toString());
        imageService.fromLocation(resourceLocation);
    }

    @Test
    public void found200Handle() {
        new Expectations() {{
            httpClient.downloadResource(withAny(validUrl));
            result = ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(body);
        }};

        ResourceLocation resourceLocation = new ResourceLocation();
        resourceLocation.setUrl(validUrl.toString());
        ResourceItem result = imageService.fromLocation(resourceLocation);

        Assert.assertEquals(body, result.getBody());
    }

    @Test(expected = BadUrlException.class)
    public void badTypeHandle() {
        new Expectations() {{
            httpClient.downloadResource(withAny(validUrl));
            result = ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(body);
        }};

        ResourceLocation resourceLocation = new ResourceLocation();
        resourceLocation.setUrl(validUrl.toString());

    }
}
