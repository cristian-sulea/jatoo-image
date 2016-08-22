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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileCacheImageOutputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A collection of utility methods to ease the work with images.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 4.2.1, May 12, 2016
 */
public final class ImageUtils {

  /** the logger */
  private static final Log logger = LogFactory.getLog(ImageUtils.class);

  /**
   * Utility classes should not have a public or default constructor.
   */
  private ImageUtils() {}

  /**
   * Returns a {@link BufferedImage} as the result of decoding the supplied
   * {@link File}.
   * 
   * @param file
   *          a {@link File} to read from.
   * 
   * @return a BufferedImage containing the decoded contents of the input.
   * 
   * @throws IOException
   *           if an error occurs during reading or if
   *           {@link ImageIO#read(File)} returns <code>null</code>.
   * 
   * @see ImageIO#read(File)
   */
  public static BufferedImage read(final File file) throws IOException {

    BufferedImage image = ImageIO.read(file);

    if (image == null) {
      throw new IOException("Failed to decode the file: " + file);
    }

    return image;
  }

  /**
   * Returns a {@link BufferedImage} as the result of decoding the supplied
   * {@link URL}.
   * 
   * @param url
   *          a {@link URL} to read from.
   * 
   * @return a BufferedImage containing the decoded contents of the input.
   * 
   * @throws IOException
   *           if an error occurs during reading or if {@link ImageIO#read(URL)}
   *           returns <code>null</code>.
   * 
   * @see ImageIO#read(URL)
   */
  public static BufferedImage read(final URL url) throws IOException {
    BufferedImage image = ImageIO.read(url);
    if (image == null) {
      throw new IOException("Failed to decode the URL: " + url);
    }
    return image;
  }

  /**
   * Returns a {@link BufferedImage} as the result of decoding the supplied
   * {@link InputStream}.
   * 
   * Please note that the {@link InputStream} will be closed after read.
   * 
   * @param inputStream
   *          an {@link InputStream} to read from.
   * 
   * @return a BufferedImage containing the decoded contents of the input.
   * 
   * @throws IOException
   *           if an error occurs during reading or if
   *           {@link ImageIO#read(InputStream)} returns <code>null</code>.
   * 
   * @see ImageIO#read(InputStream)
   */
  public static BufferedImage read(final InputStream inputStream) throws IOException {

    BufferedImage image;

    try {
      image = ImageIO.read(inputStream);
    } catch (IOException e) {
      throw e;
    } finally {
      inputStream.close();
    }

    if (image == null) {
      throw new IOException("Failed to decode the provided stream.");
    }

    return image;
  }

  /**
   * Writes an image using an arbitrary {@link ImageWriter} that supports the
   * given format to a {@link File}.
   * 
   * @param image
   *          the {@link BufferedImage} to be written
   * @param formatName
   *          the informal name of the format
   * @param file
   *          the {@link File} to be written to
   * 
   * @exception IOException
   *              if an error occurs during writing
   */
  public static void write(final BufferedImage image, final String formatName, final File file) throws IOException {
    ImageIO.write(image, formatName, file);
  }

  public static void write(final BufferedImage image, final String formatName, final File file, final boolean fixExtension) throws IOException {

    final File writeFile;

    if (fixExtension) {

      String writeFileName = file.getName();

      if (!writeFileName.endsWith(formatName)) {

        writeFileName = writeFileName.substring(0, writeFileName.lastIndexOf('.'));
        writeFileName += "." + formatName;
      }

      writeFile = new File(file.getAbsoluteFile().getParentFile(), writeFileName);
    }

    else {
      writeFile = file;
    }

    ImageIO.write(image, formatName, writeFile);
  }

  /**
   * Writes an image using an arbitrary {@link ImageWriter} that supports the
   * given format to an {@link OutputStream}. The method will close the provided
   * {@link OutputStream} after the write operation has completed.
   * 
   * @param image
   *          the {@link BufferedImage} to be written
   * @param formatName
   *          the informal name of the format
   * @param stream
   *          the {@link OutputStream} to be written to
   * 
   * @throws IOException
   *           if an error occurs during writing
   */
  public static void write(final BufferedImage image, final String formatName, final OutputStream stream) throws IOException {
    try {
      ImageIO.write(image, formatName, stream);
    } catch (IOException e) {
      throw e;
    } finally {
      stream.close();
    }
  }

