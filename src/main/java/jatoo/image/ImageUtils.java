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
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileCacheImageOutputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A collection of utility methods to ease the work with images.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 7.0-SNAPSHOT, May 24, 2017
 */
public class ImageUtils {

  /** The logger. */
  private static final Log logger = LogFactory.getLog(ImageUtils.class);
  
  private static ImageMetadataHelper metadataHelper;

  /**
   * Utility classes should not have a public or default constructor.
   */
  private ImageUtils() {}

  /**
   * Returns a {@link BufferedImage} as the result of decoding the supplied {@link File}.
   * 
   * @param file
   *          a {@link File} to read from
   * 
   * @return a BufferedImage containing the decoded contents of the file
   * 
   * @throws IOException
   *           if an error occurs during reading or if {@link ImageIO#read(File)} returns <code>null</code>
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
   * Returns a {@link BufferedImage} as the result of decoding the supplied file path.
   * 
   * @param file
   *          a file (path) to read from
   * 
   * @return a BufferedImage containing the decoded contents of the file
   * 
   * @throws IOException
   *           if an error occurs during reading or if the argument is <code>null</code>
   * 
   * @see ImageUtils#read(File)
   */
  public static BufferedImage read(final String file) throws IOException {
    return read(new File(file));
  }

  /**
   * Returns a {@link BufferedImage} as the result of decoding the supplied {@link URL}.
   * 
   * @param url
   *          a {@link URL} to read from
   * 
   * @return a BufferedImage containing the decoded contents of the input
   * 
   * @throws IOException
   *           if an error occurs during reading or if {@link ImageIO#read(URL)} returns <code>null</code>
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
   * Returns a {@link BufferedImage} as the result of decoding the supplied {@link InputStream}. Please note that the
   * {@link InputStream} will be closed after read.
   * 
   * @param inputStream
   *          an {@link InputStream} to read from
   * 
   * @return a BufferedImage containing the decoded contents of the input
   * 
   * @throws IOException
   *           if an error occurs during reading or if {@link ImageIO#read(InputStream)} returns <code>null</code>
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
   * Returns a {@link BufferedImage} as the result of decoding the supplied {@link ImageInputStream}.
   * 
   * Please note that the {@link ImageInputStream} will be closed after read.
   * 
   * @param imageInputStream
   *          an {@link ImageInputStream} to read from.
   * 
   * @return a BufferedImage containing the decoded contents of the input
   * 
   * @throws IOException
   *           if an error occurs during reading or if {@link ImageIO#read(ImageInputStream)} returns <code>null</code>
   * 
   * @see ImageIO#read(ImageInputStream)
   */
  public static BufferedImage read(final ImageInputStream imageInputStream) throws IOException {

    BufferedImage image;

    try {
      image = ImageIO.read(imageInputStream);
    } catch (IOException e) {
      throw e;
    } finally {
      try {
        imageInputStream.close();
      } catch (IOException e) {}
    }

    if (image == null) {
      throw new IOException("Failed to decode the provided image input stream.");
    }

    return image;
  }

  /**
   * Writes an image using an arbitrary {@link ImageWriter} that supports the given format to a {@link File}.
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
   * Writes an image using an arbitrary {@link ImageWriter} that supports the given format to an {@link OutputStream}.
   * The method will close the provided {@link OutputStream} after the write operation has completed.
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

  public static void writeJPEG(final BufferedImage image, final String file) throws IOException {
    write(image, "jpg", new File(file));
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
   * Creates a {@link BufferedImage} whose data layout and color model tries to be compatible with this
   * GraphicsConfiguration. If any error occurs trying to do this a normal {@link BufferedImage} will be created and
   * returned.
   * 
   * @param width
   *          the width of the returned {@link BufferedImage}
   * @param height
   *          the height of the returned {@link BufferedImage}
   * @param hasAlpha
   *          if <code>true</code> then the image will have alpha
   * 
   * @return a {@link BufferedImage} whose data layout and color model tries to be compatible with this
   *         {@link GraphicsConfiguration}
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

  public static BufferedImage copy(final BufferedImage image, final boolean hasAlpha) {

    BufferedImage newImage = create(image.getWidth(), image.getHeight(), hasAlpha);

    Graphics2D g = newImage.createGraphics();
    g.drawImage(image, 0, 0, null);
    g.dispose();

    return newImage;
  }

  public static BufferedImage copy(final BufferedImage image) {
    return copy(image, hasAlpha(image));
  }

  /**
   * Returns whether or not alpha is supported by the specified {@link BufferedImage}.
   * 
   * @param image
   *          the {@link BufferedImage} to be checked
   * 
   * @return <code>true</code> if this image has alpha; <code>false</code> otherwise
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
   * @return <code>true</code> if this image has alpha; <code>false</code> otherwise
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
   * Creates a new image from a rectangle with the specified location and size into the specified image.
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
   * Creates a new image from a rectangle with the specified size positioned into the middle of the specified image.
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
   * Resizes an image (keeping the original ratio) to fit inside a rectangle with the specified width and height.
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
   * Resizes an image (keeping the original ratio) to fit inside a rectangle with the specified size.
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
   * Resizes an image (keeping the original ratio) to fit inside a rectangle with the specified size.
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
   * Resize and save an image (keeping the original ratio) to fit inside a rectangle with the specified width and
   * height.
   * 
   * @param srcImageFile
   *          the file with the image to be resized
   * @param dstImageFile
   *          the file where the resized image to be saved
   * @param width
   *          maximum width of the resized image
   * @param height
   *          maximum height of the resized image
   * 
   * @throws IOException
   *           if an error occurs
   */
  public static void resizeToFit(final File srcImageFile, final File dstImageFile, final int width, final int height) throws IOException {
    resizeTo(true, srcImageFile, dstImageFile, width, height);
  }

