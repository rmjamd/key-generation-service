package com.ramij.kgs.core.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Tools {
    public String getFullAddress(String host, String port) {
        return host + ":" + port;
    }

}
