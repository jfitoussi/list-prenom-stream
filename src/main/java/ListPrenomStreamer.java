import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;

import models.Fields;
import models.ParisData;
import models.Records;

public class ListPrenomStreamer {

    private static final Gson GSON = new Gson();
    private ParisData parisData;

    public ListPrenomStreamer(String filename) {
        InputStreamReader inputStreamReader = new InputStreamReader(getClass().getResourceAsStream(filename));
        this.parisData = GSON.fromJson(inputStreamReader, ParisData.class);
    }

    public static void main(String[] args) throws IOException {
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");
        System.out.println(listPrenomStreamer.getSize());
        System.out.println(listPrenomStreamer.top3name2008());
    }

    public int getSize() {

        return parisData.getRecords().size();
    }

    public List<String> top3name2008() {

        return null;
    }

    public List<String> top3name2010() {

        Comparator<Fields> top3name = (Fields R1, Fields R2) -> Integer.compare(R1.getNombre(), R2.getNombre());
        Stream<String> stream = this.parisData.getRecords().stream().map(fields -> fields.getFields()).sorted(top3name.reversed()).filter(fields -> fields.getAnnee() == 2010).map(person -> person.getPrenoms()).limit(3);
        return stream.collect(Collectors.toList());
    }

    public List<String> top3girl2009() {

        Comparator<Fields> top3name = (Fields R1, Fields R2) -> Integer.compare(R1.getNombre(), R2.getNombre());
        Stream<String> stream = this.parisData.getRecords().stream().map(fields -> fields.getFields()).sorted(top3name.reversed()).filter(fields -> fields.getAnnee() == 2009 && fields.getSexe().equals("F")).map(person -> person.getPrenoms()).limit(3);
        return stream.collect(Collectors.toList());
    }

    public List<String> top3boy2012() {

        Comparator<Fields> top3name = (Fields R1, Fields R2) -> Integer.compare(R1.getNombre(), R2.getNombre());
        Stream<String> stream = this.parisData.getRecords().stream().map(fields -> fields.getFields()).sorted(top3name.reversed()).filter(fields -> fields.getAnnee() == 2012 && fields.getSexe().equals("M")).map(person -> person.getPrenoms()).limit(3);
        return stream.collect(Collectors.toList());
    }

    public List<String> top52009_2016() {

        Comparator<Fields> top3name = (Fields R1, Fields R2) -> Integer.compare(R1.getNombre(), R2.getNombre());
        Stream<String> stream = this.parisData.getRecords().stream().map(fields -> fields.getFields()).sorted(top3name.reversed()).filter(fields -> fields.getAnnee() >= 2009 && fields.getAnnee() <= 2016).map(person -> person.getPrenoms()).limit(5);
        return stream.collect(Collectors.toList());
    }

    public List<String> worst10_2009_2016() {

        Comparator<Fields> top3name = (Fields R1, Fields R2) -> Integer.compare(R1.getNombre(), R2.getNombre());
        Stream<String> stream = this.parisData.getRecords().stream().map(fields -> fields.getFields()).sorted(top3name).filter(fields -> fields.getAnnee() >= 2009 && fields.getAnnee() <= 2016).map(person -> person.getPrenoms()).limit(13);
        return stream.collect(Collectors.toList());
    }

    public List<String> worst10girl_2009_2016() {

        Comparator<Fields> top3name = (Fields R1, Fields R2) -> Integer.compare(R1.getNombre(), R2.getNombre());
        Stream<String> stream = this.parisData.getRecords().stream().map(fields -> fields.getFields()).sorted(top3name).filter(fields -> fields.getAnnee() == 2016 && fields.getSexe().equals("F")).map(person -> person.getPrenoms()).limit(5); /* limite : normalement 12 */
        return stream.collect(Collectors.toList());
    }

    public List<String> worst12girl_2009_2016() {

        Comparator<Fields> top3name = (Fields R1, Fields R2) -> Integer.compare(R1.getNombre(), R2.getNombre());
        Stream<String> stream = this.parisData.getRecords().stream().map(fields -> fields.getFields()).sorted(top3name).filter(fields -> fields.getAnnee() == 2016 && fields.getSexe().equals("F")).map(person -> person.getPrenoms()).limit(3); /* limite : normalement 12 */
        return stream.collect(Collectors.toList());
    }

    public List<String> allgender() {

        Stream<String> stream = this.parisData.getRecords().stream().map(fields -> fields.getFields()).sorted().filter(fields -> fields.getSexe().equals("F")).map(person -> person.getPrenoms()).limit(3); /* limite : normalement 12 */
        return stream.collect(Collectors.toList());
    }
}
