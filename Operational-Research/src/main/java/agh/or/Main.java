package agh.or;

import agh.or.globals.ConfigurationGlobal;
import agh.or.records.Configuration;

public class Main {
    public static void main(String[] args) throws Exception {
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
                100,
                120,
                seed
        );

        ConfigurationGlobal.setInstance(configuration);

        Solution solution = Solution.genetic();
        if(solution.willEnd()){
            Simulation simulation = new Simulation(solution);
            System.out.println(simulation.run(true));
        }
    }
}