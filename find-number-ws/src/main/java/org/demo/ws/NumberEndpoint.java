package org.demo.ws;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import org.demo.service.NumberSearchService;
import org.myschema.bignumbers.ObjectFactory;
import org.myschema.bignumbers.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class NumberEndpoint {

  private static final String NAMESPACE = "http://myschema.org/bignumbers";

  private NumberSearchService numberSearchService;

  @Autowired
  public NumberEndpoint(NumberSearchService numberSearchService) {
    this.numberSearchService = numberSearchService;
  }

  /**
   * Find number web service.
   *
   * @param number to look for.
   * @return Result of number search.
   */
  @PayloadRoot(namespace = NAMESPACE, localPart = "number")
  public @ResponsePayload
  Result findNumber(@RequestPayload JAXBElement<Integer> number) {
    return numberSearchService.findNumber(number.getValue());
  }
}
