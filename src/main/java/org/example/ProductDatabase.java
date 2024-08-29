package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDatabase {

    private static final String PRODUCT_FILE = "products.xlsx";

    // 添加商品
    public void addProduct(Product product) throws IOException {
        File file = new File(PRODUCT_FILE);
        boolean fileExists = file.exists();
        
        try (Workbook workbook = fileExists ? new XSSFWorkbook(new FileInputStream(file)) : new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(file)) {

            Sheet sheet;
            if (workbook.getSheet("Products") != null) {
                // 如果工作表已存在，则使用它
                sheet = workbook.getSheet("Products");
            } else {
                // 否则，创建一个新的工作表
                sheet = workbook.createSheet("Products");
                // 创建表头
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("ProductId");
                headerRow.createCell(1).setCellValue("ProductName");
                headerRow.createCell(2).setCellValue("Manufacturer");
                headerRow.createCell(3).setCellValue("Model");
                headerRow.createCell(4).setCellValue("PurchasePrice");
                headerRow.createCell(5).setCellValue("RetailPrice");
                headerRow.createCell(6).setCellValue("Nums");
            }

            // 添加商品数据
            int lastRowNum = sheet.getLastRowNum();
            Row row = sheet.createRow(lastRowNum + 1);
            row.createCell(0).setCellValue(product.getProductId());
            row.createCell(1).setCellValue(product.getProductName());
            row.createCell(2).setCellValue(product.getManufacturer());
            row.createCell(3).setCellValue(product.getModel());
            row.createCell(4).setCellValue(product.getPurchasePrice());
            row.createCell(5).setCellValue(product.getRetailPrice());
            row.createCell(6).setCellValue(product.getNums());

            workbook.write(fos);
        }
    }

    // 删除商品
    public static void deleteProduct(int productId) throws IOException {
        File file = new File(PRODUCT_FILE);
        if (!file.exists()) return;

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(file));
             FileOutputStream fos = new FileOutputStream(file)) {

            Sheet sheet = workbook.getSheet("Products");
            if (sheet == null) return;

            int rowIndexToDelete = -1;
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; 
                if (row.getCell(0).getNumericCellValue() == productId) {
                    rowIndexToDelete = row.getRowNum();
                    break;
                }
            }

            if (rowIndexToDelete != -1) {
                sheet.removeRow(sheet.getRow(rowIndexToDelete));
                for (int i = rowIndexToDelete; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        row.setRowNum(i - 1);
                    }
                }
                workbook.write(fos);
            }
        }
    }

    // 查找商品（通过ID）
    public static Product findProductById(int productId) throws IOException {
        File file = new File(PRODUCT_FILE);
        if (!file.exists()) return null;

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(file))) {
            Sheet sheet = workbook.getSheet("Products");
            if (sheet == null) return null;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; 
                if (row.getCell(0).getNumericCellValue() == productId) {
                    Product product = new Product();
                    product.setProductId((int) row.getCell(0).getNumericCellValue());
                    product.setProductName(row.getCell(1).getStringCellValue());
                    product.setManufacturer(row.getCell(2).getStringCellValue());
                    product.setModel(row.getCell(3).getStringCellValue());
                    product.setPurchasePrice(row.getCell(4).getNumericCellValue());
                    product.setRetailPrice(row.getCell(5).getNumericCellValue());
                    product.setNums((int) row.getCell(6).getNumericCellValue());
                    return product;
                }
            }
        } catch (IOException e) {
            System.out.println("Error finding product: " + e.getMessage());
        }
        return null;
    }

    // 更新商品
    public static void updateProduct(int productId, Product updatedProduct) throws IOException {
        File file = new File(PRODUCT_FILE);
        if (!file.exists()) return;

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(file));
             FileOutputStream fos = new FileOutputStream(file)) {

            Sheet sheet = workbook.getSheet("Products");
            if (sheet == null) return;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; 
                if (row.getCell(0).getNumericCellValue() == productId) {
                    row.getCell(1).setCellValue(updatedProduct.getProductName());
                    row.getCell(2).setCellValue(updatedProduct.getManufacturer());
                    row.getCell(3).setCellValue(updatedProduct.getModel());
                    row.getCell(4).setCellValue(updatedProduct.getPurchasePrice());
                    row.getCell(5).setCellValue(updatedProduct.getRetailPrice());
                    row.getCell(6).setCellValue(updatedProduct.getNums());
                    workbook.write(fos);
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Error finding product: " + e.getMessage());
        }
    }

    // 获取所有商品
    public static List<Product> getAllProducts() throws IOException {
        List<Product> products = new ArrayList<>();
        File file = new File(PRODUCT_FILE);
        if (!file.exists()) return products;

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(file))) {
            Sheet sheet = workbook.getSheet("Products");
            if (sheet == null) return products;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; 

                Product product = new Product();
                product.setProductId((int) row.getCell(0).getNumericCellValue());
                product.setProductName(row.getCell(1).getStringCellValue());
                product.setManufacturer(row.getCell(2).getStringCellValue());
                product.setModel(row.getCell(3).getStringCellValue());
                product.setPurchasePrice(row.getCell(4).getNumericCellValue());
                product.setRetailPrice(row.getCell(5).getNumericCellValue());
                product.setNums((int) row.getCell(6).getNumericCellValue());
                products.add(product);
            }
        } catch (IOException e) {
            System.out.println("Error finding product: " + e.getMessage());
        }
        return products;
    }

    // 更新商品库存数量（减少）
    public static void updateProductQuantity(int productId, int quantity) throws IOException {
        File file = new File(PRODUCT_FILE);
        if (!file.exists()) return;

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(file));
             FileOutputStream fos = new FileOutputStream(file)) {

            Sheet sheet = workbook.getSheet("Products");
            if (sheet == null) return;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; 
                if (row.getCell(0).getNumericCellValue() == productId) {
                    int currentQuantity = (int) row.getCell(6).getNumericCellValue();
                    row.getCell(6).setCellValue(currentQuantity - quantity);
                    workbook.write(fos);
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Error finding product: " + e.getMessage());
        }
    }

    // 更新商品库存数量（增加）
    public static void increaseProductQuantity(int productId, int quantity) throws IOException {
        File file = new File(PRODUCT_FILE);
        if (!file.exists()) return;

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(file));
             FileOutputStream fos = new FileOutputStream(file)) {

            Sheet sheet = workbook.getSheet("Products");
            if (sheet == null) return;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; 
                if (row.getCell(0).getNumericCellValue() == productId) {
                    int currentQuantity = (int) row.getCell(6).getNumericCellValue();
                    row.getCell(6).setCellValue(currentQuantity + quantity);
                    workbook.write(fos);
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Error finding product: " + e.getMessage());
        }
    }
}
