package com.akhp.springbootwebfluxdemo.handler;

import com.akhp.springbootwebfluxdemo.dao.CustomerDao;
import com.akhp.springbootwebfluxdemo.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler /*implements HandlerFunction<ServerResponse>*/ {

    @Autowired
    private  CustomerDao customerDao;

    public Mono<ServerResponse> loadCustomers(ServerRequest serverRequest){
        Flux<Customer> customers = customerDao.getCustomerList();
        return ServerResponse.ok().body(customers, Customer.class);
    }

    public Mono<ServerResponse> getCustomer(ServerRequest serverRequest) {
        int customerId = Integer.valueOf(serverRequest.pathVariable("id"));
        Mono<Customer> customer = customerDao.getCustomerList().filter(c->c.getId() == customerId).next();
        return ServerResponse.ok().body(customer, Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest serverRequest) {
        Mono<Customer> customerMono = serverRequest.bodyToMono(Customer.class);
        Mono<String> saveResponse = customerMono.map(dto -> dto.getId() + ":" + dto.getName());
        return ServerResponse.ok().body(saveResponse, String.class);
    }
}
