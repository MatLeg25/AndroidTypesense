package com.example.androidtypesense;

import android.util.Log;

import org.typesense.api.*;
import org.typesense.resources.*;
import org.typesense.model.*;

import java.lang.Override;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TypesenseInitializer {

    private Client client;

    private String XXX = null;//TODO
    private String API_KEY = null;//TODO


    public TypesenseInitializer() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    List<Node> nodes = new ArrayList<>();
                    nodes.add(
                            new Node(
                                    "https",       // For Typesense Cloud use https
                                    XXX,  // For Typesense Cloud use xxx.a1.typesense.net
                                    "443"        // For Typesense Cloud use 443
                            )
                    );

                    Configuration configuration = new Configuration(nodes, Duration.ofSeconds(2), API_KEY);
                    client = new Client(configuration);
                    System.out.println(client.health.retrieve().get("ok"));
                    Log.w("ts", client.health.retrieve().get("ok").toString());
                } catch (Exception e) {
                    Log.e("ts", e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public void createDoc() throws Exception {
        List<Field> fields = new ArrayList<>();
        fields.add(new Field().name("countryName").type(FieldTypes.STRING));
        fields.add(new Field().name("capital").type(FieldTypes.STRING));
        fields.add(new Field().name("gdp").type(FieldTypes.INT32).facet(true).sort(true));

        CollectionSchema collectionSchema = new CollectionSchema();
        collectionSchema.name("countries").fields(fields).defaultSortingField("gdp");

        client.collections().create(collectionSchema);
    }

    public void retrieve() throws Exception {
        CollectionResponse countries = client.collections("countries").retrieve();
        CollectionResponse[] retrieve = client.collections().retrieve();
        if(countries != null) {
            Log.w("ts",countries.getName());
            Log.w("ts",countries.getNumDocuments().toString());
            Log.w("ts",countries.getCreatedAt().toString());
        } else {
            Log.w("ts"," countries = NULL");
        }

        if(retrieve != null) {
            Log.w("ts", String.valueOf(retrieve.length));
        } else {
            Log.w("ts"," retrieve = NULL");
        }
    }


}
