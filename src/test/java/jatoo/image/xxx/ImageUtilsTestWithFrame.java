/*
 * Copyright (C) Cristian Sulea ( http://cristian.sulea.net )
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package jatoo.image.xxx;

import jatoo.image.ImageUtils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImageUtilsTestWithFrame {

  public static void main(String[] args) throws Exception {

    final BufferedImage image = ImageUtils.read(new File("C:\\Temp\\x\\IMG_8791.JPG"));
    
    JPanel panel = new JPanel() {
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //image = ImageUtils.resizeToFit(image, getWidth() - 1, getHeight() - 1);
        //image = ImageUtilsOldMethods.resize(image, getWidth() - 1, getHeight() - 1, true);
//        image = ImageUtilsOldMethods.resize(image, 100, 100, true);

//        g.drawImage(image, 0, 0, null);
        
//        Dimension d = getSize();
//        Dimension d1 = ImageUtils.calculateSizeToFit(image, getWidth(), getHeight());
//        Dimension d2 = ImageUtils.calculateSizeToFill(image, getWidth(), getHeight());
//        
//        System.out.println(d);
//        System.out.println(d1);
//        System.out.println(d2);
//        System.out.println();
        
//        g.drawImage(image, 0, 0, d2.width - 1, d2.height- 1, null);
//        g.drawImage(ImageUtils.resizeTo(false, image, getWidth(), getHeight()), 0, 0, null);
        g.drawImage(ImageUtils.resizeToFit(image, getWidth(), getHeight()), 0, 0, null);
//        g.drawImage(ImageUtils.resizeToFill(image, getWidth(), getHeight()), 0, 0, null);
      }
    };
    panel.setBackground(Color.LIGHT_GRAY);

    JFrame frame = new JFrame("ImageUtils Test");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(panel);
    frame.setSize(300, 300);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    
    ImageUtils.writeGIF(image, new File("image2.gif"));
    ImageUtils.writeJPEG(image, new File("image2.jpg"));
    ImageUtils.writeJPEG(image, new File("image2.jpg"), 75);
    ImageUtils.writePNG(image, new File("image3.jpg"));
  }

}
