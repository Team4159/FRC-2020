package org.team4159.lib.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
  private FileWriter writer;

  public CSVWriter(File file) {
    try {
      writer = new FileWriter(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public CSVWriter(String filename) {
    this(new File(filename));
  }

  public static CSVWriter createTempFile(String filename) {
    File file;

    try {
      file = File.createTempFile("temp-", filename);
      return new CSVWriter(file);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return new CSVWriter(filename);
  }

  public void write(Object... columns) {
    try {
      for (Object column : columns) {
        writer.append(column.toString());
        writer.append(",");
      }
      writer.append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void close() {
    try {
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
