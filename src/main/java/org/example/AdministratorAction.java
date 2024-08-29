package org.example;

import java.io.IOException;
import java.util.Scanner;

public class AdministratorAction {
    public void run() throws IOException{
        Scanner scanner=new Scanner(System.in);
        
        RunProduct runProduct=new RunProduct();
        RunCustomer runCustomer=new RunCustomer();
        AdministratorPasswordManage apm=new AdministratorPasswordManage();
        Administrator admin=new Administrator();


        while(true){
            displayMenu();

            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符


            switch (choice) {
                case 1:
                    runProduct.run();;
                    break;
                case 2:
                    runCustomer.run();
                    break;
                case 3:
                    apm.run();
                    break;
                case 4:
                    System.out.println("退出管理员系统");
                    admin.logoutAdmin();
                    return;
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }
    }

    private void displayMenu(){
        System.out.println("请选择操作：");
        System.out.println("1. 商品管理");
        System.out.println("2. 用户管理");
        System.out.println("3. 密码管理");
        System.out.println("4. 退出管理员登录");
    }
}

