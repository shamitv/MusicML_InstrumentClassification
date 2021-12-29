import javax.sound.midi.MidiSystem;
import javax.sound.midi.Patch;

public class PrintInstruments {
    public static void main(String[] args) throws Exception {
        var insList = MidiSystem.getSynthesizer().getAvailableInstruments();
        System.out.println("Number of instruments "+insList.length);
        String td_start = "<td>";
        String td_end = "</td>";
        String td_mid = td_end + td_start;

        System.out.println("<tr>"+td_start + "Name" + td_mid + "Bank" +td_mid + "Program" +td_end+"</tr>");

        for(var i : insList){
            Patch p = i.getPatch();
            System.out.println("<tr>"+td_start + i.getName() + td_mid + p.getBank() +td_mid + p.getProgram() +td_end+"</tr>");
        }
    }
}
