/*
 * Copyright (C) 2014 Cristian Sulea ( http://cristian.sulea.net )
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
 * @version 1.2, October 5, 2015
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
    // ensure folders before checking if is a folder

    folder.mkdirs();

    //
    // if is not a folder

    if (!folder.isDirectory()) {
      throw new IllegalArgumentException(folder + " is not a folder");
    }

    //
    // we are just fine

    this.folder = folder;
  }

  public final synchronized BufferedImage get(final File file, final int width, final int height) {

    final File thumbnailFileParent = new File(folder, width + FILE_NAME_PATTERN_REPLACEMENT + height);
    thumbnailFileParent.mkdirs();

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

        thumbnailFile.setLastModified(System.currentTimeMillis());
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

    for (File parent : folder.listFiles()) {

      for (File file : parent.listFiles()) {
        file.delete();
      }

      parent.delete();
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
