package in.shamit.rnd.music.midi.preprocess;

import com.google.gson.Gson;
import in.shamit.rnd.music.midi.Config;
import org.bson.Document;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class GenerateInstrumentSongs {

    static Map<String,Integer> instruments;
    static{
        instruments = new HashMap<>();
        instruments.put("SITAR",ChangeInstrument.SITAR);
        instruments.put("PIANO",ChangeInstrument.PIANO);
        instruments.put("HARP",ChangeInstrument.HARP);
        instruments.put("GUITAR",ChangeInstrument.GUITAR);
        instruments.put("TRUMPET",ChangeInstrument.TRUMPET);
        instruments.put("FLUTE",ChangeInstrument.FLUTE);
    }

    static Logger log = Logger.getLogger("SongGen");
    static List<String> getSongs() throws IOException {
        String jsonPath = Config.SIMPLE_SONG_LIST;
        log.info("Reading :: "+jsonPath);
        String jsonStr = Files.readString(new File(jsonPath).toPath(), StandardCharsets.UTF_8);
        List<Map> raw = new Gson().fromJson(jsonStr, List.class);
        List<String> ret =   new ArrayList<>();
        for (var s:raw){
            ret.add(s.get("_id").toString());
        }
        return ret;
    }


    static String getInputSongPath(String id){
        String path = Config.RAW_MIDI_DIR + File.separator +
                id.substring(0,1) + File.separator + id ;
        return path;
    }

    static String getInputSongPath(String id, boolean additional_path){
        String baseDir = null;
        if(additional_path){
            baseDir = Config.RAW_ADDITIONAL_DIR;
        }else{
            baseDir = Config.RAW_MIDI_DIR;
        }
        String path = baseDir + File.separator +
                id.substring(0,1) + File.separator + id ;

        return path;
    }


    static String getOutputSongPath(String id, String instrument){
        String path = Config.GENERATED_SONGS + File.separator + instrument + File.separator
                + id.substring(0,1) + File.separator + id ;
        return path;
    }

    public static void generateSongs(String s) {
        final String inp = getInputSongPath(s);
        instruments.keySet().stream().forEach(i->{
            int instrument_id = instruments.get(i);
            String out_path = getOutputSongPath(s,i);
            try {
                Path p = Paths.get(out_path);
                Files.createDirectories(p.getParent());
                File f = new File(inp);
                if(f.exists()){
                    ChangeInstrument.changeInstrument(inp,out_path,instrument_id);
                }else{
                    ChangeInstrument.changeInstrument(getInputSongPath(s,true),out_path,instrument_id);
                }

            } catch (Exception e) {
                throw new RuntimeException("Error processing : " + s +" Instrument : " + i,e);
            }
        });
    }

    public static void main(String args[])  {
        try {
            var songs = getSongs();
            log.info("Processing, song count :: "+songs.size());
            songs.stream().forEach(s->{
                log.info(s);
                generateSongs(s);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
