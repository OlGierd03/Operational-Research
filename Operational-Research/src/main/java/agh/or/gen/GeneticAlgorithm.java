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

        List<Integer> carList = ConfigurationGlobal.getCarList();

        for (int i = 0; i < carList.size(); i++) {
            System.out.println("Lane " + i + ": " +carList.get(i) + " cars");
        }

        System.out.println("Best found:");
        System.out.println("[");
        for (Object individual : population.getBest()) {
            System.out.println(" " + individual + ",");
        }
        System.out.println("]");
        return population.getBest();
    }
}
