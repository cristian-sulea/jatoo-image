/*
 * Copyright (C) Cristian Sulea ( http://cristian.sulea.net )
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jatoo.image;

import java.awt.image.BufferedImage;
import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ImageCacheFileTest {

  private static final File CACHE_FOLDER = new File("target", ".cache");
  private static final ImageCacheFile CACHE = new ImageCacheFile(CACHE_FOLDER);

  @BeforeClass
  public static void beforeClass() {

    Assert.assertTrue(CACHE_FOLDER.exists());

    CACHE.clear();
    Assert.assertEquals(0, CACHE_FOLDER.list().length);
  }

  // @AfterClass
  public static void afterClass() {

    CACHE.clear();
    Assert.assertEquals(0, CACHE_FOLDER.list().length);

    CACHE_FOLDER.delete();
    Assert.assertFalse(CACHE_FOLDER.exists());
  }

  @Before
  public void before() {
    CACHE.clear();
  }

  @After
  public void after() {}

  @Test
  public void test() throws Throwable {

    File file = new File("src\\test\\resources\\jatoo\\image\\image.png");

    BufferedImage imageO = ImageUtils.read(file);
    BufferedImage imageR = ImageUtils.resizeTo(true, imageO, 200, 200, true);
    BufferedImage imageC = ImageUtils.resizeTo(true, imageR, imageO.getWidth(), imageO.getHeight(), true);

    CACHE.add(imageR, file, "fit200px");
    CACHE.add(imageC, file, imageO.getWidth(), imageO.getHeight());

    Assert.assertEquals(2, CACHE_FOLDER.list().length);

    BufferedImage imageC1 = CACHE.get(file, "fit200px");
    Assert.assertEquals(200, Math.max(imageC1.getWidth(), imageC1.getHeight()));

    BufferedImage imageC2 = CACHE.get(file, imageO.getWidth(), imageO.getHeight());
    Assert.assertEquals(Math.max(imageO.getWidth(), imageO.getHeight()), Math.max(imageC2.getWidth(), imageC2.getHeight()));
  
//    CACHE.add(ImageUtils.read(new File("src\\test\\resources\\jatoo\\image\\image.png")), new File("src\\test\\resources\\jatoo\\image\\image.png"), 100);
    
//    CACHE.add(ImageUtils.read(new File("D:\\20180305_130931.jpg")), new File("D:\\20180305_130931.jpg"), 100);
//    BufferedImage thumb1 = CACHE.get(new File("src\\test\\resources\\jatoo\\image\\image.png"), 100);
//    Assert.assertEquals(1, THUMBNAILS_FOLDER.list().length);
//    Assert.assertEquals(100, thumb1.getWidth());
//    Assert.assertEquals(100, thumb1.getHeight());
//
//    CACHE.get(new File("src\\test\\resources\\jatoo\\image\\imageVertical.png"), 100);
//    Assert.assertEquals(1, THUMBNAILS_FOLDER.list().length);
//    Assert.assertEquals(2, new File(THUMBNAILS_FOLDER, "100_100").list().length);
//
//    BufferedImage thumb3 = CACHE.get(new File("src\\test\\resources\\jatoo\\image\\image.png"), 200);
//    Assert.assertEquals(2, THUMBNAILS_FOLDER.list().length);
//    Assert.assertEquals(1, new File(THUMBNAILS_FOLDER, "200_200").list().length);
//    Assert.assertEquals(200, thumb3.getWidth());
//    Assert.assertEquals(200, thumb3.getHeight());
  }

}
