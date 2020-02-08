package org.team4159.lib.hardware.wrapper;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
  public enum LEDMode {
    Default(0),
    ForceOff(1),
    ForceBlink(2),
    ForceOn(3);

    public int value;

    LEDMode(int value) {
      this.value = value;
    }
  }

  public enum OperationMode {
    VisionProcessor(0),
    DriverCamera(1);

    public int value;

    OperationMode(int value) {
      this.value = value;
    }
  }

  public enum StreamingMode {
    Standard(0),
    PiPMain(1),
    PiPSecondary(2);

    public int value;

    StreamingMode(int value) {
      this.value = value;
    }
  }

  private NetworkTable limelight_table;

  public Limelight(NetworkTableInstance nt) {
    limelight_table = nt.getTable("limelight");
  }

  public Limelight() {
    this(NetworkTableInstance.getDefault());
  }

  // "Best" Contour Information

  /**
   * Gets whether the limelight has any valid targets.
   *
   * @return whether the limelight has any valid targets
   */
  public boolean isTargetVisible() {
    return getDouble("tv") == 1;
  }

  /**
   * Gets the horizontal offset from crosshair to target
   *
   * @return Horizontal Offset From Crosshair To Target (LL1: -27 degrees to 27 degrees | LL2: -29.8 to 29.8 degrees)
   */
  public double getTargetHorizontalOffset() {
    return getDouble("tx");
  }

  /**
   * Gets the vertical offset from crosshair to target
   *
   * @return Vertical Offset From Crosshair To Target (LL1: -20.5 degrees to 20.5 degrees | LL2: -24.85 to 24.85 degrees)
   */
  public double getTargetVerticalOffset() {
    return getDouble("ty");
  }

  /**
   * Gets the target area
   *
   * @return Target Area (0% of image to 100% of image)
   */
  public double getTargetArea() {
    return getDouble("ta");
  }

  /**
   * Gets the sidelength of shortest side of the fitted bounding box
   *
   * @return Sidelength of shortest side of the fitted bounding box (pixels)
   */
  public double getBboxShortestSide() {
    return getDouble("tshort");
  }

  /**
   * Gets the sidelength of longest side of the fitted bounding box
   *
   * @return Sidelength of longest side of the fitted bounding box (pixels)
   */
  public double getBboxLongestSide() {
    return getDouble("tlong");
  }

  /**
   * Gets the horizontal sidelength of the rough bounding box
   *
   * @return Horizontal sidelength of the rough bounding box (0 - 320 pixels)
   */
  public double getBboxHorizontalSidelength() {
    return getDouble("thor");
  }

  /**
   * Gets the vertical sidelength of the rough bounding box
   *
   * @return Vertical sidelength of the rough bounding box (0 - 320 pixels)
   */
  public double getBboxVerticalSidelength() {
    return getDouble("tvert");
  }

  // General Status / Configuration Information

  /**
   * Gets skew or rotation
   *
   * @return Skew or rotation (-90 degrees to 0 degrees)
   */
  public double getRotation() {
    return getDouble("ts");
  }

  /**
   * Gets the pipeline's latency contribution
   *
   * @return The pipeline’s latency contribution (ms) Add at least 11ms for image capture latency.
   */
  public double getLatency() {
    return getDouble("tl");
  }

  /**
   * Gets the true active pipeline index of the camera
   *
   * @return True active pipeline index of the camera (0 .. 9)
   */
  public double getCurrentPipeline() {
    return getDouble("getpipe");
  }

  // Configuration Setters

  /**
   * Sets limelight’s LED state
   *
   * @param mode LED state to set limelight to
   */
  public void setLEDMode(LEDMode mode) {
    setNumber("ledMode", mode.value);
  }

  /**
   * Sets limelight’s operation mode
   *
   * @param mode Operation mode to set limelight to
   */
  public void setOperationMode(OperationMode mode) {
    setNumber("camMode", mode.value);
  }

  /**
   * Sets limelight’s current pipeline
   *
   * @param pipeline_idx Index of pipeline to set limelight to
   */
  public void setPipeline(int pipeline_idx) {
    setNumber("pipeline", pipeline_idx);
  }

  /**
   * Sets limelight’s streaming mode
   *
   * @param mode Streaming mode to set limelight to
   */
  public void setStreamingMode(StreamingMode mode) {
    setNumber("stream", mode.value);
  }

  /**
   * Stops the limelight from taking snapshots
   */
  public void startTakingSnapShots() {
    setNumber("snapshot", 0);
  }

  /**
   * Sets limelight to take two snapshots per second
   */
  public void stopTakingSnapShots() {
    setNumber("snapshot", 1);
  }

  private void setNumber(String entry_name, Number num) {
    limelight_table.getEntry(entry_name).setNumber(num);
  }

  private double getDouble(String entry_name) {
    return limelight_table.getEntry(entry_name).getDouble(0);
  }
}
