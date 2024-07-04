package com.auctionapp;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ThreadSave implements Runnable{

    private String user;
    private double value;


    public ThreadSave(String user, double value) {
        this.user = user;
        this.value = value;
    }


    @Override
    public void run() {
        String url = "mongodb+srv://admin:MdtrJacTvRqTDHWt@mongodb.ad6kqfb.mongodb.net/";
        MongoClientURI clientURI = new MongoClientURI(url);
        MongoClient mongoClient = new MongoClient(clientURI);    
        
        MongoDatabase mongoDatabase = mongoClient.getDatabase("MongoDB");
        MongoCollection collection = mongoDatabase.getCollection("Users");

         Document document = new Document("name" , this.user);
         document.append("value", this.value);

         collection.insertOne(document);

        }

}
