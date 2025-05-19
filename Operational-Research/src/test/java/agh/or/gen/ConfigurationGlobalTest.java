package agh.or.gen;

import agh.or.globals.ConfigurationGlobal;
import agh.or.records.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConfigurationGlobalTest {

    private final int CAR_COUNT = 10;
    private final int DRIVING_TIME = 10;
    private final int CHANGE_TIME = 15;
    private final int MIN_LIGHTS_TIME = 30;
    private final int MAX_LIGHTS_TIME = 100;
    private final int GENERATION_COUNT = 15;
    private final int POPULATION_SIZE = 120;
    private final long SEED = 420L;

    @BeforeEach
    void setUp() {
        Configuration configuration = new Configuration(
                CAR_COUNT,
                DRIVING_TIME,
                CHANGE_TIME,
                MIN_LIGHTS_TIME,
                MAX_LIGHTS_TIME,
                GENERATION_COUNT,
                POPULATION_SIZE,
                SEED
        );
        ConfigurationGlobal.setInstance(configuration);
    }

    @Test
    void testGetters() {
        assertNotNull(ConfigurationGlobal.getInstance(), "ConfigurationGlobal instance should not be null");
        assertEquals(CAR_COUNT, ConfigurationGlobal.getCarCount(), "Car count should match");
        assertEquals(DRIVING_TIME, ConfigurationGlobal.getDrivingTime(), "Driving time should match");
        assertEquals(CHANGE_TIME, ConfigurationGlobal.getChangeTime(), "Change time should match");
        assertEquals(MIN_LIGHTS_TIME, ConfigurationGlobal.getMinLightsTime(), "Min lights time should match");
        assertEquals(MAX_LIGHTS_TIME, ConfigurationGlobal.getMaxLightsTime(), "Max lights time should match");
        assertEquals(GENERATION_COUNT, ConfigurationGlobal.getGenerationCount(), "Generation count should match");
        assertEquals(POPULATION_SIZE, ConfigurationGlobal.getPopulationSize(), "Population size should match");
        assertEquals(SEED, ConfigurationGlobal.getSeed(), "Seed should match");
    }
}
