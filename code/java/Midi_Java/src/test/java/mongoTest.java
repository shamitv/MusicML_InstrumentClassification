import com.mongodb.MongoClient;
import com.mongodb.client.MongoIterable;

import java.util.ArrayList;
import java.util.List;

public class mongoTest {
    public static void main(String args[]){
        String mongoHost = "mongo1";
        MongoClient mongoClient = new MongoClient( mongoHost );
        MongoIterable<String> dbs = mongoClient.listDatabaseNames();
        List<String> names = dbs.into(new ArrayList<>());
        System.out.println(names);
    }
}
