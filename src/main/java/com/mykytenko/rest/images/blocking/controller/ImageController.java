package com.mykytenko.rest.images.blocking.controller;

import com.mykytenko.rest.images.blocking.model.ResourceLocation;
import com.mykytenko.rest.images.blocking.exception.BadUrlException;
import com.mykytenko.rest.images.blocking.service.ZippedResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "image")
public class ImageController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ZippedResourceService zippedImages;

    public ImageController(ZippedResourceService zippedImages) {
        this.zippedImages = zippedImages;
    }

    @GetMapping(value = "url")
    public @ResponseBody
    ResponseEntity<byte[]> getImage(@Valid ResourceLocation url, BindingResult validationResult) {
        log.info("Received request for image: " + url.getUrl());
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new BadUrlException(String.join(";", errors));
        }
        log.debug("Start to return zipped image: " + url.getUrl());
        return ResponseEntity.ok()
                .header("Content-type", "application/zip")
                .header("Content-Disposition", "attachment; filename=\"image.zip\"")
                .body(zippedImages.fromLocation(url).getBody());

    }
}
