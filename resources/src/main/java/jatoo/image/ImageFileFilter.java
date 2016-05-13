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

import java.io.File;
import java.io.FileFilter;

/**
 * A filter for image pathnames.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.2, October 5, 2015
 */
public final class ImageFileFilter implements FileFilter {

  private static final ImageFileFilter INSTANCE = new ImageFileFilter();

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
