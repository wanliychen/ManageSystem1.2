package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerDatabase {

    private static final String CUSTOMER_FILE = "customers.xlsx";
    private static  List<Customer> customerList;

    public CustomerDatabase(List<Customer> customerList) {
        this.customerList = customerList;
    }

    //  从 Excel 文件中读取客户数据
    public static List<Customer> loadCustomersFromFile() {
        List<Customer> customers = new ArrayList<>();
        File file = new File(CUSTOMER_FILE);
    
        if (!file.exists()) {
            System.out.println("客户文件不存在，将返回空列表。");
            return customers;
        }
    
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(file))) {
            Sheet sheet = workbook.getSheet("Customers");
            if (sheet == null) return customers;
    
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; 
    
                Customer customer = new Customer();
                customer.setUsername(row.getCell(0).getStringCellValue());
                customer.setPassword(row.getCell(1).getStringCellValue());
                customer.setEmail(row.getCell(2).getStringCellValue());
                customer.setPhone(row.getCell(3).getStringCellValue());
                customer.setRegistrationDate(row.getCell(4).getStringCellValue());
                customer.setUserLevel(row.getCell(5).getStringCellValue());
                customers.add(customer);
            }
            System.out.println("成功加载客户数据！");
        } catch (IOException e) {
            System.out.println("加载客户数据时出错: " + e.getMessage());
        }
        return customers;
    }
    
   // 将客户数据保存到 Excel 文件
    public static void saveCustomersToFile(List<Customer> customers) {
        if (customers == null || customers.isEmpty()) {
            System.out.println("客户列表为空，无法保存！");
            return;
        }

        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(CUSTOMER_FILE)) {
            
            // 创建表单并写入表头
            Sheet sheet = workbook.createSheet("Customers");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("username");
            headerRow.createCell(1).setCellValue("password");
            headerRow.createCell(2).setCellValue("useremail");
            headerRow.createCell(3).setCellValue("phone");
            headerRow.createCell(4).setCellValue("registrationDate");
            headerRow.createCell(5).setCellValue("userLevel");

            // 写入客户数据
            int rowNum = 1;
            for (Customer customer : customers) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(customer.getUsername());
                row.createCell(1).setCellValue(customer.getPassword());
                row.createCell(2).setCellValue(customer.getEmail());
                row.createCell(3).setCellValue(customer.getPhone());
                row.createCell(4).setCellValue(customer.getRegistrationDate()); // 现在是 String 类型
                row.createCell(5).setCellValue(customer.getUserLevel());
                 // 将工作簿写入文件
                workbook.write(fileOut);

                System.out.println("客户数据已成功保存到文件: " + CUSTOMER_FILE);
            }     

        } catch (IOException e) {
            System.out.println("保存客户数据时出错: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void addCustomer(Customer customer) {
        customerList.add(customer);
        System.out.println("用户已成功添加: " + customer.getUsername());
    }

    public static void deleteCustomerByUsername(String username) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("您确定要删除用户 " + username + " 吗？该操作不可撤销。 (y/n)");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (!confirmation.equals("y")) {
            System.out.println("删除操作已取消。");
            return;
        }

        boolean removed = customerList.removeIf(p -> p.getUsername().equals(username));
        if (removed) {
            System.out.println("用户已成功删除，用户名: " + username);
        } else {
            System.out.println("未找到对应用户，用户名: " + username);
        }
    }

    public static Customer findCustomerByUsername(String username) {
        for (Customer customer : customerList) {
            if (customer.getUsername().equals(username)) {
                System.out.println("找到用户，用户名: " + username);
                return customer;
            }
        }
        System.out.println("未找到对应用户，用户名: " + username);
        return null;
    }

    public static void updateCustomer(String username, Customer updatedCustomer) {
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getUsername().equals(username)) {
                customerList.set(i, updatedCustomer);
                System.out.println("用户已成功更新，用户名: " + username);
                break;
            }
        }
    }

    public List<Customer> getAllCustomers() {
        return customerList;
    }
}


