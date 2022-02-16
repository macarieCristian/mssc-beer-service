package guru.springframework.msscbeerservice.web.controller;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.exception.ResourceNotFoundException;
import guru.springframework.msscbeerservice.repository.BeerRepository;
import guru.springframework.msscbeerservice.web.mapper.BeerMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    private final BeerMapper beerMapper;
    private final BeerRepository beerRepository;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable UUID beerId) {
        return ResponseEntity.ok(beerMapper.toDto(
                beerRepository.findById(beerId).orElseThrow(ResourceNotFoundException::new)
        ));
    }

    @PostMapping
    public ResponseEntity<URI> saveBeer(@Validated @RequestBody BeerDto beerDto, HttpServletRequest request) {
        Beer newBeer = beerRepository.save(beerMapper.toEntity(beerDto));

        return ResponseEntity.created(
                URI.create(
                        request.getRequestURL()
                                .append("/")
                                .append(newBeer.getId())
                                .toString()
                )).build();
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeer(@PathVariable UUID beerId, @Validated @RequestBody BeerDto beerDto) {
        beerRepository.findById(beerId).ifPresentOrElse(beer -> {
            beer.setBeerName(beerDto.getBeerName());
            beer.setBeerStyle(beerDto.getBeerStyle().name());
            beer.setPrice(beerDto.getPrice());
            beer.setUpc(beerDto.getUpc());

            beerRepository.save(beer);
        }, () -> {
            throw new ResourceNotFoundException();
        });

        return ResponseEntity.noContent().build();
    }
}
