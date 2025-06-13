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
            // check if every individual is the same
            List<List<O>> individuals1  = population1.getIndividuals();
            List<List<O>> individuals2 = population2.getIndividuals();
            for (int j = 0; j < individuals1.size(); j++) {
                List<O> individual1 = individuals1.get(j);
                List<O> individual2 = individuals2.get(j);
                assertEquals(individual1.size(), individual2.size(), "Rozmiary indywidualnych nie są takie same");
                for (int k = 0; k < individual1.size(); k++) {
                    assertEquals(individual1.get(k), individual2.get(k), "Indywidualy różnią się na pozycji " + k);
                }
            }
        }

    }

}
