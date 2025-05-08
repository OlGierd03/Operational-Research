package agh.or;

import agh.or.gen.GeneticAlgorithm;
import agh.or.globals.ConfigurationGlobal;
import agh.or.records.Configuration;
import agh.or.records.O;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
    private final List<O> values;

    public static Solution random(Configuration configuration) {
        return new Solution(ORandomizer.randomize(configuration));
    }

    public static Solution genetic() {
        Configuration configuration = ConfigurationGlobal.getInstance().getConfiguration();
        List<Integer> carCount = ConfigurationGlobal.getInstance().getCarList();
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
}
