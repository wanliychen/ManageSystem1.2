package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductDatabase {

    private static final String PRODUCT_FILE = "products.xlsx";
    private static List<Product> productList;

    public ProductDatabase(List<Product> productList){
        this.productList=productList;
    }

    // 从 Excel 文件中读取产品数据
    public static List<Product> loadProductsFromFile() {
        List<Product> products = new ArrayList<>();
        File file = new File(PRODUCT_FILE);

        if (!file.exists()) {
            System.out.println("产品文件不存在，将返回空列表。");
            return products;
        }

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(file))) {
            Sheet sheet = workbook.getSheet("Products");
            if (sheet == null) return products;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // 跳过表头

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

            System.out.println("成功加载产品数据！");
        } catch (IOException e) {
            System.out.println("加载产品数据时出错: " + e.getMessage());
        }
        return products;
    }

    // 将产品数据保存到 Excel 文件
    public static void saveProductsToFile(List<Product> products) {
        if (products == null || products.isEmpty()) {
            System.out.println("产品列表为空，无法保存！");
            return;
        }

        try (Workbook workbook = new XSSFWorkbook();
            FileOutputStream fos = new FileOutputStream(PRODUCT_FILE)) {

            // 创建表单并写入表头
            Sheet sheet = workbook.createSheet("Products");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ProductId");
            headerRow.createCell(1).setCellValue("ProductName");
            headerRow.createCell(2).setCellValue("Manufacturer");
            headerRow.createCell(3).setCellValue("Model");
            headerRow.createCell(4).setCellValue("PurchasePrice");
            headerRow.createCell(5).setCellValue("RetailPrice");
            headerRow.createCell(6).setCellValue("Nums");

            // 写入产品数据
            int rowNum = 1;
            for (Product product : products) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(product.getProductId());
                row.createCell(1).setCellValue(product.getProductName());
                row.createCell(2).setCellValue(product.getManufacturer());
                row.createCell(3).setCellValue(product.getModel());
                row.createCell(4).setCellValue(product.getPurchasePrice());
                row.createCell(5).setCellValue(product.getRetailPrice());
                row.createCell(6).setCellValue(product.getNums());
            }

            // 将工作簿写入文件
            workbook.write(fos);
            System.out.println("产品数据已成功保存到文件: " + PRODUCT_FILE);

        } catch (IOException e) {
            System.out.println("保存产品数据时出错: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // 添加商品
    public static void addProduct(Product product) {
        productList.add(product);
        System.out.println("商品已成功添加: " + product.getProductName());
    }

    // 删除商品
    public static void deleteProduct(int productId) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("您确定要删除商品 " + productId + " 吗？该操作不可撤销。 (y/n)");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        if (!confirmation.equals("y")) {
            System.out.println("删除操作已取消。");
            return; // 取消删除操作
        }
        productList.removeIf(product -> product.getProductId() == productId);
    }

    // 查找商品（通过ID）
    public static Product findProductById(int productId) {
        for (Product product : productList) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }

    // 更新商品
    public static void updateProduct(int productId, Product updatedProduct) {
        for (Product product : productList) {
            if (product.getProductId() == productId) {
                product.setProductName(updatedProduct.getProductName());
                product.setManufacturer(updatedProduct.getManufacturer());
                product.setModel(updatedProduct.getModel());
                product.setPurchasePrice(updatedProduct.getPurchasePrice());
                product.setRetailPrice(updatedProduct.getRetailPrice());
                product.setNums(updatedProduct.getNums());
                break;
            }
        }
    }

    // 获取所有商品
    public List<Product> getAllProducts() {
        return productList;
    }

    // 更新商品库存数量
    public static void updateProductQuantity(int productId, int quantity) {
        for (Product product : productList) {
            if (product.getProductId() == productId) {
                int newQuantity = product.getNums() - quantity;
                if (newQuantity <= 0) {
                    deleteProduct(productId);
                } else {
                    product.setNums(newQuantity);
                }
                break;
            }
        }
    }
}
