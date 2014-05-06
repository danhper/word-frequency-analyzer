package com.tuvistavie.analyzer.config;

import org.apache.commons.cli.*;


public final class CLIParser {
  private static final Options options = getOptions();
  private static final HelpFormatter helpFormatter = new HelpFormatter();

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
            .withDescription("the number of words to display")
            .withLongOpt("rows-count")
            .hasArg(true)
            .isRequired(false)
            .create("n");
  }

  private static Option getUsesBuiltinHashOption() {
    return OptionBuilder
            .withDescription("Use builtin hash class instead of custom implementation")
            .withLongOpt("builtin-hash")
            .hasArg(false)
            .isRequired(false)
            .create("b");
  }

  private static Options getOptions() {
    Options options = new Options();
    options.addOption(getFilenameOption());
    options.addOption(getRowsCountOption());
    options.addOption(getUsesBuiltinHashOption());
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
