package com.travelers.util;

import java.util.UUID;

public class FileUtils {

    public static String getStoredLocation(String fileName, String location) {
        return location + UUID.randomUUID() + extractExt(fileName);
    }

    private static String extractExt(String fileName) {
        int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos);
    }

}
