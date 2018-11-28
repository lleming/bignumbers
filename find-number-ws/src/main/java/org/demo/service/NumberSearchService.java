package org.demo.service;

import java.sql.SQLException;
import org.myschema.bignumbers.Result;

/**
 * Service to search number.
 */
public interface NumberSearchService {

  Result findNumber(Integer number);
}
