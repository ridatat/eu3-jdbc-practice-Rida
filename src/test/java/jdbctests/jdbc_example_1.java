package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;

public class jdbc_example_1 {
    String dbUrl = "jdbc:oracle:thin:@18.212.66.90:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";

    @Test
    public void test1() throws SQLException {
        //create connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement object-- we added the parameter the first thing allow us to navigate up and down in the db
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("Select * from departments");

        //how to find how many rows we have for the query
        //go to last row
        resultSet.last();
        //get the row count
        int row = resultSet.getRow();

        System.out.println(row);

        //we need to move before first row to get full list since we are at the last row rigth now.
        resultSet.beforeFirst();
        while(resultSet.next()){
            System.out.println(resultSet.getString(2));
        }

        resultSet=statement.executeQuery("select * from regions");
        while (resultSet.next()){
            System.out.println(resultSet.getString(2));
        }




        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }
    @Test
    public void MetaDataExample() throws SQLException {
        //create connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("select * from employees");

        //get the database related data inside the dbMetadata object
        DatabaseMetaData dbMetadata = connection.getMetaData();

        System.out.println("User = "+ dbMetadata.getUserName());
        System.out.println("Database Product Name = "+ dbMetadata.getDatabaseProductName());
        System.out.println("Database Product Version = "+ dbMetadata.getDatabaseProductVersion());
        System.out.println("Driver name = "+ dbMetadata.getDriverName());
        System.out.println("Driver Version = "+ dbMetadata.getDriverVersion());

        //get the resultset objecct metadata
        ResultSetMetaData rsMetadata = resultSet.getMetaData();
        //how many colums we have ?
        int columnCount = rsMetadata.getColumnCount();
        System.out.println(columnCount);

        //column names
        System.out.println("rsMetadata.getColumnClassName(1) = " + rsMetadata.getColumnName(1));
        System.out.println("rsMetadata.getColumnName(2) = " + rsMetadata.getColumnName(2));

        //print all the column names dynamically
        for (int i = 1; i < rsMetadata.getColumnCount(); i++) {
            System.out.println(rsMetadata.getColumnName(i));
        }


        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }

}
