package guru.springframework.msscbeerservice.bootstrap;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if (beerRepository.count() == 0) {
            beerRepository.saveAll(
                    Arrays.asList(Beer.builder()
                                    .beerName("Ursus")
                                    .beerStyle("IPA")
                                    .quantityToBrew(200)
                                    .upc(234234234234234L)
                                    .price(BigDecimal.valueOf(12.95))
                                    .build(),
                            Beer.builder()
                                    .beerName("Galaxy")
                                    .beerStyle("PALE_ALE")
                                    .quantityToBrew(200)
                                    .upc(234234234234239L)
                                    .price(BigDecimal.valueOf(10.95))
                                    .build()
                    )
            );
        }
    }

}
