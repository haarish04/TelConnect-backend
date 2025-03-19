package com.example.TelConnect.service;

import com.example.TelConnect.model.CustomerPlanMapping;
import com.example.TelConnect.model.ServicePlan;
import com.example.TelConnect.repository.CustomerPlanRepository;
import com.example.TelConnect.repository.ServicePlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerPlanServiceTest {

    @Mock
    private CustomerPlanRepository customerPlanRepository;

    @Mock
    private ServicePlanRepository servicePlanRepository;

    @InjectMocks
    private CustomerPlanService customerPlanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. Test case for adding a new plan when the customer has no existing plan
    @Test
    void testCreateNewCustomerPlanMapping_NewPlan() {
        CustomerPlanMapping newCustomerPlanMapping = new CustomerPlanMapping();
        newCustomerPlanMapping.setCustomerId(1L);
        newCustomerPlanMapping.setPlanId("PLAN123");

        List<CustomerPlanMapping> existingPlans = new ArrayList<>();
        when(customerPlanRepository.findByCustomerId(1L)).thenReturn(existingPlans);

        boolean result = customerPlanService.createNewCustomerPlanMapping(newCustomerPlanMapping);

        assertTrue(result);
        verify(customerPlanRepository, times(1)).save(newCustomerPlanMapping);
    }

    // 2. Test case for not adding a duplicate plan
    @Test
    void testCreateNewCustomerPlanMapping_ExistingPlan() {
        CustomerPlanMapping newCustomerPlanMapping = new CustomerPlanMapping();
        newCustomerPlanMapping.setCustomerId(1L);
        newCustomerPlanMapping.setPlanId("PLAN123");

        CustomerPlanMapping existingPlan = new CustomerPlanMapping();
        existingPlan.setCustomerId(1L);
        existingPlan.setPlanId("PLAN123");

        List<CustomerPlanMapping> existingPlans = new ArrayList<>();
        existingPlans.add(existingPlan);
        when(customerPlanRepository.findByCustomerId(1L)).thenReturn(existingPlans);

        boolean result = customerPlanService.createNewCustomerPlanMapping(newCustomerPlanMapping);

        assertFalse(result);
        verify(customerPlanRepository, never()).save(newCustomerPlanMapping);
    }


    // 4. Test case for getting customer plan status when plans exist
    @Test
    void testGetCustomerPlanStatus_WithPlans() {
        Long customerId = 1L;

        // Create and configure a CustomerPlanMapping object
        CustomerPlanMapping planMapping = new CustomerPlanMapping();
        planMapping.setPlanId("PLAN123");
        planMapping.setStatus("Active");

        // Add the CustomerPlanMapping object to a list
        List<CustomerPlanMapping> customerPlans = new ArrayList<>();
        customerPlans.add(planMapping);

        // Create and configure a ServicePlan object
        ServicePlan servicePlan = new ServicePlan();
        servicePlan.setPlanId("PLAN123");
        servicePlan.setPlanName("Test Plan");

        // Mock the behavior of repositories
        when(customerPlanRepository.findByCustomerId(customerId)).thenReturn(customerPlans);
        when(servicePlanRepository.findByPlanId("PLAN123")).thenReturn(servicePlan);

        // Call the method being tested
        List<CustomerPlanMapping> result = customerPlanService.getCustomerPlanStatus(customerId);

        // Assert the result size
        assertEquals(1, result.size());

        // Assert the fields of the first (and only) element in the result list
        CustomerPlanMapping resultPlan = result.get(0);
        assertEquals("PLAN123", resultPlan.getPlanId());
        assertEquals("Active", resultPlan.getStatus());
    }


    // 5. Test case for getting customer plan status when no plans exist
    @Test
    void testGetCustomerPlanStatus_NoPlans() {
        Long customerId = 1L;

        List<CustomerPlanMapping> customerPlans = new ArrayList<>();
        when(customerPlanRepository.findByCustomerId(customerId)).thenReturn(customerPlans);

        List<CustomerPlanMapping> result = customerPlanService.getCustomerPlanStatus(customerId);

        assertNull(result);
    }

    // 6. Test case for multiple plans under the same customer
    @Test
    void testGetCustomerPlanStatus_MultiplePlans() {
        Long customerId = 1L;

        CustomerPlanMapping plan1 = new CustomerPlanMapping();
        plan1.setPlanId("PLAN123");
        plan1.setStatus("Active");

        CustomerPlanMapping plan2 = new CustomerPlanMapping();
        plan2.setPlanId("PLAN456");
        plan2.setStatus("Expired");

        List<CustomerPlanMapping> customerPlans = new ArrayList<>();
        customerPlans.add(plan1);
        customerPlans.add(plan2);

        ServicePlan servicePlan1 = new ServicePlan();
        servicePlan1.setPlanId("PLAN123");
        servicePlan1.setPlanName("Test Plan 1");

        ServicePlan servicePlan2 = new ServicePlan();
        servicePlan2.setPlanId("PLAN456");
        servicePlan2.setPlanName("Test Plan 2");

        when(customerPlanRepository.findByCustomerId(customerId)).thenReturn(customerPlans);
        when(servicePlanRepository.findByPlanId("PLAN123")).thenReturn(servicePlan1);
        when(servicePlanRepository.findByPlanId("PLAN456")).thenReturn(servicePlan2);

        List<CustomerPlanMapping> result = customerPlanService.getCustomerPlanStatus(customerId);

        // Assert the size of the result list
        assertEquals(2, result.size());

        // Assert the first element in the list
        CustomerPlanMapping resultPlan1 = result.get(0);
        assertEquals("PLAN123", resultPlan1.getPlanId());
        assertEquals("Active", resultPlan1.getStatus());

        // Assert the second element in the list
        CustomerPlanMapping resultPlan2 = result.get(1);
        assertEquals("PLAN456", resultPlan2.getPlanId());
        assertEquals("Expired", resultPlan2.getStatus());
    }

    // 7. Test case for updating the status of an existing plan
    @Test
    void testUpdateCustomerPlanStatus_ExistingPlan() {
        Long customerId = 1L;
        String planId = "PLAN123";
        String newStatus = "Inactive";

        CustomerPlanMapping planMapping = new CustomerPlanMapping();
        planMapping.setPlanId(planId);
        planMapping.setStatus("Active");

        List<CustomerPlanMapping> customerPlans = new ArrayList<>();
        customerPlans.add(planMapping);

        when(customerPlanRepository.findByCustomerId(customerId)).thenReturn(customerPlans);

        boolean result = customerPlanService.updateCustomerPlanStatus(customerId, planId, newStatus);

        assertTrue(result);
        assertEquals(newStatus, planMapping.getStatus());
        verify(customerPlanRepository, times(1)).save(planMapping);
    }

    // 8. Test case for failing to update the status if no such plan exists
    @Test
    void testUpdateCustomerPlanStatus_NoSuchPlan() {
        Long customerId = 1L;
        String planId = "PLAN123";
        String newStatus = "Inactive";

        List<CustomerPlanMapping> customerPlans = new ArrayList<>();
        when(customerPlanRepository.findByCustomerId(customerId)).thenReturn(customerPlans);

        boolean result = customerPlanService.updateCustomerPlanStatus(customerId, planId, newStatus);

        assertFalse(result);
        verify(customerPlanRepository, never()).save(any());
    }

    // Test to get all the customer plans
    @Test
    void testGetAllCustomerPlans() {
        // Prepare test data
        CustomerPlanMapping customerPlan1 = new CustomerPlanMapping();
        customerPlan1.setCustomerId(1L);
        customerPlan1.setPlanId("plan1");

        CustomerPlanMapping customerPlan2 = new CustomerPlanMapping();
        customerPlan2.setCustomerId(2L);
        customerPlan2.setPlanId("plan2");

        // Mock repository behavior
        when(customerPlanRepository.findAll()).thenReturn(Arrays.asList(customerPlan1, customerPlan2));

        // Act
        List<CustomerPlanMapping> result = customerPlanService.getAllCustomerPlans();

        // Assert
        assertNotNull(result); // Ensure result is not null
        assertEquals(2, result.size()); // Check if the list contains 2 elements
        assertEquals(customerPlan1, result.get(0)); // Verify the first element
        assertEquals(customerPlan2, result.get(1)); // Verify the second element

        // Verify that the repository's findAll method was called once
        verify(customerPlanRepository, times(1)).findAll();
    }

    @Test
    void testGetAllCustomerPlans_EmptyList() {
        // Mock repository behavior to return an empty list
        when(customerPlanRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<CustomerPlanMapping> result = customerPlanService.getAllCustomerPlans();

        // Assert
        assertNotNull(result); // Ensure result is not null
        assertTrue(result.isEmpty()); // Check if the result is an empty list

        // Verify that the repository's findAll method was called once
        verify(customerPlanRepository, times(1)).findAll();
    }
}
