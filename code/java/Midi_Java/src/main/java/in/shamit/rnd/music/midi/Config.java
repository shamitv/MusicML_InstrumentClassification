package in.shamit.rnd.music.midi;

import java.io.File;

public class Config {
    public static final String BASE_DIR = "/Users/shamit/work/music_ml/MusicML_InstrumentClassification/data/lmd/";
    public static final String PROCESS_DIR = BASE_DIR + "process"+ File.separator;
    public static final String PROCESS_MIDI_DIR = PROCESS_DIR + "midi"+ File.separator;
    public static final String GENERATED_SONGS = PROCESS_MIDI_DIR + "songs"+ File.separator;
    public static final String RAW_DIR = BASE_DIR + "raw"+ File.separator;
    public static final String RAW_ADDITIONAL_DIR = BASE_DIR + "raw_additional"+ File.separator;
    public static final String RAW_MIDI_DIR = RAW_DIR + "lmd_full/";
    public static final String EXPLORATION_DIR = BASE_DIR + "analysis/";
    public static final String TRACK_LIST = EXPLORATION_DIR + File.separator + "trackslist.json";
    public static final String TRACK_LIST_STAGE_2 = EXPLORATION_DIR + File.separator + "trackslist_stage_2.json";
    public static final String SIMPLE_SONG_LIST = EXPLORATION_DIR + File.separator + "simple_songs.json";


    public static final String MONGO_HOST = "mongo1";
    public static final String MONGO_DB = "lakh_midi";
    public static final String MONGO_COLLECTION_SONGS = "midi_data_raw";

    public static final String MUSESCORE_BIN = "/Applications/MuseScore 3.app/Contents/MacOS/mscore";
}
