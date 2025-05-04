package agh.or;

import agh.or.gen.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
    private final List<O> values;

    public static Solution random(Configuration configuration) {
        return new Solution(ORandomizer.randomize(configuration));
    }

    public static Solution genetic(Configuration configuration) {
        return new Solution(new GeneticAlgorithm(configuration).run());
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

    public static boolean willEnd(List<O> values) {
        int lightsFlag = 0;
        for (O o : values) {
            lightsFlag |= o.lights().getState();
        }
        return lightsFlag == ((1 << Lights.LIGHT_COUNT) - 1);
    }

    public boolean willEnd() {
        return willEnd(values);
    }
}
