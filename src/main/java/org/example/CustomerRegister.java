package org.example;

import java.util.*;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class CustomerRegister {
    private List<Customer> customersList;

    public CustomerRegister(List<Customer> customersList) {
        this.customersList = customersList;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名：");   
        String username = scanner.nextLine();
        System.out.println("请输入密码：");
        String rawPassword = scanner.nextLine();
        System.out.println("请输入邮箱：");
        String email = scanner.nextLine();
        System.out.println("请输入电话号码：");
        String phone = scanner.nextLine();
        System.out.println("请输入注册日期（格式：yyyy-MM-dd）：");
        String registrationDate = scanner.nextLine();
        System.out.println("请输入用户等级：");
        String userLevel = scanner.nextLine();

        if (isValidUsername(username) && isValidPassword(rawPassword) && isValidEmail(email)) {
            String hashedPassword = hashPassword(rawPassword);
            Customer customer = new Customer(username, hashedPassword, email, phone, registrationDate, userLevel);
            customersList.add(customer);
            System.out.println("注册成功！");
        } else {
            System.out.println("用户名、密码或邮箱不符合要求，注册失败！");
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
