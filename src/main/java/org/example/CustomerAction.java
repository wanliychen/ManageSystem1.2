package org.example;


import java.io.IOException;
import java.util.Scanner;

public class CustomerAction {

    public void run() throws IOException {
        Scanner scanner=new Scanner(System.in);
        
        RunShopping runShopping=new RunShopping();
        CustomerPasswordManage cpm=new CustomerPasswordManage();
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            switch (choice) {
                case 1:
                    runShopping.run();
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

    private void displayMenu() {
        System.out.println("请选择操作：");
        System.out.println("1. 购物管理");
        System.out.println("2. 修改密码");
        System.out.println("3. 退出登录");
    }
}
