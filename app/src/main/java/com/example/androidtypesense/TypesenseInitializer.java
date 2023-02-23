package com.example.androidtypesense;

import org.typesense.api.*;
import org.typesense.resources.*;
import org.typesense.model.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TypesenseInitializer {

    private Client client;

    private String XXX = null; //TODO
    private String API_KEY = null; //TODO


    public TypesenseInitializer() throws Exception {
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
    }

    public Boolean getConnectionState() throws Exception {
        return (Boolean) client.health.retrieve().get("ok");
    }

    public SearchResult search(String collectionName, String queryText) throws Exception {
        SearchParameters searchParameters = new SearchParameters()
                .q(queryText)
                .queryBy("countryName,capital")
                .prefix("true,true")
                .perPage(20);
        return client.collections(collectionName).documents().search(searchParameters);
    }


}
