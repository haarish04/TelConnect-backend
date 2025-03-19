package com.example.TelConnect.service;

import com.example.TelConnect.model.ServicePlan;
import com.example.TelConnect.repository.ServicePlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServicePlanServiceTest {

    @Mock
    private ServicePlanRepository servicePlanRepository;

    @InjectMocks
    private ServicePlanService servicePlanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //Test creating new plan
    @Test
    void testCreatePlan() {
        ServicePlan plan = new ServicePlan();
        plan.setPlanId("PREP-TC-1234");

        // Mocking the findByPlanId operation
        when(servicePlanRepository.findByPlanId(plan.getPlanId())).thenReturn(null);

        // Mocking the save operation
        when(servicePlanRepository.save(any(ServicePlan.class))).thenReturn(plan);

        boolean result = servicePlanService.createPlan(plan);

        assertTrue(result);
        verify(servicePlanRepository, times(1)).save(any(ServicePlan.class));
    }

    //Test creating existing plan
    @Test
    void testCreatePlanWhenPlanExists() {
        ServicePlan plan = new ServicePlan();
        plan.setPlanId("PREP-TC-1234");

        // Mocking the findByPlanId operation to return an existing plan
        when(servicePlanRepository.findByPlanId(plan.getPlanId())).thenReturn(plan);

        boolean result = servicePlanService.createPlan(plan);

        assertFalse(result);
        verify(servicePlanRepository, never()).save(any(ServicePlan.class));
    }

    //Test retrieving plan
    @Test
    void testGetPlan() {
        String planId = "PREP-TC-1234";
        ServicePlan plan = new ServicePlan();
        plan.setPlanId(planId);

        // Mocking the findByPlanId operation
        when(servicePlanRepository.findByPlanId(planId)).thenReturn(plan);

        ServicePlan result = servicePlanService.getPlan(planId);

        assertNotNull(result);
        assertEquals(planId, result.getPlanId());
    }

    //Test retrieving non-existent plan
    @Test
    void testGetPlanWhenNotFound() {
        String planId = "PREP-TC-1234";

        // Mocking the findByPlanId operation to return null
        when(servicePlanRepository.findByPlanId(planId)).thenReturn(null);

        ServicePlan result = servicePlanService.getPlan(planId);

        assertNull(result);
    }

    //Test get all plans
    @Test
    void testGetAllPlans() {
        List<ServicePlan> plans = new ArrayList<>();
        ServicePlan plan1 = new ServicePlan();
        plan1.setPlanId("PREP-TC-1234");
        ServicePlan plan2 = new ServicePlan();
        plan2.setPlanId("POST-TC-5678");

        plans.add(plan1);
        plans.add(plan2);

        // Mocking the findAll operation
        when(servicePlanRepository.findAll()).thenReturn(plans);

        List<ServicePlan> result = servicePlanService.getAllPlans();

        assertEquals(2, result.size());
        assertEquals("PREP-TC-1234", result.get(0).getPlanId());
        assertEquals("POST-TC-5678", result.get(1).getPlanId());
    }

    //Test deleting plans
    @Test
    void testDeletePlan() {
        String planId = "PREP-TC-1234";

        // Mocking the deleteById operation
        doNothing().when(servicePlanRepository).deleteById(planId);

        servicePlanService.deletePlan(planId);

        // Verifying that deleteById method is called once
        verify(servicePlanRepository, times(1)).deleteById(planId);
    }

    //Test updating plan
    @Test
    void testUpdatePlan_withValidPlan() {
        // Prepare test data
        String planId = "PLAN123";
        ServicePlan existingPlan = new ServicePlan();
        existingPlan.setPlanId(planId);
        existingPlan.setPlanName("Old Plan");
        existingPlan.setPlanDescription("Old Description");
        existingPlan.setPlanDuration("30 days");
        existingPlan.setPlanPrice("100");

        ServicePlan newPlan = new ServicePlan();
        newPlan.setPlanName("New Plan");
        newPlan.setPlanDescription("New Description");
        newPlan.setPlanDuration("25 days");
        newPlan.setPlanPrice("300");

        // Mock the repository behavior
        when(servicePlanRepository.findByPlanId(planId)).thenReturn(existingPlan);

        // Act
        boolean result = servicePlanService.updatePlan(newPlan, planId);

        // Assert
        assertTrue(result); // Verify that the method returns true
        assertEquals("New Plan", existingPlan.getPlanName()); // Check if planName was updated
        assertEquals("New Description", existingPlan.getPlanDescription()); // Check if planDescription was updated
        assertEquals("25 days", existingPlan.getPlanDuration()); // Check if planDuration was updated
        assertEquals("300", existingPlan.getPlanPrice()); // Check if planPrice was updated

        // Verify that save was called on the repository
        verify(servicePlanRepository, times(1)).save(existingPlan);
    }

    //Test updating non-existing plan
    @Test
    void testUpdatePlan_withNullPlan() {
        // Prepare test data
        String planId = "PLAN123";
        ServicePlan existingPlan = new ServicePlan();
        existingPlan.setPlanId(planId);

        // Mock the repository behavior
        when(servicePlanRepository.findByPlanId(planId)).thenReturn(existingPlan);

        // Act
        boolean result = servicePlanService.updatePlan(null, planId);

        // Assert
        assertFalse(result); // Verify that the method returns false

        // Verify that save was not called since the plan is null
        verify(servicePlanRepository, never()).save(any());
    }

//    @Test
//    void testDeletePlanWhenException() {
//        String planId = "PREP-TC-1234";
//
//        // Mocking the deleteById operation to throw an exception
//        doThrow(new RuntimeException("Database error")).when(servicePlanRepository).deleteById(planId);
//
//        // You might want to handle the exception in your service method or just verify it
//        assertDoesNotThrow(() -> servicePlanService.deletePlan(planId));
//    }
}
