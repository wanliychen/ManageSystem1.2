package org.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CustomerShoppingCart {
    Map<Integer, Integer> shoppingCart; // 商品集，使用 Integer 作为键，以便与 Product ID 一致
    ProductDatabase productDatabase=new ProductDatabase();
    Scanner scanner=new Scanner(System.in);

    public CustomerShoppingCart(Customer customer) {
        this.shoppingCart = new HashMap<>();
        this.productDatabase = new ProductDatabase();
    }

    // 加入购物车
    public void addToCart() {
        System.out.println("请输入商品ID：");
        int productId = scanner.nextInt();
        System.out.println("请输入数量：");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // 消耗换行
        try {
            Product product = productDatabase.findProductById(productId);
            if (product != null) {
                shoppingCart.put(productId, shoppingCart.getOrDefault(productId, 0) + quantity);
                productDatabase.updateProductQuantity(productId, quantity);
                System.out.println("加入成功");
            } else {
                System.out.println("Product ID " + productId + " not found in the database.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while accessing the product database: " + e.getMessage());
        }
    }

    // 从购物车删除
    public void removeFromCart() {
        System.out.println("请输入商品ID：");
        int productId = scanner.nextInt();
        System.out.println("请输入数量：");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // 消耗换行
        if (shoppingCart.containsKey(productId)) {
            int currentQuantity = shoppingCart.get(productId);
            if (currentQuantity > quantity) {
                shoppingCart.put(productId, currentQuantity - quantity);
            } else {
                shoppingCart.remove(productId);
            }
            try {
                productDatabase.increaseProductQuantity(productId, quantity);
            } catch (IOException e) {
                System.out.println("An error occurred while updating the product quantity: " + e.getMessage());
            }
        } else {
            System.out.println("Product ID " + productId + " not found in the cart.");
        }
    }

    // 更新购物车商品数量
    public void updateCartItemQuantity() {
        System.out.println("请输入商品ID：");
        int productId = scanner.nextInt();
        System.out.println("请输入新的数量：");
        int newQuantity = scanner.nextInt();
        scanner.nextLine(); // 消耗换行
        if (shoppingCart.containsKey(productId)) {
            int currentQuantity = shoppingCart.get(productId);
            int quantityChange = newQuantity - currentQuantity;
            shoppingCart.put(productId, newQuantity);
            try {
                productDatabase.updateProductQuantity(productId, quantityChange);
            } catch (IOException e) {
                System.out.println("An error occurred while updating the product quantity: " + e.getMessage());
            }
        } else {
            System.out.println("Product ID " + productId + " not found in the cart.");
        }
    }

    // 获取购物车内容
    public void getShoppingCartHistory() {
        StringBuilder cart = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : shoppingCart.entrySet()) {
            cart.append("Product ID: ").append(entry.getKey())
                .append(", Quantity: ").append(entry.getValue())
                .append("\n");
        }
        System.out.println(cart.toString());
    }

     // 结账
     public void checkout(){
        System.out.println("请选择支付方式：");
        System.out.println("1. 支付宝");
        System.out.println("2. 微信");
        System.out.println("3. 银行卡");

        int choice=scanner.nextInt();
        scanner.nextLine();
        
        switch(choice){
            case 1:
                System.out.println("使用支付宝支付");
                break;
            case 2:
                System.out.println("使用微信支付");
                break;
            case 3:
                System.out.println("使用银行卡支付");
                break;
            default:
                System.out.println("无效的支付方式");
        }
    }
}
