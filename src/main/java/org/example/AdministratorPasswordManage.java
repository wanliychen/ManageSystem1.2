package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Random;
import java.util.Scanner;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AdministratorPasswordManage {

    String adminPassword;

    private static final String ADMIN_FILE = "admins.xlsx";
   
  private CustomerDatabase customerDatabase; 
    
    private Scanner scanner = new Scanner(System.in);

    public AdministratorPasswordManage(CustomerDatabase customerDatabase) {
        this.customerDatabase = customerDatabase;
    }

    public void run() {
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    setAdminPassword();
                    break;
                case 2:
                    resetCustomerPassword();
                    break;
                case 3:
                    System.out.println("退出管理员密码管理");
                    return;
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }
    }

    private void displayMenu() {
        System.out.println("请选择操作：");
        System.out.println("1. 修改管理员密码");
        System.out.println("2. 重置用户密码");
        System.out.println("3. 退出管理员密码管理");
    }

    public void setAdminPassword() {
        System.out.println("输入用户名：");
        String username = scanner.next();
        System.out.println("输入密码：");
        String password = scanner.next();
        System.out.println("输入新密码：");
        String newPassword = scanner.next();

        File file = new File(ADMIN_FILE);
        if (!file.exists() || file.length() == 0) {
            System.out.println("文件不存在或为空！");
            return;
        }

        try (FileInputStream fileIn = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fileIn);
             FileOutputStream fileOut = new FileOutputStream(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean passwordUpdated = false;

            for (Row row : sheet) {
                Cell usernameCell = row.getCell(0);
                Cell passwordCell = row.getCell(1);

                if (usernameCell != null && passwordCell != null) {
                    if (usernameCell.getStringCellValue().equals(username) && passwordCell.getStringCellValue().equals(password)) {
                        passwordCell.setCellValue(newPassword);
                        workbook.write(fileOut);
                        adminPassword = newPassword;
                        System.out.println("密码修改成功！");
                        passwordUpdated = true;
                        break;
                    }
                }
            }
            if (!passwordUpdated) {
                System.out.println("用户名或密码错误！");
            }

        } catch (IOException e) {
            System.out.println("发生错误：" + e.getMessage());
        }
    }


    public void resetCustomerPassword() {
        System.out.println("输入用户名：");
        String username = scanner.nextLine();
        String newPassword = generateRandomPassword();
        String hashedPassword = hashPassword(newPassword);

        Customer customer = customerDatabase.findCustomerByUsername(username);
        if (customer != null) {
            customer.setPassword(hashedPassword);
            customerDatabase.updateCustomer(username, customer);
            System.out.println("密码已成功重置为： " + newPassword);
        } else {
            System.out.println("用户不存在！");
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