package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.PokemonDTO;
import dtos.ResponseDTO;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import mapper.ObjectMapperFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Epic(value = "Тестирование Pokemon API")
@Execution(ExecutionMode.CONCURRENT)
public class ApiTest {

    private final String BASE_URL = "https://pokeapi.co/api/v2/pokemon/";
    protected static ThreadLocal<ObjectMapper> objectMapperInstance = new ThreadLocal<>();

    protected ObjectMapper getObjectMapper(){
        return objectMapperInstance.get();
    }

    @BeforeEach
    public void setObjectMapper() {
        objectMapperInstance.set(ObjectMapperFactory.getInstance());
    }

    @Test
    @Feature("сравнение покемонов")
    @Story(value = "сравнение покемонов rattata и pidgeotto")
    public void comparison() throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        String rattataURL = "rattata/";
        String pidgeottoURL = "pidgeotto/";

        String rattataJson = getRequestBy(rattataURL, 200);
        PokemonDTO rattataPokemon = objectMapper.readValue(rattataJson, PokemonDTO.class);

        String pidgeottoJson = getRequestBy(pidgeottoURL, 200);
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
        String json = getRequestBy("", 200);
        ResponseDTO response = objectMapper.readValue(json,ResponseDTO.class);
        int limit = Integer.parseInt(response.getNext().split("limit=")[1]); //null или нецелое число не спарсится
        Assertions.assertEquals(response.getResults().size(), limit);
        response.getResults().forEach(x-> Assertions.assertNotNull(x.getName()));
    }

    private String getRequestBy(String by, int statusCode) throws MalformedURLException {
        return RestAssured.when()
                .get(new URL(BASE_URL + by))
                .then().assertThat().statusCode(statusCode)
                .extract().asString();
    }

}
