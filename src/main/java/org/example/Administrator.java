package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Scanner;

/// 管理员的登录、登出、修改密码，以及重置特定用户的密码
public class Administrator {

    boolean isLogin;
    String adminPassword;
    private static final String ADMIN_FILE = "admins.xlsx";

    private static final String DEFAULT_ADMIN_USERNAME = "admin";
    private static final String DEFAULT_ADMIN_PASSWORD = "ynuinfo#777";

    // 新建一个表
    public void createTable() {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(ADMIN_FILE)) {
            Sheet sheet = workbook.createSheet("Admins");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("username");
            headerRow.createCell(1).setCellValue("password");
            workbook.write(fileOut);
        } catch (IOException e) {
            System.err.println("创建表时发生错误: " + e.getMessage());
        }
    }

    // 插入默认管理员用户名和密码的方法
    public void insertDefaultAdmin() {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(ADMIN_FILE));
             FileOutputStream fileOut = new FileOutputStream(ADMIN_FILE)) {
            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();
            Row row = sheet.createRow(++rowCount);
            row.createCell(0).setCellValue(DEFAULT_ADMIN_USERNAME);
            row.createCell(1).setCellValue(DEFAULT_ADMIN_PASSWORD);
            workbook.write(fileOut);
            System.out.println("默认管理员用户名和密码已成功插入到Excel文件中！");
        } catch (IOException e) {
            System.out.println("插入默认管理员用户名和密码失败: " + e.getMessage());
        }
    }

    public boolean loginAdmin(Scanner scanner) {
        System.out.println("输入用户名:");
        String username = scanner.next();
        System.out.println("输入密码:");
        String password = scanner.next();

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(ADMIN_FILE))) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getCell(0).getStringCellValue().equals(username) && row.getCell(1).getStringCellValue().equals(password)) {
                    isLogin = true;
                    adminPassword = password;
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void logoutAdmin() {
        isLogin = false;
    }

}
