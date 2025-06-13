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

//        carCount = CarListGenerator.createCars(configuration);
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

    @Test
    void seedTest() {
        Population population1 = new Population();
        Population population2 = new Population();

        population1.setParentSelectionType(ParentSelectionType.TOURNAMENT);
        population2.setParentSelectionType(ParentSelectionType.TOURNAMENT);


        for (int i = 0; i < ConfigurationGlobal.getGenerationCount(); i++) {
            population1.nextGeneration();
            population2.nextGeneration();
            assertEquals(population1.getIndividuals().size(), population2.getIndividuals().size(), "Rozmiary populacji powinny być takie same");
        }

    }

}
