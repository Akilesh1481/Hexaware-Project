package com.repository;

import com.model.Customer;
import com.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    DBConnection dbConnection = new DBConnection();

    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        Connection connection =  dbConnection.dbConnect();

        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL get_all_customers()}");
            ResultSet rst = callableStatement.executeQuery();

            while(rst.next()){
                int id = rst.getInt("id");
                String name = rst.getString("name");
                String city = rst.getString("city");
                int age = rst.getInt("age");
                Customer customer = new Customer(id,name,city,age);
                list.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        dbConnection.dbClose();
        return list;
    }

    public List<Customer> getCustomersByCity(String city) throws SQLException {
        List<Customer> list = new ArrayList<>();
        Connection connection =  dbConnection.dbConnect();


        CallableStatement callableStatement = connection.prepareCall("{CALL get_customers_by_city(?)}");
        callableStatement.setString(1,city);

        ResultSet rst = callableStatement.executeQuery();

        while(rst.next()){
            int id = rst.getInt("id");
            String name = rst.getString("name");
            String cityDb = rst.getString("city");
            int age = rst.getInt("age");
            Customer customer = new Customer(id,name,cityDb,age);
            list.add(customer);
        }

        dbConnection.dbClose();
        return list;
    }

    public List<Customer> getAllCustomerView() {
        List<Customer> list = new ArrayList<>();
        Connection connection =  dbConnection.dbConnect();
        try {
            PreparedStatement preparedStatement =  connection.prepareStatement("select * from customer_view");

            ResultSet rst = preparedStatement.executeQuery();

            while(rst.next()){
                int id = rst.getInt("id");
                String name = rst.getString("name");
                String city = rst.getString("city");

                Customer customer = new Customer(id,name,city);
                list.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        dbConnection.dbClose();
        return list;
    }
}