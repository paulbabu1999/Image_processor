package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The `Model` class implements the `IModel` interface and represents
 * a basic image processing model.
 * It provides methods to load, manipulate, and save images in RGBA format.
 */
public class Model implements IModel {

  private final HashMap<String, ArrayList<ArrayList<ArrayList<Integer>>>> storage;


  public Model() {
    this.storage = new HashMap<>();
  }

  @Override
  public void load(String name, ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray) {
    storage.put(name, createRGBA(rgbaArray));
  }


  private ArrayList<ArrayList<ArrayList<Integer>>> createRGBA(
      ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray) {
    if (rgbaArray.get(0).get(0).size() != 4) {
      throw new IllegalArgumentException("Illegal array dimensions!");
    }

    return deepCopy(rgbaArray);
  }

  private ArrayList<ArrayList<ArrayList<Integer>>> deepCopy(
      ArrayList<ArrayList<ArrayList<Integer>>> original) {
    ArrayList<ArrayList<ArrayList<Integer>>> copy = new ArrayList<>();

    for (ArrayList<ArrayList<Integer>> list2D : original) {
      ArrayList<ArrayList<Integer>> copy2D = new ArrayList<>();
      for (ArrayList<Integer> list1D : list2D) {
        ArrayList<Integer> copy1D = new ArrayList<>(list1D);
        copy2D.add(copy1D);
      }
      copy.add(copy2D);
    }

    return copy;
  }

  /**
   * Brighten the image by a given constant.
   *
   * @param factor the constant to be added to each pixel of the image.
   * @param src    the name of the source image.
   * @param dest   the name of the destination image.
   */
  @Override
  public void brighten(float factor, String src, String dest) {
    if (storage.get(src) == null) {
      throw new IllegalArgumentException("The image '" + src + "' does not exist.");
    }
    if (storage.get(dest) == null) {
      storage.put(dest, new ArrayList<>());
    }
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray = storage.get(src);
    ArrayList<ArrayList<ArrayList<Integer>>> brightenedImage = createRGBA(rgbaArray);
    for (int i = 0; i < getHeight(brightenedImage); i += 1) {
      for (int j = 0; j < getWidth(brightenedImage); j += 1) {
        brightenedImage.get(i).get(j).set(0,
            (int) Math.min(255, Math.max(0, rgbaArray.get(i).get(j).get(0) + factor)));
        brightenedImage.get(i).get(j).set(1,
            (int) Math.min(255, Math.max(0, rgbaArray.get(i).get(j).get(1) + factor)));
        brightenedImage.get(i).get(j).set(2,
            (int) Math.min(255, Math.max(0, rgbaArray.get(i).get(j).get(2) + factor)));
      }
    }
    storage.put(dest, brightenedImage);
  }

