package view;

import controller.Features;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.IROModel;

public class GUIView extends JFrame implements IGUIView{

  private IROModel rom;

  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;
  private JLabel imageLabel;

  private final JPanel mainPanel;

  private final JScrollPane mainScrollPane;

  private final JButton blurButton;
  private final JButton sharpenButton;
  private final JButton brightenButton;
  private final JButton lumaButton;
  private final JButton openFileButton;
  private final JButton saveFileButton;

  public GUIView(IROModel rom) throws IOException {
    this.rom = rom;
    setVisible(true);
    setTitle("GRIME");
    setSize(600, 400);

    //main panel
    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    //menu panel
    JPanel menuPanel = new JPanel();
    menuPanel.setBorder(BorderFactory.createTitledBorder("Operations Menu"));
    menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.PAGE_AXIS));

    //blur button
    blurButton = new JButton("Blur");
    blurButton.setActionCommand("Blur");

    //sharpen button
    sharpenButton = new JButton("Sharpen");
    sharpenButton.setActionCommand("Sharpen");

    //brighten button
    brightenButton = new JButton("Brighten");
    brightenButton.setActionCommand("Brighten");

    //luma button
    lumaButton = new JButton("Luma");
    lumaButton.setActionCommand("Luma");

    menuPanel.add(blurButton);
    menuPanel.add(sharpenButton);
    menuPanel.add(brightenButton);
    menuPanel.add(lumaButton);

    mainPanel.add(menuPanel);

    //top panel
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

    //open file button
    openFileButton = new JButton("Open File");
    openFileButton.setActionCommand("Open File");

    //save file button
    saveFileButton = new JButton("Save File");
    saveFileButton.setActionCommand("Save File");

    topPanel.add(openFileButton);
    topPanel.add(saveFileButton);

    add(topPanel, BorderLayout.NORTH);



    //image panel
    JPanel imagePanel = new JPanel();
    imageLabel = new JLabel();
    imagePanel.add(imageLabel);
    mainPanel.add(imagePanel);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

//  @Override
//  public void actionPerformed(ActionEvent e) {
//    switch (e.getActionCommand()) {
//      case "Blur":
//        // Implement blur action
//        break;
//      case "Open File": {
//        final JFileChooser fchooser = new JFileChooser(".");
//        FileNameExtensionFilter filter = new FileNameExtensionFilter(
//            "JPG, PNG, and PPM Images", "jpg", "png", "ppm");
//        fchooser.setFileFilter(filter);
//        int retvalue = fchooser.showOpenDialog(GUIView.this);
//        if (retvalue == JFileChooser.APPROVE_OPTION) {
//          File f = fchooser.getSelectedFile();
//          fileOpenDisplay = new JLabel();
//          fileOpenDisplay.setText(f.getAbsolutePath());
//          System.out.println(f.getAbsolutePath());
//
//          try {
//            BufferedImage newImage = ImageIO.read(f);
//            ImageIcon newIcon = new ImageIcon(newImage);
//            imageLabel.setIcon(newIcon);
//          } catch (IOException ex) {
//            ex.printStackTrace();
//          }
//        }
//      }
//      break;
//      case "Save File": {
//        final JFileChooser fchooser = new JFileChooser(".");
//        int retvalue = fchooser.showSaveDialog(GUIView.this);
//        if (retvalue == JFileChooser.APPROVE_OPTION) {
//          File f = fchooser.getSelectedFile();
//          fileSaveDisplay = new JLabel("File path will appear here");
//          fileSaveDisplay.setText(f.getAbsolutePath());
//        }
//      }
//      break;
//    }
//  }

  @Override
  public void showImage(String imageName) {
    System.out.println(imageName);
      BufferedImage newImage = rom.getBufferedImage(imageName);
      ImageIcon newIcon = new ImageIcon(newImage);
      imageLabel.setIcon(newIcon);
  }

  @Override
  public void showHistogram(String imageName) {

  }

  @Override
  public void addFeatures(Features features) {
    blurButton.addActionListener(evt -> {
      try {
        features.blur();
      } catch (IOException | InstantiationException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    });
    sharpenButton.addActionListener(evt -> {
      try {
        features.sharpen();
      } catch (IOException | InstantiationException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    });
    brightenButton.addActionListener(evt -> {
      try {
        features.brighten(90);
      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (InstantiationException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    });
    lumaButton.addActionListener(evt -> {
      try {
        features.lumaComponent();
      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (InstantiationException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    });
//    openFileButton.addActionListener(evt -> {
//      try {
//        features.load("res/nature.png");
//      } catch (IOException e) {
//        throw new RuntimeException(e);
//      } catch (InstantiationException e) {
//        throw new RuntimeException(e);
//      } catch (IllegalAccessException e) {
//        throw new RuntimeException(e);
//      }
//    });
    openFileButton.addActionListener(evt -> {
      JFileChooser fchooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
          "JPG, PNG, and PPM Images", "jpg", "png", "ppm");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showOpenDialog(null);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        fileOpenDisplay = new JLabel();
        fileOpenDisplay.setText(f.getAbsolutePath());
        try {
          BufferedImage newImage = ImageIO.read(f);
          ImageIcon newIcon = new ImageIcon(newImage);
          imageLabel.setIcon(newIcon);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
      try {
        features.load(fileOpenDisplay.getText());
      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (InstantiationException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    });
    saveFileButton.addActionListener(evt -> {
      try {
        features.save("res/new.png");
      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (InstantiationException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    });

  }

  @Override
  public void printNextPrompt(boolean script) throws IOException {

  }

  @Override
  public void showError(String message) throws IOException {

  }
}