package agh.or.gen;

import agh.or.Configuration;
import agh.or.O;

import java.util.List;

public class GeneticAlgorithm {
    private static final int GENERATIONS_NUM = 2;
    private static final int POPULATION_SIZE = 20;
    private final Population population;

    public GeneticAlgorithm(Configuration configuration, List<Integer> carCount) {
        this.population = new Population(configuration, carCount, POPULATION_SIZE);
    }

    public List<O> run() {
        for (int i = 0; i < GENERATIONS_NUM; i++) {
            population.nextGeneration();
        }
        return population.getBest();
    }
}
