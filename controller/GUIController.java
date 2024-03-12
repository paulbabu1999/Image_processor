package controller;

import java.io.IOException;
import model.IModel;
import view.IGUIView;

public class GUIController extends IMEController implements Features {
  private int index;
  private IGUIView IGUIView;


  /**
   * Constructs an `controller.IMEController` with the specified input source, view, and model class.
   *
   * @param view        The view for displaying information.
   * @param model       The model class for image processing.
   */
  public GUIController(IGUIView view, IModel model) {

    super(view,model);
    this.index=0;

    this.IGUIView =view;
  }
  public void setView() {
    //provide view with all the callbacks
    IGUIView.addFeatures(this);
  }

  @Override
  public void brighten(float factor)
      throws IOException, InstantiationException, IllegalAccessException {
    runCommand("brighten "+factor+" "+index+" "+ ++index);
    show();
  }

  @Override
  public void verticalFlip() throws IOException, InstantiationException, IllegalAccessException {
    runCommand("vertical-flip "+index+" "+ ++index);
    show();

  }
  @Override
  public void horizontalFlip() throws IOException, InstantiationException, IllegalAccessException {
    runCommand("horizontal-flip " + index + " " + ++index);
    show();
  }

  @Override
  public void sepia() throws IOException, InstantiationException, IllegalAccessException {
    runCommand("sepia " + index + " " + ++index);
    show();
  }

  @Override
  public void blur() throws IOException, InstantiationException, IllegalAccessException {
    String command="blur " + index + " " + ++index;
    System.out.println(command);
    runCommand(command);
    show();
  }

  @Override
  public void sharpen() throws IOException, InstantiationException, IllegalAccessException {
    runCommand("sharpen " + index + " " + ++index);
    show();
  }

  @Override
  public void lumaComponent() throws IOException, InstantiationException, IllegalAccessException {
    runCommand("luma-component " + index + " " + ++index);
    show();
  }

  @Override
  public void redComponent() throws IOException, InstantiationException, IllegalAccessException {
    runCommand("red-component " + index + " " + ++index);
    show();
  }

  @Override
  public void greenComponent() throws IOException, InstantiationException, IllegalAccessException {
    runCommand("green-component " + index + " " + ++index);
    show();
  }

  @Override
  public void blueComponent() throws IOException, InstantiationException, IllegalAccessException {
    runCommand("blue-component " + index + " " + ++index);
    show();
  }

  @Override
  public void colorCorrect() throws IOException, InstantiationException, IllegalAccessException {
    runCommand("color-correct " + index + " " + ++index);
    show();
  }

  @Override
  public void levelAdjust(int b, int m, int w) throws IOException, InstantiationException, IllegalAccessException {
    runCommand("levels-adjust " + b + " " + m + " " + w + " " + index + " " + ++index);
    show();
  }

  @Override
  public void compress(float percent) {
    try {
      runCommand("compress " + percent + " " + index + " " + ++index);


      show();

    }
    catch (Exception ignored){

    }


  }


  @Override
  public void load(String path) throws IOException, InstantiationException, IllegalAccessException {
    runCommand("load "+path +" "+ index);
   show();

  }

  @Override
  public void save(String path) throws IOException, InstantiationException, IllegalAccessException {
    runCommand("save "+path +" "+ index);



  }

  @Override
  public void split(float percentage)
      throws IOException, InstantiationException, IllegalAccessException {
    runCommand("chop "+percentage+" "+index + ++index);
    runCommand("underlay "+ index +" "+ (index-1));
    IGUIView.showImage(String.valueOf(index));
    IGUIView.showHistogram("h-"+(index-1));




  }
  private void show() throws IOException, InstantiationException, IllegalAccessException {
    IGUIView.showImage(String.valueOf(index));
    runCommand("histogram "+ index+" "+"h-"+index);
    IGUIView.showHistogram("h-"+index);

  }

}
