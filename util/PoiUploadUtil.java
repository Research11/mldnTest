package org.ibas.credit.util;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiUploadUtil {
	
	/**
	 * 得到Excel工作簿对象
	 * @param fileStream 文件IO流
	 * @param fileName 文件名
	 * @return
	 * @throws Exception
	 */
	public static Workbook getWorkbook(InputStream fileStream,String fileName) throws Exception{
		Workbook wb =null;
		if(StringUtils.isBlank(fileName)){
			return wb;
		}
		String ext = fileName.substring(fileName.lastIndexOf("."));
		if(".xls".equals(ext.trim().toLowerCase())){
			wb = new HSSFWorkbook(fileStream);//创建 Excel 2003 工作簿对象
		}else if(".xlsx".equals(ext.trim().toLowerCase())){
			wb = new XSSFWorkbook(fileStream);//创建 Excel 2007 工作簿对象
		}
		return wb;
	}
	
}
