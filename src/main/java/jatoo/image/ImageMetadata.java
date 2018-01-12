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

import java.util.Date;

/**
 * Image metadata allows information to be transported with an image file, in a way that can be understood by other
 * software, hardware, and end users, regardless of the format. Image metadata includes details relevant to the image
 * itself as well as information about its production.
 * 
 * <ul>
 * <li>http://www.exiv2.org/tags.html</li>
 * <li>https://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/EXIF.html</li>
 * </ul>
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.0.2, January 12, 2018
 */
public class ImageMetadata {

// CHECKSTYLE:OFF

  //
  // File

  private String sFileName; // IMG_1670.jpg
  private String sFileSize; // 1519 kB
  private String sFileModifyDate; // 2017:05:15 17:01:08+03:00
  private String sFileAccessDate; // 2017:05:15 16:55:40+03:00
  private String sFileCreateDate; // 2017:05:15 16:24:41+03:00
  private String sFilePermissions; // rw-rw-rw-
  private String sFileType; // JPEG
  private String sFileTypeExtension; // jpg
  private String sMIMEType; // image/jpeg
  private String sExifByteOrder; // Big-endian (Motorola, MM)
  private int imageWidth; // 3264
  private int imageHeight; // 2448
  private String sEncodingProcess; // Baseline DCT, Huffman coding
  private String sBitsPerSample; // 8
  private String sColorComponents; // 3
  private String sYCbCrSubSampling; // YCbCr4:2:0 (2 2)

  //
  // EXIF

  private String sImageDescription; // test title
  private String sMake; // Apple
  private String sModel; // iPhone 5s
  private String orientation; // Horizontal (normal)
  private String sXResolution; // 72
  private String sYResolution; // 72
  private String sResolutionUnit; // inches
  private String sSoftware; // 10.0.2
  private String sModifyDate; // 2016:10:01 14:23:25
  private String sYCbCrPositioning; // Centered
  private String sExposureTime; // 1/33
  private String sFNumber; // 2.2
  private String sExposureProgram; // Program AE
  private String sISO; // 160
  private String sExifVersion; // 0221
  private Date dateTimeOriginal; // 2016:10:01 14:23:25
  private String sCreateDate; // 2016:10:01 14:23:25
  private String sComponentsConfiguration; // Y, Cb, Cr, -
  private String sShutterSpeedValue; // 1/33
  private String sApertureValue; // 2.2
  private String sBrightnessValue; // 1.894974591
  private String sExposureCompensation; // 0
  private String sMeteringMode; // Spot
  private String sFlash; // Auto, Did not fire
  private String sFocalLength; // 4.2 mm
  private String sSubjectArea; // 1052 1314 610 612
  private String sSubSecTimeOriginal; // 597
  private String sSubSecTimeDigitized; // 597
  private String sFlashpixVersion; // 0100
  private String sColorSpace; // sRGB
  private String sExifImageWidth; // 3264
  private String sExifImageHeight; // 2448
  private String sSensingMethod; // One-chip color area
  private String sSceneType; // Directly photographed
  private String sExposureMode; // Auto
  private String sWhiteBalance; // Auto
  private String sFocalLengthIn35mmFormat; // 29 mm
  private String sSceneCaptureType; // Standard
  private String sLensInfo; // 4.15mm f/2.2
  private String sLensMake; // Apple
  private String sLensModel; // iPhone 5s back camera 4.15mm f/2.2
  private String sOffsetSchema; // 4192
  private String sXPTitle; // test title
  private String sXPComment; // za comments abc def
  private String sXPKeywords; // a;b
  private String sXPSubject; // subject
  private String sPadding; // (Binary data 1942 bytes, use -b option to extract)
  private String sCompression; // JPEG (old-style)
  private String sThumbnailOffset; // 5922
  private String sThumbnailLength; // 4229
  private String sThumbnailImage; // (Binary data 4229 bytes, use -b option to extract)

  //
  // MakerNotes

  private String sRunTimeFlags; // Valid
  private String sRunTimeValue; // 135396107442500
  private String sRunTimeEpoch; // 0
  private String sRunTimeScale; // 1000000000
  private String sAccelerationVector; // -0.9131559497 -0.09396347414 -0.4030339084

  //
  // XMP

