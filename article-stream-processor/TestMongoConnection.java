import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class TestMongoConnection {
    public static void main(String[] args) {
        try {
            System.out.println("Testing MongoDB connection...");
            
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase db = mongoClient.getDatabase("echowire");
            MongoCollection<Document> collection = db.getCollection("articles");
            
            // Test insert
            Document testDoc = new Document()
                    .append("test", "connection")
                    .append("timestamp", System.currentTimeMillis());
            
            collection.insertOne(testDoc);
            System.out.println("✅ MongoDB connection successful! Test document inserted.");
            
            // Clean up test document
            collection.deleteOne(testDoc);
            System.out.println("✅ Test document cleaned up.");
            
            mongoClient.close();
            
        } catch (Exception e) {
            System.err.println("❌ MongoDB connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 