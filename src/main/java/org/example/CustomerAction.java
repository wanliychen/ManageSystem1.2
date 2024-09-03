package org.example;

import java.util.Scanner;

public class CustomerAction implements Actionable {
    private CustomerDatabase customerDatabase;
    private ProductDatabase productDatabase;
    private Scanner scanner;

     public CustomerAction(CustomerDatabase customerDatabase, ProductDatabase productDatabase) {
        this.customerDatabase = customerDatabase;
        this.productDatabase = productDatabase;
        this.scanner = new Scanner(System.in);
    }

@Override
public void run() {
   
    RunShopping runShopping = new RunShopping(productDatabase); 
    CustomerPasswordManage cpm = new CustomerPasswordManage(customerDatabase);

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            switch (choice) {
                case 1:
                    runShopping.run(); // 确保 RunShopping 的构造函数符合要求
                    break;
                case 2:
                    cpm.changePassword();
                    break;
                case 3:
                    System.out.println("退出用户系统");
                    return;
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }
    }

    @Override
    public void displayMenu() {
        System.out.println("请选择操作：");
        System.out.println("1. 购物管理");
        System.out.println("2. 修改密码");
        System.out.println("3. 退出登录");
    }
}

