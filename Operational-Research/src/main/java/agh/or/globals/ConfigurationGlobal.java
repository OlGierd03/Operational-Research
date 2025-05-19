package agh.or.globals;

import agh.or.CarListGenerator;
import agh.or.records.Configuration;

import java.util.List;

public class ConfigurationGlobal {
    private static ConfigurationGlobal instance;
    private static List<Integer> carList;
    private static Configuration configuration;

    private ConfigurationGlobal(Configuration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("Configuration cannot be null");
        }

        assert configuration.changeTime() >= configuration.drivingTime();

        if (instance == null) {
            instance = this;
            ConfigurationGlobal.configuration = configuration;
            carList = CarListGenerator.createCars(configuration);
        }
    }

    public static void setInstance(Configuration configuration) {
        instance = new ConfigurationGlobal(configuration);

    }

    public static void freeInstance() {
        instance = null;
    }

    /// Getters

    public static ConfigurationGlobal getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ConfigurationSingleton is not initialized. Call setInstance(Configuration configuration) first.");
        }
        return instance;
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static List<Integer> getCarList() {
        return carList;
    }

    public static int getCarCount() {
        return configuration.carCount();
    }

    public static int getDrivingTime() {
        return configuration.drivingTime();
    }

    public static int getChangeTime() {
        return configuration.changeTime();
    }

    public static int getMinLightsTime() {
        return configuration.minLightsTime();
    }

    public static int getMaxLightsTime() {
        return configuration.maxLightsTime();
    }

    public static int getGenerationCount() {
        return configuration.generationCount();
    }

    public static int getPopulationSize() {
        return configuration.populationSize();
    }

    public static long getSeed() {
        return configuration.seed();
    }

}
