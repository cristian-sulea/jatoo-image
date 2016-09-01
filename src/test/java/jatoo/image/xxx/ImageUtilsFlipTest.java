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
