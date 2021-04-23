package guru.sfg.beer.inventory.service.services.jms;

import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import guru.sfg.common.events.BeerDto;
import guru.sfg.common.events.NewInventoryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewInventoryListener {

    private final BeerInventoryRepository beerInventoryRepository;


    @JmsListener( destination = JmsConfig.QUEUE_NEW_INVENTORY )
    //@Transactional
    public void listen(NewInventoryEvent newInventoryEvent) {
        log.debug("New inventory event... " + newInventoryEvent.toString());

        final BeerDto beerDto = newInventoryEvent.getBeerDto();
        beerInventoryRepository.save(
          BeerInventory.builder()
                  .beerId(beerDto.getId())
                  .upc(beerDto.getUpc())
                  .quantityOnHand(beerDto.getQuantityOnHand())
                  .build()
        );
    }
}
