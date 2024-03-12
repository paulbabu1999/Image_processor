package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ROModel implements IROModel{
  private IModel m;

  public ROModel(IModel m) {
    this.m=m;


  }

  @Override
  public BufferedImage getBufferedImage(String imageName) {
    return convertToBufferImagePNG(m.getRgbaArray(imageName));
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
}
