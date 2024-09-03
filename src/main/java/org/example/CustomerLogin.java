package org.example;

import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CustomerLogin {
    private Scanner scanner = new Scanner(System.in);
    private Map<String, Integer> loginAttemptsMap = new HashMap<>();
    private CustomerPasswordManage cpm;
    private CustomerDatabase customerDatabase;
    private ProductDatabase productDatabase;

    public CustomerLogin(CustomerDatabase customerDatabase, ProductDatabase productDatabase) {
        this.customerDatabase = customerDatabase;
        this.productDatabase = productDatabase;
        this.cpm = new CustomerPasswordManage(customerDatabase);
    }
    
    public void run() {
        while (true) {
            System.out.println("请输入用户名：");
            String username = scanner.nextLine();
            System.out.println("请输入密码：");
            String password = scanner.nextLine();

            if (loginCustomer(username, password)) {
                System.out.println("登录成功！");
                CustomerAction customerAction = new CustomerAction(customerDatabase, productDatabase);
                customerAction.run();
                break;
            } else {
                System.out.println("登录失败，用户名或密码不正确！");
            }
        }
    }

    public boolean loginCustomer(String username, String password) {
        Customer customer = customerDatabase.findCustomerByUsername(username);
        if (customer != null) {
            String storedPassword = customer.getPassword();
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
                int remainingAttempts = 5 - loginAttempts - 1;
                System.out.println("密码错误，剩余尝试次数：" + remainingAttempts);
                System.out.println("是否重置密码？y/n");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("y")) {
                    cpm.resetPassword(username, password);
                }
                return false;
            }
        }
        System.out.println("用户名不存在！");
        return false;
    }

    private static boolean verifyPassword(String inputPassword, String storedHash) {
        String hashedInput = hashPassword(inputPassword);
        return hashedInput.equals(storedHash);
    }

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
