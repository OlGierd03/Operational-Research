package agh.or.gen;

import agh.or.records.Configuration;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarListGeneratorTest {

    @Test
    public void seededTest() {
        Configuration configuration = new Configuration(
                10,
                10,
                15,
                30,
                100,
                0L
        );
        List<Integer> cars1 = agh.or.CarListGenerator.createCars(configuration);
        List<Integer> cars2 = agh.or.CarListGenerator.createCars(configuration);

        for (int i = 0; i < cars1.size(); i++) {
            assertEquals(cars1.get(i), cars2.get(i), "Cars should be the same for the same seed");
        }
        System.out.println("Cars1: " + cars1);
        System.out.println("Cars2: " + cars2);
    }
}
