package org.team4159.lib.control.signal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import org.team4159.lib.control.signal.filters.LowPassFilter;
import org.team4159.lib.logging.CSVWriter;
import org.team4159.lib.math.Cardinal;
import org.team4159.lib.math.Epsilon;

public class LowPassFilterTest {
  @Test
  public void TestDoesNothingWhenSmoothingIs1() {
    LowPassFilter filter = new LowPassFilter(0, 1, 1);

    for (int i = 0; i < 10; i++) {
      Assert.assertEquals(i, filter.calculate(i), Epsilon.kEpsilon);
    }
  }

  @Test
  public void TestSmoothing() {
    double signal = 0;
    LowPassFilter filter = new LowPassFilter(0,  20, 1);

    File raw_data_file, smoothed_data_file;

    CSVWriter raw_writer = null,
              smoothed_writer = null;

    BufferedReader raw_reader = null,
                   smoothed_reader = null;

    try {
      raw_data_file = File.createTempFile("low-pass-", "-raw.csv");
      System.out.println(raw_data_file);

      raw_writer = new CSVWriter(raw_data_file);
      raw_reader = new BufferedReader(new FileReader(raw_data_file));

      smoothed_data_file = File.createTempFile("low-pass-", "-smoothed.csv");
      System.out.println(smoothed_data_file);

      smoothed_writer = new CSVWriter(smoothed_data_file);
      smoothed_reader = new BufferedReader(new FileReader(smoothed_data_file));
    } catch (IOException e) {
      e.printStackTrace();
      Assert.fail();
    }

    for (int i = 0; i < 1000; i++) {
      double signal_delta = Math.random() - 0.5;
      signal += signal_delta;

      double smoothed_value = filter.calculate(signal);

      raw_writer.write(i, signal);
      smoothed_writer.write(i, smoothed_value);
    }

    raw_writer.close();
    smoothed_writer.close();

    double raw_noise = 0;
    double smoothed_noise = 0;

    double last_raw_signal = 0;
    double last_smoothed_signal = 0;

    try {
      for (int i = 0; i < 1000; i++) {
        double raw_signal = Double.parseDouble(raw_reader.readLine().split(",")[1]);
        double smoothed_signal = Double.parseDouble(smoothed_reader.readLine().split(",")[1]);

        raw_noise += Math.sqrt(Math.pow(last_raw_signal - raw_signal, 2) + 1);
        smoothed_noise += Math.sqrt(Math.pow(last_smoothed_signal - smoothed_signal, 2) + 1);

        last_raw_signal = raw_signal;
        last_smoothed_signal = smoothed_signal;
      }
    } catch (IOException e) {
      e.printStackTrace();
      Assert.fail();
    }

    Assert.assertTrue(raw_noise > smoothed_noise);
  }
}
