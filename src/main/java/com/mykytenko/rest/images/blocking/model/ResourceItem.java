package com.mykytenko.rest.images.blocking.model;

import org.springframework.http.MediaType;

import java.util.Arrays;

/**
 * Class container for the resource body and properties
 */
public class ResourceItem {
    public ResourceItem(String name, Type type, byte[] body) {
        this.name = name;
        this.type = type;
        this.body = body;
    }

    public ResourceItem() {
    }

    /**
     * Name of the resource
     */
    private String name;

    /**
     * Resource type
     */
    private Type type;

    /**
     * Contents of the resource
     */
    private byte[] body;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    /**
     * Enumeration of supported resource types
     */
    public enum Type {
        JPEG(".jpeg", MediaType.IMAGE_JPEG),
        PNG(".png", MediaType.IMAGE_PNG),
        GIF(".gif", MediaType.IMAGE_GIF),
        NONE("", MediaType.ALL);

        private final String value;
        private final MediaType mediaType;

        Type(String value, MediaType mediaType) {
            this.value = value;
            this.mediaType = mediaType;
        }

        public String getValue() {
            return value;
        }

        public MediaType getMediaType() {
            return mediaType;
        }

        public static Type fromMediaType(MediaType mediaType) {
            return Arrays.stream(values())
                    .filter(x -> x.getMediaType().equals(mediaType))
                    .findFirst()
                    .orElse(NONE);
        }
    }
}
