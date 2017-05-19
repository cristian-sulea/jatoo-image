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
