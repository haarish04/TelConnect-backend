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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository repo;

    @Autowired
    public final PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Test
    public void testCreateCustomer() {
        Customer Customer1 = new Customer();

        Customer1.setCustomerId(0L);
        Customer1.setCustomerName("telConnectAdmin");
        Customer1.setPassword(passwordEncoder().encode("adminPass"));
        Customer1.setCustomerEmail("admin@gmail.com");
        Customer1.setCustomerPhno("7897237891");
        Customer1.setRole("ADMIN");
        Customer1.setCustomerAddress("508D Eugene Trailer, North Isaias");
        Customer1.setCustomerDOB(LocalDate.parse("2024-08-28"));
        Customer1.setAccountCreationDate(LocalDate.now());

        Customer savedCustomer = repo.save(Customer1);

        Customer existCustomer = entityManager.find(Customer.class, savedCustomer.getCustomerId());
        assertThat(Customer1.getCustomerEmail()).isEqualTo(existCustomer.getCustomerEmail());
    }
}