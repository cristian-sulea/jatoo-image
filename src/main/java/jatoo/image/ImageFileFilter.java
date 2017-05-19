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

import java.io.File;
import java.io.FileFilter;

/**
 * A filter for image pathnames.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.2.1, May 12, 2016
 */
public final class ImageFileFilter implements FileFilter {

  /** The singleton instance. */
  private static final ImageFileFilter INSTANCE = new ImageFileFilter();

  /**
   * Returns an image file filter (singleton) for image pathnames.
   * 
   * @return an image file filter (singleton)
   */
  public static ImageFileFilter getInstance() {
    return INSTANCE;
  }

  /** The list with the image formats that can be read and decoded. */
  private final String[] imageFormatNames = ImageUtils.getFormatNames();

  /**
   * Private constructor, this is a singleton.
   */
  private ImageFileFilter() {

    //
    // convert the format names to lower case
    // the file name will be also converted to lower case

    for (int i = 0; i < imageFormatNames.length; i++) {
      imageFormatNames[i] = imageFormatNames[i].toLowerCase();
    }
  }

  @Override
  public boolean accept(final File file) {

    boolean accept = false;

    if (file.isFile()) {

      String fileName = file.getName().toLowerCase();

      for (String imageFormatName : imageFormatNames) {
        if (fileName.endsWith(imageFormatName)) {
          accept = true;
          break;
        }
      }
    }

    return accept;
  }
}
