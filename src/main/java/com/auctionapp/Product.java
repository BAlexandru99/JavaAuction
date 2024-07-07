package com.auctionapp;

import java.util.Scanner;

public class Product {
    private String name;
    private String description;
    private Double startValue;

    public Product() {
    }

    public Product(String name, String description, Double startValue) {
        this.name = name;
        this.description = description;
        this.startValue = startValue;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getStartValue() {
        return this.startValue;
    }

    public void setStartValue(Double startValue) {
        this.startValue = startValue;
    }

    public void listItem(Scanner scan) {
        System.out.println("What product do you want to list?");
        this.name = scan.nextLine();

        System.out.println("Please give us a short description.");
        this.description = scan.nextLine();

        System.out.println("From what price do you want to start the bid?");
        this.startValue = scan.nextDouble();
    }

    public String messageToTheUsers() {
        return "The product put up for auction is: " + this.name + ". \n" +
               "Description: " + this.description + ".\n" +
               "The auction starts from: " + this.startValue + ".\n" +
               "Cine doreste sa liciteze sa tasteze cat oferta ";
    }
}
