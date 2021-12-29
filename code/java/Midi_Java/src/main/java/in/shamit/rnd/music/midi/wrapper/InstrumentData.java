package in.shamit.rnd.music.midi.wrapper;

public class InstrumentData {
    int channel;
    int id;
    String name;
    int bank;
    int preset;

    public InstrumentData(int channel, int id, String name, int bank, int preset) {
        this.channel = channel;
        this.id = id;
        this.name = name;
        this.bank = bank;
        this.preset = preset;
    }

    @Override
    public String toString() {
        return "InstrumentData{" +
                "channel=" + channel +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", bank=" + bank +
                ", preset=" + preset +
                '}';
    }
}
