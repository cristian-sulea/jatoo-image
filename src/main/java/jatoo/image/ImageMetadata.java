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
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.0, May 22, 2017
 */
public class ImageMetadata {

//CHECKSTYLE:OFF

  //
  // File

/** Check http://www.exiv2.org/tags.html for tag description. */ private String sFileName; // IMG_1670.jpg
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sFileSize; // 1519 kB
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sFileModifyDate; // 2017:05:15 17:01:08+03:00
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sFileAccessDate; // 2017:05:15 16:55:40+03:00
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sFileCreateDate; // 2017:05:15 16:24:41+03:00
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sFilePermissions; // rw-rw-rw-
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sFileType; // JPEG
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sFileTypeExtension; // jpg
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sMIMEType; // image/jpeg
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sExifByteOrder; // Big-endian (Motorola, MM)
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sImageWidth; // 3264
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sImageHeight; // 2448
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sEncodingProcess; // Baseline DCT, Huffman coding
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sBitsPerSample; // 8
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sColorComponents; // 3
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sYCbCrSubSampling; // YCbCr4:2:0 (2 2)

  //
  // EXIF

/** Check http://www.exiv2.org/tags.html for tag description. */ private String sImageDescription; // test title
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sMake; // Apple
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sModel; // iPhone 5s
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sOrientation; // Horizontal (normal)
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sXResolution; // 72
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sYResolution; // 72
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sResolutionUnit; // inches
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sSoftware; // 10.0.2
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sModifyDate; // 2016:10:01 14:23:25
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sYCbCrPositioning; // Centered
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sExposureTime; // 1/33
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sFNumber; // 2.2
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sExposureProgram; // Program AE
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sISO; // 160
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sExifVersion; // 0221
/** Check http://www.exiv2.org/tags.html for tag description. */ private Date dateTimeOriginal; // 2016:10:01 14:23:25
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sCreateDate; // 2016:10:01 14:23:25
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sComponentsConfiguration; // Y, Cb, Cr, -
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sShutterSpeedValue; // 1/33
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sApertureValue; // 2.2
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sBrightnessValue; // 1.894974591
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sExposureCompensation; // 0
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sMeteringMode; // Spot
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sFlash; // Auto, Did not fire
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sFocalLength; // 4.2 mm
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sSubjectArea; // 1052 1314 610 612
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sSubSecTimeOriginal; // 597
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sSubSecTimeDigitized; // 597
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sFlashpixVersion; // 0100
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sColorSpace; // sRGB
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sExifImageWidth; // 3264
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sExifImageHeight; // 2448
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sSensingMethod; // One-chip color area
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sSceneType; // Directly photographed
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sExposureMode; // Auto
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sWhiteBalance; // Auto
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sFocalLengthIn35mmFormat; // 29 mm
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sSceneCaptureType; // Standard
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sLensInfo; // 4.15mm f/2.2
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sLensMake; // Apple
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sLensModel; // iPhone 5s back camera 4.15mm f/2.2
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sOffsetSchema; // 4192
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sXPTitle; // test title
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sXPComment; // za comments abc def
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sXPKeywords; // a;b
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sXPSubject; // subject
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sPadding; // (Binary data 1942 bytes, use -b option to extract)
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sCompression; // JPEG (old-style)
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sThumbnailOffset; // 5922
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sThumbnailLength; // 4229
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sThumbnailImage; // (Binary data 4229 bytes, use -b option to extract)

  //
  // MakerNotes

/** Check http://www.exiv2.org/tags.html for tag description. */ private String sRunTimeFlags; // Valid
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sRunTimeValue; // 135396107442500
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sRunTimeEpoch; // 0
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sRunTimeScale; // 1000000000
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sAccelerationVector; // -0.9131559497 -0.09396347414 -0.4030339084

  //
  // XMP

/** Check http://www.exiv2.org/tags.html for tag description. */ private String sAbout; // uuid:faf5bdd5-ba3d-11da-ad31-d33d75182f1b
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sSubject; // a, b
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sTitle; // test title
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sDescription; // test title
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sLastKeywordXMP; // a, b

  //
  // Composite

/** Check http://www.exiv2.org/tags.html for tag description. */ private String sAperture; // 2.2
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sImageSize; // 3264x2448
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sMegapixels; // 8.0
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sRunTimeSincePowerUp; // 1 days 13:36:36
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sScaleFactor35efl; // 7.0
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sShutterSpeed; // 1/33
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sSubSecCreateDate; // 2016:10:01 14:23:25.597
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sSubSecDateTimeOriginal; // 2016:10:01 14:23:25.597
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sCircleOfConfusion; // 0.004 mm
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sFOV; // 63.7 deg
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sFocalLength35efl; // 4.2 mm (35 mm equivalent; // 29.0 mm)
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sHyperfocalDistance; // 1.82 m
/** Check http://www.exiv2.org/tags.html for tag description. */ private String sLightValue; // 6.6

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

  public final String getImageHeight() {
    return sImageHeight;
  }

  public final void setImageHeight(final String imageHeight) {
    sImageHeight = imageHeight;
  }

  public final String getImageWidth() {
    return sImageWidth;
  }

  public final void setImageWidth(final String imageWidth) {
    sImageWidth = imageWidth;
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
    return sOrientation;
  }

  public final void setOrientation(final String orientation) {
    sOrientation = orientation;
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