  /**
   * Flip an image along its vertical axis.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  @Override
  public void verticalFlip(String src, String dest) {
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray = storage.get(src);
    ArrayList<ArrayList<ArrayList<Integer>>> flippedImage = createRGBA(rgbaArray);
    for (int i = 0; i < getHeight(flippedImage); i++) {
      int lastJ = getWidth(flippedImage) - 1;
      for (int j = 0; j < getWidth(flippedImage); j++) {
        flippedImage.get(i).get(j).set(0, rgbaArray.get(i).get(lastJ).get(0));
        flippedImage.get(i).get(j).set(1, rgbaArray.get(i).get(lastJ).get(1));
        flippedImage.get(i).get(j).set(2, rgbaArray.get(i).get(lastJ).get(2));
        flippedImage.get(i).get(j).set(3, rgbaArray.get(i).get(lastJ).get(3));
        lastJ -= 1;
      }
    }
    storage.put(dest, flippedImage);
  }

  /**
   * Flip an image along its horizontal axis.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  @Override
  public void horizontalFlip(String src, String dest) {
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray = storage.get(src);
    ArrayList<ArrayList<ArrayList<Integer>>> flippedImage = createRGBA(rgbaArray);
    int lastI = getHeight(flippedImage) - 1;
    for (int i = 0; i < getHeight(flippedImage); i += 1) {
      for (int j = 0; j < getWidth(flippedImage); j += 1) {
        flippedImage.get(i).get(j).set(0, rgbaArray.get(lastI).get(j).get(0));
        flippedImage.get(i).get(j).set(1, rgbaArray.get(lastI).get(j).get(1));
        flippedImage.get(i).get(j).set(2, rgbaArray.get(lastI).get(j).get(2));
        flippedImage.get(i).get(j).set(3, rgbaArray.get(lastI).get(j).get(3));
      }
      lastI -= 1;
    }
    storage.put(dest, flippedImage);
  }

  private double[][] multiplyArrays(double[][] matrixA, double[][] matrixB) {
    int rowsA = matrixA.length;
    int rowsB = matrixB.length;
    int colsA = matrixA[0].length;
    int colsB = matrixB[0].length;

    if (colsA != matrixB.length) {
      throw new IllegalArgumentException(
          "Matrix A columns must be equal to Matrix B rows for multiplication.");
    }

    double[][] result = new double[rowsA][rowsB];

    for (int i = 0; i < rowsA; i++) {
      for (int j = 0; j < colsB; j++) {
        result[i][j] = 0;
        for (int k = 0; k < colsA; k++) {
          result[i][j] += matrixA[i][k] * matrixB[k][j];
        }
      }
    }

    return result;
  }

  /**
   * Convert a normal color image into a sepia-toned image.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  @Override
  public void sepia(String src, String dest) {
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray = storage.get(src);
    ArrayList<ArrayList<ArrayList<Integer>>> sepiaImage = createRGBA(rgbaArray);
    double[][] sepiaMatrixValues = {
        {0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}
    };
    for (int i = 0; i < getHeight(sepiaImage); i += 1) {
      for (int j = 0; j < getWidth(sepiaImage); j += 1) {
        double[][] pixelValueArray = new double[3][1];
        pixelValueArray[0][0] = rgbaArray.get(i).get(j).get(0);
        pixelValueArray[1][0] = rgbaArray.get(i).get(j).get(1);
        pixelValueArray[2][0] = rgbaArray.get(i).get(j).get(2);
        double[][] result = multiplyArrays(sepiaMatrixValues, pixelValueArray);
        sepiaImage.get(i).get(j).set(0, Math.min(255, Math.max(0, (int) result[0][0])));
        sepiaImage.get(i).get(j).set(1, Math.min(255, Math.max(0, (int) result[1][0])));
        sepiaImage.get(i).get(j).set(2, Math.min(255, Math.max(0, (int) result[2][0])));
      }
    }
    storage.put(dest, sepiaImage);
  }

  private void applyFilter(ArrayList<ArrayList<ArrayList<Integer>>> image, double[][] filter) {
    int height = image.size();
    int width = image.get(0).size();
    int filterSize = filter.length;

    // Create a copy of the original image to store the filtered values
    ArrayList<ArrayList<ArrayList<Integer>>> filteredImage = new ArrayList<>(height);

    for (int i = 0; i < height; i++) {
      filteredImage.add(new ArrayList<>());
      for (int j = 0; j < width; j++) {
        filteredImage.get(i).add(new ArrayList<>(3));
      }
    }

    // Iterate through each pixel in the original image
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        applyFilterAtPixel(image, i, j, filter, height, width, filterSize, filteredImage);
      }
    }

    // Copy the filtered values back to the original image
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int c = 0; c < 3; c++) {
          image.get(i).get(j).set(c, filteredImage.get(i).get(j).get(c));
        }
      }
    }
  }

  private void applyFilterAtPixel(ArrayList<ArrayList<ArrayList<Integer>>> image, int i, int j,
      double[][] filter, int height, int width, int filterSize,
      ArrayList<ArrayList<ArrayList<Integer>>> filteredImage) {
    int filterCenter = filterSize / 2;

    for (int c = 0; c < 3; c++) {
      double result = 0.0;
      for (int x = 0; x < filterSize; x++) {
        for (int y = 0; y < filterSize; y++) {
          int newX = i + x - filterCenter;
          int newY = j + y - filterCenter;
          if (newX >= 0 && newX < height && newY >= 0 && newY < width) {
            result += image.get(newX).get(newY).get(c) * filter[x][y];
          }
        }
      }
      filteredImage.get(i).get(j).add((int) Math.min(255, Math.max(0, result)));
    }
  }

  /**
   * Blur an image using a Gaussian-blur filter.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  @Override
  public void blur(String src, String dest) {
    double[][] blurFilter = {
        {1.0 / 16, 1.0 / 8, 1.0 / 16},
        {1.0 / 8, 1.0 / 4, 1.0 / 8},
        {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray = storage.get(src);
    ArrayList<ArrayList<ArrayList<Integer>>> blurredImage = createRGBA(rgbaArray);

    applyFilter(blurredImage, blurFilter);

    storage.put(dest, blurredImage);
  }

  /**
   * Sharpen an image using a sharpening filter.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  @Override
  public void sharpen(String src, String dest) {
    double[][] sharpenFilter = {
        {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
        {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
        {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
        {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
        {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    };

    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray = storage.get(src);
    ArrayList<ArrayList<ArrayList<Integer>>> sharpImage = createRGBA(rgbaArray);

    applyFilter(sharpImage, sharpenFilter);

    storage.put(dest, sharpImage);
  }

  /**
   * Create an image that visualizes the value component of the image.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  @Override
  public void valueComponent(String src, String dest) {
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray = storage.get(src);
    ArrayList<ArrayList<ArrayList<Integer>>> valueCompImage = createRGBA(rgbaArray);
    for (int i = 0; i < getHeight(valueCompImage); i += 1) {
      for (int j = 0; j < getWidth(valueCompImage); j += 1) {
        int maxValue = Math.max(
            Math.max(rgbaArray.get(i).get(j).get(0), rgbaArray.get(i).get(j).get(1)
            ), rgbaArray.get(i).get(j).get(2));
        valueCompImage.get(i).get(j).set(0, Math.min(255, Math.max(0, maxValue)));
        valueCompImage.get(i).get(j).set(1, Math.min(255, Math.max(0, maxValue)));
        valueCompImage.get(i).get(j).set(2, Math.min(255, Math.max(0, maxValue)));
      }
    }
    storage.put(dest, valueCompImage);
  }

  /**
   * Create an image that visualizes the intensity component of the image.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  @Override
  public void intensityComponent(String src, String dest) {
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray = storage.get(src);
    ArrayList<ArrayList<ArrayList<Integer>>> intensityImage = createRGBA(rgbaArray);
    for (int i = 0; i < getHeight(intensityImage); i += 1) {
      for (int j = 0; j < getWidth(intensityImage); j += 1) {
        double average = (rgbaArray.get(i).get(j).get(0)
            + rgbaArray.get(i).get(j).get(1)
            + rgbaArray.get(i).get(j).get(2)) / 3.0;
        intensityImage.get(i).get(j).set(0, (int) Math.min(255, Math.max(0, average)));
        intensityImage.get(i).get(j).set(1, (int) Math.min(255, Math.max(0, average)));
        intensityImage.get(i).get(j).set(2, (int) Math.min(255, Math.max(0, average)));
      }
    }
    storage.put(dest, intensityImage);
  }

  /**
   * Create an image that visualizes the luma component of the image.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  @Override
  public void lumaComponent(String src, String dest) {
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray = storage.get(src);
    ArrayList<ArrayList<ArrayList<Integer>>> lumaImage = createRGBA(rgbaArray);
    for (int i = 0; i < getHeight(lumaImage); i += 1) {
      for (int j = 0; j < getWidth(lumaImage); j += 1) {
        double weightedSum = 0.2126 * rgbaArray.get(i).get(j).get(0)
            + 0.7152 * rgbaArray.get(i).get(j).get(1)
            + 0.0722 * rgbaArray.get(i).get(j).get(2);
        lumaImage.get(i).get(j).set(0, (int) Math.min(255, Math.max(0, weightedSum)));
        lumaImage.get(i).get(j).set(1, (int) Math.min(255, Math.max(0, weightedSum)));
        lumaImage.get(i).get(j).set(2, (int) Math.min(255, Math.max(0, weightedSum)));
      }
    }
    storage.put(dest, lumaImage);
  }

  private void getColourComponent(int colour, String src, String dest) {
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray = storage.get(src);
    ArrayList<ArrayList<ArrayList<Integer>>> colourCompImage = createRGBA(rgbaArray);
    for (int i = 0; i < getHeight(colourCompImage); i += 1) {
      for (int j = 0; j < getWidth(colourCompImage); j += 1) {
        for (int c = 0; c < 3; c++) {
          if (c != colour) {
            colourCompImage.get(i).get(j).set(c, 0);
          }
        }
      }
    }
    storage.put(dest, colourCompImage);
  }

  /**
   * Create an image that visualizes the individual red component of the image.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  @Override
  public void redComponent(String src, String dest) {
    getColourComponent(0, src, dest);
  }

  /**
   * Create an image that visualizes the individual green component of the image.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  @Override
  public void greenComponent(String src, String dest) {
    getColourComponent(1, src, dest);
  }

  /**
   * Create an image that visualizes the individual blue component of the image.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  @Override
  public void blueComponent(String src, String dest) {
    getColourComponent(2, src, dest);
  }

  /**
   * Split a single image into three images representing each of the three channels.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  @Override
  public void rgbSplit(String src, String[] dest) {
    if (storage.get(src) == null) {
      throw new IllegalArgumentException("The image '" + src + "' does not exist.");
    }
    for (int i = 0; i < 3; i++) {
      String new_image = dest[i];
      getColourComponent(i, src, new_image);
    }
  }

  /**
   * Combine three greyscale image into a single color image whose R,G,B values come from the three
   * images.
   *
   * @param imageArray an array of source image names.
   * @param dest       the name of the destination image.
   */
  @Override
  public void rgbCombine(String[] imageArray, String dest) {
    for (int i = 0; i < 3; i++) {
      String src = imageArray[i];
      if (storage.get(src) == null) {
        throw new IllegalArgumentException("The image '" + src + "' does not exist.");
      }
    }
    if (storage.get(dest) == null) {
      storage.put(dest, new ArrayList<>());
    }
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray1 = storage.get(imageArray[0]);
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray2 = storage.get(imageArray[1]);
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray3 = storage.get(imageArray[2]);

    ArrayList<ArrayList<ArrayList<Integer>>> combinedImage = createRGBA(rgbaArray1);

    if ((getWidth(rgbaArray1) != getWidth(rgbaArray2)
        || getWidth(rgbaArray1) != getWidth(rgbaArray3))
        || (getHeight(rgbaArray1) != getHeight(rgbaArray2)
        || getHeight(rgbaArray1) != getHeight(rgbaArray3))) {
      throw new IllegalArgumentException("Width and height of images must match!");
    }
    for (int i = 0; i < getHeight(rgbaArray1); i += 1) {
      for (int j = 0; j < getWidth(rgbaArray1); j += 1) {
        combinedImage.get(i).get(j).set(0, rgbaArray1.get(i).get(j).get(0));
        combinedImage.get(i).get(j).set(1, rgbaArray2.get(i).get(j).get(1));
        combinedImage.get(i).get(j).set(2, rgbaArray3.get(i).get(j).get(2));
      }
    }
    storage.put(dest, combinedImage);
  }

