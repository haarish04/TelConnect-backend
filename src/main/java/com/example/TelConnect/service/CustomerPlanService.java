package com.example.TelConnect.service;

import com.example.TelConnect.model.CustomerPlanMapping;
import com.example.TelConnect.repository.CustomerPlanRepository;
import org.springframework.stereotype.Service;
import com.example.TelConnect.repository.ServicePlanRepository;
import com.example.TelConnect.model.ServicePlan;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerPlanService {

    private final CustomerPlanRepository customerPlanRepository;
    private final ServicePlanRepository servicePlanRepository;

    public CustomerPlanService(CustomerPlanRepository customerPlanRepository, ServicePlanRepository servicePlanRepository){
        this.customerPlanRepository=customerPlanRepository;
        this.servicePlanRepository=servicePlanRepository;
    }

    public boolean createNewCustomerPlanMapping(CustomerPlanMapping newCustomerPlanMapping){
        List<CustomerPlanMapping> customerPlans = customerPlanRepository.findByCustomerId(newCustomerPlanMapping.getCustomerId());
        //If customer already has plans
        if (!customerPlans.isEmpty()) {
            for (CustomerPlanMapping customerPlan : customerPlans) {
                //If customer already has the same plan
                if (Objects.equals(customerPlan.getPlanId(), newCustomerPlanMapping.getPlanId()))
                    return false;
            }
        }
        customerPlanRepository.save(newCustomerPlanMapping);
        return true;
    }

    public List<CustomerPlanMapping> getCustomerPlanStatus(Long customerId) {
        List<CustomerPlanMapping> customerPlans = customerPlanRepository.findByCustomerId(customerId);

        //If customer has plans
        if (!customerPlans.isEmpty()) {
            return customerPlans;
        } else {
            return null;
        }
    }

    public boolean updateCustomerPlanStatus(Long customerId, String planId, String status){
        List<CustomerPlanMapping> customerPlans = customerPlanRepository.findByCustomerId(customerId);
        //If customer has the specified plan
        if (!customerPlans.isEmpty()) {
            for (CustomerPlanMapping customerPlan : customerPlans) {
                if(customerPlan.getPlanId().equals(planId)) {
                    customerPlan.setStatus(status);
                    customerPlanRepository.save(customerPlan);
                    return true;
                }

            }
        }
        return false;
    }

    public List<CustomerPlanMapping> getAllCustomerPlans(){
        return customerPlanRepository.findAll();
    }

}
