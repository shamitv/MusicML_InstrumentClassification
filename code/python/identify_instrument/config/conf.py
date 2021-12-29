class Conf:
    def __init__(self):
        self.FILE_PATH_SEPARATOR = '/'
        self.BASE_DIR = "/Users/shamit/work/music_ml/lakh_midi/data/lmd/"
        self.PROCESS_DIR = self.BASE_DIR + "process" + self.FILE_PATH_SEPARATOR
        self.PROCESS_MIDI_DIR = self.PROCESS_DIR + "midi" + self.FILE_PATH_SEPARATOR
        self.GENERATED_SONGS = self.PROCESS_MIDI_DIR + "songs" + self.FILE_PATH_SEPARATOR
        self.PROCESS_WAV_DIR = self.PROCESS_DIR + "wav" + self.FILE_PATH_SEPARATOR
        self.GENERATED_SONGS_WAV = self.PROCESS_WAV_DIR + "songs" + self.FILE_PATH_SEPARATOR
        self.RAW_DIR = self.BASE_DIR + "raw" + self.FILE_PATH_SEPARATOR
        self.RAW_MIDI_DIR = self.RAW_DIR + "lmd_full/"
        self.EXPLORATION_DIR = self.BASE_DIR + "analysis/"
        self.TRACK_LIST = self.EXPLORATION_DIR + self.FILE_PATH_SEPARATOR + "trackslist.json"
        self.TRACK_LIST_STAGE_2 = self.EXPLORATION_DIR + self.FILE_PATH_SEPARATOR + "trackslist_stage_2.json"
        self.SIMPLE_SONG_LIST = self.EXPLORATION_DIR + self.FILE_PATH_SEPARATOR + "simple_songs.json"
        self.WAV_CONV_SHELL_SCRIPT = self.EXPLORATION_DIR + self.FILE_PATH_SEPARATOR + "midi2wav.sh"
        self.MONGO_HOST = "mongo1"
        self.MONGO_DB = "lakh_midi"
        self.MONGO_COLLECTION_SONGS = "midi_data_raw"
        self.MUSESCORE_BIN = "/Applications/MuseScore 3.app/Contents/MacOS/mscore"
        self.instruments = ['SITAR', 'PIANO', 'HARP', 'GUITAR', 'TRUMPET', 'FLUTE']
