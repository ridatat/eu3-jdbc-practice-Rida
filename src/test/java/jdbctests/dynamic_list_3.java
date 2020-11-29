package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.*;

public class dynamic_list_3 {


        String dbUrl = "jdbc:oracle:thin:@3.80.189.73:1521:xe";
        String dbUsername = "hr";
        String dbPassword = "hr";
        @Test
        public void dynamic_list() throws SQLException {
            //create connection
            Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
            //create statement object
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //run query and get the result in resultset object
            ResultSet resultSet = statement.executeQuery("select first_name,last_name,salary,job_id from employees\n" +
                    "where rownum <6");

            //get the resultset object metadata
            ResultSetMetaData rsMetadata = resultSet.getMetaData();

            //list for keeping all rows a map
            List<Map<String,Object>> queryData = new ArrayList<>();


            //number of columns
            int colCount = rsMetadata.getColumnCount();

            //loop through each row
            while(resultSet.next()){
                Map<String,Object> row = new HashMap<>();

                for (int i = 1; i <=colCount; i++) {

                    row.put(rsMetadata.getColumnName(i),resultSet.getObject(i));

                }

                //add your map to your list
                queryData.add(row);
            }


            //print the result
            for (Map<String, Object> row : queryData) {
                System.out.println(row.toString());
            }




            //close all connections
            resultSet.close();
            statement.close();
            connection.close();
        }


}
