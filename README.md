# AndroidTypesense
### Sample project to determine Android setup for Typesense search.
**Typesense version: 'org.typesense:typesense-java:0.0.9-beta5'**

#### Steps:
>1. Add internet permission in AndroidManifest
```
uses-permission android:name="android.permission.INTERNET"/>
```

>2a. Set Typesense Client dependency in build.gradle
```
implementation 'org.typesense:typesense-java:0.0.9-beta5'
```

>2b. Exclude group to prevent Duplication class error in build.gradle:
```
configurations {
    cleanedAnnotations
    implementation.exclude group: 'org.jetbrains' , module:'annotations'
    implementation.exclude group: 'org.jetbrains.kotlin' , module:'kotlin-stdlib'
    implementation.exclude group: 'org.apache.logging.log4j' , module:'log4j-api'
    implementation.exclude group: 'org.apache.logging.log4j' , module:'log4j-core'
    implementation.exclude group: 'com.squareup.okio' , module:'okio'
    implementation.exclude group: 'com.squareup.okhttp3' , module:'okhttp'
    implementation.exclude group: 'jakarta.xml.bind' , module:'jakarta.xml.bind-api'
    implementation.exclude group: 'javax.xml.bind' , module:'jaxb-api'
    implementation.exclude group: 'jakarta.activation' , module:'jakarta.activation-api'
    implementation.exclude group: 'io.swagger.core.v3' , module:'swagger-annotations'
    implementation.exclude group: 'com.fasterxml.jackson.module' , module:'jackson-module-jaxb-annotations'
}
```
>3. Create class TypesenseInitializer with connection Node details
```
(...)
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
    }
(...)
```
>4. Create Typesense client in MainActivity class and connect UI actions with the client

>5. Run the app!

[device-2023-02-23-222833.webm](https://user-images.githubusercontent.com/70913892/221036286-2095f666-2584-4973-acca-a7e0e7703201.webm)






<hr />

 Demo works with 'intermariumCountries' collection created with web console of Typesense Cloud.
 Documents in the collection:
```
[
{
    "countryName": "Estonia",
    "capital": "Tallinn",
    "gdp": 0
},
{
    "countryName": "Latvia",
    "capital": "Riga",
    "gdp": 0
},
{
    "countryName": "Lithuania",
    "capital": "Vilnius",
    "gdp": 0
},
{
    "countryName": "Poland",
    "capital": "Warsaw",
    "gdp": 0
},
{
    "countryName": "Czech Republic",
    "capital": "Prague",
    "gdp": 0
},
{
    "countryName": "Slovakia",
    "capital": "Bratislava",
    "gdp": 0
},
{
    "countryName": "Austria",
    "capital": "Vienna",
    "gdp": 0
},
{
    "countryName": "Hungary",
    "capital": "Budapest",
    "gdp": 0
},
{
    "countryName": "Slovenia",
    "capital": "Ljubljana",
    "gdp": 0
},
{
    "countryName": "Croatia",
    "capital": "Zagreb",
    "gdp": 0
},
{
    "countryName": "Romania",
    "capital": "Bucharest",
    "gdp": 0
},
{
    "countryName": "Bulgaria",
    "capital": "Sofia",
    "gdp": 0
}
]
```
