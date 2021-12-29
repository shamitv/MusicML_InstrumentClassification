import in.shamit.rnd.music.midi.Config;

import javax.sound.midi.*;
import java.io.File;

public class InstrumentChangeTest {
    public static void main(String[] args) throws Exception{
        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        var instruments=synthesizer.getDefaultSoundbank().getInstruments();
        String midiPath = Config.RAW_ADDITIONAL_DIR + File.separator + "t"
                + File.separator + "twinkle_twinkle_single_track.mid" ;
        String midiOutPath = Config.EXPLORATION_DIR + File.separator
                + "a3305a61f9af7680de8ac93965f962f9.mid" ;
        Sequence seq_in = MidiSystem.getSequence(new File(midiPath));
        Sequence seq_out = new Sequence(seq_in.getDivisionType(), seq_in.getResolution());
        int num_tracks = seq_in.getTracks().length;
        if(num_tracks != 1){
            throw new RuntimeException("Multi-track no supported yet");
        }else{
            Track t_in = seq_in.getTracks()[0];
            Track t_out = seq_out.createTrack();
            int eventCount=t_in.size();
            for(int i=0;i<eventCount;i++) {
                MidiEvent event = t_in.get(i);
                MidiMessage message=event.getMessage();
                if (message instanceof ShortMessage){
                    ShortMessage sm=(ShortMessage)message;
                    if (sm.getCommand()==ShortMessage.PROGRAM_CHANGE){
                        System.out.println("sm.getChannel()="+sm.getChannel()+"  sm.getData1()="+sm.getData1()+"  "
                                +instruments[sm.getData1()]);
                        int sitar_instrument_id = 104;
                        int piano_instrument_id = 0;
                        int harp_instrument_id = 46;
                        int guitar_instrument_id = 25;
                        int trumpet_instrument_id = 56;
                        int flute_instrument_id = 73;

                        ShortMessage out_msg = new ShortMessage(ShortMessage.PROGRAM_CHANGE ,
                                sm.getChannel() , sitar_instrument_id, sm.getData2());
                        MidiEvent out_evt = new MidiEvent(out_msg,0);
                        t_out.add(out_evt);
                    }else{
                        t_out.add(event);
                    }
                }else{
                    t_out.add(event);
                }
            }
            MidiFileFormat  fmt = MidiSystem.getMidiFileFormat(new File(midiPath));
            MidiSystem.write(seq_out,fmt.getType(),new File(midiOutPath));
            System.out.println("Wrote :: "+ midiOutPath);
        }
    }

}
