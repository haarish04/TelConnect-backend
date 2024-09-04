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
import java.util.List;
import java.util.Optional;

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

    @Test
    void testGetCustomerPlanStatus_WithPlans() {
        Long customerId = 1L;

        CustomerPlanMapping planMapping = new CustomerPlanMapping();
        planMapping.setPlanId("PLAN123");
        planMapping.setStatus("Active");

        List<CustomerPlanMapping> customerPlans = new ArrayList<>();
        customerPlans.add(planMapping);

        ServicePlan servicePlan = new ServicePlan();
        servicePlan.setPlanId("PLAN123");
        servicePlan.setPlanName("Test Plan");

        when(customerPlanRepository.findByCustomerId(customerId)).thenReturn(customerPlans);
        when(servicePlanRepository.findByPlanId("PLAN123")).thenReturn(servicePlan);

        String result = customerPlanService.getCustomerPlanStatus(customerId);

        assertEquals("Plan: Test Plan, Status: Active\n", result);
    }

    @Test
    void testGetCustomerPlanStatus_NoPlans() {
        Long customerId = 1L;

        List<CustomerPlanMapping> customerPlans = new ArrayList<>();
        when(customerPlanRepository.findByCustomerId(customerId)).thenReturn(customerPlans);

        String result = customerPlanService.getCustomerPlanStatus(customerId);

        assertEquals("", result);
    }

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
}

