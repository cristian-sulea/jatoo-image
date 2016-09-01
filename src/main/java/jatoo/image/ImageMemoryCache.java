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
