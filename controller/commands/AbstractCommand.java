package controller.commands;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import model.Extension;

/**
 * The AbstractCommand class is an abstract base class for implementing image processing commands.
 * It provides common functionality and helper methods that can be used by concrete command
 * implementations.
 */
public abstract class AbstractCommand implements Command {

  protected String[] componentHelper(String params)
      throws InstantiationException, IllegalAccessException {
    String[] temp = params.split(" ");
    if (temp.length != 2) {
      throw new IllegalArgumentException("Illegal Number of Parameters " + params);
    }
    String original_image = temp[0];
    String new_image = temp[1];
    return new String[]{original_image, new_image};
  }

  protected Extension getExtension(String filePath) throws IOException {
    String fileExtension = getExtensionHelper(filePath);
    if (Extension.fromExtension(fileExtension) != Extension.UNKNOWN) {
      return Extension.fromExtension(fileExtension);
    }
    FileInputStream fileInputStream = new FileInputStream(filePath);
    byte[] headerBytes = new byte[2];
    int bytesRead = fileInputStream.read(headerBytes);

    if (bytesRead != 2) {
      // Not enough data to identify the format
      return Extension.UNKNOWN;
    }

    // Convert the header bytes to a string
    String header = new String(headerBytes, StandardCharsets.UTF_8);

    if (header.equals("P3") || header.equals("P6")) {
      return Extension.PPM;
    } else if (header.equals("BM")) {
      return Extension.BMP;
    } else if (header.equals("\u0089PNG\r\n\u001a\n")) {
      return Extension.PNG;
    } else if (header.equals("GIF87a") || header.equals("GIF89a")) {
      return Extension.GIF;
    } else {
      return Extension.UNKNOWN;
    }
  }

  private String getExtensionHelper(String filePath) {
    int lastIndex = filePath.lastIndexOf('.');
    if (lastIndex > 0 && lastIndex < filePath.length() - 1) {
      return filePath.substring(lastIndex + 1).toLowerCase();
    }
    return null;
  }

  protected boolean validFile(String filepath) {
    File directory = new File(filepath).getParentFile();
    return (directory != null && directory.exists() && directory.isDirectory());
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


}
