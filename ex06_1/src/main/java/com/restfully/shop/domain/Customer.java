package com.restfully.shop.domain;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "customer")
public class Customer {

  @XmlAttribute
  private int id;
  @XmlElement(name = "first-name")
  private String firstName;
  @XmlElement(name = "last-name")
  private String lastName;
  @XmlElement
  private String street;
  @XmlElement
  private String city;
  @XmlElement
  private String state;
  @XmlElement
  private String zip;
  @XmlElement
  private String country;

}
