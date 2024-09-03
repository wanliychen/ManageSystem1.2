package org.example;

import java.util.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CustomerPasswordManage {
    private Scanner scanner = new Scanner(System.in);
    private CustomerDatabase customerDatabase;

    public CustomerPasswordManage(CustomerDatabase customerDatabase) {
        this.customerDatabase = customerDatabase;
    }
    
    public void changePassword() {
        System.out.println("输入用户名：");
        String username = scanner.nextLine();
        System.out.println("输入旧密码：");
        String oldPassword = scanner.nextLine();
        System.out.println("输入新密码：");
        String newPassword = scanner.nextLine();

        if (isValidPassword(newPassword)) {
            if (updatePassword(username, oldPassword, newPassword)) {
                System.out.println("密码修改成功！");
            } else {
                System.out.println("旧密码不正确，密码修改失败！");
            }
        } else {
            System.out.println("新密码不符合要求，密码修改失败！");
        }
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

    private boolean updatePassword(String username, String oldPassword, String newPassword) {
        Customer customer = customerDatabase.findCustomerByUsername(username);
        if (customer != null && verifyPassword(oldPassword, customer.getPassword())) {
            String hashedPassword=hashPassword(newPassword);
            customer.setPassword(hashedPassword);
            customerDatabase.updateCustomer(username, customer);
            return true;
        }
        return false;
    }






    void resetPassword(String username, String oldPassword) {
        System.out.println("输入注册邮箱：");
        String email = scanner.nextLine();
        String newPassword = generateRandomPassword();
        String hashedPassword = hashPassword(newPassword);
        if (isEmailCorrect(username, email)) {

            Customer customer = customerDatabase.findCustomerByUsername(username);
            if (customer != null) {
                customer.setPassword(hashedPassword);
                customerDatabase.updateCustomer(username, customer);
                System.out.println("密码已成功重置为： " + newPassword);
            } else {
                System.out.println("用户不存在！");
            }
            System.out.println("新密码已发送到您的邮箱，请查收！");
        } else {
            System.out.println("用户名或邮箱不正确，密码重置失败！");
        }
    }

    private boolean isEmailCorrect(String username, String email) {
        Customer customer = customerDatabase.findCustomerByUsername(username);
        return customer != null && customer.getEmail().equals(email);
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

    private static boolean verifyPassword(String inputPassword, String storedHash) {
        String hashedInput = hashPassword(inputPassword);
        return hashedInput.equals(storedHash);
    }
}
