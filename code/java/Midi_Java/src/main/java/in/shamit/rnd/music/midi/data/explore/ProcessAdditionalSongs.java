package in.shamit.rnd.music.midi.data.explore;

import in.shamit.rnd.music.midi.preprocess.GenerateInstrumentSongs;

public class ProcessAdditionalSongs{
    static String songs[]={"twinkle_twinkle_single_track.mid"};
    public static void main(String args[]){
        for(var s : songs){
            GenerateInstrumentSongs.generateSongs(s);
        }
    }
}
