package jatoo.image.xxx;

import jatoo.image.ImageUtils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImageUtilsRotateTest {

  public static void main(String[] args) throws Exception {

    final BufferedImage image = ImageUtils.resizeToFit(ImageUtils.read(ImageUtilsRotateTest.class.getResource("image.png")), 300);

    JPanel panel = new JPanel() {
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageUtils.rotate(image, 45, Color.GRAY), 0, 0, null);
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
