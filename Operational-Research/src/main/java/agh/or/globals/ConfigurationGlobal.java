package agh.or.globals;

import agh.or.CarListGenerator;
import agh.or.records.Configuration;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationGlobal {
    private static List<Integer> carList;

    private static Configuration configuration;
    public static void setInstance(Configuration configuration) {
        ConfigurationGlobal.configuration = configuration;
        carList = CarListGenerator.createCars(configuration);
    }

    public static void setCarList(List<Integer> carList) {
        ConfigurationGlobal.carList = carList;
    }

    /// Getters

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static List<Integer> getCarList() {
        return new ArrayList<>(carList);
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
