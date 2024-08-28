package com.example.TelConnect;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.TelConnect.model.Customer;
import com.example.TelConnect.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository repo;

    @Test
    public void testCreateCustomer() {
        Customer Customer = new Customer();
        Customer.setCustomerId(0L);
        Customer.setCustomerName("Admin");
        Customer.setPassword("AdminPass");
        Customer.setCustomerEmail("admin@gmail.com");
        Customer.setCustomerPhno("7897237891");
        Customer.setRole("Admin");

        Customer.setAccountCreationDate(LocalDate.now());

        Customer savedCustomer = repo.save(Customer);

        Customer existCustomer = entityManager.find(Customer.class, savedCustomer.getCustomerId());

        assertThat(Customer.getCustomerEmail()).isEqualTo(existCustomer.getCustomerEmail());
    }
}