  /**
   * Resizes an image (keeping the original ratio) to fill a rectangle with the specified width and height (removing
   * margins from image if needed).
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
   * Resizes an image (keeping the original ratio) to fill a rectangle with the specified size (removing margins from
   * image if needed).
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
   * Resizes an image (keeping the original ratio) to fill a rectangle with the specified size (removing margins from
   * image if needed).
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
   * Resize and save an image (keeping the original ratio) to fill a rectangle with the specified width and height
   * (removing margins from image if needed).
   * 
   * @param srcImageFile
   *          the file with the image to be resized
   * @param dstImageFile
   *          the file where the resized image to be saved
   * @param width
   *          maximum width of the resized image
   * @param height
   *          maximum height of the resized image
   * 
   * @throws IOException
   *           if an error occurs
   */
  public static void resizeToFill(final File srcImageFile, final File dstImageFile, final int width, final int height) throws IOException {
    resizeTo(false, srcImageFile, dstImageFile, width, height);
  }

  /**
   * Resizes an image (keeping the original ratio):
   * <ul>
   * <li>to fit inside a rectangle with the specified width and height (adding empty space if needed);
   * <li>to fill a rectangle with the specified width and height (removing margins from image if needed).
   * </ul>
   * 
   * @param fit
   *          <code>true</code> if is <strong>FIT</strong>, <code>false</code> if is <strong>FILL</strong>
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
   * Resize and save an image (keeping the original ratio):
   * <ul>
   * <li>to fit inside a rectangle with the specified width and height (adding empty space if needed);
   * <li>to fill a rectangle with the specified width and height (removing margins from image if needed).
   * </ul>
   * 
   * @param fit
   *          <code>true</code> if is <strong>FIT</strong>, <code>false</code> if is <strong>FILL</strong>
   * @param srcImageFile
   *          the file with the image to be resized
   * @param dstImageFile
   *          the file where the resized image to be saved
   * @param width
   *          maximum width of the resized image
   * @param height
   *          maximum height of the resized image
   * 
   * @throws IOException
   *           if an error occurs during reading, writing or resizing
   */
  public static void resizeTo(final boolean fit, final File srcImageFile, final File dstImageFile, final int width, final int height) throws IOException {

    ImageInputStream srcImageInputStream = ImageIO.createImageInputStream(srcImageFile);
    String formatName = ImageIO.getImageReaders(srcImageInputStream).next().getFormatName();

    BufferedImage srcImage = read(srcImageInputStream);
    BufferedImage dstImage = resizeTo(true, srcImage, width, height);

    write(dstImage, formatName, dstImageFile);
  }

  /**
   * Calculates the size of an image (keeping the original ratio) to fit inside a rectangle with the specified width and
   * height.
   * 
   * @param image
   *          the image to fit
   * @param width
   *          maximum width of the the image to fit
   * @param height
   *          maximum height of the the image to fit
   * 
   * @return a {@link Dimension} object representing the size of the the image to fit
   */
  public static Dimension calculateSizeToFit(final BufferedImage image, final int width, final int height) {
    return calculateSizeTo(true, image, width, height);
  }

