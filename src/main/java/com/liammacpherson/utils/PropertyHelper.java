package com.liammacpherson.utils;

public final class PropertyHelper {

    public static boolean propertyEquals(String environmentVariable, String defaultValue, String equalsValue) {
        return System.getProperty(environmentVariable, defaultValue).equalsIgnoreCase(equalsValue);
    }

}
