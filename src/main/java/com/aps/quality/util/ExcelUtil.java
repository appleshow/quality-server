package com.aps.quality.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

@Slf4j
public class ExcelUtil {

    /**
     * @param multipartFile
     * @return
     */
    public static InputStream getInputStream(MultipartFile multipartFile) {
        try {
            return multipartFile.getInputStream();
        } catch (IOException e) {
            log.error("Can not get InputStream", e);
            return null;
        }
    }

    /**
     * @param inputStream
     * @param fileName
     * @return
     */
    public static Workbook createWorkbook(InputStream inputStream, String fileName) {
        final String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

        if (fileType.equals("xls")) {
            try {
                return new HSSFWorkbook(inputStream);
            } catch (IOException e) {
                log.error("Error reading xls file", e);
                return null;
            }
        } else if (fileType.equals("xlsx")) {
            try {
                return new XSSFWorkbook(inputStream);
            } catch (IOException e) {
                log.error("Error reading xlsx file", e);
                return null;
            }
        } else {
            log.error("Invalid file, please upload xls or xlsx format file");
            return null;
        }
    }

    /**
     * @param sheet
     * @param indexRow
     * @param indexCol
     * @return
     */
    public static String getCellFormatValue(Sheet sheet, int indexRow, int indexCol) {
        try {
            final Cell cell = sheet.getRow(indexRow).getCell(indexCol);

            if (cell != null) {
                cell.setCellType(CellType.STRING);
                return cell.toString();
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }

    }

    public static Object getCellValue(Cell cell) {
        Object res = null;
        if (cell == null) return null;
        switch (cell.getCellTypeEnum()) {
            case STRING:
                res = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    res = cell.getDateCellValue();
                } else {
                    res = cell.getNumericCellValue();
                }
                break;
            case BOOLEAN:
                res = cell.getBooleanCellValue();
                break;
            case FORMULA:
                if (cell.getCachedFormulaResultTypeEnum() == CellType.NUMERIC) {
                    res = cell.getNumericCellValue();
                } else if (cell.getCachedFormulaResultTypeEnum() == CellType.STRING) {
                    res = cell.getRichStringCellValue();
                }
                break;
        }
        return res;
    }

    public static String getCellValueAsString(Cell cell) {
        Object res = getCellValue(cell);
        if (res instanceof Double) {
            DecimalFormat df = new DecimalFormat("0");
            res = df.format(res);
        } else if (res instanceof Boolean) {
            res = Boolean.toString((boolean) res);
        } else if (res instanceof Date) {
            res = res.toString();
        }
        if (res == null) return null;
        res = ((String) res).trim();
        return (String) res;
    }

    public static Double getCellValueAsDouble(Cell cell) {
        Object res = getCellValue(cell);
        if (res instanceof String) {
            res = Double.parseDouble((String) res);
        }
        return (Double) res;
    }

    public static Integer getCellValueAsInteger(Cell cell) {
        Object res = getCellValue(cell);
        if (res == null) return null;
        return Integer.valueOf(new BigDecimal(res.toString()).intValue());
    }
}
