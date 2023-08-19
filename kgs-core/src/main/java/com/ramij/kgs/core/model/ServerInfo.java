package com.ramij.kgs.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ServerInfo {
    private final String ipAddress;
    private final String port;
}
