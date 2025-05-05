package agh.or.gen;

import agh.or.Configuration;
import agh.or.O;
import agh.or.ORandomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Population {

    private final List<List<O>> individuals = new ArrayList<>();
    private final Configuration configuration;

    public Population(Configuration configuration, int size) {
        this.configuration = configuration;
        for (int i = 0; i < size; i++) {
            this.individuals.add(ORandomizer.randomize(configuration));
        }
    }

    public void nextGeneration() {
        List<List<O>> parents = selectParents();
        List<List<O>> offspring = crossoverInGeneration(parents);
        for (List<O> child : offspring) {
            mutateIndividual(child);
        }
        replacePopulation(offspring);
    }

    private List<List<O>> crossoverInGeneration(List<List<O>> parents) {
        List<List<O>> copy = new ArrayList<>(parents);
        List<List<O>> offspring = new ArrayList<>();
        while (!copy.isEmpty()) {
            Collections.shuffle(copy);
            List<O> child = crossoverParents(copy.getFirst(), copy.getLast());
            copy.removeFirst();
            copy.removeLast();
            offspring.add(child);
        }
        return offspring;
    }

    private List<O> crossoverParents(List<O> parent1, List<O> parent2) {
        // Placeholder: TODO: krzyżowanie rodziców
        List<O> offspring = new ArrayList<>();
        for (int i = 0; i < Math.min(parent1.size(), parent2.size()); i++) {
            offspring.add(crossoverGens(parent1.get(i), parent2.get(i)));
        }
        return offspring;
    }

    private O crossoverGens(O gen1, O gen2) {
        Random rand = new Random();
        if (rand.nextInt(100) > 60){
            return gen1;
        }
        else {
            return gen2;
        }
    }

    private int getScore(List<O> individual) {
        // Placeholder: TODO: implementacja funkcji kosztu
        int score = 0;
        for (O o : individual) {
            score += o.time();
            score += configuration.changeTime();
        }
        // TODO: dopierdolić karę za nieopróżnienie skrzyżowania, obniżyć gdy się udało
        return score;
    }

    private List<List<O>> selectParents() {
        // Placeholder: TODO: selekcja rodziców (np. turniej, ruletka)
        return individuals.stream()
                .sorted((o1, o2) -> Integer.compare(getScore(o2), getScore(o1))) // sort descending
                .limit(individuals.size() / 2)
                .toList();
    }

    private void mutateIndividual(List<O> individual) {
        // Placeholder: TODO: mutacja potomstwa
    }

    private void replacePopulation(List<List<O>> newGeneration) {
        individuals.clear();
        individuals.addAll(newGeneration);
    }

    public List<List<O>> getIndividuals() {
        return individuals;
    }

    public List<O> getBest() {
        return individuals.stream()
                .max((o1, o2) -> Integer.compare(getScore(o1), getScore(o2)))
                .orElseThrow(() -> new RuntimeException("No individuals in population"));
    }
}
