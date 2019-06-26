package com.timyang.protobufdemo.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfiguration {

  @Bean
  public FtpConfig ftpConfig() {
    return new FtpConfig();
  }

  @Bean
  public CreateNewWorldConfig createNewWorldConfig() {
    return new CreateNewWorldConfig();
  }
}
