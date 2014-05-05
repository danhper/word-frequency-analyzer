package com.tuvistavie.analyzer;

import com.tuvistavie.analyzer.models.WordInfo;
import com.tuvistavie.analyzer.config.CLIParser;
import com.tuvistavie.analyzer.config.FrequencyAnalyzer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FrequencyRenderer extends Application {

  private int histogramRowsCount = 15;
  private String fileName;

  public FrequencyRenderer() {
    super();
  }

  @Override
  public void init() throws Exception {
    super.init();
    setFromArgs();
  }

  @Override
  public void start(Stage stage) {
    stage.setTitle("Word occurrences in " + this.getFileName());
    final NumberAxis xAxis = new NumberAxis();
    final CategoryAxis yAxis = new CategoryAxis();
    final BarChart<Number,String> bc = new BarChart<>(xAxis,yAxis);
    bc.setTitle("Word occurrences in " + this.getFileName());
    xAxis.setLabel("Occurrences number");
    xAxis.setTickLabelRotation(90);
    yAxis.setLabel("Word");

    List<WordInfo> data = getData(this.getHistogramRowsCount());
    XYChart.Series occurenceSeries = new XYChart.Series();
    occurenceSeries.setName("Word occurrences");
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
    InputStream is = null;
    try {
      is = new FileInputStream(new File(this.getFileName()));
    } catch (IOException e) {
      CLIParser.printHelp();
      System.exit(1);
    }
    return FrequencyAnalyzer.getSortedFrequencies(is, limit);
  }

  private CommandLine getCli() {
    try {
      List<String> params = getParameters().getRaw();
      return CLIParser.parseArgs(params.toArray(new String[params.size()]));
    } catch (ParseException e) {
      CLIParser.printHelp();
      System.err.println(e.getLocalizedMessage());
      System.exit(1);
      return null;
    }
  }

  private void setFromArgs() {
    CommandLine cli = getCli();
    this.setFileName(cli.getOptionValue("filename"));
    if (cli.hasOption("rows-count")) {
      this.setHistogramRowsCount(Integer.parseInt(cli.getOptionValue("rows-count")));
    }
  }

  public int getHistogramRowsCount() {
    return histogramRowsCount;
  }

  public void setHistogramRowsCount(int histogramRowsCount) {
    this.histogramRowsCount = histogramRowsCount;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}