  /**
   * Retrieves the pixel data of all three channels of the image.
   *
   * @param name the name of the image.
   * @return a three-dimensional array of pixels representing the image.
   */
  @Override
  public ArrayList<ArrayList<ArrayList<Integer>>> getRgbaArray(String name) {
    return storage.get(name);
  }

  /**
   * Chop the image vertically at the given percentage of the width of the original image.
   *
   * @param percentage the percentage of the width of the original image.
   * @param src        the name of the source image.
   * @param dest       the name of the destination image.
   */
  @Override
  public void chop(float percentage, String src, String dest) {
    checkSourceExists(src);
    if (percentage < 0 || percentage > 100) {
      throw new IllegalArgumentException("Invalid percentage!");
    }
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray = storage.get(src);
    ArrayList<ArrayList<ArrayList<Integer>>> choppedImage = new ArrayList<>();
    int height = rgbaArray.get(0).size();
    int width = rgbaArray.size();

    for (int x = 0; x < width * (percentage / 100); x++) {
      ArrayList<ArrayList<Integer>> rowList = new ArrayList<>();
      for (int y = 0; y < height; y++) {
        ArrayList<Integer> pixel = new ArrayList<>();
        pixel.add(rgbaArray.get(x).get(y).get(0));
        pixel.add(rgbaArray.get(x).get(y).get(1));
        pixel.add(rgbaArray.get(x).get(y).get(2));
        pixel.add(rgbaArray.get(x).get(y).get(3));
        rowList.add(pixel);
      }
      choppedImage.add(rowList);

    }
    storage.put(dest, choppedImage);
  }

