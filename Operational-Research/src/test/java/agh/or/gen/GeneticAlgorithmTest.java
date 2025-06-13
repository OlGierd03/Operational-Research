package agh.or.gen;

import agh.or.Solution;
import agh.or.globals.ConfigurationGlobal;
import agh.or.records.Configuration;
import org.junit.jupiter.api.Test;

public class GeneticAlgorithmTest {

    @Test
    public void seedTest() {

        Configuration configuration = new Configuration(
                1000,
                10,
                15,
                30,
                100,
                15,
                120,
                0L
        );

        ConfigurationGlobal.setInstance(configuration);
        Solution solution1 = Solution.genetic();
        Solution solution2 = Solution.genetic();

        Assertions.assertEquals(solution1.getValues(), solution2.getValues(), "Solutions should be equal with the same seed");

    }
}
