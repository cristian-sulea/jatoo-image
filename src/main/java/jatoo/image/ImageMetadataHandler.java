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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A collection of utility methods to ease the work with image metadata.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 2.1, March 7, 2018
 */
public abstract class ImageMetadataHandler {

  /** The logger. */
  private static final Log logger = LogFactory.getLog(ImageMetadataHandler.class);

  public static final String HANDLER_EXIFTOOL = "ExifTool";
  public static final String HANDLER_METADATA_EXTRACTOR = "metadata extractor";

  private static ImageMetadataHandler defaultInstance;
  private static Map<String, ImageMetadataHandler> availableInstances = new HashMap<>();

  static {

    Map<String, String> handlerClasses = new LinkedHashMap<>();
    handlerClasses.put(HANDLER_EXIFTOOL, "jatoo.image.metadata.exiftool.ExifToolImageMetadataHandler");
    handlerClasses.put(HANDLER_METADATA_EXTRACTOR, "jatoo.image.metadata.extractor.ExtractorImageMetadataHandler");

    List<Throwable> handlerClassesExceptions = new ArrayList<>();

    for (String handler : handlerClasses.keySet()) {

      try {

        availableInstances.put(handler, (ImageMetadataHandler) Class.forName(handlerClasses.get(handler)).newInstance());

        if (defaultInstance == null) {
          defaultInstance = availableInstances.get(handler);
        }
      }

      catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
        handlerClassesExceptions.add(e);
      }
    }

    if (defaultInstance == null) {

      logger.warn("there is not even one image metadata handler implementation on the class path");
      for (Throwable t : handlerClassesExceptions) {
        logger.warn(t);
      }

      defaultInstance = new ImageMetadataHandler() {

        @Override
        protected String getNotImplementedExceptionText() {
          return super.getNotImplementedExceptionText() + " / there is not even one image metadata handler implementation on the class path";
        }

        @Override
        public boolean copyMetadata(File srcImage, File dstImage) {
          throw new IllegalStateException(getNotImplementedExceptionText());
        }

        @Override
        public boolean setDateTaken(File image, Date date) {
          throw new IllegalStateException(getNotImplementedExceptionText());
        }

        @Override
        public ImageMetadata getMetadata(File image) {
          throw new IllegalStateException(getNotImplementedExceptionText());
        }

        @Override
        public boolean removeMetadata(File image) {
          throw new IllegalStateException(getNotImplementedExceptionText());
        }

        @Override
        public Map<File, Date> getDateTakenForFolder(File folder) {
          throw new IllegalStateException(getNotImplementedExceptionText());
        }

        @Override
        public Date getDateTaken(File image) {
          throw new IllegalStateException(getNotImplementedExceptionText());
        }

        @Override
        public int getOrientation(File image) {
          throw new IllegalStateException(getNotImplementedExceptionText());
        }
      };
    }
  }

  public static ImageMetadataHandler getInstance() {
    return defaultInstance;
  }

  public static ImageMetadataHandler getInstance(String handler) {
    return availableInstances.get(handler);
  }

  public static void setDefaultInstance(ImageMetadataHandler defaultInstance) {
    ImageMetadataHandler.defaultInstance = defaultInstance;
  }

  protected ImageMetadataHandler() {}

  //
  //

  protected String getNotImplementedExceptionText() {
    return "method not implemented / operation not supported";
  }

  //
  //

  public ImageMetadata getMetadata(String image) {
    return getMetadata(new File(image));
  }

  public abstract ImageMetadata getMetadata(File image);

  public boolean copyMetadata(String srcImage, String dstImage) {
    return copyMetadata(new File(srcImage), new File(dstImage));
  }

  public abstract boolean copyMetadata(File srcImage, File dstImage);

  public boolean removeMetadata(String image) {
    return removeMetadata(new File(image));
  }

  public abstract boolean removeMetadata(File image);

  //
  //

  public Date getDateTaken(String image) {
    return getDateTaken(new File(image));
  }

  public abstract Date getDateTaken(File image);

  public boolean setDateTaken(String image, int year, int month, int day, int hour, int minute, int second) {
    return setDateTaken(new File(image), year, month, day, hour, minute, second);
  }

  public boolean setDateTaken(File image, int year, int month, int day, int hour, int minute, int second) {

    Calendar c = Calendar.getInstance();
    c.clear();
    c.set(year, month - 1, day, hour, minute, second);

    return setDateTaken(image, c.getTime());
  }

  public boolean setDateTaken(String image, Date date) {
    return setDateTaken(new File(image), date);
  }

  public abstract boolean setDateTaken(File image, Date date);

  public Map<File, Date> getDateTakenForFolder(String folder) {
    return getDateTakenForFolder(new File(folder));
  }

  public abstract Map<File, Date> getDateTakenForFolder(File folder);

  //
  //

  public int getOrientation(String image) {
    return getOrientation(new File(image));
  }

  public abstract int getOrientation(File image);

  public String getOrientationText(String image) {
    return getOrientationText(getOrientation(image));
  }

  public String getOrientationText(File image) {
    return getOrientationText(getOrientation(image));
  }

  protected static String getOrientationText(int orientation) {

    switch (orientation) {

      case 1:
        return "Horizontal (normal)";
      case 2:
        return "Mirror horizontal";
      case 3:
        return "Rotate 180";
      case 4:
        return "Mirror vertical";
      case 5:
        return "Mirror horizontal and rotate 270 CW";
      case 6:
        return "Rotate 90 CW";
      case 7:
        return "Mirror horizontal and rotate 90 CW";
      case 8:
        return "Rotate 270 CW";

      default:
        return String.valueOf(orientation);
    }
  }

}