  private String sAbout; // uuid:faf5bdd5-ba3d-11da-ad31-d33d75182f1b
  private String sSubject; // a, b
  private String sTitle; // test title
  private String sDescription; // test title
  private String sLastKeywordXMP; // a, b

  //
  // Composite

  private String sAperture; // 2.2
  private String sImageSize; // 3264x2448
  private String sMegapixels; // 8.0
  private String sRunTimeSincePowerUp; // 1 days 13:36:36
  private String sScaleFactor35efl; // 7.0
  private String sShutterSpeed; // 1/33
  private String sSubSecCreateDate; // 2016:10:01 14:23:25.597
  private String sSubSecDateTimeOriginal; // 2016:10:01 14:23:25.597
  private String sCircleOfConfusion; // 0.004 mm
  private String sFOV; // 63.7 deg
  private String sFocalLength35efl; // 4.2 mm (35 mm equivalent; // 29.0 mm)
  private String sHyperfocalDistance; // 1.82 m
  private String sLightValue; // 6.6

  //
  //

  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder();

    buffer.append("DateTimeOriginal:").append(getDateTimeOriginal()).append(System.lineSeparator());

    buffer.append("ImageWidth:").append(getImageWidth()).append(System.lineSeparator());
    buffer.append("ImageHeight:").append(getImageHeight()).append(System.lineSeparator());

    buffer.append("Orientation:").append(getOrientation()).append(System.lineSeparator());

