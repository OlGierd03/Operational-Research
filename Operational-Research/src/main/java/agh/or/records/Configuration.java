package agh.or.records;

public record Configuration(
        int carCount,
        int drivingTime,
        int changeTime,
        int minLightsTime,
        int maxLightsTime,
        long seed
) {
}
