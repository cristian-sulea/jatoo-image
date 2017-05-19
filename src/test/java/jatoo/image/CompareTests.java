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

  private static BufferedImage IMAGE_1_0;
  private static BufferedImage IMAGE_2_0;

  @BeforeClass
  public static void beforeClass() throws Throwable {
    IMAGE_1_0 = ImageUtils.read(CompareTests.class.getResource("compare/test-1-0.jpg"));
    IMAGE_2_0 = ImageUtils.read(CompareTests.class.getResource("compare/test-2-0.jpg"));
  }

  @AfterClass
  public static void afterClass() {}

  @Before
  public void before() {}

  @After
  public void after() {}

  @Test
  public void testCompare_1_1() throws Throwable {

    BufferedImage image1 = IMAGE_1_0;
    BufferedImage image2 = ImageUtils.read(CompareTests.class.getResource("compare/test-1-1.jpg"));

    List<Rectangle> changes = ImageUtils.compare(image1, image2);
    Assert.assertEquals(19, changes.size());

    List<Rectangle> changesMerged = ImageUtils.compare(image1, image2, true);
    Assert.assertEquals(1, changesMerged.size());
  }

  @Test
  public void testCompare_1_2() throws Throwable {

    BufferedImage image1 = IMAGE_1_0;
    BufferedImage image2 = ImageUtils.read(CompareTests.class.getResource("compare/test-1-2.jpg"));

    List<Rectangle> changes = ImageUtils.compare(image1, image2);
    Assert.assertEquals(21, changes.size());

    List<Rectangle> changesMerged = ImageUtils.compare(image1, image2, true);
    Assert.assertEquals(2, changesMerged.size());
  }

  @Test
  public void testCompare_1_3() throws Throwable {

    BufferedImage image1 = IMAGE_1_0;
    BufferedImage image2 = ImageUtils.read(CompareTests.class.getResource("compare/test-1-3.jpg"));

    List<Rectangle> changes = ImageUtils.compare(image1, image2);
    Assert.assertEquals(46, changes.size());

    List<Rectangle> changesMerged = ImageUtils.compare(image1, image2, true);
    Assert.assertEquals(1, changesMerged.size());
  }

  @Test
  public void testCompare_2_1() throws Throwable {

    BufferedImage image1 = IMAGE_2_0;
    BufferedImage image2 = ImageUtils.read(CompareTests.class.getResource("compare/test-2-1.jpg"));

    List<Rectangle> changes = ImageUtils.compare(image1, image2);
    Assert.assertEquals(217, changes.size());

    List<Rectangle> changesMerged = ImageUtils.compare(image1, image2, true);
    Assert.assertEquals(1, changesMerged.size());
  }

  @Test
  public void testCompare_2_2() throws Throwable {

    BufferedImage image1 = IMAGE_2_0;
    BufferedImage image2 = ImageUtils.read(CompareTests.class.getResource("compare/test-2-2.jpg"));

    List<Rectangle> changes = ImageUtils.compare(image1, image2);
    Assert.assertEquals(220, changes.size());

    List<Rectangle> changesMerged = ImageUtils.compare(image1, image2, true);
    Assert.assertEquals(1, changesMerged.size());
  }

}
