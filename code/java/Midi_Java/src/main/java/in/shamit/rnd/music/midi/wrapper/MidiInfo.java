package in.shamit.rnd.music.midi.wrapper;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MidiInfo {
    Sequence sequence=null;
    MidiFileFormat format = null;
    public MidiInfo(String filePath) {
        loadData(new File(filePath));
    }

    public int getFileType(){
        return format.getType();
    }

    private void loadData(File file) {
        try {
            sequence = MidiSystem.getSequence(file);
            format = MidiSystem.getMidiFileFormat(file);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
           // e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MidiInfo(File file) {
        loadData(file);
    }

    public static final int TRACK_NAME = 3;

    public List<TrackData> getTracks(){
        try {
            List<TrackData> tracks=new ArrayList<>();
            Instrument instruments[];
            Synthesizer synthesizer = null;
            synthesizer = MidiSystem.getSynthesizer();
            Soundbank sb=synthesizer.getDefaultSoundbank();
            instruments=synthesizer.getDefaultSoundbank().getInstruments();
            var tr=sequence.getTracks();
            for(Track t:tr){
                var tdata = new TrackData();
                int eventCount=t.size();
                for(int i=0;i<eventCount;i++){
                    MidiEvent event=t.get(i);
                    MidiMessage message=event.getMessage();
                    if (message instanceof ShortMessage){
                        ShortMessage sm=(ShortMessage)message;
                        if (sm.getCommand()==ShortMessage.PROGRAM_CHANGE) {
                            Instrument ins = instruments[sm.getData1()];
                            InstrumentData instrument = new InstrumentData(sm.getChannel(), sm.getData1(),
                                    ins.getName(), ins.getPatch().getBank(),
                                    ins.getPatch().getProgram());
                            tdata.instruments.add(instrument);
                        }
                    }else{
                        if(message instanceof MetaMessage){
                            MetaMessage metaMessage = (MetaMessage)message;
                            if (metaMessage.getType() == TRACK_NAME) {
                                tdata.name = new String(metaMessage.getData());
                            }
                        }
                    }
                }
                tracks.add(tdata);
            }
            return tracks;
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }

    }

    public List<InstrumentData> getInstruments(){
        List<InstrumentData> ret = new ArrayList<>();
        try {
            Instrument instruments[];
            Synthesizer synthesizer = null;
            synthesizer = MidiSystem.getSynthesizer();
            Soundbank sb=synthesizer.getDefaultSoundbank();
            instruments=synthesizer.getDefaultSoundbank().getInstruments();
            int trackNumber=0;
            var tracks=sequence.getTracks();
            for (Track track : tracks)
            {
                //System.out.print("Track "+(trackNumber++)+" : ");
                int eventCount=track.size();
                for(int i=0;i<eventCount;i++){
                    MidiEvent event=track.get(i);
                    MidiMessage message=event.getMessage();
                    if (message instanceof ShortMessage)
                    {
                        ShortMessage sm=(ShortMessage)message;
                        if (sm.getCommand()==192){
                            Instrument ins=instruments[sm.getData1()];
                            /*System.out.println("sm.getChannel()="+sm.getChannel()+"  sm.getData1()="+sm.getData1()+"  "
                                    +ins);*/
                            InstrumentData data = new InstrumentData(sm.getChannel(),sm.getData1(),
                                    ins.getName(),ins.getPatch().getBank(),
                                    ins.getPatch().getProgram());
                            ret.add(data);
                        }

                    }
                }
            }


        } catch (MidiUnavailableException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        }

        return ret;
    }


}