  /**
   * Calculates the size of an image (keeping the original ratio) to fit inside a rectangle with the specified size.
   * 
   * @param image
   *          the image to fit
   * @param size
   *          maximum size of the image to fit
   * 
   * @return a {@link Dimension} object representing the size of the the image to fit
   */
  public static Dimension calculateSizeToFit(final BufferedImage image, final Dimension size) {
    return calculateSizeToFit(image, size.width, size.height);
  }

  /**
   * Calculates the size of an image (keeping the original ratio) to fit inside a rectangle with the specified size.
   * 
   * @param image
   *          the image to fit
   * @param size
   *          maximum size of the image to fit
   * 
   * @return a {@link Dimension} object representing the size of the the image to fit
   */
  public static Dimension calculateSizeToFit(final BufferedImage image, final int size) {
    return calculateSizeToFit(image, size, size);
  }

  /**
   * Calculates the size of an image (keeping the original ratio) to fill a rectangle with the specified width and
   * height.
   * 
   * @param image
   *          the image to fill
   * @param width
   *          maximum width of the image to fill
   * @param height
   *          maximum height of the image to fill
   * 
   * @return a {@link Dimension} object representing the size of the the image to fill
   */
  public static Dimension calculateSizeToFill(final BufferedImage image, final int width, final int height) {
    return calculateSizeTo(false, image, width, height);
  }

  /**
   * Calculates the size of an image (keeping the original ratio) to fill a rectangle with the specified size.
   * 
   * @param image
   *          the image to fill
   * @param size
   *          maximum size of the image to fill
   * 
   * @return a {@link Dimension} object representing the size of the the image to fill
   */
  public static Dimension calculateSizeToFill(final BufferedImage image, final Dimension size) {
    return calculateSizeToFill(image, size.width, size.height);
  }

  /**
   * Calculates the size of an image (keeping the original ratio) to fill a rectangle with the specified size.
   * 
   * @param image
   *          the image to fill
   * @param size
   *          maximum size of the image to fill
   * 
   * @return a {@link Dimension} object representing the size of the the image to fill
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
   *          <code>true</code> is is <strong>FIT</strong>, <code>false</code> if is <strong>FILL</strong>
   * @param image
   *          the image to fit or fill
   * @param width
   *          maximum width of the image to fit or fill
   * @param height
   *          maximum height of the image to fit or fill
   * 
   * @return a {@link Dimension} object representing the size of the the image to fit or fill
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
   *          the color of the background for the cases when the angle is not a multiplier of 90' ( <code>null</code>
   *          for transparent)
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

  /**
   * The luminosity function or luminous efficiency function describes the average spectral sensitivity of human visual
   * perception of brightness.
   * 
   * @param blue
   *          the blue component of the sRGB color
   * @param green
   *          the green component of the sRGB color
   * @param red
   *          the red component of the sRGB color
   * 
   * @return the brightness of the color (it's actually the relative luminance)
   */
  public static double getBrightness(final int blue, final int green, final int red) {

    //
    // constants

    final double redFactor = 0.2126;
    final double greenFactor = 0.7152;
    final double blueFactor = 0.0722;

    //
    // brightness

    return red * redFactor + green * greenFactor + blue * blueFactor;
  }

  /**
   * The luminosity function or luminous efficiency function describes the average spectral sensitivity of human visual
   * perception of brightness.
   * 
   * @param color
   *          the sRGB color
   * 
   * @return the brightness of the color (it's actually the relative luminance)
   */
  public static double getBrightness(final int color) {

    //
    // colors

    final int blue = color & 0xff;
    final int green = (color & 0xff00) >> 8;
    final int red = (color & 0xff0000) >> 16;

    //
    // brightness

    return getBrightness(blue, green, red);
  }

  public static int getAverageBrightness(final BufferedImage image, final Rectangle area) {

    int totalBrightness = 0;

    for (int x = area.x; x < area.x + area.width; x++) {
      for (int y = area.y; y < area.y + area.height; y++) {
        totalBrightness += getBrightness(image.getRGB(x, y));
      }
    }

    return totalBrightness / (area.width * area.height);
  }

  public static int getAverageBrightness(final BufferedImage image) {
    return getAverageBrightness(image, new Rectangle(0, 0, image.getWidth(), image.getHeight()));
  }

  public static List<Rectangle> compare(final BufferedImage image1, final BufferedImage image2, final boolean mergeChanges) {

    if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
      throw new IllegalArgumentException("different width and/or height ( " + image1.getWidth() + "x" + image1.getHeight() + " / " + image2.getWidth() + "x" + image2.getHeight() + ")");
    }

    //
    // constants

    final int thresholdAverageBrightness = 20;
    final int blockWidth = 10;
    final int blockHeight = 10;

