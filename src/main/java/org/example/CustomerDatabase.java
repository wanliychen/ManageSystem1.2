package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CustomerDatabase {
    
    private static final String CUSTOMER_FILE = "customers.xlsx";  

    // 初始化
    public void initializeDatabase() {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(CUSTOMER_FILE)) {  // 使用 CUSTOMER_FILE
            Sheet sheet = workbook.createSheet("Customers");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("username");
            headerRow.createCell(1).setCellValue("password");
            headerRow.createCell(2).setCellValue("useremail");
            headerRow.createCell(3).setCellValue("phone");
            headerRow.createCell(4).setCellValue("registrationDate");
            headerRow.createCell(5).setCellValue("userLevel");
            workbook.write(fileOut);
            System.out.println("customers初始化成功！");
        } catch (IOException e) {
            System.out.println("customers初始化失败: " + e.getMessage());
        }
    }

    // 增加用户信息
    public static boolean addCustomer(Customer customer) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(CUSTOMER_FILE));  
             FileOutputStream fileOut = new FileOutputStream(CUSTOMER_FILE)) { 
            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();
            Row row = sheet.createRow(++rowCount);
            row.createCell(0).setCellValue(customer.getUsername());
            row.createCell(1).setCellValue(customer.getUserpassword());
            row.createCell(2).setCellValue(customer.getUseremail());
            row.createCell(3).setCellValue(customer.getPhone());
            row.createCell(4).setCellValue(customer.getRegistrationDate().toString());
            row.createCell(5).setCellValue(customer.getUserLevel());
            workbook.write(fileOut);
            System.out.println("增加用户成功！");
            return true;
        } catch (IOException e) {
            System.out.println("增加用户失败:" + e.getMessage());
            return false;
        }
    }

    // 删除用户信息
    public static void deleteCustomerByUsername(String username) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(CUSTOMER_FILE));  
             FileOutputStream fileOut = new FileOutputStream(CUSTOMER_FILE)) {  
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getCell(0).getStringCellValue().equals(username)) {
                    sheet.removeRow(row);
                    break;
                }
            }
            workbook.write(fileOut);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // 查找用户信息
    public static Customer findCustomerByUsername(String username) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(CUSTOMER_FILE))) {  
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getCell(0).getStringCellValue().equals(username)) {
                    return new Customer(
                            row.getCell(0).getStringCellValue(),
                            row.getCell(1).getStringCellValue(),
                            row.getCell(2).getStringCellValue(),
                            row.getCell(3).getStringCellValue(),
                            Date.valueOf(row.getCell(4).getStringCellValue()),
                            row.getCell(5).getStringCellValue()
                    );
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // 更新用户信息
    public static void updateCustomer(String username, Customer updatedCustomer) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(CUSTOMER_FILE));  
             FileOutputStream fileOut = new FileOutputStream(CUSTOMER_FILE)) {  
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getCell(0).getStringCellValue().equals(username)) {
                    row.getCell(1).setCellValue(updatedCustomer.getUserpassword());
                    row.getCell(2).setCellValue(updatedCustomer.getUseremail());
                    row.getCell(3).setCellValue(updatedCustomer.getPhone());
                    row.getCell(4).setCellValue(updatedCustomer.getRegistrationDate().toString());
                    row.getCell(5).setCellValue(updatedCustomer.getUserLevel());
                    break;
                }
            }
            workbook.write(fileOut);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // 获取所有用户信息
    public static List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(CUSTOMER_FILE))) {  
            Sheet sheet = workbook.getSheet("Customers");

            // 跳过表头，从第1行开始读取
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    String username = row.getCell(0).getStringCellValue();
                    String password = row.getCell(1).getStringCellValue();
                    String useremail = row.getCell(2).getStringCellValue();
                    String phone = row.getCell(3).getStringCellValue();
                    
                    // 读取日期，并进行格式化检查
                    String dateString = row.getCell(4).getStringCellValue();
                    java.util.Date utilDate;
                    try {
                        utilDate = sdf.parse(dateString);
                    } catch (ParseException e) {
                        System.out.println("日期格式错误: " + dateString);
                        utilDate = new java.util.Date(); // 赋予一个默认值
                    }
                    Date sqlDate = new Date(utilDate.getTime());

                    String userLevel = row.getCell(5).getStringCellValue();

                    customers.add(new Customer(username, password, useremail, phone, sqlDate, userLevel));

                } catch (Exception e) {
                    System.out.println("处理行时发生错误: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("文件读取错误: " + e.getMessage());
        }
        return customers;
    }

}
