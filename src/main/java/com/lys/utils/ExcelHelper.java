package com.lys.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExcelHelper
{
	private static final String EXCEL_XLS = "xls";
	private static final String EXCEL_XLSX = "xlsx";

	public static Workbook createExcel()
	{
		return new HSSFWorkbook();
	}

	public static Workbook readExcel(File file)
	{
		Workbook wb = null;
		try
		{
			FileInputStream in = new FileInputStream(file);
			if (file.getName().endsWith(EXCEL_XLS))
			{
				wb = new HSSFWorkbook(in);
			}
			else if (file.getName().endsWith(EXCEL_XLSX))
			{
				wb = new XSSFWorkbook(in);
			}
			in.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return wb;
	}

	public static void writeExcel(Workbook wb, File file)
	{
		try
		{
			FileOutputStream out = new FileOutputStream(file);
			wb.write(out);
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
