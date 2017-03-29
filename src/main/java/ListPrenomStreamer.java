import com.google.gson.Gson;
import models.ParisData;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        System.out.println(listPrenomStreamer.top3name2010());
        System.out.println(listPrenomStreamer.top3NameGirl2009());
        System.out.println(listPrenomStreamer.top10WorstName2009To2016());
        System.out.println(listPrenomStreamer.top12WorstGirlName2016());
        System.out.println(listPrenomStreamer.allNamesByGender().get("M"));

    }

    public int getSize() {
        return parisData.getRecords().size();
    }

    public List<String> top3name2010() {
        return parisData.getRecords().stream().filter(record -> record.getFields().getAnnee() == 2010)
                                              .sorted((o1, o2) -> o2.getFields().getNombre() - o1.getFields().getNombre())
                                              .limit(3)
                                              .map(record -> record.getFields().getPrenoms())
                                              .collect(Collectors.toList());
    }

    public List<String> top3NameGirl2009() {
        return parisData.getRecords().stream().filter(record -> record.getFields().getAnnee() == 2009)
                .filter(records -> records.getFields().getSexe().equals("F"))
                .sorted((o1, o2) -> o2.getFields().getNombre() - o1.getFields().getNombre())
                .limit(3)
                .map(record -> record.getFields().getPrenoms())
                .collect(Collectors.toList());
    }

    public List<String> top10WorstName2009To2016(){
        return parisData.getRecords().stream()
                                     .filter(records -> (records.getFields().getPrenoms()!=null) &&
                                                        (records.getFields().getAnnee() >= 2009) &&
                                                        (records.getFields().getAnnee() <= 2016))
                                     .collect(Collectors.groupingBy(t -> t.getFields().getPrenoms(), Collectors.summingInt(records -> records.getFields().getNombre())))
                                     .entrySet().stream()
                                                .sorted(Comparator.comparingInt(value -> value.getValue()))
                                                .limit(10).map(stringIntegerEntry -> stringIntegerEntry.getKey())
                                                .collect(Collectors.toList());

    }

    // Top 12 of worst girl name in 2016
    public List<String> top12WorstGirlName2016(){
        return parisData.getRecords().stream()
                                     .filter(records -> records.getFields().getSexe().equals("F") &&
                                                        records.getFields().getAnnee() == 2016)
                                     .sorted(Comparator.comparingInt(value -> value.getFields().getNombre()))
                                     .limit(12)
                                     .map(records -> records.getFields().getPrenoms())
                                     .collect(Collectors.toList());
    }


    // All names by gender
    public Map<String,List<String>> allNamesByGender(){
        return parisData.getRecords().stream()
                                     .collect(
                                             Collectors.groupingBy(t -> t.getFields().getSexe(),
                                                                   Collectors.mapping(t -> t.getFields().getPrenoms(),
                                                                                      Collectors.toList())));

    }

}
