package com.test.functional.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListToMap {
    public static void main(String[] args) {

        List<Product> products = new ArrayList<>();
        products.add(new Product("Soap", "MSANDLE", 55));
        products.add(new Product("Soap", "SANTOOR", 65));
        products.add(new Product("Soap", "DOVE", 65));
        products.add(new Product("ToothPaste", "Colegate", 98));
        products.add(new Product("ToothPaste", "Closup", 105));
        products.add(new Product("Snack", "Haldirams", 185));

        Map<String, List<Product>> groupByCollect = products
                .stream()
                .collect(Collectors.groupingBy(Product::getCategory));
        System.out.println("groupByCollect ");
        groupByCollect.entrySet().stream().forEach(System.out::println);

        Map<String, List<String>> categoryByProductNames = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory,
                        Collectors.mapping(Product::getName, Collectors.toList())));
        System.out.println("categoryByProductNames ");
        categoryByProductNames.entrySet().stream().forEach(System.out::println);

        Map<String, Product> productMapUsingToMapGetLatestProduct = products.stream()
                .collect(
                        Collectors.toMap(/*MapKEey */ Product::getCategory,
                                /*Map Value */product -> product,
                                /* If collision resolution */
                                (product1, product2) -> product2));
        System.out.println("productMapUsingToMapGetLatestProduct ");
        productMapUsingToMapGetLatestProduct.entrySet().stream().forEach(System.out::println);

        Map<String, String> getProductCatByFirstProductName = products.stream()
                .collect(Collectors.toMap(
                        Product::getCategory,
                        product -> product.getName(),
                        (product1, product2) -> product1
                ));

        System.out.println("getProductCatByFirstProductName ");
        getProductCatByFirstProductName.entrySet().stream().forEach(System.out::println);

    }

    static class Product{
        String category;
        String name;
        int price;

        public Product(String category, String name, int price) {
            this.category = category;
            this.name = name;
            this.price = price;
        }
        public String getCategory() {
            return category;
        }
        public String getName() {
            return name;
        }
        public int getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "category='" + category + '\'' +
                    ", name='" + name + '\'' +
                    ", price=" + price +
                    '}';
        }
    }
}
