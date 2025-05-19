package agh.or;

import agh.or.gen.GeneticAlgorithm;
import agh.or.globals.ConfigurationGlobal;
import agh.or.records.Configuration;
import agh.or.records.O;

import java.util.*;

public class Solution {
    private List<O> values;
    private static Random rng;

    public static Solution random(Configuration configuration) {
        return new Solution(ORandomizer.randomize(configuration));
    }

    public static Solution genetic() {
        return new Solution(new GeneticAlgorithm().run());
    }

    public Solution(List<O> values) {
        this.values = values;
    }

    public List<O> getValues() {
        return Collections.unmodifiableList(values);
    }

    public O get(int i) {
        return values.get(i);
    }

    public int size() {
        return values.size();
    }

    private static Set<Integer> getMissingList(List<O> values) {
        Set<Integer> lanes = new HashSet<>();
        for(int i = 0; i != Lights.LIGHT_COUNT; ++i){
            lanes.add(i);
        }
        for(O o : values){
            o.lights().on().forEach(lanes::remove);
        }
        return lanes;
    }

    private Set<Integer> getMissing() {
        Set<Integer> lanes = new HashSet<>();
        for(int i = 0; i != Lights.LIGHT_COUNT; ++i){
            lanes.add(i);
        }
        for(O o : values){
            o.lights().on().forEach(lanes::remove);
        }
        return lanes;
    }

    public static boolean willEnd(List<O> values) {
        int lightsFlag = 0;
        for (O o : values) {
            lightsFlag |= o.lights().getState();
        }
        return lightsFlag == ((1 << Lights.LIGHT_COUNT) - 1);
    }

    public static boolean willEnd(List<O> values, Configuration configuration, List<Integer> carCount) {
        List<Integer> cars = new ArrayList<>();
        for (int i = 0; i < Lights.LIGHT_COUNT; ++i) {
            cars.add(0);
        }
        for(O o : values) {
            for (int i = 0; i < Lights.LIGHT_COUNT; ++i) {
                if (o.lights().get(i) == 1) {
                    cars.set(i, cars.get(i) + o.time());
                }
            }
        }

        for (int i = 0; i < Lights.LIGHT_COUNT; ++i) {
            if (cars.get(i) < carCount.get(i) * configuration.drivingTime()) {
                return false;
            }
        }
        return true;
    }

    public boolean willEnd() {
        return willEnd(values);
    }

    public static List<O> fixList(List<O> values, Configuration configuration) {
        var missing = getMissingList(values);
        var valids = LightsSets.getValids();
        if(rng == null){
            rng = new Random(configuration.seed());
        }

        while (!missing.isEmpty()) {
            Lights bestSeq = null;
            int bestCoverage = 0;

            for(var seq : valids){
                Set<Integer> seqSet = new HashSet<>(seq.on());
                seqSet.retainAll(missing);
                int coverage = seqSet.size();

                if(coverage > bestCoverage){
                    bestCoverage = coverage;
                    bestSeq = seq;
                }
            }
            values.add(new O(bestSeq, rng.nextInt(
                    configuration.minLightsTime(),
                    configuration.maxLightsTime() + 1
            )));
            assert bestSeq != null;
            bestSeq.on().forEach(missing::remove);

        }

        return values;
    }

    public Solution fix(Configuration configuration) {
        values = fixList(values, configuration);

        return this;
    }
}
