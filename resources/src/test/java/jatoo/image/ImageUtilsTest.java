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
