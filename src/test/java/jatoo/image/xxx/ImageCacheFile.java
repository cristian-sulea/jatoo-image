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

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferUShort;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A handy image file cache, with the images stored as (or restored from) data
 * buffer arrays.
 * <p>
 * Note that using <code>image.getRaster().getDataBuffer().getData()</code> may
 * cause the <code>DataBuffer</code> object to be incompatible with performance
 * optimizations used by some implementations (such as caching an associated
 * image in video memory).
 * <p>
 * For example in Windows 7 the paint of a recovered image with this cache will
 * be very slow.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.0, July 9, 2014
 */
public class ImageCacheFile {

  private static final Pattern FILE_NAME_PATTERN = Pattern.compile("[^a-zA-Z0-9\\-]");
  private static final String FILE_NAME_PATTERN_REPLACEMENT = "_";

  private final Log logger = LogFactory.getLog(getClass());

  private final File folder;

  /**
   * Constructs a new cache in the provided folder. A lock will be created so a
   * new instance will not be able to use the same folder.
   * 
   * @param folder
   *          the folder where cache files are stored
   * 
   * @throws IOException
   */
//  @SuppressWarnings("resource")
  public ImageCacheFile(final File folder) {

    //
    // ensure folders before checking if is a folder
    
    folder.mkdirs();
    
    //
    // if is not a folder, also #mkdirs() will fail

    if (folder.isDirectory()) {
      this.folder = folder;
    } else {
      this.folder = new File(folder.getParentFile(), "image-cache-check-your-fucking-provided-folder");
      this.folder.mkdirs();
    }

    //
    // lock folder

//    RandomAccessFile lockRandomAccessFile = null;
//    FileChannel lockChannel = null;
//
//    try {
//
//      final File lockFile = new File(folder, ".lock");
//
//      if (!lockFile.exists()) {
//        lockFile.createNewFile();
//      }
//
//      lockRandomAccessFile = new RandomAccessFile(lockFile, "rw");
//      lockChannel = lockRandomAccessFile.getChannel();
//
//      boolean isLocked = false;
//
//      try {
//        isLocked = lockChannel.tryLock() == null;
//      } catch (OverlappingFileLockException e) {
//        isLocked = true;
//      }
//
//      if (isLocked) {
//
//        lockChannel.close();
//        lockRandomAccessFile.close();
//
//        throw new IllegalArgumentException("folder already used by another cache instance: " + folder.getAbsolutePath());
//      }
//    }
//
//    catch (IOException e) {
//      throw new IllegalArgumentException("unexpected exception trying to lock the folder: " + folder.getAbsolutePath(), e);
//    }
  }

  public synchronized BufferedImage put(File file, BufferedImage image) throws IOException {

    final int imageWidth = image.getWidth();
    final int imageHeight = image.getHeight();
    final int imageType = image.getType();

    final DataBuffer imageDataBuffer = image.getRaster().getDataBuffer();
    final int imageDataBufferType = imageDataBuffer.getDataType();

    Object data;

    switch (imageDataBufferType) {

      case DataBuffer.TYPE_BYTE:
        data = ((DataBufferByte) imageDataBuffer).getData();
        break;

      case DataBuffer.TYPE_USHORT:
        data = ((DataBufferUShort) imageDataBuffer).getData();
        break;

      case DataBuffer.TYPE_INT:
        data = ((DataBufferInt) imageDataBuffer).getData();
        break;

      default:
        throw new IllegalArgumentException("unknown image data buffer type: " + imageDataBufferType);
    }

    ObjectOutputStream stream = null;

    try {

      stream = new ObjectOutputStream(new FileOutputStream(new File(folder, convertAbsolutePathInCacheFileName(file))));

      stream.writeLong(file.lastModified());
      stream.writeLong(file.length());

      stream.writeInt(imageWidth);
      stream.writeInt(imageHeight);
      stream.writeInt(imageType);

      stream.writeObject(data);
    }

    finally {
      stream.close();
    }

    return image;
  }

