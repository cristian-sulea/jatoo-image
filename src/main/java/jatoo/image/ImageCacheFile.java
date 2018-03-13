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
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A handy image file cache.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.0, March 13, 2018
 */
public class ImageCacheFile extends ImageCache {

  /** The logger. */
  private static final Log logger = LogFactory.getLog(ImageThumbnails.class);

  // private static final Pattern FILE_NAME_PATTERN = Pattern.compile("[^a-zA-Z0-9\\-]");
  private static final ImageUtils.FORMAT FORMAT = ImageUtils.FORMAT.JPEG;
  private static final String EXTENSION = "." + ImageUtils.FORMAT.JPEG.name().toLowerCase();

  /** The folder where cached image files are stored. */
  private final File folder;

  /**
   * Constructs a new image cache in the provided folder.
   * 
   * @param folder
   *          the folder where files are stored
   */
  public ImageCacheFile(final File folder) {

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
    // done

    this.folder = folder;
  }

  @Override
  protected void addImpl(BufferedImage image, String key) {

    File file = createFileFromKey(key);

    try {
      ImageUtils.save(image, file, FORMAT);
    } catch (IOException e) {
      logger.error("failed to add the image: " + file, e);
    }
  }

  @Override
  protected BufferedImage getImpl(String key) {

    File file = createFileFromKey(key);

    BufferedImage image = null;

    //
    // if the file does not exists
    // there is no need to try a read

    if (file.exists()) {

      try {

        //
        // try to read the cached image

        image = ImageUtils.read(file);

        //
        // touch (will be used to know when this image was used last time)

        if (!file.setLastModified(System.currentTimeMillis())) {
          logger.info("set last-modified time on the cached image file " + file + " failed");
        }

        //
        // return the cached image

        return image;
      }

      catch (IOException e) {
        logger.warn("failed to read the cached image file: " + file, e);
      }
    }

    //
    // null is an accepted value

    return null;
  }

  @Override
  protected void removeImpl(String key) {

    File file = createFileFromKey(key);

    if (!file.delete()) {
      throw new IllegalStateException(file + " cannot be deleted");
    }
  }

  @Override
  protected void clearImpl() {

    File[] files = folder.listFiles();

    if (files != null) {
      for (File file : files) {
        delete(file);
      }
    }
  }

  private File createFileFromKey(String key) {
    // return new File(folder, THUMBNAIL_FILE_NAME_PATTERN.matcher(key).replaceAll("_") + "." +
    // ImageUtils.FORMAT.JPEG.name().toLowerCase());
    return new File(folder, new File(key).getName() + "_" + key.hashCode() + EXTENSION);
  }

  private void delete(final File file) {
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      if (files != null) {
        for (File f : files) {
          delete(f);
        }
      }
    }
    if (!file.delete()) {
      throw new IllegalStateException(file + " cannot be deleted");
    }
  }

}
