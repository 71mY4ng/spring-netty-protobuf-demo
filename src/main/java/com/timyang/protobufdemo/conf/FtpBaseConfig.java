package com.timyang.protobufdemo.conf;

public class FtpBaseConfig {
  private String profileName;
  private String host;
  private Integer port;
  private String login;
  private String password;

  public String getProfileName() {
    return profileName;
  }

  public void setProfileName(String profileName) {
    this.profileName = profileName;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "FtpBaseConfig{" +
      "profileName='" + profileName + '\'' +
      ", host='" + host + '\'' +
      ", port=" + port +
      ", login='" + login + '\'' +
      ", password='" + password + '\'' +
      '}';
  }
}
