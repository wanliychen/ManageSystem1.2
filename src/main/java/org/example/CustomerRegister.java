package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.Date;
import java.util.*;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CustomerRegister {
    private static final String CUSTOMER_FILE = "customers.xlsx";

    private Scanner scanner = new Scanner(System.in);

     // 用户注册
     public void run() {
        
        System.out.println("请输入用户名：");
        String username = scanner.nextLine();
        System.out.println("请输入密码：");
        String password = scanner.nextLine();
        System.out.println("请输入邮箱：");
        String email = scanner.nextLine();
        System.out.print("请输入电话号码: ");
        String phone = scanner.next();
        System.out.print("请输入用户等级: ");
        String userLevel = scanner.next();

        // 注册日期可以直接使用当前日期
        Date registrationDate = new Date(System.currentTimeMillis());

        if (isValidUsername(username) && isValidPassword(password) && isValidEmail(email)) {
            if (registerCustomer(username, password, email, phone, registrationDate, userLevel)) {
                System.out.println("注册成功！");
            } else {
                System.out.println("注册失败，用户名已存在！");
            }
        } else {
            System.out.println("用户名、密码或邮箱不符合要求，注册失败！");
        }
    }
  

    private boolean registerCustomer(String username, String userpassword, String useremail, String phone, Date registrationDate, String userLevel) {
          String encryptedPassword=hashPassword(userpassword);
        // 创建 Customer 对象
        Customer customer = new Customer(username, encryptedPassword, useremail, phone, registrationDate, userLevel);

        // 插入用户信息到 Excel 文件
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(CUSTOMER_FILE));
             FileOutputStream fileOut = new FileOutputStream(CUSTOMER_FILE)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                sheet = workbook.createSheet("Customers");
            }

            int rowCount = sheet.getLastRowNum();
            Row row = sheet.createRow(++rowCount);

            int columnCount = 0;
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(customer.getUsername());

            cell = row.createCell(columnCount++);
            cell.setCellValue(customer.getUserpassword());

            cell = row.createCell(columnCount++);
            cell.setCellValue(customer.getUseremail());

            cell = row.createCell(columnCount++);
            cell.setCellValue(customer.getPhone());

            cell = row.createCell(columnCount++);
            cell.setCellValue(customer.getRegistrationDate().toString());

            cell = row.createCell(columnCount);
            cell.setCellValue(customer.getUserLevel());

            workbook.write(fileOut);
            System.out.println("用户注册成功！");
            return true;
        } catch (IOException e) {
            System.out.println("用户注册失败: " + e.getMessage());
            return false;
        }
        
    }
    

    private boolean isValidUsername(String username) {
        return username.length() >= 5;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true;
        }
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    private boolean isValidEmail(String email) {
        // 简单的邮箱格式验证
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }


    // 使用MD5加密
    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}
