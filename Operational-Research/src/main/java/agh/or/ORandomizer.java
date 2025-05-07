package agh.or;

import agh.or.records.Configuration;
import agh.or.records.O;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ORandomizer {

    public static List<O> randomize(Configuration configuration) {
        List<O> result = new ArrayList<O>();
        var validSet = LightsSets.getValids();
        Random random = new Random(configuration.seed());

        while (!Solution.willEnd(result)){
            var lightsToAdd = validSet.stream()
                    .skip(random.nextInt(validSet.size()))
                    .findFirst()
                    .get();
            result.add(new O(
                    lightsToAdd, random.nextInt(
                            configuration.minLightsTime(),
                    configuration.maxLightsTime() + 1
                    )
            ));

        }

        return result;
    }
}
