package com.auctionapp;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ThreadSaveProduct implements Runnable {

    private String name;
    private double price;
    

    public ThreadSaveProduct(String name, double price) {
        this.name = name;
        this.price = price;
    }


    @Override
    public void run() {
        String url = "mongodb+srv://admin:MdtrJacTvRqTDHWt@mongodb.ad6kqfb.mongodb.net/";
        MongoClientURI mongoClientURI = new MongoClientURI(url);
        MongoClient mongoClient = new MongoClient(mongoClientURI);
    
        MongoDatabase mongoDatabase = mongoClient.getDatabase("MongoDB");
        MongoCollection products = mongoDatabase.getCollection("Products");
    
        Document document = new Document("name" , this.name);
        document.append("value", this.price);
        
        products.insertOne(document);
    }

}