  public static void writeGIF(final BufferedImage image, final File file) throws IOException {
    write(image, "gif", file);
  }

  public static void writeGIF(final BufferedImage image, final OutputStream stream) throws IOException {
    write(image, "gif", stream);
  }

  public static void writeJPEG(final BufferedImage image, final File file) throws IOException {
    write(image, "jpg", file);
  }

  public static void writeJPEG(final BufferedImage image, final File file, final boolean fixExtension) throws IOException {
    write(image, "jpg", file, fixExtension);
  }

  public static void writeJPEG(final BufferedImage image, final OutputStream stream) throws IOException {
    write(image, "jpg", stream);
  }

  public static void writeJPEG(final BufferedImage image, final File file, final int compression) throws IOException {
    writeJPEG(image, new FileImageOutputStream(file), compression);
  }

  public static void writeJPEG(final BufferedImage image, final File file, final int compression, final boolean fixExtension) throws IOException {

    final String formatName = "jpg";
    final File writeFile;

    if (fixExtension) {

      String writeFileName = file.getName();

      if (!writeFileName.endsWith(formatName)) {

        writeFileName = writeFileName.substring(0, writeFileName.lastIndexOf('.'));
        writeFileName += "." + formatName;
      }

      writeFile = new File(file.getAbsoluteFile().getParentFile(), writeFileName);
    }

    else {
      writeFile = file;
    }

    writeJPEG(image, new FileImageOutputStream(writeFile), compression);
  }

  public static void writeJPEG(final BufferedImage image, final OutputStream stream, final int compression) throws IOException {
    writeJPEG(image, new FileCacheImageOutputStream(stream, null), compression);
  }

  public static void writeJPEG(final BufferedImage image, final ImageOutputStream stream, final int compression) throws IOException {

    if (compression <= 0 || compression > 100) {
      throw new IllegalArgumentException("Compression (" + compression + ") must be in interval (0, 100]");
    }

    try {

      ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();

      ImageWriteParam params = writer.getDefaultWriteParam();
      params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
      params.setCompressionQuality(compression / 100f);

      writer.setOutput(stream);
      writer.write(null, new IIOImage(image, null, null), params);
      writer.dispose();
    }

    catch (IOException e) {
      throw e;
    }

    finally {
      stream.close();
    }
  }

  public static void writePNG(final BufferedImage image, final File file) throws IOException {
    write(image, "png", file);
  }

  public static void writePNG(final BufferedImage image, final File file, final boolean fixExtension) throws IOException {
    write(image, "png", file, fixExtension);
  }

  public static void writePNG(final BufferedImage image, final OutputStream stream) throws IOException {
    write(image, "png", stream);
  }

