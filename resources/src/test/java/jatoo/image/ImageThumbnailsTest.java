package jatoo.image;

import java.awt.image.BufferedImage;
import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ImageThumbnailsTest {

  private static final File THUMBNAILS_FOLDER = new File(new File("target"), ".thumbnails");
  private static final ImageThumbnails THUMBNAILS = new ImageThumbnails(THUMBNAILS_FOLDER);

  @BeforeClass
  public static void beforeClass() {

    Assert.assertTrue(THUMBNAILS_FOLDER.exists());

    THUMBNAILS.clear();
    Assert.assertEquals(0, THUMBNAILS_FOLDER.list().length);
  }

  @AfterClass
  public static void afterClass() {

    THUMBNAILS.clear();
    Assert.assertEquals(0, THUMBNAILS_FOLDER.list().length);

    THUMBNAILS_FOLDER.delete();
    Assert.assertFalse(THUMBNAILS_FOLDER.exists());
  }

  @Before
  public void before() {}

  @After
  public void after() {}

  @Test
  public void test() throws Exception {

    THUMBNAILS.clear();

    BufferedImage thumb1 = THUMBNAILS.get(new File("src\\test\\resources\\jatoo\\image\\image.png"), 100);
    Assert.assertEquals(1, THUMBNAILS_FOLDER.list().length);
    Assert.assertEquals(100, thumb1.getWidth());
    Assert.assertEquals(100, thumb1.getHeight());

    THUMBNAILS.get(new File("src\\test\\resources\\jatoo\\image\\imageVertical.png"), 100);
    Assert.assertEquals(1, THUMBNAILS_FOLDER.list().length);
    Assert.assertEquals(2, new File(THUMBNAILS_FOLDER, "100_100").list().length);

    BufferedImage thumb3 = THUMBNAILS.get(new File("src\\test\\resources\\jatoo\\image\\image.png"), 200);
    Assert.assertEquals(2, THUMBNAILS_FOLDER.list().length);
    Assert.assertEquals(1, new File(THUMBNAILS_FOLDER, "200_200").list().length);
    Assert.assertEquals(200, thumb3.getWidth());
    Assert.assertEquals(200, thumb3.getHeight());
  }

}
