package agh.or;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {
    private Configuration configuration;
    private List<Integer> carCount;
    private Solution solution;

    private void createCars() {
        Random rand = new Random();
        for(int i = 0; i != configuration.carCount(); ++i) {
            var place = rand.nextInt(Lights.LIGHT_COUNT);
            carCount.set(place, carCount.get(place) + 1);
        }
    }

    public Simulation(Configuration configuration, Solution solution) {
        this.configuration = configuration;
        assert configuration.changeTime() >= configuration.drivingTime();

        this.carCount = new ArrayList<>();
        this.solution = solution;
        for(int i = 0; i != 12; ++i){
            carCount.add(0);
        }
        this.createCars();
    }

    private int carCountSum() {
        return carCount.stream().reduce(0, Integer::sum);
    }

    private void printCars() {
        System.out.println("Cars on lanes:");
        for(int i = 0; i != Lights.LIGHT_COUNT; ++i) {
            System.out.println("%d: %d".formatted(i, carCount.get(i)));
        }
    }

    public void run(boolean print) {
        assert solution.willEnd();

        int time = 0;

        if(print) {
            System.out.println("Solution:");
            for(var o : solution.getValues()){
                System.out.println("%s; %d".formatted(o.lights().toString(), o.time()));
            }

            System.out.println();
            printCars();
        }

        for (int i = 0; carCountSum() != 0; i = (i + 1) % solution.size()) {
            Lights lights = solution.get(i).lights();
            int lightsTime = solution.get(i).time();

            int carsOnGreen = lightsTime / configuration.drivingTime();
            for(var lane : lights.on()) {
                carCount.set(lane, Math.max(carCount.get(lane) - carsOnGreen, 0));
            }
            time += lightsTime;

            if(lightsTime % configuration.drivingTime() != 0) {
                for(var lane : lights.on()) {
                    carCount.set(lane, Math.max(carCount.get(lane) - 1, 0));
                }
            }
            time += configuration.changeTime();

            if(print) {
                System.out.println("\nt = %d".formatted(time));
                System.out.println("Lights:\n%s; %d".formatted(lights.toString(), lightsTime));
                printCars();
            }
        }
    }

//    public Simulation copy() {
//        Simulation newSimulation = new Simulation(this.configuration, false);
//        for(int i = 0; i != 12; ++i){
//            newSimulation.carCount.set(i, carCount.get(i).intValue());
//        }
//        return newSimulation;
//    }
}
