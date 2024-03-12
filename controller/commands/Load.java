package controller.commands;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.imageio.ImageIO;
import model.Extension;
import model.IModel;

/**
 * The Load class represents a command that performs a load operation on an image.
 */
public class Load extends AbstractCommand {


  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] temp = params.split(" ");
    String name = temp[temp.length - 1];
    String[] temp1 = Arrays.copyOfRange(temp, 0, temp.length - 1);
    String filePath = String.join(" ", temp1);
    Object im;
    File file = new File(filePath);

    if (!file.isAbsolute()) {
      String currentDir = System.getProperty("user.dir");
      file = new File(currentDir, filePath);
    }
    filePath = file.getAbsolutePath();
    if (!(new File(filePath).isFile() && validFile(filePath))) {
      throw new FileNotFoundException("The specified file in load not valid: " + filePath);
    }

    Extension extension = getExtension(filePath);
    BufferedImage inputImage;
    ArrayList<ArrayList<ArrayList<Integer>>> rgbaArray = null;

    if (extension != Extension.UNKNOWN) {
      if (extension == Extension.PPM) {
        rgbaArray = readPPM(filePath);
      } else {
        inputImage = ImageIO.read(new File(filePath));
        rgbaArray = convertToArray(inputImage);
      }
    }
    model.load(name, rgbaArray);
  }

  private ArrayList<ArrayList<ArrayList<Integer>>> readPPM(String filePath) throws IOException {

    Scanner sc = new Scanner(new FileInputStream(filePath));

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }
    sc = new Scanner(builder.toString());
    String token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
      return null;
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    sc.nextInt();
    int[][][] image = new int[width][height][4];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        image[x][y][0] = sc.nextInt();
        image[x][y][1] = sc.nextInt();
        image[x][y][2] = sc.nextInt();
        image[x][y][3] = 255;
      }
    }

    return convertArrayToArrayList(image);


  }

  private ArrayList<ArrayList<ArrayList<Integer>>> convertArrayToArrayList(int[][][] array3D) {
    ArrayList<ArrayList<ArrayList<Integer>>> arrayList3D = new ArrayList<>();

    for (int[][] array2D : array3D) {
      ArrayList<ArrayList<Integer>> arrayList2D = new ArrayList<>();
      for (int[] array1D : array2D) {
        ArrayList<Integer> arrayList1D = new ArrayList<>();
        for (int value : array1D) {
          arrayList1D.add(value);
        }
        arrayList2D.add(arrayList1D);
      }
      arrayList3D.add(arrayList2D);
    }

    return arrayList3D;
  }

}



