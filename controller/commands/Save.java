package controller.commands;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import model.Extension;
import model.IModel;

/**
 * The Save class represents a command that performs a save operation on an image.
 */
public class Save extends AbstractCommand {


  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] temp = params.split(" ");
    String name = temp[temp.length - 1];
    String[] temp1 = Arrays.copyOfRange(temp, 0, temp.length - 1);
    String filePath = String.join(" ", temp1);
    File file = new File(filePath);

    if (!file.isAbsolute()) {
      String currentDir = System.getProperty("user.dir");
      file = new File(currentDir, filePath);
    }
    filePath = file.getAbsolutePath();

    if (!validFile(filePath)) {
      throw new FileNotFoundException("The specified file in load not valid: " + filePath);
    }
    Extension extension = getExtension(filePath);
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray = model.getRgbaArray(name);

    BufferedImage outputImage;
    if (extension != Extension.UNKNOWN) {
      if (extension == Extension.PPM) {
        writePPM(filePath, rgbaArray);
      } else if (extension == Extension.PNG) {
        outputImage = convertToBufferImagePNG(rgbaArray);
        ImageIO.write(outputImage, extension.getExtension(), new File(filePath));
      } else {
        outputImage = convertToBufferedImageOther(rgbaArray);
        ImageIO.write(outputImage, extension.getExtension(), new File(filePath));

      }

    }

  }

  private BufferedImage convertToBufferedImageOther(
      ArrayList<ArrayList<ArrayList<Integer>>> rgbaArrayList) {
    int width = rgbaArrayList.size();
    int height = rgbaArrayList.get(0).size();
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        ArrayList<Integer> pixel = rgbaArrayList.get(x).get(y);
        int red = pixel.get(0);
        int green = pixel.get(1);
        int blue = pixel.get(2);
        int rgb = (red << 16) | (green << 8) | blue;
        image.setRGB(x, y, rgb);
      }
    }
    return image;
  }

  private BufferedImage convertToBufferImagePNG(
      ArrayList<ArrayList<ArrayList<Integer>>> rgbaArrayList) {
    int width = rgbaArrayList.size();
    int height = rgbaArrayList.get(0).size();
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        ArrayList<Integer> pixel = rgbaArrayList.get(x).get(y);
        int alpha = pixel.get(3);
        int red = pixel.get(0);
        int green = pixel.get(1);
        int blue = pixel.get(2);
        int rgba = (alpha << 24) | (red << 16) | (green << 8) | blue;
        image.setRGB(x, y, rgba);
      }
    }
    return image;
  }

  private void writePPM(String ppmFilePath, ArrayList<ArrayList<ArrayList<Integer>>> rgbArray)
      throws IOException {
    // The try-with-resources statement automatically closes the file when the block is exited,
    // even if an exception is thrown
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(ppmFilePath))) {
      int height = rgbArray.get(0).size();
      int width = rgbArray.size();
      int maxValue = 255; // Maximum color value

      // Write the PPM header
      writer.write("P3\n");
      writer.write(width + " " + height + "\n");
      writer.write(maxValue + "\n");

      // Write RGB values from the array
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          ArrayList<Integer> pixel = rgbArray.get(x).get(y);
          int red = pixel.get(0);
          int green = pixel.get(1);
          int blue = pixel.get(2);
          writer.write(red + "\n" + green + "\n" + blue + "\n");
        }
      }
    }
  }


}

