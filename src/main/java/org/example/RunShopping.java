package org.example;

import java.util.*;


public class RunShopping {
    
    Scanner scanner=new Scanner(System.in);
    Customer customer=new Customer();
    CustomerShoppingCart shoppingCart=new CustomerShoppingCart(customer);
    ProductDatabase productDatabase=new ProductDatabase();

 
    public void run() {
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            switch (choice) {
                case 1:
                    shoppingCart.addToCart();
                    break;
                case 2:
                    shoppingCart.removeFromCart();
                    break;
                case 3:
                    shoppingCart.updateCartItemQuantity();
                    break;
                case 4:
                    shoppingCart.getShoppingCartHistory();
                    break;
                case 5:
                    shoppingCart.checkout();
                    break;
                case 6:
                    System.out.println("退出用户系统");
                    return;
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }
    }

    private void displayMenu() {
        System.out.println("请选择操作：");
        System.out.println("1. 加入购物车");
        System.out.println("2. 从购物车删除");
        System.out.println("3. 更新购物车商品数量");
        System.out.println("4. 查看购物历史");
        System.out.println("5. 结账");
        System.out.println("6. 退出");
    }
    
}
