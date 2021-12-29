package in.shamit.rnd.music.midi.data.explore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import in.shamit.rnd.music.midi.Config;
import org.bson.Document;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SongAnalysis {
    static String json_inp_path = Config.TRACK_LIST;
    static String json_path_stage_2 = Config.TRACK_LIST_STAGE_2;
    static String simple_songs_path = Config.SIMPLE_SONG_LIST;

    public static Map readJson() throws IOException {
        String jsonStr = Files.readString(new File(json_inp_path).toPath(), StandardCharsets.UTF_8);
        var ret = new Gson().fromJson(jsonStr,Map.class);
        return ret;
    }

    static List<Document> buildStage2Summary(Map raw) throws IOException {
        var songList = MongoImport.createMongoObjects(raw);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //String json_str=gson.toJson(songList);
        //Files.write(new File(json_out_path).toPath(),json_str.getBytes(StandardCharsets.UTF_8));
        return songList;
    }

    private static List<Document> filerForSimpleSongs(List<Document> songList) {
        List<Document> ret = new ArrayList<>();
        for (var s : songList){
            String id = s.getString("_id");
            int fileType = s.getInteger("midiFileType");
            int trackCount = s.getInteger("tackCount");
            int insrumentsUniq = s.getInteger("insrumentsUniq");
            if(fileType ==0 && trackCount == 1 && insrumentsUniq <= 1 ){
                Document song = new Document();
                song.append("_id",id);
                ret.add(song);
            }
        }
        return ret;
    }

    public static void main(String args[]) throws Exception{
        Map raw = readJson();
        System.out.println("Read json file");
        var songList = buildStage2Summary(raw);
        System.out.println("Prepared stage 2 summary");
        List<Document> simpleSongList = filerForSimpleSongs(songList);
        System.out.println("Songs after filtering :: " + simpleSongList.size());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json_str=gson.toJson(simpleSongList);
        Files.write(new File(simple_songs_path).toPath(),json_str.getBytes(StandardCharsets.UTF_8));
    }


}
