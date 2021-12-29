import javax.sound.midi.*;
import java.io.File;


public class ListTracks2 {
    public static void main(String[] args) {
        String midiFile="D:\\work\\mldata\\MusicData\\Lakh_MIDI_Dataset\\lmd_full\\0" +
                "\\000203a04a64ad57329a058f11e235cb.mid";

        try{
            Instrument instruments[];
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            Soundbank sb=synthesizer.getDefaultSoundbank();
            if (sb!=null){
                instruments=synthesizer.getDefaultSoundbank().getInstruments();
                Sequence sequence=MidiSystem.getSequence(new File(midiFile));
                int trackNumber=0;
                var tracks=sequence.getTracks();
                for (Track track : tracks)
                {
                    System.out.print("Track "+(trackNumber++)+" : ");
                    int eventCount=track.size();
                    for(int i=0;i<eventCount;i++){
                        MidiEvent event=track.get(i);
                        MidiMessage message=event.getMessage();
                        if (message instanceof ShortMessage)
                        {
                            ShortMessage sm=(ShortMessage)message;
                            if (sm.getCommand()==192)
                                System.out.println("sm.getChannel()="+sm.getChannel()+"  sm.getData1()="+sm.getData1()+"  "
                                        +instruments[sm.getData1()]);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error "+e.getMessage());
            e.printStackTrace();
        }
    }
}
