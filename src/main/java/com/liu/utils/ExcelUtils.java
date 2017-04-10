package com.liu.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.liu.code.FileUploadApiCode;
import com.liu.exceptions.BaseException;
import com.liu.exceptions.BusinessException;
import com.liu.exceptions.MideaException;

public class ExcelUtils {

	/**
	 * 导出
	 * 
	 * @param exportData
	 *            导出数据
	 * @param rowMapper
	 *            导出列
	 * @param outPutPath
	 *            文件存放路径
	 * @param filename
	 *            导出文件名
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public static File exportExcel(List exportData, Map columnMapper, String outPutPath, String filename) {
		OutputStream out = null;
		File xlsFile = new File(outPutPath +File.separator+filename + ".xls");
		File parent = xlsFile.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
		try {
			xlsFile.createNewFile();
			out = new FileOutputStream(xlsFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		LinkedHashMap rowMapper = new LinkedHashMap();
		for (Iterator propertyIterator = columnMapper.entrySet().iterator(); propertyIterator.hasNext();) {
			java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
			rowMapper.put(propertyEntry.getKey(), propertyEntry.getValue());
		}
		String[] headers = mapToArrayByValue(rowMapper);
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(filename);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// 遍历集合数据，产生数据行
		Iterator it = exportData.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			HSSFCell cell = null;
			Object rowObject = (Object) it.next();

			String[] fields = mapToArrayByKey(rowMapper);
			for (int i = 0; i < fields.length; i++) {
				Object val = null;
				try {
					val = PropertyUtils.getProperty(rowObject, fields[i]);
					cell = row.createCell((short) i);
					cell.setCellStyle(style2);
					String value = null;
					if (val instanceof Date) {
						value = (val == null ? null : DateUtils.getDateStr((Date) val));
					} else {
						value = (val == null ? null : val.toString());
					}
					if (BeanUtils.getProperty(rowObject, fields[i]) != null) {
						Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d{1,5})?$");
						Matcher matcher = pattern.matcher(value);
						if (matcher.find()) {
							value = value + "\t"; // 避免数字变成科学计数法，前后加\t,或前面加=
							cell.setCellValue(value);
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(value);
							cell.setCellValue(richString);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return xlsFile;
	}

	@SuppressWarnings("rawtypes")
	public static String[] mapToArrayByValue(Map columnMapper) {
		List<String> arrayList = new ArrayList<String>();
		for (Iterator propertyIterator = columnMapper.entrySet().iterator(); propertyIterator.hasNext();) {
			java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
			arrayList.add(propertyEntry.getValue().toString());
		}
		return arrayList.toArray(new String[arrayList.size()]);
	}

	/**
	 * 由map中的key构成数组
	 * 
	 * @param columnMapper
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String[] mapToArrayByKey(Map columnMapper) {
		List<String> arrayList = new ArrayList<String>();
		for (Iterator propertyIterator = columnMapper.entrySet().iterator(); propertyIterator.hasNext();) {
			java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
			arrayList.add(propertyEntry.getKey().toString());
		}
		return arrayList.toArray(new String[arrayList.size()]);
	}

	/**
	 * 导入
	 */
	public static List<List<String>> readFromExcel(InputStream inputStream) {
		DecimalFormat df = new DecimalFormat("#.00");
		try {
			Workbook wb = WorkbookFactory.create(inputStream);
			FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
			// int sheetNum = wb.getNumberOfSheets();
			Sheet sheet = wb.getSheetAt(0);
			int startRow = sheet.getFirstRowNum() + 1;
			int endRow = sheet.getLastRowNum();
			if (endRow > 1000) {
				throw BusinessException.IMPORT_DATA_EXCEPTION.newInstance("一次最多不能超过1000条数据，超出时请分批导入", "");
			}

			List<List<String>> excelData = new ArrayList<List<String>>();
			for (int i = startRow; i <= endRow; i++) {
				Row row = sheet.getRow(i);
				List<String> rowData = new ArrayList<String>();
				StringBuffer auditEmptyRow = new StringBuffer();
				if (row != null) {
					int cellNum = row.getLastCellNum();
					for (int j = 0; j < cellNum; j++) {
						StringBuffer value = new StringBuffer();
						Cell cell = row.getCell(j);
						CellValue cellValue = evaluator.evaluate(cell);
						if (cellValue != null) {
							switch (cellValue.getCellType()) {
							case Cell.CELL_TYPE_BOOLEAN:
								value.append(cellValue.getBooleanValue());
								break;
							case Cell.CELL_TYPE_NUMERIC:
								if (DateUtil.isCellDateFormatted(cell)) {
									value.append(cell.getDateCellValue());
								} else {
									//value.append(cellValue.getNumberValue()+"");
									value.append(df.format(cellValue.getNumberValue()));
								}
								break;
							case Cell.CELL_TYPE_STRING:
								value.append(cellValue.getStringValue().trim());
								break;
							default:
								value.append("");
								break;
							}
						}
						auditEmptyRow.append(value.toString().trim());
						if (StringUtils.isBlank(value.toString())) {
							rowData.add(null);
						} else {
							rowData.add(value.toString());
						}
					}
				}
				if (StringUtils.isNotBlank(auditEmptyRow.toString())) {
					excelData.add(rowData);
				}
			}
			return excelData;
		} catch (InvalidFormatException e) {
			throw new BaseException(FileUploadApiCode.INVALID_FORMAT);
		} catch (IOException e) {
			throw new BaseException(FileUploadApiCode.IO_EXCEPTION);
		} catch (MideaException e) {
			throw BusinessException.IMPORT_DATA_EXCEPTION.newInstance("数据大小超过限定大小:{0}行",1000);
		}
	}
	
	public static void main(String[] args) throws IOException {
		InputStream stream = new FileInputStream(new File("E:\\temp\\用户数据.xlsx"));
		List<List<String>> data = readFromExcel(stream);
		System.out.println(FastJSONHelper.serialize(data));
	}
}
