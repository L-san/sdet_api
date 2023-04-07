package tests;

import api.PokemonAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.PokemonDTO;
import dtos.ResponseDTO;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import mapper.ObjectMapperFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.IOException;

@Epic(value = "Тестирование Pokemon API")
@Execution(ExecutionMode.CONCURRENT)
public class ApiTest {

    protected static ThreadLocal<ObjectMapper> objectMapperInstance = new ThreadLocal<>();
    protected static ThreadLocal<PokemonAPI> pokemonAPIInstance = new ThreadLocal<>();

    protected ObjectMapper getObjectMapper() {
        return objectMapperInstance.get();
    }

    protected PokemonAPI getPokemonAPI() {
        return pokemonAPIInstance.get();
    }

    @BeforeEach
    public void setObjectMapper() {
        objectMapperInstance.set(ObjectMapperFactory.getInstance());
        pokemonAPIInstance.set(PokemonAPI.builder().build());
    }

    @Test
    @Feature("сравнение покемонов")
    @Story(value = "сравнение покемонов rattata и pidgeotto")
    public void comparison() throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        PokemonAPI pokemonAPI = getPokemonAPI();

        String rattataJson = pokemonAPI.doGetByPokemonName("rattata", 200);
        PokemonDTO rattataPokemon = objectMapper.readValue(rattataJson, PokemonDTO.class);

        String pidgeottoJson = pokemonAPI.doGetByPokemonName("pidgeotto", 200);
        PokemonDTO pidgeottoPokemon = objectMapper.readValue(pidgeottoJson, PokemonDTO.class);

        Assertions.assertTrue(rattataPokemon.getWeight() < pidgeottoPokemon.getWeight());
        Assertions.assertFalse(rattataPokemon.getAbilities().stream().noneMatch(x -> x.getAbility().getName().equals("run-away")));
        Assertions.assertTrue(pidgeottoPokemon.getAbilities().stream().noneMatch(x -> x.getAbility().getName().equals("run-away")));

    }

    @Test
    @Feature("проверка списка покемонов")
    @Story(value = "проверка списка покемонов")
    public void checkPokemonList() throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        PokemonAPI pokemonAPI = getPokemonAPI();
        int limit = 15;
        String json = pokemonAPI.doGetPokemon(limit, 200);
        ResponseDTO response = objectMapper.readValue(json, ResponseDTO.class);
        int actualLimit = Integer.parseInt(response.getNext().split("limit=")[1]); //null или нецелое число не спарсится
        Assertions.assertEquals(response.getResults().size(), limit);
        response.getResults().forEach(x -> Assertions.assertNotNull(x.getName()));
        Assertions.assertEquals(limit, actualLimit);
    }

}
