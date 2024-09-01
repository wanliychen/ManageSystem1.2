package org.example;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class RunProduct implements Actionable {
    private Scanner scanner;
    private ProductDatabase productDatabase;

    public RunProduct() {
        this.scanner = new Scanner(System.in);
        this.productDatabase = new ProductDatabase();
    }

    @Override
    public void run() throws IOException {
        while (true) {
            displayMenu();
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    deleteProduct();
                    break;
                case 3:
                    findProduct();
                    break;
                case 4:
                    updateProduct();
                    break;
                case 5:
                    displayAllProducts();
                    break;
                case 6:
                    System.out.println("退出商品管理");
                    return;
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }
    }

    @Override
    public void displayMenu() {
        System.out.println("请选择操作：");
        System.out.println("1. 增加商品");
        System.out.println("2. 删除商品");
        System.out.println("3. 查找商品");
        System.out.println("4. 更新商品");
        System.out.println("5. 返回所有商品");
        System.out.println("6. 退出");
    }

    private void addProduct() throws IOException {
        System.out.println("请输入商品ID：");
        int productId = scanner.nextInt();
        scanner.nextLine(); // 消耗换行符
        System.out.println("请输入商品名称：");
        String productName = scanner.nextLine();
        System.out.println("请输入制造商：");
        String manufacturer = scanner.nextLine();
        System.out.println("请输入型号：");
        String model = scanner.nextLine();
        System.out.println("请输入进货价：");
        double purchasePrice = scanner.nextDouble();
        scanner.nextLine(); // 消耗换行符
        System.out.println("请输入零售价：");
        double retailPrice = scanner.nextDouble();
        scanner.nextLine(); // 消耗换行符
        System.out.println("请输入数量：");
        int nums = scanner.nextInt();
        scanner.nextLine(); // 消耗换行符

        Product newProduct = new Product(productId, productName, manufacturer, model, purchasePrice, retailPrice, nums);
        productDatabase.addProduct(newProduct);
        System.out.println("商品添加成功！");
    }

    private void deleteProduct() throws IOException {
        System.out.println("请输入要删除的商品ID：");
        int deleteProductId = scanner.nextInt();
        scanner.nextLine(); // 消耗换行符
        productDatabase.deleteProduct(deleteProductId);
        System.out.println("商品删除成功！");
    }

    private void findProduct() throws IOException {
        System.out.println("请输入要查找的商品ID：");
        int findProductId = scanner.nextInt();
        scanner.nextLine(); // 消耗换行符
        Product foundProduct = productDatabase.findProductById(findProductId);
        if (foundProduct != null) {
            System.out.println("找到商品：");
            System.out.println("商品ID：" + foundProduct.getProductId());
            System.out.println("商品名称：" + foundProduct.getProductName());
            System.out.println("制造商：" + foundProduct.getManufacturer());
            System.out.println("型号：" + foundProduct.getModel());
            System.out.println("进货价：" + foundProduct.getPurchasePrice());
            System.out.println("零售价：" + foundProduct.getRetailPrice());
            System.out.println("数量：" + foundProduct.getNums());
        } else {
            System.out.println("未找到商品！");
        }
    }

    private void updateProduct() throws IOException {
        System.out.println("请输入要更新的商品ID：");
        int updateProductId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("请输入新的商品名称：");
        String newProductName = scanner.nextLine();
        System.out.println("请输入新的制造商：");
        String newManufacturer = scanner.nextLine();
        System.out.println("请输入新的型号：");
        String newModel = scanner.nextLine();
        System.out.println("请输入新的进货价：");
        double newPurchasePrice = scanner.nextDouble();
        scanner.nextLine(); // 消耗换行符
        System.out.println("请输入新的零售价：");
        double newRetailPrice = scanner.nextDouble();
        scanner.nextLine(); // 消耗换行符
        System.out.println("请输入新的数量：");
        int newNums = scanner.nextInt();
        scanner.nextLine(); // 消耗换行符

        Product updatedProduct = new Product(updateProductId, newProductName, newManufacturer, newModel, newPurchasePrice, newRetailPrice, newNums);
        productDatabase.updateProduct(updateProductId, updatedProduct);
        System.out.println("商品更新成功！");
    }

    private void displayAllProducts() throws IOException {
        List<Product> allProducts = productDatabase.getAllProducts();
        System.out.println("所有商品：");
        for (Product product : allProducts) {
            System.out.println(product);
        }
    }

}