    return buffer.toString();
  }

  //
  //

  public final String getBitsPerSample() {
    return sBitsPerSample;
  }

  public final void setBitsPerSample(final String bitsPerSample) {
    sBitsPerSample = bitsPerSample;
  }

  public final String getColorComponents() {
    return sColorComponents;
  }

  public final void setColorComponents(final String colorComponents) {
    sColorComponents = colorComponents;
  }

  public final String getEncodingProcess() {
    return sEncodingProcess;
  }

  public final void setEncodingProcess(final String encodingProcess) {
    sEncodingProcess = encodingProcess;
  }

  public final String getExifByteOrder() {
    return sExifByteOrder;
  }

  public final void setExifByteOrder(final String exifByteOrder) {
    sExifByteOrder = exifByteOrder;
  }

  public final String getFileAccessDate() {
    return sFileAccessDate;
  }

  public final void setFileAccessDate(final String fileAccessDate) {
    sFileAccessDate = fileAccessDate;
  }

  public final String getFileCreateDate() {
    return sFileCreateDate;
  }

  public final void setFileCreateDate(final String fileCreateDate) {
    sFileCreateDate = fileCreateDate;
  }

  public final String getFileModifyDate() {
    return sFileModifyDate;
  }

  public final void setFileModifyDate(final String fileModifyDate) {
    sFileModifyDate = fileModifyDate;
  }

  public final String getFileName() {
    return sFileName;
  }

  public final void setFileName(final String fileName) {
    sFileName = fileName;
  }

  public final String getFilePermissions() {
    return sFilePermissions;
  }

  public final void setFilePermissions(final String filePermissions) {
    sFilePermissions = filePermissions;
  }

  public final String getFileSize() {
    return sFileSize;
  }

  public final void setFileSize(final String fileSize) {
    sFileSize = fileSize;
  }

  public final String getFileType() {
    return sFileType;
  }

  public final void setFileType(final String fileType) {
    sFileType = fileType;
  }

  public final String getFileTypeExtension() {
    return sFileTypeExtension;
  }

  public final void setFileTypeExtension(final String fileTypeExtension) {
    sFileTypeExtension = fileTypeExtension;
  }

  public final int getImageHeight() {
    return imageHeight;
  }

  public final void setImageHeight(final int imageHeight) {
    this.imageHeight = imageHeight;
  }

  public final int getImageWidth() {
    return imageWidth;
  }

  public final void setImageWidth(final int imageWidth) {
    this.imageWidth = imageWidth;
  }

  public final String getMIMEType() {
    return sMIMEType;
  }

  public final void setMIMEType(final String mIMEType) {
    sMIMEType = mIMEType;
  }

  public final String getYCbCrSubSampling() {
    return sYCbCrSubSampling;
  }

  public final void setYCbCrSubSampling(final String yCbCrSubSampling) {
    sYCbCrSubSampling = yCbCrSubSampling;
  }

  public final String getApertureValue() {
    return sApertureValue;
  }

  public final void setApertureValue(final String apertureValue) {
    sApertureValue = apertureValue;
  }

  public final String getBrightnessValue() {
    return sBrightnessValue;
  }

  public final void setBrightnessValue(final String brightnessValue) {
    sBrightnessValue = brightnessValue;
  }

  public final String getColorSpace() {
    return sColorSpace;
  }

  public final void setColorSpace(final String colorSpace) {
    sColorSpace = colorSpace;
  }

  public final String getComponentsConfiguration() {
    return sComponentsConfiguration;
  }

  public final void setComponentsConfiguration(final String componentsConfiguration) {
    sComponentsConfiguration = componentsConfiguration;
  }

  public final String getCompression() {
    return sCompression;
  }

  public final void setCompression(final String compression) {
    sCompression = compression;
  }

  public final String getCreateDate() {
    return sCreateDate;
  }

  public final void setCreateDate(final String createDate) {
    sCreateDate = createDate;
  }

  public final Date getDateTimeOriginal() {
    return dateTimeOriginal;
  }

  public final void setDateTimeOriginal(final Date dateTimeOriginal) {
    this.dateTimeOriginal = dateTimeOriginal;
  }

  public final String getExifImageHeight() {
    return sExifImageHeight;
  }

  public final void setExifImageHeight(final String exifImageHeight) {
    sExifImageHeight = exifImageHeight;
  }

  public final String getExifImageWidth() {
    return sExifImageWidth;
  }

  public final void setExifImageWidth(final String exifImageWidth) {
    sExifImageWidth = exifImageWidth;
  }

  public final String getExifVersion() {
    return sExifVersion;
  }

  public final void setExifVersion(final String exifVersion) {
    sExifVersion = exifVersion;
  }

  public final String getExposureCompensation() {
    return sExposureCompensation;
  }

  public final void setExposureCompensation(final String exposureCompensation) {
    sExposureCompensation = exposureCompensation;
  }

  public final String getExposureMode() {
    return sExposureMode;
  }

  public final void setExposureMode(final String exposureMode) {
    sExposureMode = exposureMode;
  }

  public final String getExposureProgram() {
    return sExposureProgram;
  }

  public final void setExposureProgram(final String exposureProgram) {
    sExposureProgram = exposureProgram;
  }

  public final String getExposureTime() {
    return sExposureTime;
  }

  public final void setExposureTime(final String exposureTime) {
    sExposureTime = exposureTime;
  }

  public final String getFNumber() {
    return sFNumber;
  }

  public final void setFNumber(final String fNumber) {
    sFNumber = fNumber;
  }

  public final String getFlash() {
    return sFlash;
  }

  public final void setFlash(final String flash) {
    sFlash = flash;
  }

  public final String getFlashpixVersion() {
    return sFlashpixVersion;
  }

  public final void setFlashpixVersion(final String flashpixVersion) {
    sFlashpixVersion = flashpixVersion;
  }

  public final String getFocalLength() {
    return sFocalLength;
  }

  public final void setFocalLength(final String focalLength) {
    sFocalLength = focalLength;
  }

  public final String getFocalLengthIn35mmFormat() {
    return sFocalLengthIn35mmFormat;
  }

  public final void setFocalLengthIn35mmFormat(final String focalLengthIn35mmFormat) {
    sFocalLengthIn35mmFormat = focalLengthIn35mmFormat;
  }

  public final String getISO() {
    return sISO;
  }

  public final void setISO(final String iSO) {
    sISO = iSO;
  }

  public final String getImageDescription() {
    return sImageDescription;
  }

  public final void setImageDescription(final String imageDescription) {
    sImageDescription = imageDescription;
  }

  public final String getLensInfo() {
    return sLensInfo;
  }

  public final void setLensInfo(final String lensInfo) {
    sLensInfo = lensInfo;
  }

  public final String getLensMake() {
    return sLensMake;
  }

  public final void setLensMake(final String lensMake) {
    sLensMake = lensMake;
  }

  public final String getLensModel() {
    return sLensModel;
  }

  public final void setLensModel(final String lensModel) {
    sLensModel = lensModel;
  }

  public final String getMake() {
    return sMake;
  }

  public final void setMake(final String make) {
    sMake = make;
  }

  public final String getMeteringMode() {
    return sMeteringMode;
  }

  public final void setMeteringMode(final String meteringMode) {
    sMeteringMode = meteringMode;
  }

  public final String getModel() {
    return sModel;
  }

  public final void setModel(final String model) {
    sModel = model;
  }

  public final String getModifyDate() {
    return sModifyDate;
  }

  public final void setModifyDate(final String modifyDate) {
    sModifyDate = modifyDate;
  }

  public final String getOffsetSchema() {
    return sOffsetSchema;
  }

  public final void setOffsetSchema(final String offsetSchema) {
    sOffsetSchema = offsetSchema;
  }

  public final String getOrientation() {
    return orientation;
  }

  public final void setOrientation(final String orientation) {
    this.orientation = orientation;
  }

  public final String getPadding() {
    return sPadding;
  }

  public final void setPadding(final String padding) {
    sPadding = padding;
  }

  public final String getResolutionUnit() {
    return sResolutionUnit;
  }

  public final void setResolutionUnit(final String resolutionUnit) {
    sResolutionUnit = resolutionUnit;
  }

  public final String getSceneCaptureType() {
    return sSceneCaptureType;
  }

  public final void setSceneCaptureType(final String sceneCaptureType) {
    sSceneCaptureType = sceneCaptureType;
  }

  public final String getSceneType() {
    return sSceneType;
  }

  public final void setSceneType(final String sceneType) {
    sSceneType = sceneType;
  }

  public final String getSensingMethod() {
    return sSensingMethod;
  }

  public final void setSensingMethod(final String sensingMethod) {
    sSensingMethod = sensingMethod;
  }

  public final String getShutterSpeedValue() {
    return sShutterSpeedValue;
  }

  public final void setShutterSpeedValue(final String shutterSpeedValue) {
    sShutterSpeedValue = shutterSpeedValue;
  }

  public final String getSoftware() {
    return sSoftware;
  }

  public final void setSoftware(final String software) {
    sSoftware = software;
  }

  public final String getSubSecTimeDigitized() {
    return sSubSecTimeDigitized;
  }

  public final void setSubSecTimeDigitized(final String subSecTimeDigitized) {
    sSubSecTimeDigitized = subSecTimeDigitized;
  }

  public final String getSubSecTimeOriginal() {
    return sSubSecTimeOriginal;
  }

  public final void setSubSecTimeOriginal(final String subSecTimeOriginal) {
    sSubSecTimeOriginal = subSecTimeOriginal;
  }

  public final String getSubjectArea() {
    return sSubjectArea;
  }

  public final void setSubjectArea(final String subjectArea) {
    sSubjectArea = subjectArea;
  }

  public final String getThumbnailImage() {
    return sThumbnailImage;
  }

  public final void setThumbnailImage(final String thumbnailImage) {
    sThumbnailImage = thumbnailImage;
  }

  public final String getThumbnailLength() {
    return sThumbnailLength;
  }

  public final void setThumbnailLength(final String thumbnailLength) {
    sThumbnailLength = thumbnailLength;
  }

  public final String getThumbnailOffset() {
    return sThumbnailOffset;
  }

  public final void setThumbnailOffset(final String thumbnailOffset) {
    sThumbnailOffset = thumbnailOffset;
  }

  public final String getWhiteBalance() {
    return sWhiteBalance;
  }

  public final void setWhiteBalance(final String whiteBalance) {
    sWhiteBalance = whiteBalance;
  }

  public final String getXPComment() {
    return sXPComment;
  }

  public final void setXPComment(final String xPComment) {
    sXPComment = xPComment;
  }

  public final String getXPKeywords() {
    return sXPKeywords;
  }

  public final void setXPKeywords(final String xPKeywords) {
    sXPKeywords = xPKeywords;
  }

  public final String getXPSubject() {
    return sXPSubject;
  }

  public final void setXPSubject(final String xPSubject) {
    sXPSubject = xPSubject;
  }

  public final String getXPTitle() {
    return sXPTitle;
  }

  public final void setXPTitle(final String xPTitle) {
    sXPTitle = xPTitle;
  }

  public final String getXResolution() {
    return sXResolution;
  }

  public final void setXResolution(final String xResolution) {
    sXResolution = xResolution;
  }

  public final String getYCbCrPositioning() {
    return sYCbCrPositioning;
  }

  public final void setYCbCrPositioning(final String yCbCrPositioning) {
    sYCbCrPositioning = yCbCrPositioning;
  }

  public final String getYResolution() {
    return sYResolution;
  }

  public final void setYResolution(final String yResolution) {
    sYResolution = yResolution;
  }

  public final String getAccelerationVector() {
    return sAccelerationVector;
  }

  public final void setAccelerationVector(final String accelerationVector) {
    sAccelerationVector = accelerationVector;
  }

  public final String getRunTimeEpoch() {
    return sRunTimeEpoch;
  }

  public final void setRunTimeEpoch(final String runTimeEpoch) {
    sRunTimeEpoch = runTimeEpoch;
  }

  public final String getRunTimeFlags() {
    return sRunTimeFlags;
  }

  public final void setRunTimeFlags(final String runTimeFlags) {
    sRunTimeFlags = runTimeFlags;
  }

  public final String getRunTimeScale() {
    return sRunTimeScale;
  }

  public final void setRunTimeScale(final String runTimeScale) {
    sRunTimeScale = runTimeScale;
  }

  public final String getRunTimeValue() {
    return sRunTimeValue;
  }

  public final void setRunTimeValue(final String runTimeValue) {
    sRunTimeValue = runTimeValue;
  }

  public final String getAbout() {
    return sAbout;
  }

  public final void setAbout(final String about) {
    sAbout = about;
  }

  public final String getDescription() {
    return sDescription;
  }

  public final void setDescription(final String description) {
    sDescription = description;
  }

  public final String getLastKeywordXMP() {
    return sLastKeywordXMP;
  }

  public final void setLastKeywordXMP(final String lastKeywordXMP) {
    sLastKeywordXMP = lastKeywordXMP;
  }

  public final String getSubject() {
    return sSubject;
  }

  public final void setSubject(final String subject) {
    sSubject = subject;
  }

  public final String getTitle() {
    return sTitle;
  }

  public final void setTitle(final String title) {
    sTitle = title;
  }

  public final String getAperture() {
    return sAperture;
  }

  public final void setAperture(final String aperture) {
    sAperture = aperture;
  }

  public final String getCircleOfConfusion() {
    return sCircleOfConfusion;
  }

  public final void setCircleOfConfusion(final String circleOfConfusion) {
    sCircleOfConfusion = circleOfConfusion;
  }

  public final String getFOV() {
    return sFOV;
  }

  public final void setFOV(final String fOV) {
    sFOV = fOV;
  }

  public final String getFocalLength35efl() {
    return sFocalLength35efl;
  }

  public final void setFocalLength35efl(final String focalLength35efl) {
    sFocalLength35efl = focalLength35efl;
  }

  public final String getHyperfocalDistance() {
    return sHyperfocalDistance;
  }

  public final void setHyperfocalDistance(final String hyperfocalDistance) {
    sHyperfocalDistance = hyperfocalDistance;
  }

  public final String getImageSize() {
    return sImageSize;
  }

  public final void setImageSize(final String imageSize) {
    sImageSize = imageSize;
  }

  public final String getLightValue() {
    return sLightValue;
  }

  public final void setLightValue(final String lightValue) {
    sLightValue = lightValue;
  }

  public final String getMegapixels() {
    return sMegapixels;
  }

  public final void setMegapixels(final String megapixels) {
    sMegapixels = megapixels;
  }

  public final String getRunTimeSincePowerUp() {
    return sRunTimeSincePowerUp;
  }

  public final void setRunTimeSincePowerUp(final String runTimeSincePowerUp) {
    sRunTimeSincePowerUp = runTimeSincePowerUp;
  }

  public final String getScaleFactor35efl() {
    return sScaleFactor35efl;
  }

  public final void setScaleFactor35efl(final String scaleFactor35efl) {
    sScaleFactor35efl = scaleFactor35efl;
  }

  public final String getShutterSpeed() {
    return sShutterSpeed;
  }

  public final void setShutterSpeed(final String shutterSpeed) {
    sShutterSpeed = shutterSpeed;
  }

  public final String getSubSecCreateDate() {
    return sSubSecCreateDate;
  }

  public final void setSubSecCreateDate(final String subSecCreateDate) {
    sSubSecCreateDate = subSecCreateDate;
  }

  public final String getSubSecDateTimeOriginal() {
    return sSubSecDateTimeOriginal;
  }

  public final void setSubSecDateTimeOriginal(final String subSecDateTimeOriginal) {
    sSubSecDateTimeOriginal = subSecDateTimeOriginal;
  }

}
