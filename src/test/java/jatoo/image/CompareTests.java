package jatoo.image;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CompareTests {

  private static BufferedImage IMAGE;

  @BeforeClass
  public static void beforeClass() throws Throwable {
    IMAGE = ImageUtils.read(CompareTests.class.getResource("compare/test.jpg"));
  }

  @AfterClass
  public static void afterClass() {}

  @Before
  public void before() {}

  @After
  public void after() {}

  @Test
  public void testCompare1() throws Throwable {

    BufferedImage image1 = IMAGE;
    BufferedImage image2 = ImageUtils.read(CompareTests.class.getResource("compare/test1.jpg"));

    List<Rectangle> changes = ImageUtils.compare(image1, image2);
    Assert.assertEquals(19, changes.size());

    List<Rectangle> changesMerged = ImageUtils.compare(image1, image2, true);
    Assert.assertEquals(1, changesMerged.size());
  }

  @Test
  public void testCompare2() throws Throwable {

    BufferedImage image1 = IMAGE;
    BufferedImage image2 = ImageUtils.read(CompareTests.class.getResource("compare/test2.jpg"));

    List<Rectangle> changes = ImageUtils.compare(image1, image2);
    Assert.assertEquals(21, changes.size());

    List<Rectangle> changesMerged = ImageUtils.compare(image1, image2, true);
    Assert.assertEquals(2, changesMerged.size());
  }

  @Test
  public void testCompare3() throws Throwable {

    BufferedImage image1 = IMAGE;
    BufferedImage image2 = ImageUtils.read(CompareTests.class.getResource("compare/test3.jpg"));

    List<Rectangle> changes = ImageUtils.compare(image1, image2);
    Assert.assertEquals(46, changes.size());

    List<Rectangle> changesMerged = ImageUtils.compare(image1, image2, true);
    Assert.assertEquals(1, changesMerged.size());
  }

}
