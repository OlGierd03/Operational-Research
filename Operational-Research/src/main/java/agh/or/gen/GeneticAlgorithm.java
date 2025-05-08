package agh.or.gen;

import agh.or.globals.ConfigurationGlobal;
import agh.or.records.O;

import java.util.List;

public class GeneticAlgorithm {
    private final Population population;

    public GeneticAlgorithm() {
        this.population = new Population();
    }

    public List<O> run() {
        int generationCount = ConfigurationGlobal.getInstance().getConfiguration().generationCount();
        for (int i = 0; i < generationCount; i++) {
            population.nextGeneration();
        }
        System.out.println(population.getBest());
        return population.getBest();
    }
}
