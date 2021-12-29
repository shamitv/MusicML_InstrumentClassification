package in.shamit.rnd.music.midi.preprocess;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;

public class ChangeInstrument {
    public static final int SITAR = 104;
    public static final int PIANO = 0;
    public static final int HARP = 46;
    public static final int GUITAR = 25;
    public static final int TRUMPET = 56;
    public static final int FLUTE = 73;

    static boolean doesSpecifyInstrument(Track t){
        int eventCount=t.size();
        for(int i=0;i<eventCount;i++) {
            MidiEvent event = t.get(i);
            if(isInstrumentEvent(event)){
                return true;
            }
        }
        return false;
    }

    static boolean isInstrumentEvent(MidiEvent e){
        MidiMessage message=e.getMessage();
        if (message instanceof ShortMessage) {
            ShortMessage sm = (ShortMessage) message;
            if (sm.getCommand() == ShortMessage.PROGRAM_CHANGE) {
                return true;
            }
        }
        return false;
    }

    public static void changeInstrument(String inp, String out, int instrument)
            throws MidiUnavailableException, InvalidMidiDataException, IOException {
        Sequence seq_in = MidiSystem.getSequence(new File(inp));
        Sequence seq_out = new Sequence(seq_in.getDivisionType(), seq_in.getResolution());
        int num_tracks = seq_in.getTracks().length;
        if(num_tracks != 1){
            throw new RuntimeException("Multi-track not supported yet");
        }
        Track t_in = seq_in.getTracks()[0];
        Track t_out = seq_out.createTrack();
        if(!doesSpecifyInstrument(t_in)){
            ShortMessage out_msg = new ShortMessage(ShortMessage.PROGRAM_CHANGE ,
                    0 , instrument, 0);
            MidiEvent out_evt = new MidiEvent(out_msg,0);
            t_out.add(out_evt);
        }
        int eventCount=t_in.size();
        for(int i=0;i<eventCount;i++) {
            MidiEvent event = t_in.get(i);
            MidiMessage message=event.getMessage();
            if (message instanceof ShortMessage){
                ShortMessage sm=(ShortMessage)message;
                if (sm.getCommand()==ShortMessage.PROGRAM_CHANGE){
                    ShortMessage out_msg = new ShortMessage(ShortMessage.PROGRAM_CHANGE ,
                            sm.getChannel() , instrument, sm.getData2());
                    MidiEvent out_evt = new MidiEvent(out_msg,0);
                    t_out.add(out_evt);
                }else{
                    t_out.add(event);
                }
            }else{
                t_out.add(event);
            }
        }
        MidiFileFormat  fmt = MidiSystem.getMidiFileFormat(new File(inp));
        MidiSystem.write(seq_out,fmt.getType(),new File(out));
    }
}