  private void checkSourceExists(String src) {
    if (!storage.containsKey(src)) {
      throw new IllegalArgumentException("The specified image is not found: " + src);
    }
  }

  /**
   * Underlay the given image horizontally with the original image, so that only the resulting image
   * has the width of the given image.
   *
   * @param original the image whose part has to be stitched with the original image.
   * @param src      the name of the source image.
   * @param dest     the name of the destination image.
   */
  @Override
  public void underlay(String original, String src, String dest) {
    checkSourceExists(src);
    checkSourceExists(original);
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray = storage.get(src);
    ArrayList<ArrayList<ArrayList<Integer>>> originalRgbaArray = storage.get(original);

    int height = originalRgbaArray.get(0).size();
    int width = originalRgbaArray.size();
    ArrayList<ArrayList<ArrayList<Integer>>> stitchedImage = new ArrayList<>();
    for (int x = 0; x < width; x++) {
      ArrayList<ArrayList<Integer>> rowList = new ArrayList<>();
      for (int y = 0; y < height; y++) {
        ArrayList<Integer> pixel = new ArrayList<>();
        if (x > rgbaArray.size() - 1) {
          pixel.add(originalRgbaArray.get(x).get(y).get(0));
          pixel.add(originalRgbaArray.get(x).get(y).get(1));
          pixel.add(originalRgbaArray.get(x).get(y).get(2));
          pixel.add(originalRgbaArray.get(x).get(y).get(3));
        } else {
          pixel.add(rgbaArray.get(x).get(y).get(0));
          pixel.add(rgbaArray.get(x).get(y).get(1));
          pixel.add(rgbaArray.get(x).get(y).get(2));
          pixel.add(rgbaArray.get(x).get(y).get(3));
        }
        rowList.add(pixel);
      }
      stitchedImage.add(rowList);

    }
    storage.put(dest, stitchedImage);

  }