  /**
   * Creates a {@link BufferedImage} whose data layout and color model tries to
   * be compatible with this GraphicsConfiguration. If any error occurs trying
   * to do this a normal {@link BufferedImage} will be created and returned.
   * 
   * @param width
   *          the width of the returned {@link BufferedImage}.
   * @param height
   *          the height of the returned {@link BufferedImage}.
   * @param hasAlpha
   *          if <code>true</code> then the image will have alpha.
   * 
   * @return a {@link BufferedImage} whose data layout and color model tries to
   *         be compatible with this GraphicsConfiguration.
   */
  public static BufferedImage create(final int width, final int height, final boolean hasAlpha) {

    try {

      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice gd = ge.getDefaultScreenDevice();
      GraphicsConfiguration gc = gd.getDefaultConfiguration();

      return gc.createCompatibleImage(width, height, hasAlpha ? Transparency.TRANSLUCENT : Transparency.OPAQUE);
    }

    catch (Exception e) {
      logger.warn("problems creating a compatible image", e);
      return new BufferedImage(width, height, hasAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
    }
  }

  public static BufferedImage create(final String string, final boolean addShadow) {

    BufferedImage image = create(string, null, Color.BLACK);

    if (addShadow) {
      image = addShadow(image, 30, 1, 1, 1f, Color.WHITE);
    }

    return image;
  }

  public static BufferedImage create(final String string, final Font font, final Color color) {
    return create(string, font, color, false);
  }

  public static BufferedImage create(final String string, final Font font, final Color color, final boolean antialiasing) {
    return create(string, font, color, null, antialiasing);
  }

  public static BufferedImage create(final String string, final Font font, final Color color, final Color background, final boolean antialiasing) {

    BufferedImage image;
    Graphics2D g;

    //
    // compute the width and height of the text

    image = create(1, 1, false);
    g = image.createGraphics();

    if (font != null) {
      g.setFont(font);
    }

    if (antialiasing) {
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }

    FontMetrics fm = g.getFontMetrics();

    g.dispose();

    int descent = fm.getDescent();

    int textWitdh = fm.stringWidth(string);
    int textHeight = fm.getAscent() + descent;

    //
    // create the image

    image = create(textWitdh, textHeight, background == null);
    g = image.createGraphics();

    if (background != null) {
      g.setBackground(background);
      g.clearRect(0, 0, textWitdh, textHeight);
    }

    g.setFont(font);
    g.setColor(color);

    if (antialiasing) {
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }

    g.drawString(string, 0, textHeight - descent);

    g.dispose();

    return image;
  }

  public static BufferedImage create(final String string, final Font font, final FontRenderContext frc, final Color color) {
    return create(string, font, frc, color, null);
  }

  public static BufferedImage create(final String string, final Font font, final FontRenderContext frc, final Color color, final Color background) {

    Rectangle2D stringBounds = font.getStringBounds(string, frc);

    int x = 0;
    int y = (int) (0 - stringBounds.getY());
    int width = (int) stringBounds.getWidth();
    int height = (int) stringBounds.getHeight();

    BufferedImage image = create(width, height, background == null);
    Graphics2D g = image.createGraphics();

    g.setFont(font);

    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, frc.getAntiAliasingHint());
    g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, frc.getFractionalMetricsHint());

    g.setColor(color);

    if (background != null) {
      g.setBackground(background);
      g.fillRect(0, 0, width, height);
    }

    g.drawString(string, x, y);

    g.dispose();

