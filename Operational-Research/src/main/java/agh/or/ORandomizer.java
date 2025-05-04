package agh.or;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ORandomizer {
    private static Random random = new Random();

    public static void setSeed(long seed) {
        random = new Random(seed);
    }

    public static List<O> randomize(Configuration configuration) {
        List<O> result = new ArrayList<O>();
        var validSet = LightsSets.getValids();

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
