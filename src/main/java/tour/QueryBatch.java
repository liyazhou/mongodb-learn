package tour;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;

import java.util.Arrays;

/**
 * Created by liyazhou on 2017/4/5.
 */
public class QueryBatch {
    public static void main(String[] args) {
//        mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]

//        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://novoshopsim:novoshopsim@172.30.100.12:10002");

//        MongoClient mongoClient = new MongoClient(mongoClientURI);
        MongoCredential credential = MongoCredential.createCredential("novoshopsim", "novoshopsim", "novoshopsim".toCharArray());

        MongoClient mongoClient = new MongoClient(new ServerAddress("172.30.100.12", 10002), Arrays.asList(credential));
        MongoDatabase mongoDatabase = mongoClient.getDatabase("novoshopsim");
//        mongoDatabase.listCollectionNames().forEach(new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(s);
//            }
//        });
//        mongoClient.listDatabaseNames().forEach(new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(s);
//            }
//        });

        MongoCollection<Document> collection = mongoDatabase.getCollection("userProduct");
        System.out.println(collection.find().first().toJson());
        MongoCursor<Document> cursor = collection.find()
                .projection(Projections.include("productId", "productName")).batchSize(100).iterator();
        int count = 0;
        while (cursor.hasNext()) {
            count++;
            Document document = cursor.next();
            System.out.println(document.toJson());
        }
        System.out.println(count);

    }
}
