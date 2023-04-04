package dtos;

import lombok.Data;

import java.util.List;

@Data
public class PokemonDTO {

    private String name;
    private int weight;
    private List<AbilitiesDTO> abilities;

}
