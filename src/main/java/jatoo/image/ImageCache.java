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

/**
 * Abstract image cache.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.1, March 13, 2018
 */
public abstract class ImageCache {

  //
  // add

  public synchronized void add(final BufferedImage image, final File file) {
    addImpl(image, createKeyFromFile(file));
  }

  public synchronized void add(final BufferedImage image, final File file, final Object... keys) {
    addImpl(image, createCompoundKey(createKeyFromFile(file), createCompoundKey(keys)));
  }

  public synchronized void add(final BufferedImage image, final Object... keys) {
    addImpl(image, createCompoundKey(keys));
  }

  //
  // get

  public synchronized BufferedImage get(final File file) {
    return getImpl(createKeyFromFile(file));
  }

  public synchronized BufferedImage get(final File file, final Object... keys) {
    return getImpl(createCompoundKey(createKeyFromFile(file), createCompoundKey(keys)));
  }

  public synchronized BufferedImage get(final Object... keys) {
    return getImpl(createCompoundKey(keys));
  }

  //
  // remove

  public synchronized void remove(final File file) {
    removeImpl(createKeyFromFile(file));
  }

  public synchronized void remove(final File file, final Object... keys) {
    removeImpl(createCompoundKey(createKeyFromFile(file), createCompoundKey(keys)));
  }

  public synchronized void remove(final Object... keys) {
    removeImpl(createCompoundKey(keys));
  }

  //
  // clear

  public synchronized void clear() {
    clearImpl();
  }

  //
  // private/abstract methods

  private String createCompoundKey(Object... keys) {

    if (keys.length == 0) {
      throw new IllegalArgumentException("keys.length == 0");
    }

    else if (keys.length == 1) {
      return String.valueOf(keys[0]);
    }

    else {
      return createCompoundKeyImpl(keys);
    }
  }

  protected abstract void addImpl(final BufferedImage image, final String key);

  protected abstract BufferedImage getImpl(String key);

  protected abstract void removeImpl(String key);

  protected abstract void clearImpl();

  protected String createKeyFromFile(File file) {
    return createCompoundKeyImpl(file.getAbsolutePath(), file.lastModified(), file.length());
  }

  protected String createCompoundKeyImpl(Object... keys) {

    StringBuilder buffer = new StringBuilder();
    buffer.append(String.valueOf(keys[0]));

    for (int i = 1; i < keys.length; i++) {
      buffer.append('_');
      buffer.append(String.valueOf(keys[i]));
    }

    return buffer.toString();
  }

}
