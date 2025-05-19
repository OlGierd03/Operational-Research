package agh.or;

import agh.or.globals.ConfigurationGlobal;
import agh.or.records.Configuration;

public class Main {
    public static void main(String[] args) {
        /*for (var invalidLight : LightsSets.getValids()){
            System.out.println(invalidLight);
        }
        System.out.println(LightsSets.getValids().size());*/
        long seed = System.currentTimeMillis();
        System.out.println("Seed: " + seed);
        Configuration configuration = new Configuration(
                Lights.LIGHT_COUNT * 5,
                10,
                15,
                30,
                100,
                15,
                120,
                seed
        );

        ConfigurationGlobal.setInstance(configuration);

        Simulation simulation = new Simulation(Solution.genetic());
        simulation.run(true);
    }
}