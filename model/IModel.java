package model;

import java.util.ArrayList;

/**
 * The IModel interface represents the operations that can be performed on an image.
 * It defines methods for various image processing operations such as
 * brightness adjustment, flipping,
 * color component extraction, image splitting, combining, and more.
 */
public interface IModel {


  /**
   * Brighten the image by a given constant.
   *
   * @param factor the constant to be added to each pixel of the image.
   * @param src    the name of the source image.
   * @param dest   the name of the destination image.
   */
  void brighten(float factor, String src, String dest);

  /**
   * Flip an image along its vertical axis.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  void verticalFlip(String src, String dest);

  /**
   * Flip an image along its horizontal axis.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  void horizontalFlip(String src, String dest);

  /**
   * Convert a normal color image into a sepia-toned image.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  void sepia(String src, String dest);

  /**
   * Blur an image using a Gaussian-blur filter.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  void blur(String src, String dest);

  /**
   * Sharpen an image using a sharpening filter.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  void sharpen(String src, String dest);

  /**
   * Create an image that visualizes the value component of the image.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  void valueComponent(String src, String dest);

  /**
   * Create an image that visualizes the intensity component of the image.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  void intensityComponent(String src, String dest);

  /**
   * Create an image that visualizes the luma component of the image.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  void lumaComponent(String src, String dest);

  /**
   * Create an image that visualizes the individual red component of the image.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  void redComponent(String src, String dest);

  /**
   * Create an image that visualizes the individual green component of the image.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  void greenComponent(String src, String dest);

  /**
   * Create an image that visualizes the individual blue component of the image.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  void blueComponent(String src, String dest);

  /**
   * Split a single image into three images representing each of the three channels.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  void rgbSplit(String src, String[] dest);

  /**
   * Combine three greyscale image into a single color image whose R,G,B values come from the three
   * images.
   *
   * @param imageArray an array of source image names.
   * @param dest       the name of the destination image.
   */
  void rgbCombine(String[] imageArray, String dest);

  /**
   * Chop the image vertically at the given percentage of the width of the original image.
   *
   * @param percentage the percentage of the width of the original image.
   * @param src        the name of the source image.
   * @param dest       the name of the destination image.
   */
  void chop(float percentage, String src, String dest);

  /**
   * Underlay the given image horizontally with the original image, so that only the resulting image
   * has the width of the given image.
   *
   * @param original the image whose part has to be stitched with the original image.
   * @param src      the name of the source image.
   * @param dest     the name of the destination image.
   */
  void underlay(String original, String src, String dest);

  /**
   * Produce an image that represents the histogram of the image.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  void histogram(String src, String dest);

  /**
   * Color-corrects an image by aligning the meaningful peaks of its histogram.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  void colorCorrect(String src, String dest);


  /**
   * Adjusts the levels of an image.
   *
   * @param b    the position of the black points on the horizontal axis.
   * @param m    the position of the mid-points on the horizontal axis.
   * @param w    the position of the white points on the horizontal axis.
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  void levelAdjust(int b, int m, int w, String src, String dest);

  /**
   * Compresses an image by the provided percentage.
   *
   * @param percent the percentage of compression desired.
   * @param src     the name of the source image.
   * @param dest    the name of the destination image.
   */
  void compress(float percent, String src, String dest) throws IllegalArgumentException;

  /**
   * Retrieves the pixel data of all three channels of the image.
   *
   * @param name the name of the image.
   * @return a three-dimensional array of pixels representing the image.
   */
  ArrayList<ArrayList<ArrayList<Integer>>> getRgbaArray(String name);

  void load(String name, ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray);


}
