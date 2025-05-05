package agh.or.gen;

import agh.or.CarListGenerator;
import agh.or.Configuration;
import agh.or.Lights;
import agh.or.O;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PopulationTest {

    private Configuration configuration;
    private List<Integer> carCount;

    @BeforeEach
    void setUp() {
        configuration = new Configuration(
                Lights.LIGHT_COUNT * 5,
                10,
                15,
                30,
                100
        );

        carCount = CarListGenerator.createCars(configuration, 123456789L);

    }

    @Test
    void testPopulationConstructor() {
        int populationSize = 10;

        Population population = new Population(configuration, carCount, populationSize);

        assertNotNull(population.getIndividuals(), "Lista individuals nie powinna być null");
        assertEquals(populationSize, population.getIndividuals().size(), "Rozmiar populacji powinien być równy przekazanemu rozmiarowi");
        for (List<O> individual : population.getIndividuals()) {
            assertNotNull(individual, "Indywidual nie powinien być null");
            System.out.println("Indywidual: " + individual);
        }
    }

}
