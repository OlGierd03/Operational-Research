package agh.or;

public class Main {
    public static void main(String[] args) {
        /*for (var invalidLight : LightsSets.getValids()){
            System.out.println(invalidLight);
        }
        System.out.println(LightsSets.getValids().size());*/
        Configuration configuration = new Configuration(
                Lights.LIGHT_COUNT * 5,
                10,
                15,
                30,
                100
        );

        Simulation simulation = new Simulation(configuration, Solution.random(configuration));
        simulation.run(true);
    }
}