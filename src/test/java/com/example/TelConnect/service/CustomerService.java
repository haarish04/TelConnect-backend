package com.example.TelConnect.service;

import com.example.TelConnect.model.Customer;
import com.example.TelConnect.model.RegisterCustomer;
import com.example.TelConnect.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCustomer() {
        RegisterCustomer newcustomer= new RegisterCustomer();
        newcustomer.setCustomerName("John Doe");
        newcustomer.setCustomerEmail("john.doe@example.com");
        newcustomer.setPassword(passwordEncoder.encode("password123"));
        newcustomer.setCustomerDOB(LocalDate.of(1990, 1, 1));
        newcustomer.setCustomerAddress("123 Main St");

        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        customerService.saveCustomer(newcustomer);

//        assertEquals("encodedPassword", newcustomer.getPassword());

//        verify(customerRepository, times(1)).save(newcustomer);
    }

    @Test
    void testGetByCustomerEmail() {
        Customer customer = new Customer();
        customer.setCustomerEmail("john.doe@example.com");

        when(customerRepository.findByCustomerEmail("john.doe@example.com")).thenReturn(customer);

        Customer result = customerService.getByCustomerEmail("john.doe@example.com");

        assertNotNull(result);
        assertEquals("john.doe@example.com", result.getCustomerEmail());
    }

    @Test
    void testAuthenticateCustomer_Success() {
        Customer customer = new Customer();
        customer.setCustomerEmail("john.doe@example.com");
        customer.setPassword("encodedPassword");

        when(customerRepository.findByCustomerEmail("john.doe@example.com")).thenReturn(customer);
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);

        int result = customerService.authenticateCustomer("john.doe@example.com", "password123");

        assertEquals(1, result);
    }

    @Test
    void testAuthenticateCustomer_WrongPassword() {
        Customer customer = new Customer();
        customer.setCustomerEmail("john.doe@example.com");
        customer.setPassword("encodedPassword");

        when(customerRepository.findByCustomerEmail("john.doe@example.com")).thenReturn(customer);
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        int result = customerService.authenticateCustomer("john.doe@example.com", "wrongPassword");

        assertEquals(0, result);
    }

    @Test
    void testAuthenticateCustomer_EmailNotFound() {
        when(customerRepository.findByCustomerEmail("john.doe@example.com")).thenReturn(null);

        int result = customerService.authenticateCustomer("john.doe@example.com", "password123");

        assertEquals(-1, result);
    }

    @Test
    void testFindAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        Customer customer1 = new Customer();
        customer1.setCustomerName("John Doe");
        customer1.setCustomerEmail("john.doe@example.com");

        Customer customer2 = new Customer();
        customer2.setCustomerName("Jane Doe");
        customer2.setCustomerEmail("jane.doe@example.com");

        customers.add(customer1);
        customers.add(customer2);

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.findAllCustomers();

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getCustomerName());
        assertEquals("Jane Doe", result.get(1).getCustomerName());
    }

    @Test
    void testDeleteCustomer_Success() {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCustomerEmail("john.doe@example.com");

        when(customerRepository.findByCustomerEmail("john.doe@example.com")).thenReturn(customer);

        boolean result = customerService.deleteCustomer("john.doe@example.com");

        assertTrue(result);
        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCustomer_NotFound() {
        when(customerRepository.findByCustomerEmail("john.doe@example.com")).thenReturn(null);

        boolean result = customerService.deleteCustomer("john.doe@example.com");

        assertFalse(result);
        verify(customerRepository, never()).deleteById(anyLong());
    }
}
