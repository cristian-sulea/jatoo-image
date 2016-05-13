package jatoo.image.xxx;

import jatoo.image.ImageUtils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImageUtilsFlipTest {

  public static void main(String[] args) throws Exception {

    final BufferedImage image = ImageUtils.resizeToFit(ImageUtils.read(new File("C:\\Temp\\x\\IMG_8791.JPG")), 300);

    JPanel panel = new JPanel() {
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageUtils.flipHorizontally(image), 0, 0, null);
      }
    };

    JFrame frame = new JFrame("ImageUtils Test");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(panel);
    frame.setSize(400, 400);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

}
