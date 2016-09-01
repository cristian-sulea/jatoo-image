/*
 * Copyright (C) Cristian Sulea ( http://cristian.sulea.net )
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package jatoo.image.xxx;

import jatoo.image.ImageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageCacheFileTest {

  public static void main(String[] args) throws Exception {

    ImageCacheFile cache = new ImageCacheFile(new File(new File(new File(System.getProperty("user.home")), ".jatoo"), ".image-cache"));

    List<File> folders = new ArrayList<>();
    folders.add(new File("c:\\Temp"));
    folders.add(new File("c:\\Temp\\x"));

    for (File folder : folders) {

      for (File file : folder.listFiles()) {

        if (file.isFile()) {

          cache.put(file, ImageUtils.read(file));

          long t1 = System.currentTimeMillis();
          cache.get(file);
          long t2 = System.currentTimeMillis();

          System.out.println(t2 - t1);

          long t3 = System.currentTimeMillis();
          ImageUtils.read(file);
          long t4 = System.currentTimeMillis();

          System.out.println(t4 - t3);
        }
      }
    }

    cache.clear();
  }
}
