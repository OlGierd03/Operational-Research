package agh.or;

import agh.or.globals.ConfigurationGlobal;
import agh.or.records.Configuration;

public class Main {
    public static void main(String[] args) {

        Configuration configuration = new Configuration(
                Lights.LIGHT_COUNT * 5,
                10,
                15,
                30,
                100,
                15,
                120,
                420L
        );

        ConfigurationGlobal.setInstance(configuration);

        Simulation simulation = new Simulation(Solution.genetic());
        simulation.run(true);
    }
}