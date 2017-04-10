package com.liu.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataExportWebUtils {

	/**
	 * 指定哪些数据需要通过数字转字符的方式以避免科学记数法
	 */
	private String keys = "";
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static void exportExcel(List exportData, Map columnMapper,String fileName , HttpServletResponse res) throws Exception{
		
		OutputStream out = res.getOutputStream();
		
		LinkedHashMap rowMapper = new LinkedHashMap();
		for (Iterator propertyIterator = columnMapper.entrySet().iterator(); propertyIterator.hasNext();) {
			java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
			rowMapper.put(propertyEntry.getKey(), propertyEntry.getValue());
		}
		String[] headers = mapToArrayByValue(rowMapper);
		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet(fileName);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		// 生成并设置另一个样式
		XSSFCellStyle style2 = workbook.createCellStyle();

		// 产生表格标题行
		XSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			XSSFRichTextString text = new XSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// 遍历集合数据，产生数据行
		Iterator it = exportData.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			XSSFCell cell = null;
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
							//value = value + "\t"; // 避免数字变成科学计数法，前后加\t,或前面加=
							cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
							if(StringUtils.length(value) >= 12 ){
								cell.setCellValue(value);
							}else{
								cell.setCellValue(new Double(value));
							}
						} else {
							XSSFRichTextString richString = new XSSFRichTextString(value);
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
			out.flush();
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
	 * 导出csv文件
	 * 2016年3月11日 下午8:17:14
	 * @param exportData
	 * @param columnMapper
	 * @param res
	 *
	 */
	@SuppressWarnings("rawtypes")
	public  void exportCsv(List exportData, Map columnMapper,HttpServletResponse res) {
		BufferedWriter csvFileOutputStream = null;
		LinkedHashMap rowMapper = getExportPurchaseOrderClomn(columnMapper);
		try {
			// GB2312使正确读取分隔符","
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(res.getOutputStream(), "gb2312"), 1024);
			// 写入文件头部
			for (Iterator propertyIterator = rowMapper.entrySet().iterator(); propertyIterator.hasNext();) {
				java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
				csvFileOutputStream.write("\""+ propertyEntry.getValue().toString() + "\"");
				if (propertyIterator.hasNext()) {
					csvFileOutputStream.write(",");
				}
			}
			csvFileOutputStream.newLine();

			// 写入文件内容
			if (CollectionUtils.isNotEmpty(exportData)) {
				for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
					Object row = (Object) iterator.next();
					for (Iterator propertyIterator = rowMapper.entrySet().iterator(); propertyIterator.hasNext();) {
						java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
						String value = BeanUtils.getProperty(row, propertyEntry.getKey().toString());
						if(BeanUtils.getProperty(row, propertyEntry.getKey().toString()) != null){
							value = changeNum2Str(propertyEntry.getKey().toString(), value);
							csvFileOutputStream.write("\""+ value + "\"");
						}
						if (propertyIterator.hasNext()) {
							csvFileOutputStream.write(",");
						}
					}
					if (iterator.hasNext()) {
						csvFileOutputStream.newLine();
					}
				}
			}
			
			csvFileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(csvFileOutputStream != null){
					csvFileOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private  LinkedHashMap getExportPurchaseOrderClomn(Map exportColumn){
		LinkedHashMap rowMapper = new LinkedHashMap ();
		for (Iterator propertyIterator = exportColumn.entrySet().iterator(); propertyIterator.hasNext();) {
			java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
			rowMapper.put(propertyEntry.getKey(), propertyEntry.getValue());
		}
		return rowMapper;
	}
	
	/**
	 * 处理导出文件的数字
	 * 避免变成科学记数法
	 * 2016年3月11日 下午8:15:24
	 * @param key
	 * @param value
	 * @return
	 *
	 *
	 */
	public String changeNum2Str(String key, String value){
		if(keys.contains(key)){
			value = value + "\t";
		}
		return value;
	}
	public static void main(String[] args) {
		System.out.println("1234.5433".matches("^-?\\d+(\\.\\d{1,5})?$"));
	}
}
