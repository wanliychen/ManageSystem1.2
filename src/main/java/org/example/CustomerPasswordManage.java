package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.regex.Pattern;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class CustomerPasswordManage {
    private static final String CUSTOMER_FILE = "customers.xlsx";

    private Scanner scanner=new Scanner(System.in);

    public void changePassword() {
        System.out.println("输入用户名：");
        String username = scanner.next();
        System.out.println("输入新密码:");
        String rawNewpassword = scanner.next();
        String newpassword = hashPassword(rawNewpassword);

        Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$");
        if (passwordPattern.matcher(rawNewpassword).matches()) {
            try (Workbook workbook = new XSSFWorkbook(new FileInputStream(CUSTOMER_FILE));
                 FileOutputStream fileOut = new FileOutputStream(CUSTOMER_FILE)) {
                Sheet sheet = workbook.getSheetAt(0);
                if (sheet == null) {
                    System.out.println("未找到用户或密码未更改。");
                    return;
                }
                for (Row row : sheet) {
                    if (row.getCell(0).getStringCellValue().equals(username)) {
                        row.getCell(1).setCellValue(newpassword);
                        workbook.write(fileOut);
                        System.out.println("用户密码已成功更改！");
                        return;
                    }
                }
                System.out.println("未找到用户或密码未更改。");
            } catch (IOException e) {
                System.out.println("更改用户密码时出错: " + e.getMessage());
            }
        } else {
            System.out.println("新密码不符合要求。");
        }
    }

    public void resetPassword(String username) {
       
        System.out.println("请输入注册所使用的邮箱地址：");
        String email = scanner.nextLine();

        if (isEmailCorrect(username, email)) {
            String newPassword = generateRandomPassword();
            String hashedPassword = hashPassword(newPassword);

            try (Workbook workbook = new XSSFWorkbook(new FileInputStream(CUSTOMER_FILE))) {
                Sheet sheet = workbook.getSheetAt(0);
                if (sheet == null) {
                    System.out.println("用户信息文件错误");
                    return;
                }
                for (Row row : sheet) {
                    if (row.getCell(0).getStringCellValue().equals(username)) {
                        row.getCell(1).setCellValue(hashedPassword);
                        try (FileOutputStream fileOut = new FileOutputStream(CUSTOMER_FILE)) {
                            workbook.write(fileOut);
                            sendPasswordToEmail(email, newPassword);
                            System.out.println("新密码已发送到您的邮箱，请使用新密码登录。");
                        }
                        return;
                    }
                }
                System.out.println("用户名不存在！");
            } catch (IOException e) {
                System.out.println("密码重置失败: " + e.getMessage());
            }
        } else {
            System.out.println("用户名或邮箱地址不正确。");
        }
    }

    // 生成随机密码
    private String generateRandomPassword() {
        String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
        String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String DIGITS = "0123456789";
        String SPECIAL_CHARS = "!@#$%^&*()-_=+[]{}|;:,.<>?";
        
    
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        // 确保密码中包含至少一个小写字母
        password.append(LOWERCASE_CHARS.charAt(random.nextInt(LOWERCASE_CHARS.length())));

        // 确保密码中包含至少一个大写字母
        password.append(UPPERCASE_CHARS.charAt(random.nextInt(UPPERCASE_CHARS.length())));

        // 确保密码中包含至少一个数字
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));

        // 确保密码中包含至少一个特殊字符
        password.append(SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())));

        // 填充剩余的密码长度
        for (int i = 4; i < 10; i++) {
            String allChars = LOWERCASE_CHARS + UPPERCASE_CHARS + DIGITS + SPECIAL_CHARS;
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // 将密码字符随机打乱
        char[] passwordArray = password.toString().toCharArray();
        for (int i = 0; i < passwordArray.length; i++) {
            int randomIndex = random.nextInt(passwordArray.length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[randomIndex];
            passwordArray[randomIndex] = temp;
        }

        return new String(passwordArray);
    }



    private boolean isEmailCorrect(String username, String email) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(CUSTOMER_FILE))) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                System.out.println("用户信息文件错误");
                return false;
            }
            for (Row row : sheet) {
                if (row.getCell(0).getStringCellValue().equals(username)) {
                    String storedEmail = row.getCell(2).getStringCellValue();
                    return email.equalsIgnoreCase(storedEmail);
                }
            }
            System.out.println("用户名不存在！");
            return false;
        } catch (IOException e) {
            System.out.println("数据库访问错误: " + e.getMessage());
            return false;
        }
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
    
    private void sendPasswordToEmail(String email, String password) {
        // 模拟发送邮件功能
        System.out.println("新密码发送到邮件" + email + "，新密码为：" + password);
    }
    
}

