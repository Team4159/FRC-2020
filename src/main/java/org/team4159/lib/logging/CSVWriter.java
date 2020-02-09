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

  public void write(Object... columns) {
    try {
      for (int i = 0; i < columns.length; i++) {
        writer.append(columns[i].toString());
        if (i < columns.length - 1) {
          writer.append(",");
        }
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
