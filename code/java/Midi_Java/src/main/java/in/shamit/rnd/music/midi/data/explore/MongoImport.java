package in.shamit.rnd.music.midi.data.explore;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import in.shamit.rnd.music.midi.Config;

import java.util.*;
import java.util.logging.*;
import org.bson.Document;

import static in.shamit.rnd.music.midi.data.explore.SongAnalysis.readJson;

public class MongoImport {
    private static Logger log = Logger.getLogger("MongoImport");
    static MongoDatabase getMongoDB(){
        MongoClient mongo = new MongoClient(Config.MONGO_HOST);
        MongoDatabase db = mongo.getDatabase(Config.MONGO_DB);
        return db;
    }

    static MongoCollection<Document> getMongoCollection(){
        MongoDatabase db = getMongoDB();
        MongoCollection<Document> col = db.getCollection(Config.MONGO_COLLECTION_SONGS);
        return col;
    }

    private static Document createDoc(Map songInfo, String id) {
        List trackList = (List)songInfo.get("tracks");
        Double tmpDbl = (Double)songInfo.get("midiFileType");
        Integer fileType = tmpDbl.intValue();

        Map<String,Object> tracks = new HashMap<>();
        Set<Integer> instrumentIDs = new HashSet<>();
        int i = 0;
        for(var obj : trackList){
            i++;
            tracks.put(Integer.toString(i),obj);
            Map track = (Map)obj;
            List instruments =  (List)track.get("instruments");
            for(var ins : instruments){
                Map imsMap = (Map) ins;
                Object idObj = imsMap.get("id");
                Double doubleIDObj = (Double) idObj;
                Integer insId = doubleIDObj.intValue();
                instrumentIDs.add(insId);
            }
        }
        boolean simpleSong = false;
        //If 0 or 1 tracks AND 0 or 1 instruments
        if(tracks.size() < 2 && instrumentIDs.size() < 2){
            simpleSong = true;
        }
        Document doc = new Document().append("_id",id)
                .append("tracks",tracks)
                .append("tackCount",tracks.size())
                .append("insrumentsUniq",instrumentIDs.size())
                .append("simpleSong",simpleSong)
                .append("midiFileType",fileType);
        return doc;
    }


    public static List<Document> createMongoObjects(Map raw) {
        List<Document> ret = new ArrayList<>();
        for(var key : raw.keySet()){
            Map songInfo = (Map)raw.get(key);
            Document doc = createDoc(songInfo,(String)key);
            ret.add(doc);
        }
        return ret;
    }


    public static void main(String args[]) throws Exception{
        log.info("Reading json file");
        Map raw = readJson();
        log.info("Read json file");
        List<Document>  docs= createMongoObjects(raw);
        log.info("Converted objects to Mongo documents");
        var col = getMongoCollection();
        log.info("Connected to Mongo");
        col.insertMany(docs);
        log.info("Written data to Mongo");
    }




}
