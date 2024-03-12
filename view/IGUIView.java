package view;

import controller.Features;

public interface IGUIView extends View{

  public void showImage(String imageName);
  public void showHistogram(String imageName);

  void addFeatures(Features features);

}