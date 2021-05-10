package guru.sfg.brewery.model.events;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Builder
@Setter
@Getter
public class NewInventoryEvent extends BeerEvent {
    private static final long serialVersionUID = -2618760829308238039L;

    public NewInventoryEvent(final BeerDto beerDto) {
       super(beerDto);
    }
}
