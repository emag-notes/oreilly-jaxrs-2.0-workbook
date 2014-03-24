package com.restfully.shop.util;

import com.restfully.shop.domain.Customer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author Yoshimasa Tanabe
 */
public class Marshaller {

  public static void outputCustomer(OutputStream os, Customer cust) throws IOException {
    PrintStream writer = new PrintStream(os);
    writer.println("<customer id=\"" + cust.getId() + "\">");
    writer.println("   <first-name>" + cust.getFirstName() + "</first-name>");
    writer.println("   <last-name>" + cust.getLastName() + "</last-name>");
    writer.println("   <street>" + cust.getStreet() + "</street>");
    writer.println("   <city>" + cust.getCity() + "</city>");
    writer.println("   <state>" + cust.getState() + "</state>");
    writer.println("   <zip>" + cust.getZip() + "</zip>");
    writer.println("   <country>" + cust.getCountry() + "</country>");
    writer.println("</customer>");
  }

  public static Customer readCustomer(InputStream is) {
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document doc = builder.parse(is);
      Element root = doc.getDocumentElement();
      Customer cust = new Customer();
      if (root.getAttribute("id") != null && !root.getAttribute("id").trim().equals(""))
        cust.setId(Integer.valueOf(root.getAttribute("id")));
      NodeList nodes = root.getChildNodes();
      for (int i = 0; i < nodes.getLength(); i++) {
        Element element = (Element) nodes.item(i);
        if (element.getTagName().equals("first-name")) {
          cust.setFirstName(element.getTextContent());
        } else if (element.getTagName().equals("last-name")) {
          cust.setLastName(element.getTextContent());
        } else if (element.getTagName().equals("street")) {
          cust.setStreet(element.getTextContent());
        } else if (element.getTagName().equals("city")) {
          cust.setCity(element.getTextContent());
        } else if (element.getTagName().equals("state")) {
          cust.setState(element.getTextContent());
        } else if (element.getTagName().equals("zip")) {
          cust.setZip(element.getTextContent());
        } else if (element.getTagName().equals("country")) {
          cust.setCountry(element.getTextContent());
        }
      }
      return cust;
    } catch (Exception e) {
      throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
    }
  }

}
