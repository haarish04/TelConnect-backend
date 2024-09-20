package com.example.TelConnect.service;

import com.example.TelConnect.DTO.UpdateRequestDTO;
import com.example.TelConnect.model.Customer;
import com.example.TelConnect.DTO.RegisterCustomerDTO;
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
import java.util.Optional;

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
        RegisterCustomerDTO newCustomer = new RegisterCustomerDTO();
        newCustomer.setCustomerName("John Doe");
        newCustomer.setCustomerEmail("john.doe@example.com");
        newCustomer.setPassword("password123");
        newCustomer.setCustomerDOB(LocalDate.of(1990, 1, 1));
        newCustomer.setCustomerAddress("123 Main St");
        newCustomer.setCustomerPhno("1234567890");

        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        customerService.saveCustomer(newCustomer);

        verify(customerRepository, times(1)).save(any(Customer.class));
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
    void testGetByCustomerId_CustomerExists() {
        // Prepare test data
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setCustomerName("John Doe");

        // Mock the repository behavior
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // Act
        Customer result = customerService.getByCustomerId(customerId);

        // Assert
        assertNotNull(result); // Verify that the method returns a non-null customer
        assertEquals(customerId, result.getCustomerId()); // Check if the correct customer is returned
        assertEquals("John Doe", result.getCustomerName()); // Check the customer's name

        // Verify that findById was called once on the repository
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testGetByCustomerId_CustomerDoesNotExist() {
        // Prepare test data
        Long customerId = 2L;

        // Mock the repository behavior to return an empty Optional
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act
        Customer result = customerService.getByCustomerId(customerId);

        // Assert
        assertNull(result); // Verify that the method returns null when the customer doesn't exist

        // Verify that findById was called once on the repository
        verify(customerRepository, times(1)).findById(customerId);
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

    // Additional Test Case: Check if saveCustomer encodes the password correctly
    @Test
    void testSaveCustomer_PasswordEncoding() {
        RegisterCustomerDTO newCustomer = new RegisterCustomerDTO();
        newCustomer.setCustomerName("Alice");
        newCustomer.setCustomerEmail("alice@example.com");
        newCustomer.setPassword("plaintextpassword");

        when(passwordEncoder.encode("plaintextpassword")).thenReturn("encodedPassword");

        customerService.saveCustomer(newCustomer);

        verify(customerRepository, times(1)).save(argThat(customer ->
                "encodedPassword".equals(customer.getPassword())
        ));
    }

    @Test
    void testUpdateCustomerDetails_CustomerExists() {
        // Prepare test data
        UpdateRequestDTO updateRequestDTO = new UpdateRequestDTO();
        updateRequestDTO.setCustomerEmail("test@example.com");
        updateRequestDTO.setPassword("newPassword");
        updateRequestDTO.setCustomerAddress("New Address");
        updateRequestDTO.setCustomerDOB(LocalDate.now());

        Customer existingCustomer = new Customer();
        existingCustomer.setCustomerEmail("test@example.com");
        existingCustomer.setPassword("oldPassword");
        existingCustomer.setCustomerAddress("Old Address");
        existingCustomer.setCustomerDOB(LocalDate.now());

        // Mock repository behavior
        when(customerRepository.findByCustomerEmail(updateRequestDTO.getCustomerEmail())).thenReturn(existingCustomer);
        when(passwordEncoder.encode(updateRequestDTO.getPassword())).thenReturn("encodedPassword");

        // Act
        boolean result = customerService.updateCustomerDetails(updateRequestDTO);

        // Assert
        assertTrue(result); // Check if update was successful
        assertEquals("encodedPassword", existingCustomer.getPassword()); // Verify password encoding
        assertEquals("New Address", existingCustomer.getCustomerAddress()); // Verify address update

        // Verify that save was called once
        verify(customerRepository, times(1)).save(existingCustomer);
    }

    @Test
    void testUpdateCustomerDetails_CustomerDoesNotExist() {
        // Prepare test data
        UpdateRequestDTO updateRequestDTO = new UpdateRequestDTO();
        updateRequestDTO.setCustomerEmail("nonexistent@example.com");

        // Mock repository behavior to return null
        when(customerRepository.findByCustomerEmail(updateRequestDTO.getCustomerEmail())).thenReturn(null);

        // Act
        boolean result = customerService.updateCustomerDetails(updateRequestDTO);

        // Assert
        assertFalse(result); // Check if the method returns false when the customer is not found

        // Verify that save was never called
        verify(customerRepository, never()).save(any());
    }

}
