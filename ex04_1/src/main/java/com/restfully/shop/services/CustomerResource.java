package com.restfully.shop.services;

import com.restfully.shop.domain.Customer;
import com.restfully.shop.util.Marshaller;
import org.ieft.annotations.PATCH;

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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Yoshimasa Tanabe
 */
@Path("/customers")
public class CustomerResource {

  private Map<Integer, Customer> customerDB = new ConcurrentHashMap<>();
  private AtomicInteger idCounter = new AtomicInteger();

  @POST
  @Consumes(MediaType.APPLICATION_XML)
  public Response createCustomer(InputStream is) {
    Customer customer = Marshaller.readCustomer(is);
    customer.setId(idCounter.incrementAndGet());
    customerDB.put(customer.getId(), customer);
    System.out.println("Created customer: " + customer.getId());
    return Response.created(URI.create("/customers/" + customer.getId())).build();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_XML)
  public StreamingOutput getCustomer(@PathParam("id") int id) {
    final Customer customer = customerDB.get(id);
    if (customer == null) {
      throw new NotFoundException();
    }
    return outputStream -> Marshaller.outputCustomer(outputStream, customer);
  }

  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_XML)
  public void updateCustomer(@PathParam("id") int id, InputStream is) {
    Customer update = Marshaller.readCustomer(is);
    Customer current = customerDB.get(id);
    if (current == null) throw new NotFoundException();

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

  @PATCH
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_XML)
  public void patchCustomer(@PathParam("id") int id, InputStream is) {
    updateCustomer(id, is);
  }

  @POST
  @Path("purge")
  public void purgeAllCustomers() {
    idCounter.set(0);
    customerDB.clear();
  }

}
