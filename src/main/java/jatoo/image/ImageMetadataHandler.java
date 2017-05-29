package jatoo.image;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A collection of utility methods to ease the work with image metadata.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.0-SNAPSHOT, May 26, 2017
 */
public abstract class ImageMetadataHandler {

  /** The logger. */
  private static final Log logger = LogFactory.getLog(ImageMetadataHandler.class);

  public static final String HANDLER_EXIFTOOL = "ExifTool";

  private static ImageMetadataHandler defaultInstance;
  private static Map<String, ImageMetadataHandler> availableInstances = new HashMap<>();

  static {

    Map<String, String> handlerClasses = new LinkedHashMap<>();
    handlerClasses.put(HANDLER_EXIFTOOL, "jatoo.image.metadata.exiftool.ExifToolImageMetadataHandler");

    for (String handler : handlerClasses.keySet()) {

      try {

        availableInstances.put(handler, (ImageMetadataHandler) Class.forName(handlerClasses.get(handler)).newInstance());

        if (defaultInstance == null) {
          defaultInstance = availableInstances.get(handler);
        }
      }

      catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
        e.printStackTrace();
        logger.info("", e);
      }
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

  public ImageMetadata getMetadata(String image) {
    return getMetadata(new File(image));
  }

  public abstract ImageMetadata getMetadata(File image);

  public Date getDateTimeOriginal(String image) {
    return getDateTimeOriginal(new File(image));
  }

  public abstract Date getDateTimeOriginal(File image);

  public boolean setDateTimeOriginal(String image, int year, int month, int day, int hour, int minute, int second) {
    return setDateTimeOriginal(new File(image), year, month, day, hour, minute, second);
  }

  public boolean setDateTimeOriginal(File image, int year, int month, int day, int hour, int minute, int second) {

    Calendar c = Calendar.getInstance();
    c.clear();
    c.set(year, month - 1, day, hour, minute, second);

    return setDateTimeOriginal(image, c.getTime());
  }

  public boolean setDateTimeOriginal(String image, Date date) {
    return setDateTimeOriginal(new File(image), date);
  }

  public abstract boolean setDateTimeOriginal(File image, Date date);

  public Map<File, Date> getDateTimeOriginalForFolder(String folder) {
    return getDateTimeOriginalForFolder(new File(folder));
  }

  public abstract Map<File, Date> getDateTimeOriginalForFolder(File folder);

  public boolean copyMetadata(String srcImage, String dstImage) {
    return copyMetadata(new File(srcImage), new File(dstImage));
  }

  public abstract boolean copyMetadata(File srcImage, File dstImage);

}
