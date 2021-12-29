from config import Conf
import json


def get_filtered_songs():
    conf = Conf()
    json_file = conf.SIMPLE_SONG_LIST
    with open(json_file, encoding='utf-8') as f:
        raw = json.load(f)
        ret = []
        for s in raw:
            id = s["_id"]
            ret.append(id)
        return ret


def get_id_from_midi_file(fname):
    return fname.rpartition('.')[0]


def get_instrument_midi_for_song(song_midi, instrument):
    conf = Conf()
    id = get_id_from_midi_file(song_midi)
    path = conf.GENERATED_SONGS + conf.FILE_PATH_SEPARATOR \
           + instrument + conf.FILE_PATH_SEPARATOR \
           + id[0] + conf.FILE_PATH_SEPARATOR \
           + id + ".mid"
    return path


def get_instrument_wav_for_song(song_midi, instrument):
    conf = Conf()
    id = get_id_from_midi_file(song_midi)
    path = conf.GENERATED_SONGS_WAV + conf.FILE_PATH_SEPARATOR \
           + instrument + conf.FILE_PATH_SEPARATOR \
           + id[0] + conf.FILE_PATH_SEPARATOR \
           + id + ".wav"
    return path
