package com.tuvistavie.analyzer;

import java.io.InputStream;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import com.tuvistavie.analyzer.util.FrequencyAnalyzer;
import com.tuvistavie.analyzer.models.WordInfo;

public class Main extends Application {

  @Override
  public void start(Stage stage) {
    stage.setTitle("Bible word occurences");
    final NumberAxis xAxis = new NumberAxis();
    final CategoryAxis yAxis = new CategoryAxis();
    final BarChart<Number,String> bc = new BarChart<Number,String>(xAxis,yAxis);
    bc.setTitle("Bible word occurences");
    xAxis.setLabel("Occurences number");
    xAxis.setTickLabelRotation(90);
    yAxis.setLabel("Word");

    List<WordInfo> data = getData(10);
    XYChart.Series occurenceSeries = new XYChart.Series();
    occurenceSeries.setName("Words occurence");
    for (int i = data.size() - 1; i >= 0; i--) {
      WordInfo wordInfo = data.get(i);
      occurenceSeries.getData().add(new XYChart.Data(wordInfo.occurenceCount(), wordInfo.word()));
    }

    Scene scene  = new Scene(bc,800,600);
    bc.getData().add(occurenceSeries);
    stage.setScene(scene);
    stage.show();
  }

  private List<WordInfo> getData(int limit) {
    InputStream is = getClass().getResourceAsStream("/pg10.txt");
    return FrequencyAnalyzer.getSortedFrequencies(is, limit);
  }

  // TODO: read file path from cli arguments
  public static void main(String[] args) {
    launch(args);
  }
}
