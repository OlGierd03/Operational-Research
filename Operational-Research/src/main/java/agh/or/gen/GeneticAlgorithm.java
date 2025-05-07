package agh.or.gen;

import agh.or.records.Configuration;
import agh.or.records.O;

import java.util.List;

public class GeneticAlgorithm {
    private static final int GENERATIONS_NUM = 15;
    private static final int POPULATION_SIZE = 120;
    private final Population population;

    public GeneticAlgorithm(Configuration configuration, List<Integer> carCount) {
        this.population = new Population(configuration, carCount, POPULATION_SIZE);
    }

    public List<O> run() {
        for (int i = 0; i < GENERATIONS_NUM; i++) {
            population.nextGeneration();
        }
        System.out.println(population.getBest());
        return population.getBest();
    }
}
