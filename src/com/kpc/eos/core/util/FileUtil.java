package com.kpc.eos.core.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import sun.misc.BASE64Encoder;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.exception.ServiceException;
import com.kpc.eos.core.web.context.ApplicationSetting;
import com.kpc.eos.model.common.UploadFile;

/**
 * 
 * FileUtil
 * =================
 * Description :  
 * Methods :
 */
public class FileUtil {

	private static Logger logger = Logger.getLogger(FileUtil.class);

	public static final String UPLOAD_PREFIX = "upload.";
	public static final String UPLOAD_TYPE = ".type";
	public static final String UPLOAD_PATH = ".path";
	public static final String UPLOAD_EXT_DENY = ".deny";
	public static final String UPLOAD_EXT_ALLOW = ".allow";
	public static final String UPLOAD_MAXSIZE = ".maxsize";
	public static final String UPLOAD_WIDTH = ".width";
	public static final String UPLOAD_HEIGHT = ".height";
	public static final String UPLOAD_MINWIDTH = ".minwidth";
	public static final String UPLOAD_MINHEIGHT = ".minheight";
	public static final String EXT_DOT = ".";

	public static final String UPLOAD_TYPE_IMAGES = "images";
	public static final String UPLOAD_TYPE_OTHER = "other";

	//public static final String[] noImageFileArr = Constants.noImageFiles.split("\\|");
	public static final int RATIO = 0;
	public static final int SAME = -1;

	@SuppressWarnings("unused")
	public static UploadFile uploadImageFile(HttpServletRequest request, String module, String inputFieldName, String customUploadPath, String seq) {

		if (!(request instanceof MultipartHttpServletRequest))
			return null;
		
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

		CommonsMultipartFile file = (CommonsMultipartFile) multiRequest.getFile(inputFieldName);
		if (file == null || StringUtils.isBlank(file.getOriginalFilename()))
			return null;

		String moduleConfig = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_PATH);
		if (StringUtils.isBlank(moduleConfig))
			module = "system";

		String orgFileName = file.getOriginalFilename();
		String ext = orgFileName.substring(orgFileName.lastIndexOf(".") + 1).toLowerCase();
		
