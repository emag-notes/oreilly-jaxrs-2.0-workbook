package com.restfully.shop.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class InjectionTest {

  private static final String BASE_URI = "http://localhost:8180/services";

  private static Client client;

  @BeforeClass
  public static void initClient() {
    client = ClientBuilder.newClient();
  }

  @AfterClass
  public static void closeClient() {
    client.close();
  }

  @Test
  public void testCarResource() throws Exception {
    System.out.println("**** CarResource Via @MatrixParam ***");
    String car = client.target(BASE_URI + "/cars/matrix/mercedes/e55;color=black/2006").request().get(String.class);
    System.out.println(car);

    System.out.println("**** CarResource Via PathSegment ***");
    car = client.target(BASE_URI + "/cars/segment/mercedes/e55;color=black/2006").request().get(String.class);
    System.out.println(car);

    System.out.println("**** CarResource Via PathSegments ***");
    car = client.target(BASE_URI + "/cars/segments/mercedes/e55/amg/year/2006").request().get(String.class);
    System.out.println(car);

    System.out.println("**** CarResource Via PathSegment ***");
    car = client.target(BASE_URI + "/cars/uriinfo/mercedes/e55;color=black/2006").request().get(String.class);
    System.out.println(car);
  }

  @Test
  public void testCustomerResource() throws Exception {
    System.out.println("**** CustomerResource No Query params ***");
    String customer = client.target(BASE_URI +  "/customers").request().get(String.class);
    System.out.println(customer);

    System.out.println("**** CustomerResource With Query params ***");
    String list = client.target(BASE_URI + "/customers")
      .queryParam("start", "1")
      .queryParam("size", "3")
      .request().get(String.class);
    System.out.println(list);

    System.out.println("**** CustomerResource With UriInfo and Query params ***");
    list = client.target(BASE_URI + "/customers/uriinfo")
      .queryParam("start", "2")
      .queryParam("size", "2")
      .request().get(String.class);
    System.out.println(list);
  }
}
