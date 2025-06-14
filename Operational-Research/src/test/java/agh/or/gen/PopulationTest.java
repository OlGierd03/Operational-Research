package agh.or.gen;

import agh.or.CarListGenerator;
import agh.or.globals.ConfigurationGlobal;
import agh.or.records.Configuration;
import agh.or.Lights;
import agh.or.records.O;
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
                100,
                15,
                120,
                0L
        );

        ConfigurationGlobal.setInstance(configuration);
    }

    @Test
    void testPopulationConstructor() {
        Population population = new Population();

        assertNotNull(population.getIndividuals(), "Lista individuals nie powinna być null");
        for (List<O> individual : population.getIndividuals()) {
            assertNotNull(individual, "Indywidual nie powinien być null");
            System.out.println("Indywidual: " + individual);
        }
    }



}
