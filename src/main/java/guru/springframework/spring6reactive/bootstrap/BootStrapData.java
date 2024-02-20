package guru.springframework.spring6reactive.bootstrap;

import guru.springframework.spring6reactive.domain.Beer;
import guru.springframework.spring6reactive.domain.Customer;
import guru.springframework.spring6reactive.repositories.BeerRepository;
import guru.springframework.spring6reactive.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by jt, Spring Framework Guru.
 */
@RequiredArgsConstructor
@Component
public class BootStrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();

        beerRepository.count().subscribe(beerCount -> {
            System.out.println("Beer count is: " + beerCount);
        });

        customerRepository.count().subscribe(customerCount -> {
           System.out.println("Customer count is: "+ customerCount);
        });
    }

    private void loadCustomerData() {
        customerRepository.count().subscribe(count -> {
            if (count ==0){
                Customer customer1 = Customer.builder()
                        .customerName("Peter Pan")
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();
                Customer customer2 = Customer.builder()
                        .customerName("Wendy")
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();
                Customer customer3 = Customer.builder()
                        .customerName("Nemo")
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();
                customerRepository.save(customer1).subscribe();
                customerRepository.save(customer2).subscribe();
                customerRepository.save(customer3).subscribe();
            }

        });
    }

    private void loadBeerData() {
        beerRepository.count().subscribe(count -> {
            if (count == 0) {
                Beer beer1 = Beer.builder()
                        .beerName("Galaxy Cat")
                        .beerStyle("Pale Ale")
                        .upc("12356")
                        .price(new BigDecimal("12.99"))
                        .quantityOnHand(122)
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                Beer beer2 = Beer.builder()
                        .beerName("Crank")
                        .beerStyle("Pale Ale")
                        .upc("12356222")
                        .price(new BigDecimal("11.99"))
                        .quantityOnHand(392)
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                Beer beer3 = Beer.builder()
                        .beerName("Sunshine City")
                        .beerStyle("IPA")
                        .upc("12356")
                        .price(new BigDecimal("13.99"))
                        .quantityOnHand(144)
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                beerRepository.save(beer1).subscribe();
                beerRepository.save(beer2).subscribe();
                beerRepository.save(beer3).subscribe();
            }
        });
    }
}
