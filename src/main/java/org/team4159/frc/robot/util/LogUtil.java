package org.team4159.frc.robot.util;

import java.io.File;
import java.util.Random;
import java.util.Optional;

public class LogUtil {
  private static File MEDIA_FOLDER = new File("/media");

  public static String generateHexString() {
    Random random = new Random();
    return Integer.toHexString(random.nextInt()).toUpperCase();
  }

  public static Optional<File> findUSBDrive() {
    File usb_drive = null;
    String[] usb_drive_names = MEDIA_FOLDER.list();
    if (usb_drive_names != null && usb_drive_names.length > 0)
      usb_drive = new File(usb_drive_names[0]);
    return Optional.ofNullable(usb_drive);
  }
}