    return image;
  }

  public static BufferedImage copy(final BufferedImage image) {
    return copy(image, hasAlpha(image));
  }

  public static BufferedImage copy(final BufferedImage image, final boolean hasAlpha) {

    BufferedImage newImage = create(image.getWidth(), image.getHeight(), hasAlpha);

    Graphics2D g = newImage.createGraphics();
    g.drawImage(image, 0, 0, null);
    g.dispose();

    return newImage;
  }

  /**
   * Returns whether or not alpha is supported by the specified
   * {@link BufferedImage}.
   * 
   * @param image
   *          the {@link BufferedImage} to be checked
   * 
   * @return <code>true</code> if this image has alpha; <code>false</code>
   *         otherwise
   */
  public static boolean hasAlpha(final BufferedImage image) {
    return image.getColorModel().hasAlpha();
  }

  /**
   * Returns whether or not alpha is supported by the specified {@link Image}.
   * 
   * @param image
   *          the {@link Image} to be checked
   * 
   * @return <code>true</code> if this image has alpha; <code>false</code>
   *         otherwise
   */
  public static boolean hasAlpha(final Image image) {

    // If buffered image, the color model is readily available
    if (image instanceof BufferedImage) {
      return hasAlpha((BufferedImage) image);
    }

    // Use a pixel grabber to retrieve the image's color model;
    // grabbing a single pixel is usually sufficient
    PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
    try {
      pg.grabPixels();
    } catch (InterruptedException e) {
      logger.warn("problems grabbing the pixels from this image", e);
    }

    // Get the image's color model
    return pg.getColorModel().hasAlpha();
  }

  /**
   * Creates a new image from a rectangle with the specified location and size
   * into the specified image.
   * 
   * @param image
   *          the source image
   * @param x
   *          the x coordinate of the corner of the rectangle
   * @param y
   *          the y coordinate of the corner of the rectangle
   * @param width
   *          the width of the rectangle
   * @param height
   *          the height of the rectangle
   * 
   * @return a new {@link BufferedImage}
   */
  public static BufferedImage crop(final BufferedImage image, final int x, final int y, final int width, final int height) {

    final boolean hasAlpha;

    if (x < 0 || y < 0 || x + width > image.getWidth() || y + height > image.getHeight()) {
      hasAlpha = true;
    } else {
      hasAlpha = hasAlpha(image);
    }

    final BufferedImage newImage = create(width, height, hasAlpha);

    final Graphics2D g = newImage.createGraphics();
    g.drawImage(image, 0, 0, width, height, x, y, x + width, y + height, null);
    g.dispose();

    return newImage;
  }

  /**
   * Creates a new image from a rectangle with the specified size positioned
   * into the middle of the specified image.
   * 
   * @param image
   *          the source image
   * @param width
   *          the width of the rectangle
   * @param height
   *          the height of the rectangle
   * 
   * @return a new {@link BufferedImage}
   */
  public static BufferedImage crop(final BufferedImage image, final int width, final int height) {

    final int x = (image.getWidth() - width) / 2;
    final int y = (image.getHeight() - height) / 2;

    return crop(image, x, y, width, height);
  }

  /**
   * Resizes an image (keeping the original ratio) to fit inside a rectangle
   * with the specified width and height.
   * 
   * @param image
   *          the image to be resized
   * @param width
   *          maximum width of the resized image
   * @param height
   *          maximum height of the resized image
   * 
   * @return a resized version of the image (a new object)
   */
  public static BufferedImage resizeToFit(final BufferedImage image, final int width, final int height) {
    return resizeTo(true, image, width, height);
  }

  /**
   * Resizes an image (keeping the original ratio) to fit inside a rectangle
   * with the specified size.
   * 
   * @param image
   *          the image to be resized
   * @param size
   *          maximum size of the resized image
   * 
   * @return a resized version of the image (a new object)
   */
  public static BufferedImage resizeToFit(final BufferedImage image, final Dimension size) {
    return resizeToFit(image, size.width, size.height);
  }

  /**
   * Resizes an image (keeping the original ratio) to fit inside a rectangle
   * with the specified size.
   * 
   * @param image
   *          the image to be resized
   * @param size
   *          maximum size of the resized image
   * 
   * @return a resized version of the image (a new object)
   */
  public static BufferedImage resizeToFit(final BufferedImage image, final int size) {
    return resizeToFit(image, size, size);
  }

  /**
   * Resizes an image (keeping the original ratio) to fill a rectangle with the
   * specified width and height (removing margins from image if needed).
   * 
   * @param image
   *          the image to be resized
   * @param width
   *          maximum width of the resized image
   * @param height
   *          maximum height of the resized image
   * 
   * @return a resized version of the image (a new object)
   */
  public static BufferedImage resizeToFill(final BufferedImage image, final int width, final int height) {
    return resizeTo(false, image, width, height);
  }

  /**
   * Resizes an image (keeping the original ratio) to fill a rectangle with the
   * specified size (removing margins from image if needed).
   * 
   * @param image
   *          the image to be resized
   * @param size
   *          maximum size of the resized image
   * 
   * @return a resized version of the image (a new object)
   */
  public static BufferedImage resizeToFill(final BufferedImage image, final Dimension size) {
    return resizeToFill(image, size.width, size.height);
  }

  /**
   * Resizes an image (keeping the original ratio) to fill a rectangle with the
   * specified size (removing margins from image if needed).
   * 
   * @param image
   *          the image to be resized
   * @param size
   *          maximum size of the resized image
   * 
   * @return a resized version of the image (a new object)
   */
  public static BufferedImage resizeToFill(final BufferedImage image, final int size) {
    return resizeToFill(image, size, size);
  }

  /**
   * Resizes an image (keeping the original ratio):
   * <ul>
   * <li>to fit inside a rectangle with the specified width and height (adding
   * empty space if needed);
   * <li>to fill a rectangle with the specified width and height (removing
   * margins from image if needed).
   * </ul>
   * 
   * @param fit
   *          <code>true</code> is is <strong>FIT</strong>, <code>false</code>
   *          if is <strong>FILL</strong>
   * @param image
   *          the image to be resized
   * @param width
   *          maximum width of the resized image
   * @param height
   *          maximum height of the resized image
   * 
   * @return a resized version of the image (a new object)
   */
  public static BufferedImage resizeTo(final boolean fit, final BufferedImage image, final int width, final int height) {

    int imageWidth = image.getWidth();
    int imageHeight = image.getHeight();

    //
    // same size means no resize

    if (width == imageWidth && height == imageHeight) {
      return image;
    }

    //
    // ratio

    double ratio;
    boolean isResizeDown;

    if (width < imageWidth) {
      ratio = 0.5;
      isResizeDown = true;
    }

    else {
      ratio = 1.5;
      isResizeDown = false;
    }

    //
    // calculate the size of the resized image

    Dimension resizedImageSize = calculateSizeTo(fit, image, width, height);

    //
    // resize the image

    BufferedImage resizedImage = image;
    boolean resizedImageHasAlpha = hasAlpha(image);

    while (true) {

      int tmpImageWidth = (int) (resizedImage.getWidth() * ratio);
      int tmpImageHeight = (int) (resizedImage.getHeight() * ratio);

      boolean isResized;

      if (tmpImageWidth == 0 || tmpImageHeight == 0) {
        isResized = true;
      }

      else {

        if (isResizeDown) {
          isResized = tmpImageWidth <= resizedImageSize.width || tmpImageHeight <= resizedImageSize.height;
        }

        else {
          isResized = tmpImageWidth >= resizedImageSize.width || tmpImageHeight >= resizedImageSize.height;
        }
      }

      if (isResized) {
        tmpImageWidth = resizedImageSize.width;
        tmpImageHeight = resizedImageSize.height;
      }

      BufferedImage tmpImage = create(tmpImageWidth, tmpImageHeight, resizedImageHasAlpha);

      Graphics2D tmpImageGraphics = tmpImage.createGraphics();
      tmpImageGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      tmpImageGraphics.drawImage(resizedImage, 0, 0, tmpImageWidth, tmpImageHeight, null);
      tmpImageGraphics.dispose();

      resizedImage = tmpImage;

      if (isResized) {
        break;
      }
    }

    //
    // crop the image if is fill

    if (!fit) {
      resizedImage = crop(resizedImage, width, height);
    }

    //
    // here we go

    return resizedImage;
  }

  /**
   * Calculates the size of an image (keeping the original ratio) to fit inside
   * a rectangle with the specified width and height.
   * 
   * @param image
   *          the image to fit
   * @param width
   *          maximum width of the the image to fit
   * @param height
   *          maximum height of the the image to fit
   * 
   * @return a {@link Dimension} object representing the size of the the image
   *         to fit
   */
  public static Dimension calculateSizeToFit(final BufferedImage image, final int width, final int height) {
    return calculateSizeTo(true, image, width, height);
  }

  /**
   * Calculates the size of an image (keeping the original ratio) to fit inside
   * a rectangle with the specified size.
   * 
   * @param image
   *          the image to fit
   * @param size
   *          maximum size of the image to fit
   * 
   * @return a {@link Dimension} object representing the size of the the image
   *         to fit
   */
  public static Dimension calculateSizeToFit(final BufferedImage image, final Dimension size) {
    return calculateSizeToFit(image, size.width, size.height);
  }

  /**
   * Calculates the size of an image (keeping the original ratio) to fit inside
   * a rectangle with the specified size.
   * 
   * @param image
   *          the image to fit
   * @param size
   *          maximum size of the image to fit
   * 
   * @return a {@link Dimension} object representing the size of the the image
   *         to fit
   */
  public static Dimension calculateSizeToFit(final BufferedImage image, final int size) {
    return calculateSizeToFit(image, size, size);
  }

  /**
   * Calculates the size of an image (keeping the original ratio) to fill a
   * rectangle with the specified width and height.
   * 
   * @param image
   *          the image to fill
   * @param width
   *          maximum width of the image to fill
   * @param height
   *          maximum height of the image to fill
   * 
   * @return a {@link Dimension} object representing the size of the the image
   *         to fill
   */
  public static Dimension calculateSizeToFill(final BufferedImage image, final int width, final int height) {
    return calculateSizeTo(false, image, width, height);
  }

  /**
   * Calculates the size of an image (keeping the original ratio) to fill a
   * rectangle with the specified size.
   * 
   * @param image
   *          the image to fill
   * @param size
   *          maximum size of the image to fill
   * 
   * @return a {@link Dimension} object representing the size of the the image
   *         to fill
   */
  public static Dimension calculateSizeToFill(final BufferedImage image, final Dimension size) {
    return calculateSizeToFill(image, size.width, size.height);
  }

  /**
   * Calculates the size of an image (keeping the original ratio) to fill a
   * rectangle with the specified size.
   * 
   * @param image
   *          the image to fill
   * @param size
   *          maximum size of the image to fill
   * 
   * @return a {@link Dimension} object representing the size of the the image
   *         to fill
   */
  public static Dimension calculateSizeToFill(final BufferedImage image, final int size) {
    return calculateSizeToFill(image, size, size);
  }

  /**
   * Calculates the size of an image (keeping the original ratio):
   * <ul>
   * <li>to fit inside a rectangle with the specified width and height;
   * <li>to fill a rectangle with the specified width and height.
   * </ul>
   * 
   * @param fit
   *          <code>true</code> is is <strong>FIT</strong>, <code>false</code>
   *          if is <strong>FILL</strong>
   * @param image
   *          the image to fit or fill
   * @param width
   *          maximum width of the image to fit or fill
   * @param height
   *          maximum height of the image to fit or fill
   * 
   * @return a {@link Dimension} object representing the size of the the image
   *         to fit or fill
   */
  public static Dimension calculateSizeTo(final boolean fit, final BufferedImage image, final int width, final int height) {

    int imageWidth = image.getWidth();
    int imageHeight = image.getHeight();

    //
    // same size means no resize

    if (width == imageWidth && height == imageHeight) {
      return new Dimension(width, height);
    }

    //
    // ratio

    double ratio = (double) imageWidth / (double) imageHeight;

    //
    // calculate the size of the resized image

    int resizedImageWidth = width;
    int resizedImageHeight = (int) (resizedImageWidth / ratio);

    if (fit) {
      if (resizedImageHeight > height) {
        resizedImageHeight = height;
        resizedImageWidth = (int) (resizedImageHeight * ratio);
      }
    } else {
      if (resizedImageHeight < height) {
        resizedImageHeight = height;
        resizedImageWidth = (int) (resizedImageHeight * ratio);
      }
    }

    //
    // 0 is unacceptable for images

    if (resizedImageWidth == 0) {
      resizedImageWidth = 1;
    }
    if (resizedImageHeight == 0) {
      resizedImageHeight = 1;
    }

    //
    // here we go

    return new Dimension(resizedImageWidth, resizedImageHeight);
  }

  /**
   * Rotates an image.
   * 
   * @param image
   *          the image to be resized
   * @param angle
   *          the angle of rotation in degrees
   * 
   * @return a rotated version of the image (a new object)
   */
  public static BufferedImage rotate(final BufferedImage image, final int angle) {
    return rotate(image, angle, null);
  }

  /**
   * Rotates an image.
   * 
   * @param image
   *          the image to be resized
   * @param angle
   *          the angle of rotation in degrees
   * @param background
   *          the color of the background for the cases when the angle is not a
   *          multiplier of 90' (<code>null</code> for transparent)
   * 
   * @return a rotated version of the image (a new object)
   */
  public static BufferedImage rotate(final BufferedImage image, final int angle, final Color background) {

    final double sin = Math.abs(Math.sin(Math.toRadians(angle)));
    final double cos = Math.abs(Math.cos(Math.toRadians(angle)));

    final int imageWidth = image.getWidth();
    final int imageHeight = image.getHeight();

    final int rotatedImageWidth = (int) Math.floor(imageWidth * cos + imageHeight * sin);
    final int rotatedImageHeight = (int) Math.floor(imageHeight * cos + imageWidth * sin);

    final BufferedImage rotatedImage = create(rotatedImageWidth, rotatedImageHeight, background == null);
    final Graphics2D g = rotatedImage.createGraphics();

    if (background != null) {
      g.setColor(background);
      g.fillRect(0, 0, rotatedImageWidth, rotatedImageHeight);
    }

    g.translate((rotatedImageWidth - imageWidth) / 2, (rotatedImageHeight - imageHeight) / 2);
    g.rotate(Math.toRadians(angle), imageWidth / 2d, imageHeight / 2d);
    g.drawImage(image, 0, 0, null);

    g.dispose();

    return rotatedImage;
  }

  /**
   * Flips an image horizontally.
   * 
   * @param image
   *          the image to be horizontally flipped
   * 
   * @return a flipped (horizontally) version of the image (a new object)
   */
  public static BufferedImage flipHorizontally(final BufferedImage image) {

    final int width = image.getWidth();
    final int height = image.getHeight();

    final BufferedImage flippedImage = create(width, height, hasAlpha(image));
    final Graphics2D g = flippedImage.createGraphics();

    g.drawImage(image, 0, 0, width, height, width, 0, 0, height, null);
    g.dispose();

    return flippedImage;
  }

  /**
   * Flips an image vertically.
   * 
   * @param image
   *          the image to be vertically flipped
   * 
   * @return a flipped (vertically) version of the image (a new object)
   */
  public static BufferedImage flipVertically(final BufferedImage image) {

    final int width = image.getWidth();
    final int height = image.getHeight();

    final BufferedImage flippedImage = create(width, height, hasAlpha(image));
    final Graphics2D g = flippedImage.createGraphics();

    g.drawImage(image, 0, 0, width, height, 0, height, width, 0, null);
    g.dispose();

    return flippedImage;
  }

  /**
   * Fills the image with the specified color.
   * 
   * @param image
   *          the image to be filled
   * @param color
   *          the fill color
   */
  public static void fill(final BufferedImage image, final Color color) {
    Graphics2D g = image.createGraphics();
    g.setColor(color);
    g.fillRect(0, 0, image.getWidth(), image.getHeight());
    g.dispose();
  }

  public static BufferedImage addShadow(final BufferedImage image) {
    return addShadow(image, 30, 3, 3, 0.5f, Color.BLACK);
  }

  public static BufferedImage addShadow(final BufferedImage image, final Color color) {
    return addShadow(image, 30, 3, 3, 0.5f, color);
  }

  public static BufferedImage addShadow(final BufferedImage image, final int angle, final int distance, final int size, final float opacity, final Color color) {

    double angleRadians = Math.toRadians(angle);
    int distanceX = (int) (Math.cos(angleRadians) * distance);
    int distanceY = (int) (Math.sin(angleRadians) * distance);

    //

    BufferedImage imageTmp = addBorder(image, size);

    BufferedImage shadow = create(imageTmp.getWidth(), imageTmp.getHeight(), true);

    BufferedImage shadowMask = create(imageTmp.getWidth(), imageTmp.getHeight(), true);
    Graphics2D shadowMaskGraphics = shadowMask.createGraphics();
    shadowMaskGraphics.drawImage(imageTmp, 0, 0, null);
    shadowMaskGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, opacity));
    shadowMaskGraphics.setColor(color);
    shadowMaskGraphics.fillRect(0, 0, imageTmp.getWidth(), imageTmp.getHeight());
    shadowMaskGraphics.dispose();

    float[] data = new float[size * size];
    float value = 1.0f / (float) (size * size);
    for (int i = 0; i < data.length; i++) {
      data[i] = value;
    }
    new ConvolveOp(new Kernel(size, size, data)).filter(shadowMask, shadow);

    //

    BufferedImage imageWithShadow = create(shadow.getWidth() + distanceX, shadow.getHeight() + distanceY, true);
    Graphics2D g = imageWithShadow.createGraphics();
    g.drawImage(shadow, distanceX, distanceY, null);
    g.drawImage(image, 0, 0, null);
    g.dispose();

    return imageWithShadow;
  }

  public static BufferedImage addBorder(final BufferedImage image, final Color color, final int thickness) {
    BufferedImage imageWithBorder = create(image.getWidth() + 2 * thickness, image.getHeight() + 2 * thickness, color == null ? true : hasAlpha(image));
    Graphics2D g = imageWithBorder.createGraphics();
    g.drawImage(image, thickness, thickness, null);
    if (color != null) {
      g.setColor(color);
      for (int i = 0; i < thickness; i++) {
        g.drawRect(i, i, image.getWidth() - i + 1, image.getHeight() - i + 1);
      }
    }
    g.dispose();
    return imageWithBorder;
  }

  public static BufferedImage addBorder(final BufferedImage image, final int thickness) {
    return addBorder(image, null, thickness);
  }

  /**
   * Returns a list with the image formats that can be read and decoded.
   * 
   * @return the reading image formats
   */
  public static String[] getFormatNames() {

    Set<String> formatNamesSet = new HashSet<String>();
    for (String formatName : ImageIO.getReaderFormatNames()) {
      formatNamesSet.add(formatName.toLowerCase());
    }

    List<String> formatNamesList = new ArrayList<String>(formatNamesSet);

    Collections.sort(formatNamesList);

    return (String[]) formatNamesList.toArray(new String[formatNamesList.size()]);
  }
}