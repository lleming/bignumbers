package org.demo.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.demo.db.model.ResultCode;
import org.myschema.bignumbers.Result;
import org.myschema.bignumbers.Result.FileNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NumberSearchInDatabaseServiceImpl implements NumberSearchService {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(NumberSearchInDatabaseServiceImpl.class);

  private static final String query = "select name from file f where exists "
      + "(select 1 from number_files nf where nf.file_id = f.id and nf.number = ?)";

  private final DataSource dataSource;
  private Connection connection;
  private PreparedStatement statement;

  @Autowired
  public NumberSearchInDatabaseServiceImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Result findNumber(Integer number) {
    Result result = new Result();
    try {
      PreparedStatement statement = getStatement();
      statement.setBigDecimal(1, new BigDecimal(number));
      ResultSet rs = statement.executeQuery();
      List<String> files = new ArrayList<>();
      while (rs.next()) {
        files.add(rs.getString("name"));
      }
      if (files.isEmpty()) {
        result.setCode(ResultCode.NOT_FOUND.codeValue());
        result.setError(ResultCode.NOT_FOUND.message());
      } else {
        result.setCode(ResultCode.OK.codeValue());
        result.setFileNames(new FileNames());
      }
    } catch (SQLException | IOException e) {
      result.setError(e.getMessage());
      result.setCode(ResultCode.ERROR.message());
      LOGGER.error("Error is occurred", e);
    }
    return result;
  }

  private PreparedStatement getStatement() throws SQLException, IOException {
    if (statement == null || statement.isClosed()) {
      Connection connection = dataSource.getConnection();
      statement = connection.prepareStatement(query);
      statement.setPoolable(true);
    }
    return statement;
  }
}
