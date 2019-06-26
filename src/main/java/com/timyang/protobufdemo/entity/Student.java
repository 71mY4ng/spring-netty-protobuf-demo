package com.timyang.protobufdemo.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {
  private Long id;
  private String name;
  private List<String> hobbies;
  private Map<String, Double> grades;

  public Student() {
    this.hobbies = new ArrayList<>();
    this.grades = new HashMap<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getHobbies() {
    return hobbies;
  }

  public void setHobbies(List<String> hobbies) {
    this.hobbies = hobbies;
  }

  public Map<String, Double> getGrades() {
    return grades;
  }

  public void setGrades(Map<String, Double> grades) {
    this.grades = grades;
  }
}
