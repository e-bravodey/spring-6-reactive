package guru.springframework.spring6reactive.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6reactive.config.DatabaseConfig;
import guru.springframework.spring6reactive.domain.Beer;
import guru.springframework.spring6reactive.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataR2dbcTest
@Import(DatabaseConfig.class)
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testCreateJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(objectMapper.writeValueAsString(getTestCustomer()));
    }

    @Test
    void saveNewCustomer() {
        customerRepository.save(getTestCustomer())
                .subscribe(beer -> {
                    System.out.println(beer.toString());
                });
    }

    public static Customer getTestCustomer() {
        return Customer.builder()
                .customerName("Space Dust")
                .build();
    }

}