    final int horizontalBlocks = (int) (image1.getWidth() / blockWidth);
    final int verticalBlocks = (int) (image1.getHeight() / blockHeight);

    //
    // horizontal blocks

    final int horizontalBlocksLength = (int) (image1.getWidth() / horizontalBlocks);
    final int[] horizontalBlocksX = new int[horizontalBlocks];
    final int[] horizontalBlocksWidth = new int[horizontalBlocks];
    final int horizontalBlocksWidthDiff = image1.getWidth() - horizontalBlocks * horizontalBlocksLength;

    for (int i = 0; i < horizontalBlocks; i++) {

      horizontalBlocksWidth[i] = horizontalBlocksLength;
      if (i < horizontalBlocksWidthDiff) {
        horizontalBlocksWidth[i] += 1;
      }

      if (i == 0) {
        horizontalBlocksX[i] = 0;
      } else {
        horizontalBlocksX[i] = horizontalBlocksX[i - 1] + horizontalBlocksWidth[i];
      }
    }

    //
    // vertical blocks

    final int verticalBlocksLength = (int) (image1.getHeight() / verticalBlocks);
    final int[] verticalBlocksY = new int[verticalBlocks];
    final int[] verticalBlocksHeight = new int[verticalBlocks];
    final int verticalBlocksHeightDiff = image1.getHeight() - verticalBlocks * verticalBlocksLength;

    for (int j = 0; j < verticalBlocks; j++) {

      verticalBlocksHeight[j] = verticalBlocksLength;
      if (j < verticalBlocksHeightDiff) {
        verticalBlocksHeight[j] += 1;
      }

      if (j == 0) {
        verticalBlocksY[j] = 0;
      } else {
        verticalBlocksY[j] = verticalBlocksY[j - 1] + verticalBlocksHeight[j];
      }
    }

    //
    // changes

    final List<Rectangle> changes = new ArrayList<>();

    for (int i = 0; i < horizontalBlocks; i++) {
      for (int j = 0; j < verticalBlocks; j++) {

        Rectangle block = new Rectangle(horizontalBlocksX[i], verticalBlocksY[j], horizontalBlocksWidth[i], verticalBlocksHeight[j]);

        int ab1 = getAverageBrightness(image1, block);
        int ab2 = getAverageBrightness(image2, block);

        if (Math.abs(ab1 - ab2) >= thresholdAverageBrightness) {
          changes.add(block);
        }
      }
    }

    //
    // merge changes

    if (mergeChanges) {

      //
      // the loop works as long as there are intersecting changes
      // so we start with the the pessimistic value

      boolean thereAreIntersectingChanges = true;
      while (thereAreIntersectingChanges) {

        //
        // we are in the loop now
        // so let's hope there are no interesting changes

        thereAreIntersectingChanges = false;

        //
        // for each rectangle representing a change
        // we check if he touches the others

        for (Iterator<Rectangle> i = changes.iterator(); i.hasNext();) {
          Rectangle c1 = i.next();

          for (Rectangle c2 : changes) {

            //
            // we have a double loop on the same list
            // so sometimes the two pointers are on the very same object

            if (c1.equals(c2)) {
              continue;
            }

            //
            // if first change touches or intersects the second one

            if (new Rectangle(c1.x, c1.y, c1.width + 1, c1.height + 1).intersects(c2)) {

              //
              // merge the two changes (in the second rectangle)
              // and remove the first one from the list

              c2.add(c1);
              i.remove();

              //
              // mark that we found two touching (or intersecting) changes
              // and break the second loop (we removed one element)

              thereAreIntersectingChanges = true;
              break;
            }
          }
        }
      }
    }

    //
    // return the changes

    return changes;
  }

  public static List<Rectangle> compare(final BufferedImage image1, final BufferedImage image2) {
    return compare(image1, image2, false);
  }

  public static BufferedImage drawShapes(final BufferedImage image, final List<? extends Shape> shapes, final Color color, final Stroke stroke) {

    Graphics2D g = image.createGraphics();
    g.setColor(color);

    if (stroke != null) {
      g.setStroke(stroke);
    }

    for (Shape shape : shapes) {
      g.draw(shape);
    }

    g.dispose();

    return image;
  }

  public static BufferedImage drawShapes(final BufferedImage image, final List<? extends Shape> shapes, final Color color, final int thickness) {
    return drawShapes(image, shapes, color, new BasicStroke(thickness));
  }

  public static BufferedImage drawShapes(final BufferedImage image, final List<? extends Shape> shapes, final Color color) {
    return drawShapes(image, shapes, color, null);
  }

}
