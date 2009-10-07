package org.rest.rapa.formatter;

import java.util.Arrays;
import java.util.List;

/**
 * Represents the supported formats
 */
public enum Formats {
    XML("xml"), JSON("json");

    private final String format;

    Formats(String format) {
        this.format = format;
    }

    public String toString() {
        return format;
    }

    public static List<Formats> getSupportedFormats() {
        return Arrays.asList(Formats.values());
    }
}
