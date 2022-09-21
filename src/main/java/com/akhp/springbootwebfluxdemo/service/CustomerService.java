package com.akhp.springbootwebfluxdemo.service;

import com.akhp.springbootwebfluxdemo.dao.CustomerDao;
import com.akhp.springbootwebfluxdemo.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@Slf4j
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> loadAllCustomers(){
        long start = System.currentTimeMillis();
        List<Customer> customers= customerDao.getCustomers();
        long end = System.currentTimeMillis();
        log.info("Total execution time: "+ (end-start));
        return customers;
    }

    public Flux<Customer> loadAllCustomersStream() {
        long start = System.currentTimeMillis();
        Flux<Customer> customers= customerDao.getCustomersStream();
        long end = System.currentTimeMillis();
        log.info("Total execution time: "+ (end-start));
        return customers;

    }
}
