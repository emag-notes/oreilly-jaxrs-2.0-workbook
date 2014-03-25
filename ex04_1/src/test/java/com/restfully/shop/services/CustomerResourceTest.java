package com.restfully.shop.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * @author Yoshimasa Tanabe
 */
public class CustomerResourceTest {

  final String BASE_URI = "http://localhost:8080/services/customers";
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
  public void testCreateANewCustomer() throws Exception {
    // Setup
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

    // Exercise
    response = client.target(BASE_URI).request().post(Entity.xml(xml));

    // Verify
    assertThat(response.getStatus(), is(201));
    assertThat(response.getLocation().toString(), is("http://localhost:8080/services/customers/1"));
  }

  @Test
  public void testGetCreatedCustomer() throws Exception {
    // Setup
    String createdCustomerLocation = createCustomer();

    // Exercise
    String customerXml = client.target(createdCustomerLocation).request().get(String.class);

    // Verify
    assertThat(customerXml, is(
      "<customer id=\"1\">\n" +
      "   <first-name>Bill</first-name>\n" +
      "   <last-name>Burke</last-name>\n" +
      "   <street>256 Clarendon Street</street>\n" +
      "   <city>Boston</city>\n" +
      "   <state>MA</state>\n" +
      "   <zip>02115</zip>\n" +
      "   <country>USA</country>\n" +
      "</customer>\n"));
  }

  @Test
  public void testUpdateCustomer() throws Exception {
    // Setup
    String createdCustomerLocation = createCustomer();

    // Exercise
    String updateCustomer =
      "<customer>" +
        "<first-name>William</first-name>" +
        "<last-name>Burke</last-name>" +
        "<street>256 Clarendon Street</street>" +
        "<city>Boston</city>" +
        "<state>MA</state>" +
        "<zip>02115</zip>" +
        "<country>USA</country>" +
      "</customer>";
    response = client.target(createdCustomerLocation).request().put(Entity.xml(updateCustomer));

    // Verify
    assertThat(response.getStatus(), is(204));
    response.close();
    String updatedCustomerXml = client.target(createdCustomerLocation).request().get(String.class);
    assertThat(updatedCustomerXml, is(
      "<customer id=\"1\">\n" +
        "   <first-name>William</first-name>\n" +
        "   <last-name>Burke</last-name>\n" +
        "   <street>256 Clarendon Street</street>\n" +
        "   <city>Boston</city>\n" +
        "   <state>MA</state>\n" +
        "   <zip>02115</zip>\n" +
        "   <country>USA</country>\n" +
        "</customer>\n"
    ));
  }

  @Test
  public void testPatchCustomer() throws Exception {
    // Setup
    String createdCustomerLocation = createCustomer();

    // Exercise
    String patchCustomer =
      "<customer>" +
        "<first-name>William</first-name>" +
      "</customer>";
    response = client.target(createdCustomerLocation).request().method("PATCH", Entity.xml(patchCustomer));

    // Verify
    assertThat(response.getStatus(), is(204));
    response.close();
    String updatedCustomerXml = client.target(createdCustomerLocation).request().get(String.class);
    assertThat(updatedCustomerXml, is(
      "<customer id=\"1\">\n" +
        "   <first-name>William</first-name>\n" +
        "   <last-name>Burke</last-name>\n" +
        "   <street>256 Clarendon Street</street>\n" +
        "   <city>Boston</city>\n" +
        "   <state>MA</state>\n" +
        "   <zip>02115</zip>\n" +
        "   <country>USA</country>\n" +
        "</customer>\n"
    ));
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
