package storage;

import model.Book;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelFileStorage implements StorageStrategy {

    private final String FILE_PATH = "books.xlsx";

    @Override
    public void save(List<Book> books) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Books");

        int rowIndex = 0;

        // header
        Row header = sheet.createRow(rowIndex++);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Title");
        header.createCell(2).setCellValue("Author");

        // data
        for (Book b : books) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(b.getId());
            row.createCell(1).setCellValue(b.getTitle());
            row.createCell(2).setCellValue(b.getAuthor());
        }

        try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
            workbook.write(fos);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> load() {

        List<Book> books = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                int id = (int) row.getCell(0).getNumericCellValue();
                String title = row.getCell(1).getStringCellValue();
                String author = row.getCell(2).getStringCellValue();

                books.add(new Book(id, title, author));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }
}