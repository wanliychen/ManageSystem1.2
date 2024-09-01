package org.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;

public class RunCustomer implements Actionable{
    private Scanner scanner;
    private CustomerDatabase customerDatabase;

    public RunCustomer() {
        this.scanner = new Scanner(System.in);
        this.customerDatabase = new CustomerDatabase();
    }

    @Override
    public void run() {
        while (true) {
            displayMenu();
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    deleteCustomer();
                    break;
                case 3:
                    findCustomer();
                    break;
                case 4:
                    updateCustomer();
                    break;
                case 5:
                    displayAllCustomers();
                    break;
                case 6:
                    System.out.println("退出用户管理");
                    return;
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }
    }

    @Override
    public void displayMenu() {
        System.out.println("请选择操作：");
        System.out.println("1. 增加用户");
        System.out.println("2. 删除用户");
        System.out.println("3. 查找用户");
        System.out.println("4. 更新用户");
        System.out.println("5. 返回所有用户");
        System.out.println("6. 退出");
    }

    private void addCustomer() {
        System.out.println("请输入用户名：");
        String username = scanner.nextLine();
        System.out.println("请输入密码：");
        String password = scanner.nextLine();
        System.out.println("请输入邮箱：");
        String useremail = scanner.nextLine();
        System.out.println("请输入电话号码：");
        String phone = scanner.nextLine();
        System.out.println("请输入注册日期（格式：yyyy-MM-dd）：");
        String registrationDate = scanner.nextLine();
        System.out.println("请输入用户等级：");
        String userLevel = scanner.nextLine();

        String hashPassword=hashPassword(password);
        Customer newCustomer = new Customer(username, hashPassword, useremail, phone, java.sql.Date.valueOf(registrationDate), userLevel);
        customerDatabase.addCustomer(newCustomer);
        System.out.println("用户添加成功！");
    }

    private void deleteCustomer() {
        System.out.println("请输入要删除的用户名：");
        String deleteUsername = scanner.nextLine();
        customerDatabase.deleteCustomerByUsername(deleteUsername);
        System.out.println("用户删除成功！");
    }

    private void findCustomer() {
        System.out.println("请输入要查找的用户名：");
        String findUsername = scanner.nextLine();
        Customer foundCustomer = customerDatabase.findCustomerByUsername(findUsername);
        if (foundCustomer != null) {
            System.out.println("找到用户：" + foundCustomer);
        } else {
            System.out.println("未找到用户！");
        }
    }

    private void updateCustomer() {
        System.out.println("请输入要更新的用户名：");
        String updateUsername = scanner.nextLine();
        System.out.println("请输入新的密码：");
        String newPassword = scanner.nextLine();
        System.out.println("请输入新的邮箱：");
        String newUseremail = scanner.nextLine();
        System.out.println("请输入新的电话号码：");
        String newPhone = scanner.nextLine();
        System.out.println("请输入新的注册日期（格式：yyyy-MM-dd）：");
        String newRegistrationDate = scanner.nextLine();
        System.out.println("请输入新的用户等级：");
        String newUserLevel = scanner.nextLine();

        // 加密密码
        String hashedPassword = hashPassword(newPassword);

        Customer updatedCustomer = new Customer(updateUsername, hashedPassword, newUseremail, newPhone, java.sql.Date.valueOf(newRegistrationDate), newUserLevel);
        customerDatabase.updateCustomer(updateUsername, updatedCustomer);
        System.out.println("用户更新成功！");
    }

    private void displayAllCustomers() {
        List<Customer> allCustomers = customerDatabase.getAllCustomers();
        System.out.println("所有用户：");
        for (Customer customer : allCustomers) {
            System.out.println(customer);
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
    
}
