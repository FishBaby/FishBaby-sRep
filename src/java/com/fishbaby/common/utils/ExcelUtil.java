package com.fishbaby.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.fishbaby.common.exception.BeanParseException;


/**
 * 描述：Excel写操作帮助类
 * 
 * @since 2010-11-24
 * @version 1.0v
 */
public class ExcelUtil {

	/**
	 * 从文件里面读取excel内容
	 * 
	 * @param wb
	 * @param LineNum
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws BeanParseException
	 */
	public static String[][] readFromFile(File wbFile, int lineNum, int maxRowNum) throws FileNotFoundException, IOException, BeanParseException {
		if (wbFile == null) {
			return null;
		}
		try {
			// 默认是用2003读取
			return readFrom2003File(new FileInputStream(wbFile), lineNum, maxRowNum);
		} catch (OfficeXmlFileException e) {
			// 默认是用2003读取
			return readFrom2007File(new FileInputStream(wbFile), lineNum, maxRowNum);
		}
	}

	/**
	 * 从文件里面读取excel内容
	 * 
	 * @param wb
	 * @param LineNum
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws BeanParseException
	 */
	public static String[][] readFromFile(MultipartFile wbFile, int lineNum, int maxRowNum) throws FileNotFoundException, IOException, BeanParseException {
		if (wbFile == null) {
			return null;
		}
		try {
			// 默认是用2003读取
			return readFrom2003File(wbFile.getInputStream(), lineNum, maxRowNum);
		} catch (OfficeXmlFileException e) {
			// 默认是用2003读取
			return readFrom2007File(wbFile.getInputStream(), lineNum, maxRowNum);
		}
	}

	private static String[][] readFrom2003File(InputStream fileInputStream, int lineNum, int maxRowNum) throws OfficeXmlFileException, IOException, BeanParseException {
		try {
			// 默认是用2003读取
			HSSFWorkbook wb = new HSSFWorkbook(fileInputStream);
			return readFromFile(wb.getSheetAt(0), lineNum, maxRowNum);
		} finally {
			fileInputStream.close();
		}
	}

	private static String[][] readFrom2007File(InputStream fileInputStream, int lineNum, int maxRowNum) throws OfficeXmlFileException, IOException, BeanParseException {
		// 兼容2007
		try {
			XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
			return readFromFile(wb.getSheetAt(0), lineNum, maxRowNum);
		} finally {
			fileInputStream.close();
		}
	}

	/**
	 * 从文件里面读取excel内容
	 * 
	 * @param wb
	 * @param LineNum
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws BeanParseException
	 */
	private static String[][] readFromFile(Sheet sheet, int lineNum, int maxRowNum) throws FileNotFoundException, IOException, BeanParseException {
		String[][] returns;
		int lastRowNum = sheet.getLastRowNum();
		if (maxRowNum < lastRowNum) {
			throw new BeanParseException("超过最大行数[" + maxRowNum + "]");
		}
		returns = new String[lastRowNum + 1][lineNum];
		int rowNum = 0;
		Object tmp = null;
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row next = rowIterator.next();
			for (int j = 0; j < lineNum; j++) {
				tmp = null;
				Cell cell = next.getCell(j);
				if (cell != null) {
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							// 如果是date类型则 ，获取该cell的date值
							tmp = JdDateUtil.formatDate(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()), JdDateUtil.timePattern);
						} else { // 纯数字
							tmp = AmountUtil.round(new BigDecimal(cell.getNumericCellValue()));
						}
						break;
					case HSSFCell.CELL_TYPE_STRING: // 字符串
						tmp = cell.getStringCellValue();
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
						tmp = cell.getBooleanCellValue();
						break;
					case HSSFCell.CELL_TYPE_FORMULA: // 公式
						tmp = cell.getCellFormula();
						break;
					case HSSFCell.CELL_TYPE_BLANK: // 空值
						tmp = " ";
						break;
					case HSSFCell.CELL_TYPE_ERROR: // 故障
						tmp = null;
						break;
					default:
						tmp = cell.getStringCellValue();
						break;
					}
				}
				if (tmp != null) {
					returns[rowNum][j] = tmp.toString();
				}
			}
			rowNum++;
		}
		return returns;
	}
}
