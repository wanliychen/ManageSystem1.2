package org.example;

import java.util.Scanner;

public class RunShopping implements Actionable {

    Scanner scanner = new Scanner(System.in);
    Customer customer = new Customer(null, null, null, null, null, null);
    CustomerShoppingCart shoppingCart = new CustomerShoppingCart();
    ProductDatabase productDatabase;

    public RunShopping(ProductDatabase productDatabase) {
        this.productDatabase = productDatabase;
    }

    @Override
    public void run() {
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            switch (choice) {
                case 1:
                    shoppingCart.addToCart(productDatabase);
                    break;
                case 2:
                    shoppingCart.removeFromCart();
                    break;
                case 3:
                    shoppingCart.updateCartItemQuantity();
                    break;
                case 4:
                    shoppingCart.getPurchaseHistory();
                    break;
                case 5:
                    shoppingCart.checkout(productDatabase);
                    break;
                case 6:
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
        System.out.println("1. 加入购物车");
        System.out.println("2. 从购物车删除");
        System.out.println("3. 更新购物车商品数量");
        System.out.println("4. 查看购物历史");
        System.out.println("5. 结账");
        System.out.println("6. 退出");
    }
}
