package com.timyang.protobufdemo.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

@ConfigurationProperties(prefix = "public.task-conf.create-new-world")
public class CreateNewWorldConfig {
  private String baseUrl;
  private String ftpProfile;

  @Resource
  private FtpConfig ftpConfig;

  private FtpBaseConfig ftpBaseConfig;

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public String getFtpProfile() {
    return ftpProfile;
  }

  public void setFtpProfile(String ftpProfile) {
    this.ftpProfile = ftpProfile;


    assert ftpConfig != null;

    if (ftpConfig.getMap().containsKey(this.ftpProfile)) {

      Optional<Map.Entry<String, FtpBaseConfig>> op = ftpConfig.getMap()
        .entrySet()
        .stream()
        .filter(entry -> entry.getKey().equals(this.ftpProfile))
        .findFirst();

      this.ftpBaseConfig = op.isPresent() ? op.get().getValue() : new FtpBaseConfig();
    }
  }

  public FtpBaseConfig getFtpBaseConfig() {
    return ftpBaseConfig;
  }

  public void setFtpBaseConfig(FtpBaseConfig ftpBaseConfig) {
    this.ftpBaseConfig = ftpBaseConfig;
  }

  @Override
  public String toString() {
    return "CreateNewWorldConfig{" +
      "baseUrl='" + baseUrl + '\'' +
      ", ftpProfile='" + ftpProfile + '\'' +
      ", ftpBaseConfig=" + ftpBaseConfig +
      '}';
  }
}
