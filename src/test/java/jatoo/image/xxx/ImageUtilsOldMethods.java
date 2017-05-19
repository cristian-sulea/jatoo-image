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

package jatoo.image.xxx;

import jatoo.image.ImageUtils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * A collection of utility methods to ease the work with images.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 2.0, June 23, 2014
 * 
 * @deprecated
 */
public class ImageUtilsOldMethods  {

  /**
   * Resizes an image (keeping the original ratio). The new image will fit
   * inside a rectangle with the specified width and height.
   * 
   * @param image
   *          the image to be resized
   * @param width
   *          maximum width
   * @param height
   *          maximum height
   * 
   * @return a new {@link BufferedImage} that fits inside a rectangle with the
   *         specified width and height
   * 
   * @deprecated
   */
  public static BufferedImage resize(BufferedImage image, int width, int height) {
    return resize(image, width, height, false);
  }

  /**
   * Resizes an image.
   * 
   * @param image
   *          the image to be resized
   * @param width
   *          the width of the new image, or maximum width if crop is
   *          <code>false</code>
   * @param height
   *          the height of the new image, or maximum height if crop is
   *          <code>false</code>
   * @param crop
   *          if <code>true</code> then the new image will have the exactly
   *          specified size and will probably loose some margins; if
   *          <code>false</code> the new image will keep the original ratio and
   *          will fit inside a rectangle with the specified width and height
   * 
   * @return a new {@link BufferedImage}
   * 
   * @deprecated
   */
  public static BufferedImage resize(BufferedImage image, int width, int height, boolean crop) {

    double imageWidth = (double) image.getWidth();
    double imageHeight = (double) image.getHeight();

    if (imageWidth <= width && imageHeight <= height) {
      return image;
    }

    if (crop) {

      double tmpWidth = (double) width;
      double tmpHeight = (double) height;

      double tmpWidthUnit = 1d;
      double tmpHeightUnit = 1d;

      if (width > height) {
        tmpHeightUnit = 1d * (((double) height) / ((double) width));
      } else if (width < height) {
        tmpWidthUnit = 1d * (((double) width) / ((double) height));
      }

      while (tmpWidth < imageWidth && tmpHeight < imageHeight) {
        tmpWidth += tmpWidthUnit;
        tmpHeight += tmpHeightUnit;
      }

      image = ImageUtils.crop(image, (int) tmpWidth, (int) tmpHeight);

      imageWidth = image.getWidth();
      imageHeight = image.getHeight();
    }

    boolean imageHasAlpha = ImageUtils.hasAlpha(image);

    double ratioWH = imageWidth / imageHeight;
    double ratioHW = imageHeight / imageWidth;

    while (true) {

      if (imageWidth > width) {
        imageWidth /= 2;
        if (imageWidth <= width) {
          imageWidth = width;
          imageHeight = (int) (imageWidth / ratioWH);
        }
      }

      if (imageHeight > height) {
        imageHeight /= 2;
        if (imageHeight <= height) {
          imageHeight = height;
          imageWidth = (int) (imageHeight / ratioHW);
        }
      }

      if (imageWidth <= width && imageHeight <= height) {
        if (crop) {
          imageWidth = width;
          imageHeight = height;
        }
      }

      BufferedImage newImage = ImageUtils.create((int) imageWidth, (int) imageHeight, imageHasAlpha);
      Graphics2D g = newImage.createGraphics();
      g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g.drawImage(image, 0, 0, newImage.getWidth(), newImage.getHeight(), null);
      g.dispose();

      image = newImage;

      if (imageWidth <= width && imageHeight <= height) {
        break;
      }
    }

    return image;
  }

  public static BufferedImage zoom(BufferedImage image, int zoom) {

    double ratio = ((double) zoom) / 100d;

    double width = ((double) image.getWidth()) * ratio;
    double height = ((double) image.getHeight()) * ratio;

    boolean imageHasAlpha = ImageUtils.hasAlpha(image);

    BufferedImage newImage = ImageUtils.create((int) width, (int) height, imageHasAlpha);
    Graphics2D g = newImage.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
    g.drawImage(image, 0, 0, newImage.getWidth(), newImage.getHeight(), null);
    g.dispose();

    return newImage;
  }

  /**
   * Returns the bounds of an image that must fit between another bounds. The
   * image will be centered and the ratio of the resize will be maintained.
   * 
   * @deprecated
   */
  public static Rectangle fitImage(BufferedImage image, Rectangle bounds) {

    double newWidth = image.getWidth();
    double newHeight = image.getHeight();

    double maxWidth = bounds.width - bounds.x;
    double maxHeight = bounds.height - bounds.y;

    double ratio = (double) newWidth / (double) newHeight;

    // if (newWidth > maxWidth) {
    newWidth = maxWidth;
    newHeight = newWidth / ratio;
    // }

    if (newHeight > maxHeight) {
      newHeight = maxHeight;
      newWidth = newHeight * ratio;
    }

    int x = bounds.x + (int) ((maxWidth - newWidth) / 2);
    int y = bounds.y + (int) ((maxHeight - newHeight) / 2);

    int width = (int) newWidth;
    int height = (int) newHeight;

    if (width == 0) {
      width = 1;
    }
    if (height == 0) {
      height = 1;
    }

    return new Rectangle(x, y, width, height);
  }

  /**
   * Creates a new {@link BufferedImage} using the specified source and alpha.
   * 
   * @param image
   *          the source {@link Image}
   * @param alpha
   *          the constant alpha to be applied; must be a floating point number
   *          in the inclusive range [0.0, 1.0]
   * 
   * @return a new {@link BufferedImage}
   * 
   * @deprecated
   */
  public static BufferedImage setAlpha(Image image, float alpha) {

    int width = image.getWidth(null);
    int height = image.getHeight(null);

    BufferedImage newImage = ImageUtils.create(width, height, true);
    Graphics2D g = newImage.createGraphics();

    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    g.drawImage(image, 0, 0, null);

    g.dispose();

    return newImage;
  }

}
