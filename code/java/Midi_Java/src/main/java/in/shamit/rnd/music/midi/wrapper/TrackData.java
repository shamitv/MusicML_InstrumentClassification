package in.shamit.rnd.music.midi.wrapper;

import java.util.ArrayList;
import java.util.List;

public class TrackData {
    String name;
    List<InstrumentData> instruments;
    public TrackData() {
        name = "";
        instruments = new ArrayList<>();
    }
}