  private Graphics getGraphics(BufferedImage histogramImage) {
    Graphics2D graphics = (Graphics2D) histogramImage.getGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, histogramImage.getWidth(), histogramImage.getHeight());
    graphics.setColor(Color.LIGHT_GRAY); // You can choose a different color for the grid lines
    int gridSize = 16; // Adjust the grid size as needed

    for (int i = 0; i < histogramImage.getWidth(); i += gridSize) {
      graphics.drawLine(i, 0, i, histogramImage.getHeight());
    }

    for (int j = 0; j < histogramImage.getHeight(); j += gridSize) {
      graphics.drawLine(0, j, histogramImage.getWidth(), j);
    }
    return graphics;
  }

  /**
   * Produce an image that represents the histogram of the image.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  @Override
  public void histogram(String src, String dest) {
    checkSourceExists(src);
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray = storage.get(src);

    BufferedImage histogramImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
    System.setProperty("java.awt.headless", "true");

    final Graphics graphics = getGraphics(histogramImage);

    // Initialize arrays to store histogram data for each channel
    ArrayList<int[]> histogramArray = getHistogram(rgbaArray);

    // Find the maximum count for normalization
    int maxCount = Math.max(
        Math.max(maxValue(histogramArray.get(0)), maxValue(histogramArray.get(1))),
        maxValue(histogramArray.get(2)));

    // Draw histograms
    drawHistogram(graphics, histogramArray.get(0), Color.RED, maxCount);
    drawHistogram(graphics, histogramArray.get(1), Color.GREEN, maxCount);
    drawHistogram(graphics, histogramArray.get(2), Color.BLUE, maxCount);
    graphics.dispose();

    storage.put(dest, convertToArray(histogramImage));
  }


  private ArrayList<int[]> getHistogram(ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray) {
    int[] redHistogram = new int[256];
    int[] greenHistogram = new int[256];
    int[] blueHistogram = new int[256];

    for (int i = 0; i < rgbaArray.size(); i++) {
      for (int j = 0; j < rgbaArray.get(0).size(); j++) {
        int red = rgbaArray.get(i).get(j).get(0);
        int green = rgbaArray.get(i).get(j).get(1);
        int blue = rgbaArray.get(i).get(j).get(2);

        redHistogram[red]++;
        greenHistogram[green]++;
        blueHistogram[blue]++;
      }
    }
    ArrayList<int[]> result = new ArrayList<>();
    result.add(redHistogram);
    result.add(greenHistogram);
    result.add(blueHistogram);
    return result;


  }

  private void drawHistogram(java.awt.Graphics graphics, int[] chartData, Color color,
      int maxCount) {


    Graphics2D g2d = (Graphics2D) graphics;
    g2d.setColor(color);

    for (int i = 0; i < chartData.length - 1; i++) {
      int normalizedValue = (int) (((double) chartData[i] / (maxCount)) * 255);

      int y1 = 255 - normalizedValue;
      int x2 = i + 1;
      normalizedValue = (int) (((double) chartData[i+1] / (maxCount)) * 255);
      int y2 = 255 - normalizedValue;

      g2d.drawLine(i, y1, x2, y2);
    }
  }


  private int maxValue(int[] array) {
    int max = array[0];
    for (int value : array) {
      if (value > max) {
        max = value;
      }
    }
    return max;
  }

  protected ArrayList<ArrayList<ArrayList<Integer>>> convertToArray(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArrayList = new ArrayList<>();
    boolean flag = image.getColorModel().hasAlpha();

    for (int x = 0; x < width; x++) {
      ArrayList<ArrayList<Integer>> rowList = new ArrayList<>();
      for (int y = 0; y < height; y++) {
        ArrayList<Integer> pixel = new ArrayList<>();
        int rgb = image.getRGB(x, y);
        pixel.add((rgb >> 16) & 0xFF);  // Red component
        pixel.add((rgb >> 8) & 0xFF);   // Green component
        pixel.add(rgb & 0xFF);
        pixel.add(flag ? (rgb >> 24) & 0xFF : 255); // Alpha component
        rowList.add(pixel);
      }
      rgbaArrayList.add(rowList);
    }

    return rgbaArrayList;
  }

  /**
   * Color-corrects an image by aligning the meaningful peaks of its histogram.
   *
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  @Override
  public void colorCorrect(String src, String dest) {
    checkSourceExists(src);
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray = storage.get(src);
    ArrayList<int[]> histograms = getHistogram(rgbaArray);
    int redPeak = findPeak(histograms.get(0));
    int greenPeak = findPeak(histograms.get(1));
    int bluePeak = findPeak(histograms.get(2));

    // Compute average peak value
    int averagePeak = Math.round((float) (redPeak + greenPeak + bluePeak) / 3);
    int redAdjustment = averagePeak - redPeak;
    int greenAdjustment = averagePeak - greenPeak;
    int blueAdjustment = averagePeak - bluePeak;
    ArrayList<ArrayList<ArrayList<Integer>>> correctedArrayList = new ArrayList<>();
    for (ArrayList<ArrayList<Integer>> row : rgbaArray) {
      ArrayList<ArrayList<Integer>> correctedRow = new ArrayList<>();

      for (ArrayList<Integer> pixel : row) {
        int adjustedRed = adjustValue(pixel.get(0), redAdjustment);
        int adjustedGreen = adjustValue(pixel.get(1), greenAdjustment);
        int adjustedBlue = adjustValue(pixel.get(2), blueAdjustment);
        int alpha = pixel.get(3);

        ArrayList<Integer> correctedPixel = new ArrayList<>();
        correctedPixel.add(adjustedRed);
        correctedPixel.add(adjustedGreen);
        correctedPixel.add(adjustedBlue);
        correctedPixel.add(alpha);

        correctedRow.add(correctedPixel);
      }

      correctedArrayList.add(correctedRow);
    }

    storage.put(dest, correctedArrayList);
  }

  /**
   * Adjusts the levels of an image.
   *
   * @param b    the position of the black points on the horizontal axis.
   * @param m    the position of the mid-points on the horizontal axis.
   * @param w    the position of the white points on the horizontal axis.
   * @param src  the name of the source image.
   * @param dest the name of the destination image.
   */
  @Override
  public void levelAdjust(int b, int m, int w, String src, String dest) {
    checkSourceExists(src);
    if (b > 255 || m > 255 || w > 255 || b < 0 || m < 0 || w < 0 || b > m || m > w) {
      throw new IllegalArgumentException("Invalid b,m,w values");

    }
    if (storage.get(src) == null) {
      throw new IllegalArgumentException("The image '" + src + "' does not exist.");
    }
    if (storage.get(dest) == null) {
      storage.put(dest, new ArrayList<>());
    }

    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray = storage.get(src);
    ArrayList<ArrayList<ArrayList<Integer>>> correctedArrayList = new ArrayList<>();
    for (ArrayList<ArrayList<Integer>> row : rgbaArray) {
      ArrayList<ArrayList<Integer>> correctedRow = new ArrayList<>();

      for (ArrayList<Integer> pixel : row) {
        int adjustedRed = applyLevelAdj(b, m, w, pixel.get(0));
        int adjustedGreen = applyLevelAdj(b, m, w, pixel.get(1));
        int adjustedBlue = applyLevelAdj(b, m, w, pixel.get(2));
        int alpha = pixel.get(3);

        ArrayList<Integer> correctedPixel = new ArrayList<>();
        correctedPixel.add(adjustedRed);
        correctedPixel.add(adjustedGreen);
        correctedPixel.add(adjustedBlue);
        correctedPixel.add(alpha);

        correctedRow.add(correctedPixel);
      }
      correctedArrayList.add(correctedRow);
    }
    storage.put(dest, correctedArrayList);
  }

  private int applyLevelAdj(int b, int m, int w, int x) {
    double a = Math.pow(b, 2) * (m - w) - b * (Math.pow(m, 2)
        - Math.pow(w, 2)) + w * Math.pow(m, 2) - m * Math.pow(w, 2);
    double aA = -b * (128 - 255) + 128 * w - 255 * m;
    double aB = Math.pow(b, 2) * (128 - 255) + 255 * Math.pow(m, 2) - 128 * Math.pow(w, 2);
    double aC =
        Math.pow(b, 2) * (255 * m - 128 * w) - b * (255 * Math.pow(m, 2) - 128 * Math.pow(w, 2));
    double a_fraction = aA / a;
    double b_fraction = aB / a;
    double c_fraction = aC / a;
    return Math.min(255, Math.max(0, (int) (a_fraction * x * x + b_fraction * x + c_fraction)));

  }

  private int adjustValue(int originalValue, int adjustment) {
    return Math.min(255, Math.max(0, originalValue + adjustment));
  }

  private int findPeak(int[] arr) {
    int peakValue = 0;
    int peakIndex = 0;

    for (int i = 0; i < arr.length; i++) {
      if (i > 244 || i < 11) {
        continue;
      }
      if (arr[i] > peakValue) {
        peakValue = arr[i];
        peakIndex = i;
      }
    }

    return peakIndex;

  }

  private ArrayList<ArrayList<ArrayList<Integer>>> padRGBA3DArrayList(
      ArrayList<ArrayList<ArrayList<Integer>>> originalRGBA3DArrayList) {
    int paddedSize = 1;
    while (paddedSize < Math.max(originalRGBA3DArrayList.size(),
        originalRGBA3DArrayList.get(0).size())) {
      paddedSize *= 2;
    }
    ArrayList<ArrayList<Integer>> padding = new ArrayList<>();

    ArrayList<ArrayList<ArrayList<Integer>>> paddedSquareRGBA = new ArrayList<>();

    // Create a new padded RGBA 3D ArrayList with zeros
    ArrayList<ArrayList<ArrayList<Integer>>> paddedRGBA3DArrayList = new ArrayList<>();
    for (int i = 0; i < paddedSize - originalRGBA3DArrayList.get(0).size(); i++) {
      ArrayList<Integer> paddingPixel = new ArrayList<>(List.of(0, 0, 0, 0));
      padding.add(paddingPixel);
    }

    for (int i = 0; i < originalRGBA3DArrayList.size(); i++) {
      ArrayList<ArrayList<Integer>> paddedRow = new ArrayList<>();
      paddedRow.addAll(originalRGBA3DArrayList.get(i));
      paddedRow.addAll(padding);
      paddedRGBA3DArrayList.add(paddedRow);
    }
    ArrayList<ArrayList<Integer>> zeroArrayList2D = new ArrayList<>();

    // Initialize the ArrayList with zeros
    for (int i = 0; i < paddedSize; i++) {
      ArrayList<Integer> row = new ArrayList<>();
      for (int j = 0; j < 4; j++) {
        row.add(0);
      }
      zeroArrayList2D.add(row);
    }

    for (int i = originalRGBA3DArrayList.size(); i < paddedSize; i++) {
      paddedRGBA3DArrayList.add(zeroArrayList2D);
    }

    return paddedRGBA3DArrayList;
  }

  /**
   * Compresses an image by the provided percentage.
   *
   * @param percent the percentage of compression desired.
   * @param src     the name of the source image.
   * @param dest    the name of the destination image.
   */
  @Override
  public void compress(float percent, String src, String dest) {
    checkSourceExists(src);
    if (percent < 0 || percent > 100) {
      throw new IllegalArgumentException("Illegal percentage!");
    }
    if (storage.get(src) == null) {
      throw new IllegalArgumentException("The image '" + src + "' does not exist.");
    }
    if (storage.get(dest) == null) {
      storage.put(dest, new ArrayList<>());
    }
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray = storage.get(src);
    ArrayList<ArrayList<ArrayList<Integer>>> paddedRGBA = new ArrayList<>();
    //do padding
    paddedRGBA = padRGBA3DArrayList(rgbaArray);
    List<List<Integer>>[] colorComponentsArray = new List[4];
    //get color components
    for (int i = 0; i < 4; i++) {
      colorComponentsArray[i] = getColorComp(paddedRGBA, i);
    }
    List<List<Double>>[] compressed = new List[3];

    //get harr
    for (int i = 0; i < 3; i++) {
      compressed[i] = haar(colorComponentsArray[i]);
      ArrayList<Double> flattenedList = new ArrayList<>();
      //create list to find threshold
      for (List<Double> row : compressed[i]) {
        for (double val : row) {
          flattenedList.add(Math.abs(val));

        }
      }
      //unique absolute values
      Set<Double> uniqueSet = new HashSet<>(flattenedList);
      flattenedList = new ArrayList<>(uniqueSet);
      Collections.sort(flattenedList);
      int indx = (int) ((percent / 100) * flattenedList.size());
      double threshold = flattenedList.get(indx);
      applyThreshold(compressed[i], threshold);
    }

    for (int i = 0; i < 3; i++) {
      inverseHaar(compressed[i]);
    }

    for (int i = 0; i < 3; i++) {
      colorComponentsArray[i] = unpadAndRound(compressed[i], getWidth(rgbaArray),
          getHeight(rgbaArray));
    }
    storage.put(dest, combineChannels(colorComponentsArray[0],
        colorComponentsArray[1], colorComponentsArray[2], colorComponentsArray[3]));
  }

  private ArrayList<ArrayList<ArrayList<Integer>>> combineChannels(
      List<List<Integer>> red, List<List<Integer>> green,
      List<List<Integer>> blue, List<List<Integer>> alpha) {
    int height = red.size();
    int width = red.get(0).size();

    ArrayList<ArrayList<ArrayList<Integer>>> arr = new ArrayList<>();

    for (int i = 0; i < height; i++) {
      ArrayList<ArrayList<Integer>> row = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        ArrayList<Integer> pixel = new ArrayList<>();
        pixel.add(red.get(i).get(j));
        pixel.add(green.get(i).get(j));
        pixel.add(blue.get(i).get(j));
        pixel.add(alpha.get(i).get(j));
        row.add(pixel);
      }
      arr.add(row);
    }

    return arr;
  }

  private List<List<Integer>> unpadAndRound(List<List<Double>> paddedArray, int width, int height) {
    int originalSize = paddedArray.size();
    List<List<Integer>> originalArray = new ArrayList<>();

    for (int i = 0; i < height; i++) {
      List<Integer> row = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        row.add(Math.min(255, Math.max(0, (int) Math.round(paddedArray.get(i).get(j)))));
      }
      originalArray.add(row);
    }

    return originalArray;
  }

  private void applyThreshold(List<List<Double>> twoDList, Double threshold) {
    //Absolute
    for (List<Double> row : twoDList) {
      for (int i = 0; i < row.size(); i++) {
        if (Math.abs(row.get(i)) < threshold) {
          row.set(i, 0.0);
        }
      }
    }
  }

  private List<List<Integer>> getColorComp(
      ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray, int color) {
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < rgbaArray.size(); i += 1) {
      ArrayList<Integer> row = new ArrayList<>();
      for (int j = 0; j < rgbaArray.get(0).size(); j += 1) {
        row.add(rgbaArray.get(i).get(j).get(color));
      }
      result.add(row);

    }
    return result;

  }

  private List<List<Double>> haar(List<List<Integer>> inp) {
    int c = inp.size();
    List<List<Double>> x = new ArrayList<>();
    for (List<Integer> row : inp) {
      ArrayList<Double> doubleRow = new ArrayList<>();
      for (Integer value : row) {
        doubleRow.add((double) value); // Cast the integer to double
      }
      x.add(doubleRow);
    }
    //print(X);

    while (c > 1) {
      for (int i = 0; i < c; i++) {
        x.set(i, transform(x.get(i)));
      }
      //print(X);

      // Transform columns
      for (int j = 0; j < c; j++) {
        List<Double> column = new ArrayList<>();
        for (int i = 0; i < c; i++) {
          column.add(i, x.get(i).get(j));
        }
        column = transform(column);
        for (int i = 0; i < c; i++) {
          x.get(i).set(j, column.get(i));

        }
      }

      c /= 2;
    }

    return x;
  }

  private void inverseHaar(List<List<Double>> x) {
    int rows = x.size();

    int c = 2;
    while (c <= rows) {

      for (int j = 0; j < c; j++) {
        List<Double> column = new ArrayList<>();
        for (int i = 0; i < c; i++) {
          column.add(i, x.get(i).get(j));
        }
        column = inverseTransform(column);
        for (int i = 0; i < c; i++) {
          x.get(i).set(j, column.get(i));
        }
      }
      for (int i = 0; i < c; i++) {
        x.set(i, inverseTransform(x.get(i)));
      }
      c *= 2;
    }

  }

  private ArrayList<Double> transform(List<Double> s) {
    ArrayList<Double> avg = new ArrayList<>();
    ArrayList<Double> diff = new ArrayList<>();

    for (int i = 0; i < s.size() - 1; i += 2) {
      double a = s.get(i);
      double b = s.get(i + 1);
      double av = (a + b) / Math.sqrt(2);
      double dif = (a - b) / Math.sqrt(2);

      avg.add(av);
      diff.add(dif);
    }

    ArrayList<Double> result = new ArrayList<>(avg);
    result.addAll(diff);

    return result;
  }

  private List<Double> inverseTransform(List<Double> s) {
    int middle = s.size() / 2;

    List<Double> avg = s.subList(0, middle);
    List<Double> diff = s.subList(middle, s.size());

    List<Double> originalArray = new ArrayList<>();

    for (int i = 0; i < avg.size(); i++) {
      double av = avg.get(i);
      double de = diff.get(i);

      double a = (av + de) / Math.sqrt(2);
      double b = (av - de) / Math.sqrt(2);

      originalArray.add(a);
      originalArray.add(b);
    }

    return originalArray;
  }


  int getHeight(ArrayList<ArrayList<ArrayList<Integer>>> image) {
    if (!image.isEmpty()) {
      return image.size();
    } else {
      return 0;
    }
  }

  int getWidth(ArrayList<ArrayList<ArrayList<Integer>>> image) {
    if (!image.isEmpty() && !image.get(0).isEmpty()) {
      return image.get(0).size();
    } else {
      return 0;
    }
  }


}
