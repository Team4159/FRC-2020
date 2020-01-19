package org.team4159.lib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CsvWriter {
  private final SimpleDateFormat date_format = new SimpleDateFormat("'-'yyyy-MM-dd-HH-mm'.csv'");

  private File file;
  private FileWriter writer;

  public CsvWriter(String filename) {
    final String suffix = date_format.format(new Date());

    try {
      file = File.createTempFile(filename, suffix);
      writer = new FileWriter(file);
      System.out.println("Output: " + file.getPath());

    } catch (IOException exception) {
      System.err.println(exception.toString());
    }
  }

  public void write(Double... lines) {
    try {
      for (Double line : lines) {
        writer.append(line + ", ");
      }
      writer.append("\n");

    } catch (IOException exception) {
      System.err.println(exception.toString());
    }
  }

  public String finish() {
    try {
      writer.flush();
      writer.close();

      return file.getPath();
    } catch (IOException exception) {
      return exception.toString();
    }
  }
}
