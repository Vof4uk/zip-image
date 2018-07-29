package com.mykytenko.rest.images.blocking.service;

import com.mykytenko.rest.images.blocking.model.ResourceItem;
import com.mykytenko.rest.images.blocking.model.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * An implementation of ZippedResourceService interface
 */
@Service
public class ZippedImageServiceImpl implements ZippedResourceService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ResourceService imageService;

    public ZippedImageServiceImpl(@Autowired ResourceService imageService) {
        this.imageService = imageService;
    }

    public ResourceItem fromLocation(ResourceLocation location) {
        ResourceItem image = imageService.fromLocation(location);
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ZipOutputStream os = new ZipOutputStream(bos)){
            ZipEntry entry = new ZipEntry(image.getName() + image.getType().getValue());
            os.putNextEntry(entry);
            os.write(image.getBody());
            os.closeEntry();
            os.close();
            return new ResourceItem(image.getName(), image.getType(), bos.toByteArray());
        } catch (IOException ex) {
            log.error("Failed to create archive", ex);
            throw new RuntimeException(ex);
        }
    }
}
