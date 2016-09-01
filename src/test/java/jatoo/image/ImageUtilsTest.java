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

package jatoo.image;

import java.awt.image.BufferedImage;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ImageUtilsTest {

  private static BufferedImage IMAGE;
  private static BufferedImage IMAGE_VERTICAL;

  @BeforeClass
  public static void beforeClass() throws Exception {
    IMAGE = ImageUtils.read(ImageUtilsTest.class.getResource("image.png"));
    IMAGE_VERTICAL = ImageUtils.read(ImageUtilsTest.class.getResource("imageVertical.png"));
  }

  @AfterClass
  public static void afterClass() {}

  @Before
  public void before() {}

  @After
  public void after() {}

  @Test
  public void testResizeToFit() throws Exception {

    BufferedImage image1 = ImageUtils.resizeToFit(IMAGE, 100);
    Assert.assertEquals(100, image1.getWidth());

    BufferedImage image2 = ImageUtils.resizeToFit(IMAGE_VERTICAL, 100);
    Assert.assertEquals(100, image2.getHeight());
  }

  @Test
  public void testResizeToFill() throws Exception {

    BufferedImage image1 = ImageUtils.resizeToFill(IMAGE, 100);
    Assert.assertEquals(100, image1.getWidth());
    Assert.assertEquals(100, image1.getHeight());

    BufferedImage image2 = ImageUtils.resizeToFill(IMAGE_VERTICAL, 100);
    Assert.assertEquals(100, image2.getHeight());
    Assert.assertEquals(100, image2.getWidth());
  }

  @Test
  public void testRotate() throws Exception {

    BufferedImage image1 = ImageUtils.rotate(IMAGE, 90);
    Assert.assertEquals(IMAGE.getHeight(), image1.getWidth());
    Assert.assertEquals(IMAGE.getWidth(), image1.getHeight());

    BufferedImage image2 = ImageUtils.rotate(IMAGE_VERTICAL, 90);
    Assert.assertEquals(IMAGE_VERTICAL.getHeight(), image2.getWidth());
    Assert.assertEquals(IMAGE_VERTICAL.getWidth(), image2.getHeight());
  }

  @Test
  public void testFlip() throws Exception {

    BufferedImage image1 = ImageUtils.flipHorizontally(IMAGE);
    Assert.assertEquals(IMAGE.getHeight(), image1.getHeight());
    Assert.assertEquals(IMAGE.getWidth(), image1.getWidth());

    BufferedImage image2 = ImageUtils.flipVertically(IMAGE_VERTICAL);
    Assert.assertEquals(IMAGE_VERTICAL.getHeight(), image2.getHeight());
    Assert.assertEquals(IMAGE_VERTICAL.getWidth(), image2.getWidth());
  }

}
