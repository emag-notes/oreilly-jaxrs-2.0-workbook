package com.restfully.shop.domain;

import lombok.Data;

/**
 * @author Yoshimasa Tanabe
 */
@Data
public class Customer {

  private int id;
  private String firstName;
  private String lastName;
  private String street;
  private String city;
  private String state;
  private String zip;
  private String country;

}
