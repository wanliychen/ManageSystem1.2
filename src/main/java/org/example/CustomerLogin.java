package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.*;
import java.util.*;


public class CustomerLogin {
    private static final String CUSTOMER_FILE = "customers.xlsx";
    private Map<String, Integer> loginAttemptsMap = new HashMap<>();

    private Scanner scanner = new Scanner(System.in);

    CustomerPasswordManage cpm=new CustomerPasswordManage();

    public void run() {

        while (true) {
            System.out.println("请输入用户名：");
            String username = scanner.nextLine();
            System.out.println("请输入密码：");
            String password = scanner.nextLine();

            if (loginCustomer(username, password)) {
                System.out.println("登录成功！");
                CustomerAction customerAction = new CustomerAction();
                customerAction.run();
                break;
            } else {
                System.out.println("登录失败，用户名或密码不正确！");
            }
        }
    }

    public boolean loginCustomer(String username, String password) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(CUSTOMER_FILE))) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                System.out.println("用户名或密码错误");
                return false;
            }

            for (Row row : sheet) {
                if (row.getCell(0).getStringCellValue().equals(username)) {
                    String storedPassword = row.getCell(1).getStringCellValue();
                    int loginAttempts = loginAttemptsMap.getOrDefault(username, 0);

                    if (loginAttempts >= 5) {
                        System.out.println("账户已锁定，请联系管理员解锁！");
                        return false;
                    }

                    if (verifyPassword(password, storedPassword)) {
                        loginAttemptsMap.remove(username);
                        return true;
                    } else {
                        loginAttemptsMap.put(username, loginAttempts + 1);
                        System.out.println("密码错误，剩余尝试次数：" + (4 - loginAttempts));
                        System.out.println("是否重置密码？y/n");
                        String choice = scanner.nextLine();
                        if (choice.equals("y")) {
                            cpm.resetPassword(username); 
                        }
                        return false;
                    }
                }
            }
            System.out.println("用户名不存在！");
            return false;
        } catch (IOException e) {
            System.out.println("登录失败: " + e.getMessage());
            return false;
        }
    }
   
    // 验证密码
    private static boolean verifyPassword(String inputPassword, String storedHash) {
        String hashedInput = hashPassword(inputPassword);
        return hashedInput.equals(storedHash);
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
