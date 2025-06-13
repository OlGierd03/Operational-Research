package agh.or;

import agh.or.records.Configuration;
import org.junit.jupiter.api.Test;

public class ORandomizerTest {

    @Test
    public void seedTest() {

        Configuration configuration = new Configuration(
                10,
                10,
                15,
                30,
                100,
                15,
                120,
                0L
        );

        var randomList1 = ORandomizer.randomize(configuration);

        try {
            Thread.sleep(100); // Sleep for 100 milliseconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }

        var randomList2 = ORandomizer.randomize(configuration);
        for (int i = 0; i < randomList1.size(); i++) {
            assert randomList1.get(i).equals(randomList2.get(i)) : "Randomized lists should be the same for the same seed";
        }
    }
}
