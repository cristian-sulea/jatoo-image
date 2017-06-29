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
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A handy image memory cache with {@link SoftReference} objects, which are cleared at the discretion of the garbage
 * collector in response to memory demand.
 * <p>
 * All soft references to softly-reachable objects are guaranteed to have been cleared before the virtual machine throws
 * an {@link OutOfMemoryError}.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 2.0, June 29, 2017
 */
public class ImageMemoryCache {

  private final Map<Object, SoftReference<BufferedImage>> cache = new HashMap<Object, SoftReference<BufferedImage>>();

  public synchronized void put(final File file, final BufferedImage image) {
    put(createKey(file), image);
  }

  public synchronized void put(final Object key, final BufferedImage image) {
    cache.put(key, new SoftReference<BufferedImage>(image));
    purge();
  }

  public synchronized BufferedImage get(final File file) {
    return get(createKey(file));
  }

  public synchronized BufferedImage get(final Object key) {

    BufferedImage image = null;

    SoftReference<BufferedImage> softReference = cache.get(key);
    if (softReference != null) {
      image = softReference.get();
    }

    return image;
  }

  public synchronized void remove(final File file) {
    remove(createKey(file));
  }

  public synchronized void remove(final Object key) {
    cache.remove(key);
    purge();
  }

  public synchronized void clear() {
    cache.clear();
  }

  /**
   * Purges all entries with cleared soft references.
   */
  private void purge() {

    for (Iterator<Map.Entry<Object, SoftReference<BufferedImage>>> i = cache.entrySet().iterator(); i.hasNext();) {
      Entry<Object, SoftReference<BufferedImage>> entry = i.next();

      SoftReference<BufferedImage> softReference = entry.getValue();

      if (softReference == null || softReference.get() == null) {
        i.remove();
      }
    }

    // for (Iterator<Object> i = cache.keySet().iterator(); i.hasNext();) {
    // Object key = i.next();
    //
    // SoftReference<BufferedImage> softReference = cache.get(key);
    //
    // if (softReference == null || softReference.get() == null) {
    // i.remove();
    // }
    // }
  }

  private Object createKey(final File file) {
    return file.getAbsolutePath() + "|" + file.lastModified() + "|" + file.length();
  }

}
