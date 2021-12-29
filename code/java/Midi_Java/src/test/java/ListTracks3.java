import in.shamit.rnd.music.midi.wrapper.MidiInfo;

public class ListTracks3 {
    public static void main(String[] args) {
        String midiFile="C:\\work\\music_ml\\lakh_midi\\data\\lmd\\raw\\lmd_full\\0" +
                "\\000203a04a64ad57329a058f11e235cb.mid";
        MidiInfo m = new MidiInfo(midiFile);
        var tracks = m.getTracks();
        System.out.println(m.getInstruments());
    }
}
