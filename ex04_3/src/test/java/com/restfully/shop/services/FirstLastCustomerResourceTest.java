package com.restfully.shop.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yoshimasa Tanabe
 */
public class FirstLastCustomerResourceTest {

  final String BASE_URI = "http://localhost:8080/services/customers/northamerica-db";
  Client client;
  Response response;

  @Before
  public void setUp() throws Exception {
    client = ClientBuilder.newClient();
  }

  @After
  public void tearDown() throws Exception {
    if (response != null) {
      response.close();
    }
    client.target(BASE_URI + "/purge").request().post(null);
    client.close();
  }

  @Test
  public void testGetCustomerFirstLast() throws Exception {
    // Setup
    createCustomer();

    // Exercise
    String customerXml = client.target(BASE_URI + "/Bill-Burke").request().get(String.class);

    // Verify
    assertThat(customerXml, is(
        "<customer id=\"0\">\n" +
        "   <first-name>Bill</first-name>\n" +
        "   <last-name>Burke</last-name>\n" +
        "   <street>256 Clarendon Street</street>\n" +
        "   <city>Boston</city>\n" +
        "   <state>MA</state>\n" +
        "   <zip>02115</zip>\n" +
        "   <country>USA</country>\n" +
        "</customer>\n"));
  }

  private String createCustomer() {
    String xml =
      "<customer>" +
        "<first-name>Bill</first-name>" +
        "<last-name>Burke</last-name>" +
        "<street>256 Clarendon Street</street>" +
        "<city>Boston</city>" +
        "<state>MA</state>" +
        "<zip>02115</zip>" +
        "<country>USA</country>" +
      "</customer>";
    response = client.target(BASE_URI).request().post(Entity.xml(xml));
    String createdCustomerLocation = response.getLocation().toString();
    response.close();
    return createdCustomerLocation;
  }

}