  public synchronized BufferedImage get(File file) {

    File cacheFile = new File(folder, convertAbsolutePathInCacheFileName(file));

    if (cacheFile.exists()) {

      cacheFile.setLastModified(System.currentTimeMillis());

      ObjectInputStream stream = null;

      try {

        stream = new ObjectInputStream(new FileInputStream(cacheFile));

        final long lastModified = stream.readLong();
        final long length = stream.readLong();

        if (file.lastModified() == lastModified && file.length() == length) {

          final int imageWidth = stream.readInt();
          final int imageHeight = stream.readInt();
          final int imageType = stream.readInt();

          final BufferedImage image = new BufferedImage(imageWidth, imageHeight, imageType);

          final DataBuffer imageDataBuffer = image.getRaster().getDataBuffer();
          final int imageDataBufferType = imageDataBuffer.getDataType();

          switch (imageDataBufferType) {

            case DataBuffer.TYPE_BYTE:

              byte[] imageDataAsByteArray = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
              byte[] cacheDataAsByteArray = (byte[]) stream.readObject();

              System.arraycopy(cacheDataAsByteArray, 0, imageDataAsByteArray, 0, cacheDataAsByteArray.length);

              break;

            case DataBuffer.TYPE_USHORT:

              short[] imageDataAsShortArray = ((DataBufferUShort) image.getRaster().getDataBuffer()).getData();
              short[] cacheDataAsShortArray = (short[]) stream.readObject();

              System.arraycopy(cacheDataAsShortArray, 0, imageDataAsShortArray, 0, cacheDataAsShortArray.length);

              break;

            case DataBuffer.TYPE_INT:

              int[] imageDataAsIntArray = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
              int[] cacheDataAsIntArray = (int[]) stream.readObject();

              System.arraycopy(cacheDataAsIntArray, 0, imageDataAsIntArray, 0, cacheDataAsIntArray.length);

              break;

            default:
              throw new IllegalArgumentException("unknown image data buffer type: " + imageDataBufferType);
          }

          return image;
        }
      }

      catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }

      finally {
        if (stream != null) {
          try {
            stream.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }

    return null;
  }

  public synchronized BufferedImage get(File file, GetCallback callback) {

    File cacheFile = new File(folder, convertAbsolutePathInCacheFileName(file));

    if (cacheFile.exists()) {

      cacheFile.setLastModified(System.currentTimeMillis());

      ObjectInputStream stream = null;

      try {

        synchronized (callback) {
          stream = new ObjectInputStream(new FileInputStream(cacheFile));
          callback.setStream(stream);
        }

        final long lastModified = stream.readLong();
        final long length = stream.readLong();

        if (file.lastModified() == lastModified && file.length() == length) {

          final int imageWidth = stream.readInt();
          final int imageHeight = stream.readInt();
          final int imageType = stream.readInt();

          final BufferedImage image = new BufferedImage(imageWidth, imageHeight, imageType);

          final DataBuffer imageDataBuffer = image.getRaster().getDataBuffer();
          final int imageDataBufferType = imageDataBuffer.getDataType();

          switch (imageDataBufferType) {

            case DataBuffer.TYPE_BYTE:

              byte[] imageDataAsByteArray = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
              byte[] cacheDataAsByteArray = (byte[]) stream.readObject();

              System.arraycopy(cacheDataAsByteArray, 0, imageDataAsByteArray, 0, cacheDataAsByteArray.length);

              break;

            case DataBuffer.TYPE_USHORT:

              short[] imageDataAsShortArray = ((DataBufferUShort) image.getRaster().getDataBuffer()).getData();
              short[] cacheDataAsShortArray = (short[]) stream.readObject();

              System.arraycopy(cacheDataAsShortArray, 0, imageDataAsShortArray, 0, cacheDataAsShortArray.length);

              break;

            case DataBuffer.TYPE_INT:

              int[] imageDataAsIntArray = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
              int[] cacheDataAsIntArray = (int[]) stream.readObject();

              System.arraycopy(cacheDataAsIntArray, 0, imageDataAsIntArray, 0, cacheDataAsIntArray.length);

              break;

            default:
              throw new IllegalArgumentException("unknown image data buffer type: " + imageDataBufferType);
          }

          return image;
        }
      }

      catch (IOException | ClassNotFoundException e) {
        if (!callback.isStreamClosed()) {
          logger.warn("image could not be read from cache file: " + file, e);
        }
      }

      finally {
        if (stream != null) {
          try {
            stream.close();
          } catch (IOException e) {
            logger.warn("cache stream could not be closed: " + file, e);
          }
        }
      }
    }

    return null;
  }

  public synchronized void remove(File file) {

    File cacheFile = new File(folder, convertAbsolutePathInCacheFileName(file));

    if (cacheFile.exists()) {
      cacheFile.delete();
    }
  }

  public synchronized void clear() {

    for (File file : folder.listFiles()) {
      file.delete();
    }
  }

  public static class GetCallback {

    private InputStream stream;

    private boolean streamClosed = false;

    private void setStream(InputStream stream) {
      synchronized (this) {
        this.stream = stream;
      }
    }

    public void closeStream() throws IOException {
      synchronized (this) {

        streamClosed = true;

        if (stream != null) {
          stream.close();
        }
      }
    }

    public boolean isStreamClosed() {
      synchronized (this) {
        return streamClosed;
      }
    }
  }

  private String convertAbsolutePathInCacheFileName(File file) {
    return FILE_NAME_PATTERN.matcher(file.getAbsolutePath()).replaceAll(FILE_NAME_PATTERN_REPLACEMENT) + FILE_NAME_PATTERN_REPLACEMENT + file.lastModified() + FILE_NAME_PATTERN_REPLACEMENT + file.length();
  }

}
