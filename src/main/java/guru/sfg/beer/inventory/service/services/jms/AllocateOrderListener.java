package guru.sfg.beer.inventory.service.services.jms;

import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.services.AllocationService;
import guru.sfg.brewery.model.events.AllocateOrderRequest;
import guru.sfg.brewery.model.events.AllocateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AllocateOrderListener {

    private final AllocationService allocationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener( destination = JmsConfig.QUEUE_ALLOCATE_ORDER )
    public void listen(AllocateOrderRequest allocateOrderRequest) {
       log.debug("Allocate order request received over JMS for order id: " + allocateOrderRequest.getBeerOrderDto().getId());

       boolean isAllocated = false;
       boolean errorOccured = false;

       try {
           isAllocated = allocationService.allocateOrder(allocateOrderRequest.getBeerOrderDto());
       } catch (Exception e) {
           log.error("Allocation error: " + e.getMessage() + "[ " + e + "]");
           errorOccured = true;
       }

       final AllocateOrderResult allocateOrderResult = AllocateOrderResult.builder()
               .beerOrderDto(allocateOrderRequest.getBeerOrderDto())
               .pendingInventory(isAllocated)
               .allocationError(errorOccured)
               .build();

       log.debug("Order allocated: " + isAllocated + ", error occured: " + errorOccured);
       jmsTemplate.convertAndSend(JmsConfig.QUEUE_ALLOCATE_ORDER_RESULT, allocateOrderResult);
    }

}
