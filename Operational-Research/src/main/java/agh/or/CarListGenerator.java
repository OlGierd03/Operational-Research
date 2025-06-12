package agh.or;

import agh.or.records.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarListGenerator {
    public static List<Integer> createCars(Configuration configuration) {
        List<Integer> carList = new ArrayList<>();
        for (int i = 0; i < Lights.LIGHT_COUNT; i++) {
            carList.add(0);
        }

        Random random = new Random(configuration.seed());

        for (int i = 0; i < configuration.carCount(); i++) {
            int place = random.nextInt(Lights.LIGHT_COUNT);
            carList.set(place, carList.get(place) + 1);
        }

        return carList;
    }

    /*
        Wczytuje rozmieszczenie samochodów z pliku konfiguracyjnego.
        Format pliku: każda linia zawiera numer pasa (0-Lights.LIGHT_COUNT) <=> (0-11) i liczbę samochodów oddzielone spacją lub tabulatorem.
        Jeśli pas pojawia się wielokrotnie to liczba samochodów na pasie to suma wskazanych liczb.
        Przykład:
        0 5
        1 3
        2 7
        1 15
        ...
    */
    public static List<Integer> loadFromFile(String filePath) throws IOException, IllegalArgumentException {
        List<Integer> carList = new ArrayList<>();

        for (int i = 0; i < Lights.LIGHT_COUNT; i++) {
            carList.add(0);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String firstLine = reader.readLine();
            if (firstLine == null || firstLine.trim().isEmpty()) {
                throw new IllegalArgumentException("Plik jest pusty");
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Nieprawidłowy format w linii " + lineNumber + ". Oczekiwano: numer_pasa liczba_samochodów");
                }

                try {
                    int laneNumber = Integer.parseInt(parts[0]);
                    int carCount = Integer.parseInt(parts[1]);

                    if (laneNumber < 0 || laneNumber >= Lights.LIGHT_COUNT) {
                        throw new IllegalArgumentException("Nieprawidłowy numer pasa w linii " + lineNumber + ": " + laneNumber +
                                ". Dozwolone wartości: 0-" + (Lights.LIGHT_COUNT - 1));
                    }

                    if (carCount < 0) {
                        throw new IllegalArgumentException("Liczba samochodów nie może być ujemna w linii " + lineNumber);
                    }

                    carList.set(laneNumber, carList.get(laneNumber) + carCount);

                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Nieprawidłowe liczby w linii " + lineNumber + ": " + line);
                }
            }
        }

        int totalCars = carList.stream().mapToInt(Integer::intValue).sum();
        if (totalCars == 0) {
            throw new IllegalArgumentException("Plik nie zawiera żadnych samochodów");
        }

        return carList;
    }
}
