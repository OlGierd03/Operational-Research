package agh.or;

import agh.or.globals.ConfigurationGlobal;
import agh.or.records.Configuration;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Simulation {
    private final Solution solution;
    private List<Integer> carList;

    public Simulation(Solution solution) {
        this.solution = solution;
    }

    private int carCountSum() { // ilość samochodów nadal czekających na przejazd
        return carList.stream().reduce(0, Integer::sum);
    }

    private void printCars() {
        System.out.println("Cars on lanes:");
        for (int i = 0; i != Lights.LIGHT_COUNT; ++i) {
            System.out.printf("%d: %d%n", i, carList.get(i));
        }
    }

    public int run(boolean print) {
        assert solution.willEnd();
        if (!solution.willEnd()) {
            System.out.println("Solution will not end");
            System.out.println(solution.fix(ConfigurationGlobal.getConfiguration()).willEnd());
            System.out.println(solution.getValues());
            return -1;
        }

        int time = 0;
        carList = new ArrayList<>(ConfigurationGlobal.getCarList());

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
            for (var lane : lights.on()) {
                carList.set(lane, Math.max(carList.get(lane) - carsOnGreen, 0));
            }
            time += lightsTime;

            if (lightsTime % configuration.drivingTime() != 0) {
                for (var lane : lights.on()) {
                    carList.set(lane, Math.max(carList.get(lane) - 1, 0));
                }
            }
            time += configuration.changeTime();

            if (print) {
                System.out.printf("\nt = %d%n", time);
                System.out.printf("Lights:\n%s; %d%n", lights.toString(), lightsTime);
                printCars();
            }
        }
        if(print) {
            System.out.println("t = %d".formatted(time));
        }

        return time;
    }

}
