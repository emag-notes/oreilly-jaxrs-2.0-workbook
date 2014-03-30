package com.restfully.shop.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Customer implements Serializable {

  private int id;
  private String firstName;
  private String lastName;
  private String street;
  private String city;
  private String state;
  private String zip;
  private String country;

}
