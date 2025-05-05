package agh.or;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Propozycja żeby zmienić nazwę klasy na CarFactory
public class CarListGenerator {
    public static List<Integer> createCars(Configuration configuration) { // funkcja zwraca liczbę samochodów na każdym pasie
        List<Integer> carList = new ArrayList<>();
        for (int i = 0; i < Lights.LIGHT_COUNT; i++) { // Tworzymy pozycje dla każdego pasa na skrzyżowaniu
            carList.add(0);
        }

        Random random = new Random();

        for (int i = 0; i < configuration.carCount(); i++) { // Dodajemy samochody do losowych pozycji
            int place = random.nextInt(Lights.LIGHT_COUNT);
            carList.set(place, carList.get(place) + 1);
        }

        return carList;
    }

    public static List<Integer> createCars(Configuration configuration, long seed) { // to samo co wyżej, ale z seedem
        List<Integer> carList = new ArrayList<>();
        for (int i = 0; i < Lights.LIGHT_COUNT; i++) {
            carList.add(0);
        }

        Random random = new Random(seed);

        for (int i = 0; i < configuration.carCount(); i++) {
            int place = random.nextInt(Lights.LIGHT_COUNT);
            carList.set(place, carList.get(place) + 1);
        }

        return carList;
    }
}
