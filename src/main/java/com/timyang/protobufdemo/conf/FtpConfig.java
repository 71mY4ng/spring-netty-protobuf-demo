package com.timyang.protobufdemo.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "public.ftp")
public class FtpConfig {
  private final Map<String, FtpBaseConfig> map = new HashMap<>();

  public Map<String, FtpBaseConfig> getMap() {
    return map;
  }
}
