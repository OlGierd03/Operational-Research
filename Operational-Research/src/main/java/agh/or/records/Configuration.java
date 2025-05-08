package agh.or.records;

public record Configuration(
        int carCount,
        int drivingTime,
        int changeTime,
        int minLightsTime,
        int maxLightsTime,
        int generationCount,
        int populationSize,
        long seed
) {
}
