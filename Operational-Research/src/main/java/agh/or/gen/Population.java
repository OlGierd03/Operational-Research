package agh.or.gen;

import agh.or.Configuration;
import agh.or.O;
import agh.or.ORandomizer;
import agh.or.Solution;

import java.util.*;

public class Population {

    private final List<List<O>> individuals = new ArrayList<>();
    private final Configuration configuration;
    private final List<Integer> carCount;

    public Population(Configuration configuration, List<Integer> carCount, int size) {
        this.configuration = configuration;
        this.carCount = carCount;
        for (int i = 0; i < size; i++) {
            this.individuals.add(ORandomizer.randomize(configuration));
        }
    }

    public void nextGeneration() {
        List<List<O>> parents = selectParents();
        List<List<O>> offspring = crossoverInGeneration(parents);
        for (List<O> child : offspring) {
            mutateIndividual(child, 0.1);
        }
        replacePopulation(offspring, parents);
    }

    private List<List<O>> crossoverInGeneration(List<List<O>> parents) {
        List<List<O>> copy = new ArrayList<>(parents);
        List<List<O>> offspring = new ArrayList<>();
        while (!copy.isEmpty()) {
            Collections.shuffle(copy);
            List<O> child = crossoverParents(copy.getFirst(), copy.getLast());
            List<O> child2 = crossoverParents(copy.getFirst(), copy.getLast());
            copy.removeFirst();
            if(copy.isEmpty()) break;
            copy.removeLast();
            offspring.add(child);
            offspring.add(child2);
        }
        return offspring;
    }

    private List<O> crossoverParents(List<O> parent1, List<O> parent2) {
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
        int score = 0;
        for (O o : individual) {
            score += o.time();
            score += configuration.changeTime();
        }
        if (!Solution.willEnd(individual, configuration, carCount)){
            score += 10000;
        }
        return score;
    }

    private List<List<O>> selectParents() {
        return individuals.stream()
                .sorted((o1, o2) -> Integer.compare(getScore(o1), getScore(o2))) // sort ascending
                .limit(individuals.size() / 2)
                .toList();
    }

    private void mutateIndividual(List<O> individual, double mutationChance) {
        for (int i = 0; i < individual.size(); i++) {
            if (Math.random() <= mutationChance) {
                individual.set(i, ORandomizer.randomize(configuration).get(0));
            }
        }
    }

    private void replacePopulation(List<List<O>> newGeneration, List<List<O>> parents) {
        individuals.clear();
        individuals.addAll(newGeneration);
        individuals.addAll(parents);
    }

    public List<List<O>> getIndividuals() {
        return individuals;
    }

    public List<O> getBest() {
        return individuals.stream()
                .min(Comparator.comparingInt(this::getScore))
                .orElseThrow(() -> new RuntimeException("No individuals in population"));
    }
}
