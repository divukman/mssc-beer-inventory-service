package guru.sfg.brewery.model.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeerEvent implements Serializable {
    private static final long serialVersionUID = -5013098052583709758L;

    private BeerDto beerDto;
}
