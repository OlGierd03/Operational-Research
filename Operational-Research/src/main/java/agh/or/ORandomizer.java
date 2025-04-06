package agh.or;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ORandomizer {
    public static List<O> randomize(Configuration configuration) {
        List<O> result = new ArrayList<O>();
        var validSet = LightsSets.getValids();
        var random = new Random();

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
    }
}
