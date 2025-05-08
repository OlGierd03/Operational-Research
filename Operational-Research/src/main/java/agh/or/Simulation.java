package agh.or;

import agh.or.globals.ConfigurationGlobal;
import agh.or.records.Configuration;

import java.util.List;

public class Simulation {
    private final Solution solution;

    public Simulation(Solution solution) {
        this.solution = solution;
    }

    private int carCountSum() { // ilość samochodów nadal czekających na przejazd
        return ConfigurationGlobal.getCarList().stream().reduce(0, Integer::sum);
    }

    private void printCars() {
        System.out.println("Cars on lanes:");
        for(int i = 0; i != Lights.LIGHT_COUNT; ++i) {
            System.out.printf("%d: %d%n", i, ConfigurationGlobal.getCarList().get(i));
        }
    }

    public void run(boolean print) {
        assert solution.willEnd();
        if (!solution.willEnd()) {
            System.out.println("Solution will not end");
            return;
        }


        int time = 0;
        List<Integer> carList = ConfigurationGlobal.getCarList();
        Configuration configuration = ConfigurationGlobal.getConfiguration();

        if(print) {
            System.out.println("Solution:");
            for(var o : solution.getValues()){
                System.out.printf("%s; %d%n", o.lights().toString(), o.time());
            }

            System.out.println();
            printCars();
        }

        for (int i = 0; carCountSum() != 0; i = (i + 1) % solution.size()) {
            Lights lights = solution.get(i).lights();
            int lightsTime = solution.get(i).time();

            int carsOnGreen = lightsTime / configuration.drivingTime();
            for(var lane : lights.on()) {
                carList.set(lane, Math.max(carList.get(lane) - carsOnGreen, 0));
            }
            time += lightsTime;

            if(lightsTime % configuration.drivingTime() != 0) {
                for(var lane : lights.on()) {
                    carList.set(lane, Math.max(carList.get(lane) - 1, 0));
                }
            }
            time += configuration.changeTime();

            if(print) {
                System.out.printf("\nt = %d%n", time);
                System.out.printf("Lights:\n%s; %d%n", lights.toString(), lightsTime);
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
