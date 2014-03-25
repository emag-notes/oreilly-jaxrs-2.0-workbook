package com.restfully.shop.services;

import com.restfully.shop.domain.Customer;
import com.restfully.shop.util.Marshaller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yoshimasa Tanabe
 */
public class FirstLastCustomerResource {

  private Map<String, Customer> customerDB = new ConcurrentHashMap<>();

  @GET
  @Path("{first : [a-zA-Z]+}-{last : [a-zA-Z]+}")
  @Produces(MediaType.APPLICATION_XML)
  public StreamingOutput getCustomer(@PathParam("first") String firstName,
                                              @PathParam("last")  String lastName) {
    final Customer customer = customerDB.get(firstName + "-" + lastName);
    if (customer == null) throw new NotFoundException();
    return outputStream -> Marshaller.outputCustomer(outputStream, customer);
  }

  @PUT
  @Path("{first : [a-zA-Z]+}-{last : [a-zA-Z]+}")
  @Consumes(MediaType.APPLICATION_XML)
  public void updateCustomer(@PathParam("first") String firstName,
                                        @PathParam("last")  String lastName,
                                        InputStream is) {
    Customer current = customerDB.get(firstName + "-" + lastName);
    if (current == null) throw new NotFoundException();

    Customer update = Marshaller.readCustomer(is);
    if (update.getFirstName() != null) {
      current.setFirstName(update.getFirstName());
    }
    if (update.getLastName() != null) {
      current.setLastName(update.getLastName());
    }
    if (update.getStreet() != null) {
      current.setStreet(update.getStreet());
    }
    if (update.getState() != null) {
      current.setState(update.getState());
    }
    if (update.getZip() != null) {
      current.setZip(update.getZip());
    }
    if (update.getCountry() != null) {
      current.setCountry(update.getCountry());
    }
  }

  @POST
  @Consumes(MediaType.APPLICATION_XML)
  public Response createCustomer(InputStream is) {
    Customer customer = Marshaller.readCustomer(is);
    String index = customer.getFirstName() + "-" + customer.getLastName();
    customerDB.put(index, customer);
    System.out.println("Created customer: " + index);
    return Response.created(URI.create("/customers/northamerica-db" + index)).build();
  }

  @POST
  @Path("purge")
  public void purgeAllCustomers() {
    customerDB.clear();
  }

}
