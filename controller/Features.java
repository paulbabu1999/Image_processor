package controller;

import java.io.IOException;

public interface Features {
  void brighten(float factor) throws IOException, InstantiationException, IllegalAccessException;

  /**
   * Flip an image along its vertical axis.
   */
  void verticalFlip() throws IOException, InstantiationException, IllegalAccessException;

  /**
   * Flip an image along its horizontal axis.
   */
  void horizontalFlip() throws IOException, InstantiationException, IllegalAccessException;

  /**
   * Convert a normal color image into a sepia-toned image.
   */
  void sepia() throws IOException, InstantiationException, IllegalAccessException;

  /**
   * Blur an image using a Gaussian-blur filter.
   */
  void blur() throws IOException, InstantiationException, IllegalAccessException;

  /**
   * Sharpen an image using a sharpening filter.
   */
  void sharpen() throws IOException, InstantiationException, IllegalAccessException;

  /**
   * Create an image that visualizes the luma component of the image.
   */
  void lumaComponent() throws IOException, InstantiationException, IllegalAccessException;

  /**
   * Create an image that visualizes the individual red component of the image.
   */
  void redComponent() throws IOException, InstantiationException, IllegalAccessException;

  /**
   * Create an image that visualizes the individual green component of the image.
   */
  void greenComponent() throws IOException, InstantiationException, IllegalAccessException;

  /**
   * Create an image that visualizes the individual blue component of the image.
   */
  void blueComponent() throws IOException, InstantiationException, IllegalAccessException;

  /**
   * Color-corrects an image by aligning the meaningful peaks of its histogram.
   */
  void colorCorrect() throws IOException, InstantiationException, IllegalAccessException;

  /**
   * Adjusts the levels of an image.
   *
   * @param b the position of the black points on the horizontal axis.
   * @param m the position of the mid-points on the horizontal axis.
   * @param w the position of the white points on the horizontal axis.
   */
  void levelAdjust(int b, int m, int w) throws IOException, InstantiationException, IllegalAccessException;

  /**
   * Compresses an image by the provided percentage.
   *
   * @param percent the percentage of compression desired.
   */
  void compress(float percent) throws IOException, InstantiationException, IllegalAccessException;

  void load(String path) throws IOException, InstantiationException, IllegalAccessException;

  void save(String path) throws IOException, InstantiationException, IllegalAccessException;

  void split(float percentage) throws IOException, InstantiationException, IllegalAccessException;
}
