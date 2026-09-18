package com.bootforge.customerservice.service;

import com.bootforge.commons.dto.customerservice.CreateCustomer;
import com.bootforge.commons.dto.customerservice.CustomerResponse;
import com.bootforge.commons.exception.customer.CustomerNotFoundException;
import com.bootforge.commons.exception.customer.DuplicateCustomerException;
import com.bootforge.customerservice.entity.Customer;
import com.bootforge.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponse createCustomer(CreateCustomer request) {
        if (customerRepository.existsByEmail(request.email())) {
            throw new DuplicateCustomerException("Customer already exists!");
        }
        Customer customer = Customer.builder()
                .name(request.name())
                .email(request.email())
                .phone(request.phone())
                .address(request.address())
                .build();
        Customer savedCustomer = customerRepository.save(customer);
        return toCustomerResponse(savedCustomer);
    }

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream().map(this::toCustomerResponse).toList();
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("Customer not found for customerId: " + id)
        );
        return toCustomerResponse(customer);
    }

    private CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .status(customer.getStatus())
                .build();
    }
}
