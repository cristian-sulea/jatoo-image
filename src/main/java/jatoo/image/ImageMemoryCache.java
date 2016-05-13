/*
 * Copyright (C) 2014 Cristian Sulea ( http://cristian.sulea.net )
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
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A handy image memory cache with {@link SoftReference} objects, which are
 * cleared at the discretion of the garbage collector in response to memory
 * demand.
 * <p>
 * All soft references to softly-reachable objects are guaranteed to have been
 * cleared before the virtual machine throws an {@link OutOfMemoryError}.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.3.1, May 12, 2016
 */
public class ImageMemoryCache {

  private final Map<Object, SoftReference<BufferedImage>> cache = new HashMap<Object, SoftReference<BufferedImage>>();

  public final synchronized void put(final Object key, final BufferedImage image) {
    cache.put(key, new SoftReference<BufferedImage>(image));
    purge();
  }

  public final synchronized BufferedImage get(final Object key) {

    BufferedImage image = null;

    SoftReference<BufferedImage> softReference = cache.get(key);
    if (softReference != null) {
      image = softReference.get();
    }

    return image;
  }

  public final synchronized void remove(final Object key) {
    cache.remove(key);
    purge();
  }

  public final synchronized void clear() {
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

}
