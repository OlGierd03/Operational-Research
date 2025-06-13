package agh.or;

import agh.or.gen.ChildCreationType;
import agh.or.gen.MutationSelectionType;
import agh.or.gen.ParentSelectionType;
import agh.or.gen.Population;
import agh.or.globals.ConfigurationGlobal;
import agh.or.records.Configuration;
import agh.or.Solution;
import agh.or.Simulation;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Konfiguracja");

        Label carCountLabel = new Label("Ilość samochodów:");
        TextField carCountField = new TextField(Integer.toString(Lights.LIGHT_COUNT * 5));

        Label drivingTimeLabel = new Label("Czas przejazdu:");
        TextField drivingTimeField = new TextField("10");

        Label changeTimeLabel = new Label("Czas zmiany świateł:");
        TextField changeTimeField = new TextField("15");

        Label minLightsLabel = new Label("Min czas świateł:");
        TextField minLightsField = new TextField("30");

        Label maxLightsLabel = new Label("Max czas świateł:");
        TextField maxLightsField = new TextField("100");

        Label generationLabel = new Label("Liczba pokoleń:");
        TextField generationField = new TextField("100");

        Label populationLabel = new Label("Rozmiar populacji:");
        TextField populationField = new TextField("120");

        Label seedLabel = new Label("Seed:");
        TextField seedField = new TextField(Long.toString(System.currentTimeMillis()));

        Label parentSelectionLabel = new Label("Algorytm selekcji rodziców:");
        ComboBox<String> parentSelectionCombo = new ComboBox<>();
        parentSelectionCombo.getItems().addAll("Ruletka", "Turniej", "Ranking");
        parentSelectionCombo.setValue("Ranking");

        Label mutationLabel = new Label("Rodzaj mutacji dziecka:");
        ComboBox<String> mutationSelectionCombo = new ComboBox<>();
        mutationSelectionCombo.getItems().addAll("Random", "Removal");
        mutationSelectionCombo.setValue("Random");

        Label chanceLabel = new Label("Szansa mutacji (%):");
        Spinner<Integer> chanceSpinner = new Spinner<>(0, 100, 30);
        chanceSpinner.setEditable(true);

        Label childCreationLabel = new Label("Sposób tworzenia dziecka:");
        ComboBox<String> childCreationSelectionCombo = new ComboBox<>();
        childCreationSelectionCombo.getItems().addAll("Top", "Mix", "Bottom");
        childCreationSelectionCombo.setValue("Bottom");

        Label configModeLabel = new Label("Tryb konfiguracji:");
        RadioButton randomMode = new RadioButton("Losowe rozmieszczenie");
        RadioButton fileMode = new RadioButton("Wczytaj z pliku");
        ToggleGroup modeGroup = new ToggleGroup();
        randomMode.setToggleGroup(modeGroup);
        fileMode.setToggleGroup(modeGroup);
        randomMode.setSelected(true); // Domyślnie losowe

        Label filePathLabel = new Label("Plik konfiguracji:");
        TextField filePathField = new TextField();
        Button browseButton = new Button("Przeglądaj...");

        // Listener do zmiany trybu
        modeGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            boolean isFileMode = newToggle == fileMode;
            filePathLabel.setDisable(!isFileMode);
            filePathField.setDisable(!isFileMode);
            browseButton.setDisable(!isFileMode);
            carCountLabel.setDisable(isFileMode);
            carCountField.setDisable(isFileMode);
        });

        // Ustawienie przy inicjalizacji - plik wyłączony
        filePathLabel.setDisable(true);
        filePathField.setDisable(true);
        browseButton.setDisable(true);

        // Obsługa przycisku przeglądania
        browseButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Wybierz plik konfiguracji");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Pliki tekstowe", "*.txt"),
                    new FileChooser.ExtensionFilter("Wszystkie pliki", "*.*")
            );

            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                filePathField.setText(selectedFile.getAbsolutePath());
            }
        });

        Button runButton = new Button("Oblicz");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(configModeLabel, 0, 0);
        grid.add(randomMode, 1, 0);
        grid.add(fileMode, 1, 1);
        grid.add(carCountLabel, 0, 2);
        grid.add(carCountField, 1, 2);
        grid.add(filePathLabel, 0, 3);
        grid.add(filePathField, 1, 3);
        grid.add(browseButton, 1, 4);
        grid.add(drivingTimeLabel, 0, 5);
        grid.add(drivingTimeField, 1, 5);
        grid.add(changeTimeLabel, 0, 6);
        grid.add(changeTimeField, 1, 6);
        grid.add(minLightsLabel, 0, 7);
        grid.add(minLightsField, 1, 7);
        grid.add(maxLightsLabel, 0, 8);
        grid.add(maxLightsField, 1, 8);
        grid.add(generationLabel, 0, 9);
        grid.add(generationField, 1, 9);
        grid.add(populationLabel, 0, 10);
        grid.add(populationField, 1, 10);
        grid.add(seedLabel, 0, 11);
        grid.add(seedField, 1, 11);
        grid.add(parentSelectionLabel, 0, 12);
        grid.add(parentSelectionCombo, 1, 12);
        grid.add(mutationLabel, 0, 13);
        grid.add(mutationSelectionCombo, 1, 13);
        grid.add(childCreationLabel, 0, 14);
        grid.add(childCreationSelectionCombo, 1, 14);
        grid.add(chanceLabel, 0, 15);
        grid.add(chanceSpinner, 1, 15);
        grid.add(runButton, 1, 16);

        runButton.setOnAction(event -> {
            try {
                int drivingTime = Integer.parseInt(drivingTimeField.getText());
                int changeTime = Integer.parseInt(changeTimeField.getText());
                int minLights = Integer.parseInt(minLightsField.getText());
                int maxLights = Integer.parseInt(maxLightsField.getText());
                int generationCount = Integer.parseInt(generationField.getText());
                int populationSize = Integer.parseInt(populationField.getText());
                long seed = Long.parseLong(seedField.getText());

                String selectedParentSelection = parentSelectionCombo.getValue();
                ParentSelectionType parentSelectionType = ParentSelectionType.fromLabel(selectedParentSelection);
                Population.setParentSelectionType(parentSelectionType);
                String selectedMutationSelection = mutationSelectionCombo.getValue();
                MutationSelectionType mutationSelectionType = MutationSelectionType.fromLabel(selectedMutationSelection);
                Population.setMutationSelectionType(mutationSelectionType);

                double mutationChance = (double) chanceSpinner.getValue() /100;
                Population.setMutationChance(mutationChance);

                String childCreationSelection = childCreationSelectionCombo.getValue();
                ChildCreationType childCreationType = ChildCreationType.fromLabel(childCreationSelection);
                Population.setChildCreationType(childCreationType);

                int carCount;
                List<Integer> carList = null;

                if (randomMode.isSelected()) {
                    carCount = Integer.parseInt(carCountField.getText());
                } else {
                    String filePath = filePathField.getText().trim();
                    if (filePath.isEmpty()) {
                        showAlert("Błąd", "Zły plik konfiguracji!");
                        return;
                    }

                    try {
                        carList = CarListGenerator.loadFromFile(filePath);
                        carCount = carList.stream().mapToInt(Integer::intValue).sum();
                    } catch (Exception e) {
                        showAlert("Błąd", "Nie można wczytać pliku: " + e.getMessage());
                        return;
                    }
                }

                Configuration configuration = new Configuration(
                        carCount,
                        drivingTime,
                        changeTime,
                        minLights,
                        maxLights,
                        generationCount,
                        populationSize,
                        seed
                );

                ConfigurationGlobal.setInstance(configuration);

                if (carList != null) {
                    ConfigurationGlobal.setCarList(carList);
                }

                Solution solution = Solution.genetic();
                if (solution.willEnd()) {
                    Simulation simulation = new Simulation(solution);
                    simulation.run(false);
                }

                openChartsWindow();

            } catch (NumberFormatException e) {
                showAlert("Błąd", "Nieprawidłowe wartości liczbowe!");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Błąd", "Wystąpił błąd: " + e.getMessage());
            }
        });

        Scene scene = new Scene(grid, 380, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static class DataBounds {
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;
    }

    private void openChartsWindow() {
        Stage chartStage = new Stage();
        chartStage.setTitle("Wykresy wynikowe");

        NumberAxis xAxis1 = new NumberAxis();
        xAxis1.setLabel("Pokolenie");
        xAxis1.setAutoRanging(false);
        NumberAxis yAxis1 = new NumberAxis();
        yAxis1.setLabel("Wynik");
        yAxis1.setAutoRanging(false);

        NumberAxis xAxis2 = new NumberAxis();
        xAxis2.setLabel("Pokolenie");
        xAxis2.setAutoRanging(false);
        NumberAxis yAxis2 = new NumberAxis();
        yAxis2.setLabel("Wynik");
        yAxis2.setAutoRanging(false);

        LineChart<Number, Number> bestChart = new LineChart<>(xAxis1, yAxis1);
        bestChart.setTitle("Najlepszy wynik w populacji");
        bestChart.setCreateSymbols(false);
        bestChart.setAnimated(false);
        bestChart.setMinWidth(600);

        LineChart<Number, Number> avgChart = new LineChart<>(xAxis2, yAxis2);
        avgChart.setTitle("Średni wynik w populacji");
        avgChart.setCreateSymbols(false);
        avgChart.setAnimated(false);
        avgChart.setMinWidth(600);

        XYChart.Series<Number, Number> bestSeries = new XYChart.Series<>();
        bestSeries.setName("Najlepszy");
        XYChart.Series<Number, Number> avgSeries = new XYChart.Series<>();
        avgSeries.setName("Średnia");

        DataBounds bestBounds = readDataIntoSeries("bestPops.txt", bestSeries);
        DataBounds avgBounds = readDataIntoSeries("avgPop.txt", avgSeries);

        bestChart.getData().add(bestSeries);
        avgChart.getData().add(avgSeries);

        if (bestBounds != null) {
            xAxis1.setLowerBound(0);
            xAxis1.setUpperBound(ConfigurationGlobal.getGenerationCount());
            yAxis1.setLowerBound(bestBounds.minY);
            yAxis1.setUpperBound(bestBounds.maxY);

            xAxis1.setTickUnit(ConfigurationGlobal.getGenerationCount() / 10.0);
            yAxis1.setTickUnit((bestBounds.maxY - bestBounds.minY) / 10.0);
            System.out.println(yAxis1.getTickUnit());
        }

        if (avgBounds != null) {
            xAxis2.setLowerBound(0);
            xAxis2.setUpperBound(ConfigurationGlobal.getGenerationCount());
            yAxis2.setLowerBound(avgBounds.minY);
            yAxis2.setUpperBound(avgBounds.maxY);

            xAxis2.setTickUnit(ConfigurationGlobal.getGenerationCount() / 10.0);
            yAxis2.setTickUnit((avgBounds.maxY - avgBounds.minY) / 10.0);
            System.out.println(yAxis2.getTickUnit());
        }

        HBox chartsBox = new HBox(10, bestChart, avgChart);
        chartsBox.setPadding(new Insets(10));

        Scene scene = new Scene(chartsBox, 1230, 600);
        chartStage.setScene(scene);
        chartStage.show();
    }

    private DataBounds readDataIntoSeries(String fileName, XYChart.Series<Number, Number> series) {
        DataBounds bounds = new DataBounds();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\t");
                if (parts.length == 2) {
                    try {
                        double x = Double.parseDouble(parts[0]);
                        double y = Double.parseDouble(parts[1].replace(',', '.'));
                        series.getData().add(new XYChart.Data<>(x, y));
                        if (x < bounds.minX) bounds.minX = x;
                        if (x > bounds.maxX) bounds.maxX = x;
                        if (y < bounds.minY) bounds.minY = y;
                        if (y > bounds.maxY) bounds.maxY = y;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        double paddingX = (bounds.maxX - bounds.minX) * 0.05;
        double paddingY = (bounds.maxY - bounds.minY) * 0.1;

        bounds.minX -= paddingX;
        bounds.maxX += paddingX;
        bounds.minY -= paddingY;
        bounds.maxY += paddingY;

        return bounds;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
