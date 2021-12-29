import in.shamit.rnd.music.midi.Config;
import in.shamit.rnd.music.midi.preprocess.ChangeInstrument;

import java.io.File;

public class ChangeInstrument2 {
    public static void main(String[] args) throws Exception{
        String midiName = "a3305a61f9af7680de8ac93965f962f9.mid";
        String tmp = midiName.substring(0,1);
        String midiPath = Config.RAW_MIDI_DIR + File.separator + midiName.substring(0,1)
                + File.separator + "a3305a61f9af7680de8ac93965f962f9.mid" ;
        String midiOutDir = Config.EXPLORATION_DIR + File.separator + "tmp";
        ChangeInstrument.changeInstrument(midiPath,midiOutDir + File.separator +"sitar_" + midiName, ChangeInstrument.SITAR);
    }
}
