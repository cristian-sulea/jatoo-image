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