		if (!checkFileExt(module, orgFileName)) {
			String deny 	= ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_EXT_DENY);
			throw new ServiceException("upload.fileExt.denied", new String[]{deny});
		}
		
		if (!checkSizeLimit(module, file.getSize())) {
			String maxsize 	= ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_MAXSIZE);
			long msize 		= Long.parseLong(maxsize);
			long msizeKbyte = msize / 1024L;
			throw new ServiceException("upload.fileSize.exceed", new String[]{Long.toString(msizeKbyte)});
		}
		
		try {
			if (!checkPixel(module, file.getInputStream())) {
				String width 	= ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_WIDTH);
				String height 	= ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_HEIGHT);
				throw new ServiceException("upload.filePixelSize.exceed", new String[]{width, height});
			}
		} catch(IOException e) {
			throw new ServiceException("inputStream error");
		}
		
		String type = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_TYPE);
		if (StringUtils.isBlank(type))
			type = UPLOAD_TYPE_OTHER;  

		String basePhysicalPath = null;
		String baseLogicalPath = null;
		String baseLocalPath = null;
		if (UPLOAD_TYPE_IMAGES.equals(type)) {
			basePhysicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.physical.images");
			baseLogicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.logical.images");
		} else {
			basePhysicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.physical.other");
			baseLogicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.logical.other");
		}
		baseLocalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.localPhysical");

		String modulePath = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_PATH);
		String physicalPath = basePhysicalPath + modulePath;
		String logicalPath = baseLogicalPath + modulePath;
		String localPath = baseLocalPath + modulePath;
		if (StringUtils.isNotBlank(customUploadPath)) {
			physicalPath += "/" + customUploadPath;
			logicalPath += "/" + customUploadPath;
		}

		String paddedId = StringUtils.leftPad(seq, 20, "0");
		String uploadedFileFullName = localPath + "/" + paddedId + EXT_DOT + ext;
		String remoteFileFullName = physicalPath + "/" + paddedId + EXT_DOT + ext;
		String uploadedFileFullName2 = uploadedFileFullName;
		String remoteFileFullName2 = physicalPath + "/" + paddedId + "_s" + EXT_DOT + ext;


		File dirPath = new File(localPath);
		if (!dirPath.exists() || !dirPath.isDirectory())
			dirPath.mkdirs();

		File uploadedFile = new File(uploadedFileFullName);
		File uploadedFile2 = null;
		
		try {
			file.transferTo(uploadedFile);
		} catch (Exception e) {
			throw new ServiceException("upload.fileSave.fail", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("FileUtil.uploadFile()");
			logger.debug("> module : " + module);
			logger.debug("> inputFieldName : " + inputFieldName);
			logger.debug("> customUploadPath : " + customUploadPath);
			logger.debug("> seq : " + seq);

			logger.debug("> upload.type : " + type);
			logger.debug("> modulePath : " + modulePath);
			logger.debug("> physicalPath : " + physicalPath);
			logger.debug("> logicalPath : " + logicalPath);
			logger.debug("> paddedId : " + paddedId);

			logger.debug("> originalFullName : " + orgFileName);
			logger.debug("> uploadedFileName : " + paddedId + EXT_DOT + ext);
			logger.debug("> uploadedFileFullName : " + uploadedFileFullName);
			logger.debug("> size : " + file.getSize());
		}

		UploadFile fileInfo = new UploadFile();
		fileInfo.setFileSize(file.getSize());  
		fileInfo.setUserFileName(orgFileName);  
		fileInfo.setSysFileName(paddedId + EXT_DOT + ext);  
		fileInfo.setSaveAbsPath(physicalPath);  
		fileInfo.setSaveRelPath(logicalPath);  

		if(uploadFileToImageServer(uploadedFileFullName, remoteFileFullName, module)) {
			if(uploadedFile.exists())
				uploadedFile.delete();
			
			if(uploadedFile2 != null && uploadedFile2.exists()) {
				uploadedFile2.delete();
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug("file upload success");
			}
		} else {
			throw new ServiceException(" File upload failed");
		}

		return fileInfo;
	}

	public static UploadFile uploadImageFileWithoutCheck(HttpServletRequest request, String module, String inputFieldName, String customUploadPath, String seq) {

		if (!(request instanceof MultipartHttpServletRequest))
			return null;
		
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

		CommonsMultipartFile file = (CommonsMultipartFile) multiRequest.getFile(inputFieldName);
		if (file == null || StringUtils.isBlank(file.getOriginalFilename()))
			return null;

		String moduleConfig = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_PATH);
		if (StringUtils.isBlank(moduleConfig))
			module = "system";

		String orgFileName = file.getOriginalFilename();
		String ext = orgFileName.substring(orgFileName.lastIndexOf(".") + 1).toLowerCase();
		
		String type = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_TYPE);
		if (StringUtils.isBlank(type))
			type = UPLOAD_TYPE_OTHER;  

		String basePhysicalPath = null;
		String baseLogicalPath = null;
		String baseLocalPath = null;
		if (UPLOAD_TYPE_IMAGES.equals(type)) {
			basePhysicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.physical.images");
			baseLogicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.logical.images");
		} else {
			basePhysicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.physical.other");
			baseLogicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.logical.other");
		}
		baseLocalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "localPhysical");

		String modulePath = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_PATH);
		String physicalPath = basePhysicalPath + modulePath;
		String logicalPath = baseLogicalPath + modulePath;
		String localPath = baseLocalPath + modulePath;
		if (StringUtils.isNotBlank(customUploadPath)) {
			physicalPath += "/" + customUploadPath;
			logicalPath += "/" + customUploadPath;
		}

		String paddedId = StringUtils.leftPad(seq, 20, "0");
		String uploadedFileFullName = localPath + "/" + paddedId + EXT_DOT + ext;
		String remoteFileFullName = physicalPath + "/" + paddedId + EXT_DOT + ext;
		
		File dirPath = new File(localPath);
		if (!dirPath.exists() || !dirPath.isDirectory())
			dirPath.mkdirs();

		File uploadedFile = new File(uploadedFileFullName);
		File uploadedFile2 = null;
		
		try {
			file.transferTo(uploadedFile);
		} catch (Exception e) {
			throw new ServiceException("upload.fileSave.fail", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("FileUtil.uploadFile()");
			logger.debug("> module : " + module);
			logger.debug("> inputFieldName : " + inputFieldName);
			logger.debug("> customUploadPath : " + customUploadPath);
			logger.debug("> seq : " + seq);

			logger.debug("> upload.type : " + type);
			logger.debug("> modulePath : " + modulePath);
			logger.debug("> physicalPath : " + physicalPath);
			logger.debug("> logicalPath : " + logicalPath);
			logger.debug("> paddedId : " + paddedId);

			logger.debug("> originalFullName : " + orgFileName);
			logger.debug("> uploadedFileName : " + paddedId + EXT_DOT + ext);
			logger.debug("> uploadedFileFullName : " + uploadedFileFullName);
			logger.debug("> size : " + file.getSize());
		}

		UploadFile fileInfo = new UploadFile();
		fileInfo.setFileSize(file.getSize());  
		fileInfo.setUserFileName(orgFileName);  
		fileInfo.setSysFileName(paddedId + EXT_DOT + ext);  
		fileInfo.setSaveAbsPath(physicalPath);  
		fileInfo.setSaveRelPath(logicalPath); 

		if(uploadFileToImageServer(uploadedFileFullName, remoteFileFullName, module)) {
			if(uploadedFile.exists())
				uploadedFile.delete();
			
			if(uploadedFile2 != null && uploadedFile2.exists()) {
				uploadedFile2.delete();
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug("file upload success");
			}
		} else {
			throw new ServiceException("File Upload Failed");
		}

		return fileInfo;
	}

	/**
	 * 
	 * uploadFile
	 * ===================
	 * @param request
	 * @param module
	 * @param inputFieldName
	 * @param customUploadPath
	 * @param fileName
	 * @return
	 */
	public static UploadFile uploadFile(HttpServletRequest request, String module, String inputFieldName, String customUploadPath, String fileName) {

		if (!(request instanceof MultipartHttpServletRequest))
			return null;
		
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

		CommonsMultipartFile file = (CommonsMultipartFile) multiRequest.getFile(inputFieldName);
		if (file == null || StringUtils.isBlank(file.getOriginalFilename()))
			return null;

		String moduleConfig = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_PATH);
		if (StringUtils.isBlank(moduleConfig))
			module = "system";

		String orgFileName = file.getOriginalFilename();
		String ext = orgFileName.substring(orgFileName.lastIndexOf(".") + 1).toLowerCase();
		
		String type = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_TYPE);
		if (StringUtils.isBlank(type))
			type = UPLOAD_TYPE_OTHER;  

		String basePhysicalPath = null;
		String baseLogicalPath = null;
		String baseLocalPath = null;
		if (UPLOAD_TYPE_IMAGES.equals(type)) {
			basePhysicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.physical.images");
			baseLogicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.logical.images");
		} else{
			basePhysicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.physical.other");
			baseLogicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.logical.other");
		}
		baseLocalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.local");

		String modulePath = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_PATH);
		String physicalPath = basePhysicalPath + modulePath;
		String logicalPath = baseLogicalPath + modulePath;
		String localPath = baseLocalPath + modulePath;
		if (StringUtils.isNotBlank(customUploadPath)) {
			physicalPath += "/" + customUploadPath;
			logicalPath += "/" + customUploadPath;
		}

		String paddedId = fileName;
		String uploadedFileFullName = localPath + "/" + paddedId;
		String remoteFileFullName = physicalPath + "/" + paddedId;


		File dirPath = new File(localPath);
		if (!dirPath.exists() || !dirPath.isDirectory())
			dirPath.mkdirs();

		File uploadedFile = new File(uploadedFileFullName);
		File uploadedFile2 = null;
		
		try {
			file.transferTo(uploadedFile);
		} catch (Exception e) {
			throw new ServiceException("upload.fileSave.fail", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("FileUtil.uploadFile()");
			logger.debug("> module : " + module);
			logger.debug("> inputFieldName : " + inputFieldName);
			logger.debug("> customUploadPath : " + customUploadPath);
			logger.debug("> fileName : " + fileName);

			logger.debug("> upload.type : " + type);
			logger.debug("> modulePath : " + modulePath);
			logger.debug("> physicalPath : " + physicalPath);
			logger.debug("> logicalPath : " + logicalPath);
			logger.debug("> paddedId : " + paddedId);

			logger.debug("> originalFullName : " + orgFileName);
			logger.debug("> uploadedFileName : " + paddedId);
			logger.debug("> uploadedFileFullName : " + uploadedFileFullName);
			logger.debug("> size : " + file.getSize());
		}

		UploadFile fileInfo = new UploadFile();
		fileInfo.setFileSize(file.getSize());  
		fileInfo.setUserFileName(orgFileName);  
		fileInfo.setSysFileName(paddedId);  
		fileInfo.setSaveAbsPath(physicalPath); 
		fileInfo.setSaveRelPath(logicalPath); 

		remoteFileFullName = HttpUtil.urlEncode(remoteFileFullName);
		//image서버로 upload
		if(uploadFileToImageServer(uploadedFileFullName, remoteFileFullName, module)) {
			if(uploadedFile.exists())
				uploadedFile.delete();
			
			if (logger.isDebugEnabled()) {
				logger.debug("file upload success");
			}
		} else {
			throw new ServiceException("upload failed.");
		}

		return fileInfo;
	}

    public static void resize(File src, File dest, int width, int height) throws IOException {
        Image srcImg = null;
        String suffix = src.getName().substring(src.getName().lastIndexOf('.')+1).toLowerCase();
        if (suffix.equals("bmp") || suffix.equals("png") || suffix.equals("gif")) {
            srcImg = ImageIO.read(src);
        } else {
            srcImg = new ImageIcon(src.toURL()).getImage();
        }
        
        int srcWidth = srcImg.getWidth(null);
        int srcHeight = srcImg.getHeight(null);
        
        int destWidth = -1, destHeight = -1;
        
        if (width == SAME) {
            destWidth = srcWidth;
        } else if (width > 0) {
            destWidth = width;
        }
        
        if (height == SAME) {
            destHeight = srcHeight;
        } else if (height > 0) {
            destHeight = height;
        }
        
        if (width == RATIO && height == RATIO) {
            destWidth = srcWidth;
            destHeight = srcHeight;
        } else if (width == RATIO) {
            double ratio = ((double)destHeight) / ((double)srcHeight);
            destWidth = (int)((double)srcWidth * ratio);
        } else if (height == RATIO) {
            double ratio = ((double)destWidth) / ((double)srcWidth);
            destHeight = (int)((double)srcHeight * ratio);
        }
        
        Image imgTarget = srcImg.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH); 
        int pixels[] = new int[destWidth * destHeight]; 
        PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, destWidth, destHeight, pixels, 0, destWidth); 
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            throw new IOException(e.getMessage());
        } 
        BufferedImage destImg = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB); 
        destImg.setRGB(0, 0, destWidth, destHeight, pixels, 0, destWidth); 
        
        ImageIO.write(destImg, "jpg", dest);
    }

    public static boolean checkFileExt(String module, String fileName) {

		if (StringUtils.isBlank(fileName) || !StringUtils.contains(fileName, '.'))
			return false;

		String allow = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_EXT_ALLOW);
		String deny = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_EXT_DENY);
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

		if (logger.isDebugEnabled()) {
			logger.debug(UPLOAD_PREFIX + module + UPLOAD_EXT_ALLOW + "=" + allow);
			logger.debug(UPLOAD_PREFIX + module + UPLOAD_EXT_DENY + "=" + deny);
			logger.debug("file ext=" + ext);
		}

		String[] allows = allow.split("\\|");
		String[] denies = deny.split("\\|");

		if (deny.equals("*")) {
			for (String s : allows) {
				if (s.equals(ext)) {
					return true;
				}
			}
			return false;
		} else {
			for (String s : denies) {
				if (s.equals(ext)) {
					return false;
				}
			}
		}

		if (allow.equals("*")) {
			for (String s : denies) {
				if (s.equals(ext)) {
					return false;
				}
			}
			return true;
		} else {
			for (String s : allows) {
				if (s.equals(ext)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean checkSizeLimit(String module, long size) {
		String temp = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_MAXSIZE);
		if (logger.isDebugEnabled()) {
			logger.debug(UPLOAD_PREFIX + module + UPLOAD_MAXSIZE + "=" + temp);
		}
		long maxsize = Long.parseLong(temp);
		return (maxsize < size) ? false : true;
	}

	public static boolean checkPixel(String module, InputStream fileInputStream) {

		String width = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_WIDTH);
		String height = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_HEIGHT);

		if (logger.isDebugEnabled()) {
			logger.debug(UPLOAD_PREFIX + module + UPLOAD_WIDTH + "=" + width);
			logger.debug(UPLOAD_PREFIX + module + UPLOAD_HEIGHT + "=" + height);
		}

		if (StringUtils.isBlank(width) || StringUtils.isBlank(height) || !NumberUtils.isNumber(width) || !NumberUtils.isNumber(height)) {
			return true;
		}

		int fileHeight = 0;
		int fileWidth = 0;
		try {
			BufferedImage image = ImageIO.read(fileInputStream);
			if (null != image) {
				fileHeight = image.getHeight();
				fileWidth = image.getWidth();
			}
		} catch (IOException e) {
			return false;
		}

		if (fileWidth != Integer.parseInt(width) || fileHeight != Integer.parseInt(height)) {
			return false;
		}

		return true;
	}
	
	public static boolean checkMinPixel(String module, InputStream fileInputStream) {

		String width = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_MINWIDTH);
		String height = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_MINHEIGHT);

		if (logger.isDebugEnabled()) {
			logger.debug(UPLOAD_PREFIX + module + UPLOAD_MINWIDTH + "=" + width);
			logger.debug(UPLOAD_PREFIX + module + UPLOAD_MINHEIGHT + "=" + height);
		}

		if (StringUtils.isBlank(width) || StringUtils.isBlank(height) || !NumberUtils.isNumber(width) || !NumberUtils.isNumber(height)) {
			return true;
		}

		int fileHeight = 0;
		int fileWidth = 0;
		try {
			BufferedImage image = ImageIO.read(fileInputStream);
			if (null != image) {
				fileHeight = image.getHeight();
				fileWidth = image.getWidth();
			}
		} catch (IOException e) {
			return false;
		}

		if (fileWidth < Integer.parseInt(width) || fileHeight < Integer.parseInt(height)) {
			return false;
		}

		return true;
	}

	@SuppressWarnings("rawtypes")
	public static List excelFileParsing(InputStream xslInputStream, List excelPropList, Class clazz, String ext) throws Exception {
		List dataList = new ArrayList();

		Sheet sheet = getExcelWorksheet(xslInputStream, ext);

		int lastNum = sheet.getPhysicalNumberOfRows();
		int lastCellNum = excelPropList.size();  

		for (int i = 0; i < lastNum; i++) {
			Object clazzObject = clazz.newInstance();
			for (int k = 0; lastCellNum > k; k++) {

				Cell cell = null;

				try {
					cell = sheet.getRow(i).getCell(k);
				} catch (NullPointerException npe) {
					continue;
				}
				if (cell == null)
					continue;

				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_NUMERIC:
					PropertyUtils.setNestedProperty(clazzObject, (String) excelPropList.get(k), String.valueOf(cell.getNumericCellValue()));
					break;
				case HSSFCell.CELL_TYPE_STRING:
					PropertyUtils.setNestedProperty(clazzObject, (String) excelPropList.get(k), cell.getRichStringCellValue().getString());
					break;
				default:
					PropertyUtils.setNestedProperty(clazzObject, (String) excelPropList.get(k),
							String.valueOf(cell.getRichStringCellValue()));
					break;
				}
			}
			dataList.add(clazzObject);
		}

		return dataList;
	}

	@SuppressWarnings("rawtypes")
	public static List<List> excelFileParsing(InputStream xslInputStream, Map headerMap, String ext) throws Exception {
		List dataList = new ArrayList();

		Sheet sheet = getExcelWorksheet(xslInputStream, ext);

		int lastNum = sheet.getPhysicalNumberOfRows();
		int lastCellNum = sheet.getRow(0).getPhysicalNumberOfCells();

		for (int i = 0; i < lastNum; i++) {
			Map data = new HashMap();
			String value = "";
			for (int k = 0; lastCellNum > k; k++) {
				Cell cell = null;
				try {
					cell = sheet.getRow(i).getCell(k);
				} catch (NullPointerException npe) {
					continue;
				}
				if (cell == null)
					continue;

				Object val = null;
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_NUMERIC:
					value += String.valueOf(cell.getNumericCellValue()).trim();
					val = String.valueOf((int) cell.getNumericCellValue());
					break;
				case HSSFCell.CELL_TYPE_STRING:
					value += cell.getRichStringCellValue().getString().trim();
					val = cell.getRichStringCellValue().getString();
					break;
				default:
					value += cell.getRichStringCellValue().getString().trim();
					val = cell.getRichStringCellValue().getString();
					break;
				}
				data.put(headerMap.get(k + ""), val);
			}
			if (value.length() <= 0) {
				continue;
			}

			dataList.add(data);
		}
		return dataList;
	}

	public static boolean deleteFile(UploadFile uploadedFile) {

		boolean result = false;

		File file = new File(uploadedFile.getSaveAbsPath() + "/" + uploadedFile.getSysFileName());
		if (logger.isDebugEnabled())
			logger.debug("file = " + uploadedFile);

		if (file.exists()) {
			result = file.delete();

			if (result && logger.isDebugEnabled())
				logger.debug("delete file success");
		}

		return result;

	}

	@SuppressWarnings("rawtypes")
	public static List excelUpload(HttpServletRequest request, String inputFieldName, List excelPropList, Class clazz) throws Exception {

		if (!(request instanceof MultipartHttpServletRequest))
			return null;
		
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

		CommonsMultipartFile file = (CommonsMultipartFile) multiRequest.getFile(inputFieldName);
		if (file == null || StringUtils.isBlank(file.getOriginalFilename()))
			return null;
		if (logger.isDebugEnabled()) {
			logger.debug("file original name = " + file.getOriginalFilename());
		}

		String fileName = file.getOriginalFilename();
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

		return excelFileParsing(file.getInputStream(), excelPropList, clazz, ext);
	}

	private static Sheet getExcelWorksheet(InputStream xslInputStream, String ext) throws IOException {
		Workbook wb = null;
		if ("xls".equals(ext)) {
			wb = new HSSFWorkbook(xslInputStream);
		} else if ("xlsx".equals(ext)) {
			wb = new XSSFWorkbook(xslInputStream);
		}
		Sheet sheet = wb.getSheetAt(0);
		return sheet;
	}

	public static UploadFile renameToFile(String module, File file) {
		return renameToFile(module, file, null);
	}

	public static UploadFile renameToFile(String module, File file, String customUploadPath) {

		UploadFile fileInfo = new UploadFile();

		if (StringUtils.isBlank(module) || !file.exists())
			return null;

		String orgFileName = file.getName();
		String ext = orgFileName.substring(orgFileName.lastIndexOf(".") + 1).toLowerCase();
		long fileSize = file.length();

		fileInfo.setFileSize(fileSize); 
		fileInfo.setUserFileName(orgFileName);  

		if (!checkFileExt(module, orgFileName)) {
			String deny = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_EXT_DENY);
			throw new ServiceException("upload.fileExt.denied", new String[] { deny });
		}

		if (!checkSizeLimit(module, fileSize)) {
			String maxsize = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_MAXSIZE);
			long msize = Long.parseLong(maxsize);
			long msizeKbyte = msize / 1024L;
			throw new ServiceException("upload.fileSize.exceed", new String[] { Long.toString(msizeKbyte) });
		}

		try {
			if (!checkPixel(module, new FileInputStream(file))) {
				String width = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_WIDTH);
				String height = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_HEIGHT);
				throw new ServiceException("upload.filePixelSize.exceed", new String[] { width, height });
			}
		} catch (FileNotFoundException e) {
			throw new ServiceException("No file name. " + orgFileName);
		}

		String type = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_TYPE);
		if (StringUtils.isBlank(type))
			type = UPLOAD_TYPE_OTHER;  

		String basePhysicalPath = null;
		String baseLogicalPath = null;

		if (UPLOAD_TYPE_IMAGES.equals(type)) {
			basePhysicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.physical.images");
			baseLogicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.logical.images");
		} else {
			basePhysicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.physical.other");
			baseLogicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.logical.other");
		}

		String modulePath = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_PATH);
		String physicalPath = basePhysicalPath + modulePath;
		String logicalPath = baseLogicalPath + modulePath;
		if (StringUtils.isNotBlank(customUploadPath)) {
			physicalPath += "/" + customUploadPath;
			logicalPath += "/" + customUploadPath;
		}

		String paddedId = StringUtils.leftPad(String.valueOf(new Date().getTime()), 20, "0");

		String separatedPath = PathUtil.subPath();

		physicalPath += separatedPath;
		logicalPath += separatedPath;

		String uploadedFileFullName = physicalPath + "/" + paddedId + EXT_DOT + ext;

		fileInfo.setSysFileName(paddedId + EXT_DOT + ext);  
		fileInfo.setSaveAbsPath(physicalPath);  
		fileInfo.setSaveRelPath(logicalPath);  

		File dirPath = new File(physicalPath);
		if (!dirPath.exists() || !dirPath.isDirectory())
			dirPath.mkdirs();

		File uploadedFile = new File(uploadedFileFullName);
		try {

			boolean rename = copy(file, uploadedFileFullName);

			if (!rename)
				throw new ServiceException("upload.renameTo.fail");

		} catch (Exception e) {
			throw new ServiceException("upload.renameTo.fail", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("file rename success");
		}
		return fileInfo;
	}

	public static boolean uploadFileToImageServer(String localFilePath, String remoteFilePath, String module) {
		URL urlInstance;
		String resultStr = "";
		HttpURLConnection conn;
		String url = Constants.IMAGE_SERVER_URL + "/Image.do?cmd=upload";
		try {
			urlInstance = new URL(url);
			conn = (HttpURLConnection)urlInstance.openConnection();
			conn.setDoInput(true);
			conn.setConnectTimeout(2000);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestMethod("POST");

			conn.setDoOutput(true);
			
		    File file = new File(localFilePath);
		    InputStream fis = new FileInputStream(file);
		    int filesize = 0;
		    if (file.exists()) {
		    	filesize = (int)file.length();
		        logger.debug(filesize + " bytes : " + file.getAbsoluteFile());
		    }

		    fis.close();
		    fis = new FileInputStream(localFilePath);
		    
		    OutputStream out_stream = conn.getOutputStream();
		    byte[] fbuf = new byte[(int)filesize];
		    int readCnt = 0;
		    int startPos = 0;
		    int readSize = 1024;
		    if(filesize < readSize)
		    	readSize = filesize;
		    
		    while((readCnt = fis.read(fbuf, startPos, readSize)) > 0){
			    startPos += readCnt;
			    if(filesize - startPos < readSize)
			    	readSize = filesize - startPos;
		    }
		    fis.close();
		    
		    String base64EncodeStr = new BASE64Encoder().encode(fbuf);
		    fbuf = null;
		    StringBuffer sb = new StringBuffer();
		    sb.append("filePath=").append(remoteFilePath);
		    sb.append("&resizeModule=").append(module);
		    sb.append("&file=").append(base64EncodeStr);
		    out_stream.write(sb.toString().getBytes());
		    out_stream.flush();
		    out_stream.close();
		    fis.close();

		    InputStream is      = null;
		    BufferedReader in   = null;

		    is  = conn.getInputStream();
		    in  = new BufferedReader(new InputStreamReader(is), 8 * 1024);

		    String line = null;
		    StringBuffer buff   = new StringBuffer();

		    while ( ( line = in.readLine() ) != null )
		    {
		        buff.append(line + "\n");
		    }
		    
		    resultStr    = buff.toString().trim();
		    if(resultStr.equals("SUCCESS"))
		    	return true;
		    else
		    	return false;
		} catch (MalformedURLException e) {
			logger.error("Exception occur", e);
		} catch (IOException e) {
			logger.error("Exception occur", e);
		} 
		
		return false;
	}
	
	public static boolean copy(File sourceFile, String dest) {

		FileInputStream is = null;
		FileOutputStream os = null;
		FileChannel fcin = null;
		FileChannel fcout = null;

		try {
			is = new FileInputStream(sourceFile);
			os = new FileOutputStream(dest);

			fcin = is.getChannel();
			fcout = os.getChannel();

			long size = fcin.size();
			long transferByte = fcin.transferTo(0, size, fcout);

			return size == transferByte;

		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (fcout != null)
					fcout.close();
				if (fcin != null)
					fcin.close();
				if (os != null)
					os.close();
				if (is != null)
					is.close();
			} catch (IOException ioe) {
			}
		}
	}

	static public boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}


	public static boolean checkPixelSameSize(String module, InputStream fileInputStream) {

		String width = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_WIDTH);
		String height = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_HEIGHT);

		if (logger.isDebugEnabled()) {
			logger.debug(UPLOAD_PREFIX + module + UPLOAD_WIDTH + "=" + width);
			logger.debug(UPLOAD_PREFIX + module + UPLOAD_HEIGHT + "=" + height);
		}

		if (StringUtils.isBlank(width) || StringUtils.isBlank(height) || !NumberUtils.isNumber(width) || !NumberUtils.isNumber(height)) {
			return true;
		}

		int fileHeight = 0;
		int fileWidth = 0;
		try {
			BufferedImage image = ImageIO.read(fileInputStream);
			if (null != image) {
				fileHeight = image.getHeight();
				fileWidth = image.getWidth();
			}
		} catch (IOException e) {
			return false;
		}

		if (fileWidth != Integer.parseInt(width) || fileHeight != Integer.parseInt(height)) {
			return false;
		}

		return true;
	}
	

	public static UploadFile uploadImageFileWithoutCheckForSpecial(HttpServletRequest request, String module, String inputFieldName, String customUploadPath, String fileName) {

		if (!(request instanceof MultipartHttpServletRequest))
			return null;
		
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

		CommonsMultipartFile file = (CommonsMultipartFile) multiRequest.getFile(inputFieldName);
		if (file == null || StringUtils.isBlank(file.getOriginalFilename()))
			return null;

		String moduleConfig = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_PATH);
		if (StringUtils.isBlank(moduleConfig))
			module = "system";

		String orgFileName = file.getOriginalFilename();
		
		String type = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_TYPE);
		if (StringUtils.isBlank(type))
			type = UPLOAD_TYPE_OTHER;  

		String basePhysicalPath = null;
		String baseLogicalPath = null;
		String baseLocalPath = null;
		String uploadModule = "";
		basePhysicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.physical." + type);
		baseLogicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.logical." + type);
		baseLocalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "localPhysical");
		uploadModule = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + ".uploadModule");

		String modulePath = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_PATH);
		String physicalPath = basePhysicalPath + modulePath;
		String logicalPath = baseLogicalPath + modulePath;
		String localPath = baseLocalPath + modulePath;
		if (StringUtils.isNotBlank(customUploadPath)) {
			physicalPath += "/" + customUploadPath;
			logicalPath += "/" + customUploadPath;
		}

		String uploadedFileFullName = localPath + "/" + fileName;
		String remoteFileFullName = physicalPath + "/" + fileName;

		File dirPath = new File(localPath);
		if (!dirPath.exists() || !dirPath.isDirectory())
			dirPath.mkdirs();

		File uploadedFile = new File(uploadedFileFullName);
		
		try {
			file.transferTo(uploadedFile);
		} catch (Exception e) {
			throw new ServiceException("upload.fileSave.fail", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("FileUtil.uploadFile()");
			logger.debug("> module : " + module);
			logger.debug("> inputFieldName : " + inputFieldName);
			logger.debug("> customUploadPath : " + customUploadPath);
			logger.debug("> seq : " + fileName);

			logger.debug("> upload.type : " + type);
			logger.debug("> modulePath : " + modulePath);
			logger.debug("> physicalPath : " + physicalPath);
			logger.debug("> logicalPath : " + logicalPath);

			logger.debug("> originalFullName : " + orgFileName);
			logger.debug("> uploadedFileFullName : " + uploadedFileFullName);
			logger.debug("> size : " + file.getSize());
		}

		UploadFile fileInfo = new UploadFile();
		fileInfo.setFileSize(file.getSize());  
		fileInfo.setUserFileName(orgFileName);  
		fileInfo.setSysFileName(fileName);  
		fileInfo.setSaveAbsPath(physicalPath);  
		fileInfo.setSaveRelPath(logicalPath);  

		if(uploadFileToImageServerWithModule(uploadedFileFullName, remoteFileFullName, uploadModule)) {
			if(uploadedFile.exists())
				uploadedFile.delete();
			
			if (logger.isDebugEnabled()) {
				logger.debug("file upload success");
			}
		} else {
			throw new ServiceException("Failed");
		}

		return fileInfo;
	}


	public static boolean uploadFileToImageServerWithModule(String localFilePath, String remoteFilePath, String module) {
		URL urlInstance;
		String resultStr = "";
		HttpURLConnection conn;
		String url = Constants.IMAGE_SERVER_URL + "/Image.do?cmd=upload";
		try {
			urlInstance = new URL(url);
			conn = (HttpURLConnection)urlInstance.openConnection();
			conn.setDoInput(true);
			conn.setConnectTimeout(2000);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestMethod("POST");

			conn.setDoOutput(true);
			
		    File file = new File(localFilePath);
		    InputStream fis = new FileInputStream(file);
		    int filesize = 0;
		    if (file.exists()) {
		    	filesize = (int)file.length();
		        logger.debug(filesize + " bytes : " + file.getAbsoluteFile());
		    }

		    fis.close();
		    fis = new FileInputStream(localFilePath);
		    
		    OutputStream out_stream = conn.getOutputStream();
		    byte[] fbuf = new byte[(int)filesize];
		    int readCnt = 0;
		    int startPos = 0;
		    int readSize = 1024;
		    if(filesize < readSize)
		    	readSize = filesize;
		    
		    while((readCnt = fis.read(fbuf, startPos, readSize)) > 0){
			    startPos += readCnt;
			    if(filesize - startPos < readSize)
			    	readSize = filesize - startPos;
		    }
		    fis.close();
		    
		    String base64EncodeStr = new BASE64Encoder().encode(fbuf);
		    fbuf = null;
		    StringBuffer sb = new StringBuffer();
		    sb.append("filePath=").append(remoteFilePath);
		    sb.append("&resizeModule=").append(module);
		    sb.append("&file=").append(base64EncodeStr);
		    out_stream.write(sb.toString().getBytes());
		    out_stream.flush();
		    out_stream.close();
		    fis.close();

		    InputStream is      = null;
		    BufferedReader in   = null;

		    is  = conn.getInputStream();
		    in  = new BufferedReader(new InputStreamReader(is), 8 * 1024);

		    String line = null;
		    StringBuffer buff   = new StringBuffer();

		    while ( ( line = in.readLine() ) != null )
		    {
		        buff.append(line + "\n");
		    }
		    
		    resultStr    = buff.toString().trim();
		    if(resultStr.equals("SUCCESS"))
		    	return true;
		    else
		    	return false;
		} catch (MalformedURLException e) {
			logger.error("Exception", e);
		} catch (IOException e) {
			logger.error("Exception", e);
		} 
		
		return false;
	}
	
	public static UploadFile uploadImageFileOnLocal(HttpServletRequest request
			, String module
			, String inputFieldName
			, String customUploadPath
			, String fileName) {

		if (!(request instanceof MultipartHttpServletRequest))
			return null;
		
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		
		UploadFile fileInfo = new UploadFile();

		CommonsMultipartFile file = (CommonsMultipartFile) multiRequest.getFile(inputFieldName);
		if (file == null || StringUtils.isBlank(file.getOriginalFilename()))
			return null;

		String moduleConfig = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_PATH);
		if (StringUtils.isBlank(moduleConfig))
			module = "system";

		String orgFileName = file.getOriginalFilename();
		String ext = orgFileName.substring(orgFileName.lastIndexOf(".") + 1).toLowerCase();
		
		if (!checkFileExt(module, orgFileName)) {
			String deny 	= ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_EXT_DENY);
			String allow 	= ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_EXT_ALLOW);
			fileInfo.setResultCode(Constants.RESULT_FAILURE);
			fileInfo.setResultMsg(MessageUtil.getMessage("upload.fileExt.denied", new String[]{allow}));
			return fileInfo;
			//throw new ServicException("upload.fileExt.denied", new String[]{allow});
		}
		
		if (!checkSizeLimit(module, file.getSize())) {
			String maxsize 	= ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_MAXSIZE);
			long msize 		= Long.parseLong(maxsize);
			long msizeKbyte = msize / 1024L;
			fileInfo.setResultCode(Constants.RESULT_FAILURE);
			fileInfo.setResultMsg(MessageUtil.getMessage("upload.fileSize.exceed", new String[]{Long.toString(msizeKbyte)}));
			return fileInfo;
			//throw new ServiceException("upload.fileSize.exceed", new String[]{Long.toString(msizeKbyte)});
		}
		
		try {
			if (!checkPixel(module, file.getInputStream())) {
				String width 	= ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_WIDTH);
				String height 	= ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_HEIGHT);
				//throw new ServiceException("upload.filePixelSize.exceed", new String[]{width, height});
				fileInfo.setResultCode(Constants.RESULT_FAILURE);
				fileInfo.setResultMsg(MessageUtil.getMessage("upload.filePixelSize.exceed", new String[]{width, height}));
				return fileInfo;
			}
			if (!checkMinPixel(module, file.getInputStream())){
				String width 	= ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_MINWIDTH);
				String height 	= ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_MINHEIGHT);
				//throw new ServiceException("upload.filePixelSize.small", new String[]{width, height});
				fileInfo.setResultCode(Constants.RESULT_FAILURE);
				fileInfo.setResultMsg(MessageUtil.getMessage("upload.filePixelSize.small", new String[]{width, height}));
				return fileInfo;
			}
		} catch(IOException e) {
			throw new ServiceException("inputStream error.");
		}
		
		String type = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_TYPE);
		if (StringUtils.isBlank(type))
			type = UPLOAD_TYPE_OTHER; 

		String basePhysicalPath = null;
		String baseLogicalPath = null;
		String baseLocalPath = null;
		if (UPLOAD_TYPE_IMAGES.equals(type)) {
			basePhysicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.physical.images");
			baseLogicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.logical.images");
		} else {
			basePhysicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.physical.other");
			baseLogicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.logical.other");
		}
		baseLocalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.localPhysical");

		String modulePath = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_PATH);
		String physicalPath = basePhysicalPath + modulePath;
		String logicalPath = baseLogicalPath + modulePath;
		String localPath = baseLocalPath + modulePath;
		if (StringUtils.isNotBlank(customUploadPath)) {
			physicalPath += "/" + customUploadPath;
			logicalPath += "/" + customUploadPath;
		}

		if (fileName == null)
		{
			fileName = orgFileName.substring(0, orgFileName.lastIndexOf("."));
		}

		String paddedId = fileName;
		String uploadedFileFullName = localPath + "/" + paddedId + EXT_DOT + ext;
		
		File tmp = new File(uploadedFileFullName);
		if (tmp.exists())
		{
			paddedId += "_" + Long.toString( new Date().getTime() );
			fileName = paddedId;
			uploadedFileFullName = localPath + "/" + paddedId + EXT_DOT + ext;
		}
		
		File dirPath = new File(localPath);
		if (!dirPath.exists() || !dirPath.isDirectory())
			dirPath.mkdirs();

		File uploadedFile = new File(uploadedFileFullName);
		try {
			file.transferTo(uploadedFile);
		} catch (Exception e) {
			throw new ServiceException("upload.fileSave.fail", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("FileUtil.uploadFile()");
			logger.debug("> module : " + module);
			logger.debug("> inputFieldName : " + inputFieldName);
			logger.debug("> customUploadPath : " + customUploadPath);
			logger.debug("> fileName : " + fileName);

			logger.debug("> upload.type : " + type);
			logger.debug("> modulePath : " + modulePath);
			logger.debug("> physicalPath : " + physicalPath);
			logger.debug("> logicalPath : " + logicalPath);
			logger.debug("> paddedId : " + paddedId);

			logger.debug("> originalFullName : " + orgFileName);
			logger.debug("> uploadedFileName : " + paddedId + EXT_DOT + ext);
			logger.debug("> uploadedFileFullName : " + uploadedFileFullName);
			logger.debug("> size : " + file.getSize());
		}

		fileInfo.setFileSize(file.getSize());  
		fileInfo.setUserFileName(orgFileName);  
		fileInfo.setSysFileName(paddedId + EXT_DOT + ext);  
		fileInfo.setSaveAbsPath(physicalPath); 
		fileInfo.setSaveRelPath(logicalPath);  
		
		fileInfo.setResultCode(Constants.RESULT_SUCCESS);

		return fileInfo;
	}
	
	public static String makeFileFullName(String module, String fileName)
	{
		String moduleConfig = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_PATH);
		if (StringUtils.isBlank(moduleConfig))
			module = "system";

		String baseLocalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.localPhysical");

		String modulePath = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_PATH);
		String localPath = baseLocalPath + modulePath;

		String fileFullName = localPath + "/" + fileName;
		
		return fileFullName;
	}
	
	public static void deleteFile(String module, String fileName) {

		String moduleConfig = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_PATH);
		if (StringUtils.isBlank(moduleConfig))
			module = "system";

		String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		
		String baseLocalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.localPhysical");

		String modulePath = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_PATH);
		String localPath = baseLocalPath + modulePath;

		String fileFullName = localPath + "/" + fileName;

		File deleteFile = new File(fileFullName);
		
		if(deleteFile.exists()){
			deleteFile.delete();
			System.out.println(fileFullName + " is Deleted");
		}
		
		if (module == "useritem"){
			fileFullName = localPath + "/medium/" + fileName;
			deleteFile = new File(fileFullName);
			if (deleteFile.exists()){
				deleteFile.delete();
				System.out.println("/medium/" + fileFullName + " is Deleted");
			}
			
			fileFullName = localPath + "/small/" + fileName;
			deleteFile = new File(fileFullName);
			if (deleteFile.exists()){
				deleteFile.delete();
				System.out.println("/small/" + fileFullName + " is Deleted");
			}
			
			String fileNameExceptExt = fileName.substring(0, fileName.lastIndexOf("."));
			
			fileFullName = localPath + "/small/" + fileNameExceptExt + "@2x" + EXT_DOT + ext;
			deleteFile = new File(fileFullName);
			if (deleteFile.exists()){
				deleteFile.delete();
				System.out.println("/small/" + fileFullName + " is Deleted");
			}
		}
	}
	
	/* Resize selected Area in Image with DestW and destH
	 * Params:
	 * module : module Name
	 * scrFileName 	: uploaded File Name
	 * destFileName : Resized File Name
	 * destW		: Width of Resized File 
	 * destH		: Height of Resized File
	 * offsetX		: Offset X from origin Image
	 * offsetY		: Offset Y from origin Image
	 * cropX		: Width of Selected Area
	 * cropY		: Height of Selected Area 	 
	 */
	
	public static void resizeWithCrop(String module, String srcFileName, String destFileName, String subFolderName, 
			int destW, int destH, int offsetX, int offsetY, int cropW, int cropH, int viewW, int viewH) throws IOException {
		
		String ext = srcFileName.substring(srcFileName.lastIndexOf(".") + 1).toLowerCase();
		
		String localPath = getModulePath(module);
		
		String uploadedFileFullName = localPath + "/" + srcFileName;
		String resizedFileFullName = null;
		if (StringUtils.isNotEmpty(subFolderName)){
			 resizedFileFullName = localPath + "/" + subFolderName + "/" + destFileName + EXT_DOT + ext;
			 File dirPath = new File(localPath + "/" + subFolderName);
			 if (!dirPath.exists() || !dirPath.isDirectory())
				 dirPath.mkdirs();
		}else{
			resizedFileFullName = localPath + "/" + destFileName + EXT_DOT + ext;
		}
		
		File src = new File(uploadedFileFullName);
		File dest = new File(resizedFileFullName);
		
        Image srcImg = null;
        String suffix = src.getName().substring(src.getName().lastIndexOf('.')+1).toLowerCase();
        if (suffix.equals("bmp") || suffix.equals("png") || suffix.equals("gif")) {
            srcImg = ImageIO.read(src);
        } else {
            srcImg = new ImageIcon(src.toURL()).getImage();
        }
        
        int srcWidth = srcImg.getWidth(null);
        int srcHeight = srcImg.getHeight(null);
        
        if (viewH == 0){
        	double ratio = ((double)viewW) / ((double)srcWidth);
        	viewH = (int)((double)srcHeight * ratio);
        }
        
        if (cropW == 0)
        {
        	cropW = viewW;
        }
        if (cropH == 0){
        	cropH = viewH;
        	if (destW == SAME){
        		String width 	= ApplicationSetting.getConfig(FileUtil.UPLOAD_PREFIX + "useritem" + FileUtil.UPLOAD_MINWIDTH);
        		String height 	= ApplicationSetting.getConfig(FileUtil.UPLOAD_PREFIX + "useritem" + FileUtil.UPLOAD_MINHEIGHT);
        		double ratio = (Double.parseDouble(height) / Double.parseDouble(width));
            	destW = srcWidth;
            	destH = (int)((double)srcWidth * ratio);
            }
        }else{
        
	        if (destW == SAME){
	        	destW = (int)(((double)cropW) * ((double)srcWidth) / ((double)viewW));
	        	destH = (int)(((double)cropH) * ((double)srcHeight) / ((double)viewH));
	        }
        }
        
        int destWidth = -1, destHeight = -1;
        
        double ratioX = ((double)destW) / ((double)cropW);
        destWidth = (int)((double)viewW * ratioX);
        
        double ratioY = ((double)destH) / ((double)cropH);
        destHeight = (int)((double)viewH * ratioY);
        
        int realOffsetX = (int)((double)offsetX * ratioX);
        int realOffsetY = (int)((double)offsetY * ratioY);
        
        Image imgTarget = srcImg.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH); 
        int pixels[] = new int[destW * destH]; 
        PixelGrabber pg = new PixelGrabber(imgTarget, realOffsetX, realOffsetY, destW + realOffsetX, destH + realOffsetY, pixels, 0, destW); 
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            throw new IOException(e.getMessage());
        } 
        BufferedImage destImg = new BufferedImage(destW, destH, BufferedImage.TYPE_INT_RGB); 
        destImg.setRGB(0, 0, destW, destH, pixels, 0, destW); 
        
        ImageIO.write(destImg, "jpg", dest);
    }
	
	public static boolean checkFileExist(String module, String fileName)
	{
		String localPath = getModulePath(module);
		String fileFullName = localPath + "/" + fileName;
		
		return checkFileExist(fileFullName);
	}
	
	/**
	 * Description	: check if the file exists.
	 * @author 		: RKRK
	 * @param fileFullName
	 * @return
	 * 2018
	 */
	public static boolean checkFileExist(String fileFullName)
	{
		File file = new File(fileFullName);
		
		return file.isFile() && file.exists();
	}
	
	
	
	public static String getModulePath(String module)
	{
		return getModulePath(module, false);
	}
	
	public static String getModulePath(String module, Boolean isLogical)
	{
		String moduleConfig = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_PATH);
		if (StringUtils.isBlank(moduleConfig))
			module = "system";

		String type = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_TYPE);
		if (StringUtils.isBlank(type))
			type = UPLOAD_TYPE_OTHER; 

		String basePhysicalPath = null;
		String baseLogicalPath = null;
		String baseLocalPath = null;
		if (UPLOAD_TYPE_IMAGES.equals(type)) 
		{
			basePhysicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.physical.images");
			baseLogicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.logical.images");
		} 
		else 
		{
			basePhysicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.physical.other");
			baseLogicalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.logical.other");
		}
		baseLocalPath = ApplicationSetting.getConfig(UPLOAD_PREFIX + "base.localPhysical");

		String modulePath = ApplicationSetting.getConfig(UPLOAD_PREFIX + module + UPLOAD_PATH);
		String physicalPath = basePhysicalPath + modulePath;
		String logicalPath = baseLogicalPath + modulePath;
		String localPath = baseLocalPath + modulePath;
		
		return (isLogical != null && isLogical == true)? logicalPath : localPath;
	}
	
	/**
	 * Description	: Get the base url of the uploaded fiels.
	 * @author 		: RKRK
	 * @param module
	 * @param fileName
	 * @return
	 * 2018
	 */
	public static String getBaseUrl(String module, String fileName)
	{
		String logicalPath = getModulePath(module, true) + "/";
		if ( StringUtils.isNotEmpty(fileName) )
			logicalPath += fileName;
		
		return logicalPath;
	}
	
	/**
	 * Description	: 
	 * @author 		: RKRK
	 * @param fileName
	 * @return
	 * 2018
	 */
	public static String getItemBaseUrl(String fileName)
	{
		return getBaseUrl("useritem", fileName);
	}
	
	public static String getItemImgUrl(String sizeType, String fileName)
	{
		String basePath = getModulePath("useritem", false);
		String partFilePath =  "/" + sizeType + "/";
		if ("big".equals(sizeType)) 
		{
			partFilePath = "/";
		}
		
		if (StringUtils.isNotEmpty(fileName) && checkFileExist( basePath +  partFilePath + fileName))
		{
			partFilePath += fileName; 
		} 
		else 
		{
			if ("medium".equals(sizeType)) 
				partFilePath += Constants.ITEM_NO_M_IMG_NAME;
			else if ("small".equals(sizeType)) 
				partFilePath += Constants.ITEM_NO_S_IMG_NAME;
			else
				partFilePath = "/" +Constants.ITEM_NO_IMG_NAME;
		}
		
		return getModulePath("useritem", true) + partFilePath;
	}
}