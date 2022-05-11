package com.company;

public class Member implements iMember{

  public Member(String name, int age) {
    this.name = name;
    this.age = age;

  }

  private int findSu

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int age) {
    this.age = age;
  }

  private String name;
  private int age;
  private boolean active;
  private int subscription;
  private Discipline crawl;
  private Discipline backCrawl;
  private Discipline breastStroke;
  private Discipline butterfly;

  @Override
  public double getSubscription() {
    return subscription;
  }


}
