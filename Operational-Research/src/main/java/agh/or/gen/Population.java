package agh.or.gen;

import agh.or.Simulation;
import agh.or.globals.ConfigurationGlobal;
import agh.or.records.Configuration;
import agh.or.records.O;
import agh.or.ORandomizer;
import agh.or.Solution;

import java.util.*;

public class Population {

    private final List<List<O>> individuals = new ArrayList<>();
    private final Configuration configuration;
    private Random random;
    private static ParentSelectionType parentSelectionType;
    private final List<Integer> carCount;

    public Population() {
        this.configuration = ConfigurationGlobal.getConfiguration();
        this.carCount = ConfigurationGlobal.getCarList();
        this.random = new Random(configuration.seed());

        for (int i = 0; i < configuration.populationSize() ; i++) {
            this.individuals.add(ORandomizer.randomize(configuration));
        }
    }

    public static void setParentSelectionType(ParentSelectionType parentSelectionType) {
        Population.parentSelectionType = parentSelectionType;
    }

    public void nextGeneration() {
        List<List<O>> parents = selectParents();
        List<List<O>> offspring = crossoverInGeneration(parents);
        for (List<O> child : offspring) {
            mutateIndividual(child, 0.1);
            Solution.fixList(child, configuration);
        }
        replacePopulation(offspring, parents);
    }

    private List<List<O>> crossoverInGeneration(List<List<O>> parents) {
        List<List<O>> copy = new ArrayList<>(parents);
        List<List<O>> offspring = new ArrayList<>();
        while (!copy.isEmpty()) {
            Collections.shuffle(copy, random);
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
        int genomeLength = random.nextInt(Math.min(parent1.size(), parent2.size()), Math.max(parent1.size(), parent2.size()) + 1);
        for (int i = 0; i < genomeLength; i++) {
            offspring.add(crossoverGens(parent1, parent2, i));
        }

        return offspring;//Solution.fixList(offspring, configuration);
    }

    private O crossoverGens(List<O> parent1, List<O> parent2, int i) {
        var maxParent = parent1.size() < parent2.size() ? parent2 : parent1;

        try {
            if (random.nextInt(100) > 50) {
                return parent1.get(i);
            } else {
                return parent2.get(i);
            }
        }
        catch (IndexOutOfBoundsException e) {
            return maxParent.get(i);
        }
    }

    public int getBestScore() {
        return getScore(getBest());
    }

    public double getAverageScore() {
        return individuals.stream()
                .map(this::getScore)
                .map(Double::valueOf)
                .reduce(Double::sum)
                .orElse(Double.NaN) / individuals.size();
    }

    private int getScore(List<O> individual) {
        int score = 0;
        Solution solution = new Solution(new ArrayList<>(individual));

        boolean doubleFlag = !solution.willEnd();
        if(doubleFlag) {
            solution.fix(ConfigurationGlobal.getConfiguration());
        }

        assert solution.willEnd();
        score = new Simulation(solution).run(false);

//        System.out.println("%d\t%d".formatted(individual.size(), solution.size()));

        return doubleFlag ? score * 2 : score;
    }

    private List<List<O>> selectParents() {
        return switch (Population.parentSelectionType) {
            case ROULETTE -> selectParentsRoulette();
            case TOURNAMENT -> selectParentsTournament();
            case RANKING -> selectParentsTopHalf();
        };
    }

    private List<List<O>> selectParentsTopHalf() {
        return individuals.stream()
                .sorted((o1, o2) -> Integer.compare(getScore(o1), getScore(o2))) // sort ascending
                .limit(individuals.size() / 2)
                .toList();
    }

    private List<List<O>> selectParentsTournament() {
        List<List<O>> parents = new ArrayList<>();
        int parentsCount = individuals.size() / 2;
        int tournamentSize = Math.max(2, individuals.size() / 10);

        for (int i = 0; i < parentsCount; i++) {
            // losowe osobniki biorą udział w turnieju
            List<List<O>> tournament = new ArrayList<>();
            for (int j = 0; j < tournamentSize; j++) {
                int randomIndex = random.nextInt(individuals.size());
                tournament.add(individuals.get(randomIndex));
            }

            // zwycięza turnieju
            List<O> winner = tournament.stream()
                    .min(Comparator.comparingInt(this::getScore))
                    .orElseThrow(() -> new RuntimeException("Tournament selection failed"));

            parents.add(winner);
        }

        return parents;
    }

    private List<List<O>> selectParentsRoulette() {
        List<List<O>> parents = new ArrayList<>();
        int parentsCount = individuals.size() / 2;

        List<Double> fitnessValues = new ArrayList<>();
        double maxScore = individuals.stream()
                .mapToInt(this::getScore)
                .max()
                .orElse(1);

        double totalFitness = 0.0;
        for (List<O> individual : individuals) {
            double fitness = maxScore - getScore(individual) + 1.0; // stała, żeby zawsze dodatnie
            fitnessValues.add(fitness);
            totalFitness += fitness;
        }

        // pętla ruletki
        for (int i = 0; i < parentsCount; i++) {
            double randomValue = random.nextDouble() * totalFitness;
            double cumulativeFitness = 0.0;

            for (int j = 0; j < individuals.size(); j++) {
                cumulativeFitness += fitnessValues.get(j);
                if (cumulativeFitness >= randomValue) {
                    parents.add(individuals.get(j));
                    break;
                }
            }
        }

        return parents;
    }

    private void mutateIndividual(List<O> individual, double mutationChance) {
        for (int i = 0; i < individual.size(); i++) {
            if (random.nextDouble() <= mutationChance) {
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
                .map(min -> Solution.fixList(min, configuration))
                .orElseThrow(() -> new RuntimeException("No individuals in population"));
    }
}
