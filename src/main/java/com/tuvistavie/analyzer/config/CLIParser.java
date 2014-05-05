package com.tuvistavie.analyzer.config;

import org.apache.commons.cli.*;


public final class CLIParser {
  private static Options options = getOptions();
  private static HelpFormatter helpFormatter = new HelpFormatter();

  private CLIParser() {

  }

  private static Option getFilenameOption() {
    return OptionBuilder
            .withDescription("the path to the name to parse")
            .withLongOpt("filename")
            .hasArg(true)
            .isRequired(true)
            .create("f");
  }

  private static Option getRowsCountOption() {
    return OptionBuilder
            .withArgName("n")
            .withDescription("the number of words to display")
            .withLongOpt("rows-count")
            .hasArg(true)
            .isRequired(false)
            .create("n");
  }

  private static Options getOptions() {
    Options options = new Options();
    options.addOption(getFilenameOption());
    options.addOption(getRowsCountOption());
    return options;
  }

  public static CommandLine parseArgs(String[] args) throws ParseException {
    Parser parser = new PosixParser();
    return parser.parse(options, args);
  }

  public static void printHelp() {
    helpFormatter.printHelp("frequency-analyzer [options] FILENAME", options);
  }
}
