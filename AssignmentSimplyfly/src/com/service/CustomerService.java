package com.service;

import com.model.Customer;
import com.repository.CustomerRepository;

import java.sql.SQLException;
import java.util.List;

public class CustomerService {

    CustomerRepository customerRepository = new CustomerRepository();

    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    public List<Customer> getCustomersByCity(String city) throws SQLException {
        return customerRepository.getCustomersByCity(city);
    }

    public List<Customer> getAllCustomerView() {
        return customerRepository.getAllCustomerView();
    }
}