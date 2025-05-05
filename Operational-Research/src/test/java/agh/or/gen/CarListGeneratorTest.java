package agh.or.gen;

import agh.or.Configuration;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarListGeneratorTest {

    @Test
    public void seededTest() {
        Configuration configuration = new agh.or.Configuration(
                10,
                10,
                15,
                30,
                100
        );
        List<Integer> cars1 = agh.or.CarListGenerator.createCars(configuration, 123456789L);
        List<Integer> cars2 = agh.or.CarListGenerator.createCars(configuration, 123456789L);

        for (int i = 0; i < cars1.size(); i++) {
            assertEquals(cars1.get(i), cars2.get(i), "Cars should be the same for the same seed");
        }
        System.out.println("Cars1: " + cars1);
        System.out.println("Cars2: " + cars2);
    }
}
