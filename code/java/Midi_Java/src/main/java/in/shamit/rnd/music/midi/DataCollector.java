package in.shamit.rnd.music.midi;

import com.google.gson.GsonBuilder;
import in.shamit.rnd.music.midi.wrapper.InstrumentData;
import in.shamit.rnd.music.midi.wrapper.MidiInfo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import com.google.gson.Gson;
import in.shamit.rnd.music.midi.wrapper.TrackData;

public class DataCollector {
    private static final Logger logger = Logger.getLogger("Data Collection");
    public static void main(String[] args) {
        String midi_dir=Config.RAW_MIDI_DIR;
        String json_file=Config.TRACK_LIST;
        AtomicLong fileCount= new AtomicLong();
        Map<String, Object> songs=new ConcurrentHashMap<>();

        try {
            logger.info("Scanning :: "+midi_dir);
            Files.walk(Path.of(midi_dir)).parallel().forEach(
                    p->{
                        var f=p.toFile();
                        if(f.isFile()){
                            try{
                                MidiInfo m = new MidiInfo(f);
                                var tracks=m.getTracks();
                                String midiName=f.getName();
                                Map<String,Object> info= new HashMap<>();
                                info.put("tracks",tracks);
                                info.put("midiFileType",new Integer((int)m.getFileType()));
                                songs.put(midiName,info);
                            }catch(Exception e){
                               // e.printStackTrace();
                            }
                            long val=fileCount.getAndIncrement();
                            if(val%10000==0){
                                logger.info(""+val);
                            }
                        }
                    }
            );

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json_str=gson.toJson(songs);
            logger.info("Writing :: " + json_file);
            Files.write(new File(json_file).toPath(),json_str.getBytes(StandardCharsets.UTF_8));
              } catch (IOException e) {
            //e.printStackTrace();
        }

    }
}
