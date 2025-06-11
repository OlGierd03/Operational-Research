package agh.or.gen;

import agh.or.globals.ConfigurationGlobal;
import agh.or.records.O;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GeneticAlgorithm {
    private final Population population;

    public GeneticAlgorithm() {
        this.population = new Population();
    }

    public List<O> run() {
        try(FileWriter file = new FileWriter("bestPops.txt")) {
            try(FileWriter fileAvg = new FileWriter("avgPop.txt")) {
                file.write("0\t%d\n".formatted(population.getBestScore()));
                fileAvg.write("0\t%.2f\n".formatted(population.getAverageScore()));
                for (int i = 0; i < ConfigurationGlobal.getGenerationCount(); i++) {
                    population.nextGeneration();
                    file.write("%d\t%d\n".formatted(i + 1, population.getBestScore()));
                    fileAvg.write("%d\t%.2f\n".formatted(i + 1, population.getAverageScore()));
                }
            }
            catch (Exception ignored) {}
        } catch (Exception ignored) {}
        System.out.println("Best after:");
        System.out.println(population.getBest());
        return population.getBest();
    }
}
