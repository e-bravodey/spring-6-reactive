package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.mappers.CustomerMapper;
import guru.springframework.spring6reactive.model.BeerDTO;
import guru.springframework.spring6reactive.model.CustomerDTO;
import guru.springframework.spring6reactive.repositories.BeerRepositoryTest;
import guru.springframework.spring6reactive.repositories.CustomerRepositoryTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static guru.springframework.spring6reactive.repositories.CustomerRepositoryTest.getTestCustomer;
import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class CustomerControllerTest {

    @Autowired
    WebTestClient webTestClient;


    @Test
    @Order(999)
    void testDeleteById() {
        webTestClient.delete().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus()
                .isNoContent();

    }

    @Test
    @Order(4)
    void testPatchExistingCustomer() {
        webTestClient.patch().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(CustomerRepositoryTest.getTestCustomer()), CustomerDTO.class)
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    @Order(5)
    void testUpdateExistingCustomer() {
        webTestClient.put().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(CustomerRepositoryTest.getTestCustomer()), CustomerDTO.class)
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    @Order(3)
    void testCreateNewCustomer() {
        webTestClient.post().uri(CustomerController.CUSTOMER_PATH)
                .body(Mono.just(CustomerRepositoryTest.getTestCustomer()), CustomerDTO.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectHeader() .location("http://localhost:8080/api/v2/customer/4");
    }

    @Test
    @Order(2)
    void testGetCustomerById() {
        webTestClient.get().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(CustomerDTO.class);
    }

    @Test
    @Order(1)
    void testListCustomers() {
        webTestClient.get().uri(CustomerController.CUSTOMER_PATH )
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(3);

    }
}