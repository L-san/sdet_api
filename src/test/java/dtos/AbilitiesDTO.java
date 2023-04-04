package dtos;

import lombok.Data;

@Data
public class AbilitiesDTO {

    private AbilityDTO ability;
    private boolean is_hidden;
    private int slot;

}
