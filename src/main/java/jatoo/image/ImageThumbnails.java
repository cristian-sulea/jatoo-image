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

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Thumbnails are reduced-size versions of images, used to help in recognizing
 * and organizing them.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.2.1, May 12, 2016
 */
public class ImageThumbnails {

  private static final Pattern FILE_NAME_PATTERN = Pattern.compile("[^a-zA-Z0-9\\-]");
  private static final String FILE_NAME_PATTERN_REPLACEMENT = "_";
  private static final String PNG_EXTENSION = ".png";

  /** the logger */
  private static final Log logger = LogFactory.getLog(ImageThumbnails.class);

  private final File folder;

  /**
   * Constructs a new image thumbnails manager in user folder.
   */
  public ImageThumbnails() {
    this(new File(new File(new File(new File(System.getProperty("user.home")), ".jatoo"), "image"), "thumbnails"));
  }

  /**
   * Constructs a new image thumbnails manager in the provided folder.
   * 
   * @param folder
   *          the folder where thumbnail files are stored
   */
  public ImageThumbnails(final File folder) {

    //
    // if folder does not exists

    if (!folder.exists()) {

      //
      // creates the folder (and parent folders)

      if (!folder.mkdirs()) {
        throw new IllegalArgumentException(folder + " was not created");
      }
    }

    //
    // if folder exists

    else {

      //
      // check if it's a folder

      if (!folder.isDirectory()) {
        throw new IllegalArgumentException(folder + " is not a folder");
      }
    }

    //
    // we are just fine

    this.folder = folder;
  }

  public final synchronized BufferedImage get(final File file, final int width, final int height) {

    final File thumbnailFileParent = new File(folder, width + FILE_NAME_PATTERN_REPLACEMENT + height);
    if (!thumbnailFileParent.exists() && !thumbnailFileParent.mkdirs()) {
      throw new IllegalArgumentException(thumbnailFileParent + " was not created");
    }

    final File thumbnailFile = new File(thumbnailFileParent, getThumbnailFileName(file));

    BufferedImage thumbnail = null;

    //
    // if the thumbnail file does not exists
    // there is no need to try a read

    if (thumbnailFile.exists()) {

      try {

        //
        // try to read the thumbnail image

        thumbnail = ImageUtils.read(thumbnailFile);

        //
        // touch (will be used to know when this thumbnail was used last time)

        if (!thumbnailFile.setLastModified(System.currentTimeMillis())) {
          logger.info("set last-modified time on thumbnail file " + thumbnailFile + " failed");
        }
      }

      catch (IOException e) {
        logger.warn("failed to read thumbnail: " + thumbnailFile, e);
      }
    }

    //
    // no thumbnail or some unexpected exception while reading

    if (thumbnail == null) {

      try {

        //
        // load (read)

        thumbnail = ImageUtils.read(file);

        //
        // resize (if needed) & crop

        // if (thumbnail.getWidth() > width || thumbnail.getHeight() > height) {
        // thumbnail = ImageUtils.resizeToFit(thumbnail, width, height);
        // }
        // thumbnail = ImageUtils.crop(thumbnail, width, height);

        //
        // or use resize to fill

        thumbnail = ImageUtils.resizeToFill(thumbnail, width, height);

        //
        // save (write)

        try {
          ImageUtils.writePNG(thumbnail, thumbnailFile);
        } catch (IOException e) {
          logger.error("failed to save the image thumbnail to file: " + thumbnailFile, e);
        }
      }

      catch (IOException e) {
        logger.error("failed to read image from the provided file: " + file, e);
      }
    }

    //
    // null is an accepted value

    return thumbnail;
  }

  public final synchronized BufferedImage get(final File file, final Dimension size) {
    return get(file, size.width, size.height);
  }

  public final synchronized BufferedImage get(final File file, final int size) {
    return get(file, size, size);
  }

  public final synchronized void clear() {

    File[] files = folder.listFiles();

    if (files != null) {
      for (File file : files) {
        delete(file);
      }
    }
  }

  private void delete(File file) {
    if (file.isDirectory()) {
      File[] files2 = file.listFiles();
      if (files2 != null) {
        for (File file2 : files2) {
          delete(file2);
        }
      }
    }
    if (!file.delete()) {
      throw new IllegalStateException(file + " cannot be deleted");
    }
  }

  private String getThumbnailFileName(final File file) {

    final Matcher matcher = FILE_NAME_PATTERN.matcher(file.getAbsolutePath());

    final StringBuilder thumbnailFileName = new StringBuilder();

    thumbnailFileName.append(matcher.replaceAll(FILE_NAME_PATTERN_REPLACEMENT));
    thumbnailFileName.append(FILE_NAME_PATTERN_REPLACEMENT);
    thumbnailFileName.append(file.lastModified());
    thumbnailFileName.append(FILE_NAME_PATTERN_REPLACEMENT);
    thumbnailFileName.append(file.length());
    thumbnailFileName.append(PNG_EXTENSION);

    return thumbnailFileName.toString();
  }

}
