import in.shamit.rnd.music.midi.Config;
import in.shamit.rnd.music.midi.preprocess.ChangeInstrument;

import java.io.File;

public class MusescoreTest {
    public static void main(String[] args) throws Exception{
        String midiName = "d0e099885ff2594c2e2d895dbf57f45a";
        String midiPath = Config.GENERATED_SONGS + File.separator + "FLUTE"
                + File.separator +midiName.substring(0,1)
                + File.separator + midiName + ".mid";
        String midiOutDir = Config.EXPLORATION_DIR + File.separator + "tmp";
        String midiOutFile = midiOutDir + File.separator + midiName + ".wav";
        String cmd = '"' + Config.MUSESCORE_BIN + '"' + " " + " -o "  + midiOutFile + " " + midiPath;
        System.out.println(cmd);
    }
}
