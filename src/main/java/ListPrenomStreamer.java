import com.google.gson.Gson;
import models.Fields;
import models.ParisData;
import models.Records;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
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
        System.out.println("TOTAL SIZE : " + listPrenomStreamer.getSize());
        System.out.println("TOP 3 NAME 2010 : " + listPrenomStreamer.top3name2010());
        System.out.println("TOP 3 NAME BOY 2012 : " + listPrenomStreamer.top3NameBoy2012());
        System.out.println("TOP 3 NAME GIRL 2009 : " + listPrenomStreamer.top3NameGirl2009());
        System.out.println("TOP 5 NAME 2009 to 2016 : " + listPrenomStreamer.top5name2009to2016());
        System.out.println("TOP 10 WORST NAME 2009 to 2016 : " + listPrenomStreamer.top10WorstName2009To2016());
        System.out.println("TOP 12 WORST NAME GIRL 2016 : " + listPrenomStreamer.top12WorstGirlName2016());
        System.out.println("ALL BY GENDER : " + listPrenomStreamer.allNamesByGender().get("F"));
        System.out.println("NAME JUST IN 2011 : " + listPrenomStreamer.nameJust2011());
        System.out.println("TOP 5 BEST LETTER BY YEAR : " + listPrenomStreamer.top5BestFirstLetter());
        System.out.println("TOP 24 BEST LETTER 2009 to 2016 : " + listPrenomStreamer.top24letter2009to2016());
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

    public List<String> top3NameBoy2012() {
        return parisData.getRecords().stream().filter(record -> record.getFields().getAnnee() == 2012)
                .filter(records -> records.getFields().getSexe().equals("M"))
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

    public List<String> top5name2009to2016() {

        List<Fields> data = parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() >= 2009 && records.getFields().getAnnee() <= 2016)
                .map(Records::getFields)
                .collect(Collectors.toList());

        Map<String, Integer> groupedMap = data.stream()
                .filter(fields -> fields.getPrenoms() != null)
                .collect(Collectors.groupingBy(Fields::getPrenoms, Collectors.summingInt(Fields::getNombre)));


        return groupedMap.entrySet().stream()
                .sorted((o1, o2) -> o2.getValue() - o1.getValue())
                .limit(5)
                .map(stringIntegerEntry -> stringIntegerEntry.getKey())
                .collect(Collectors.toList());
    }

    public List<String> top10WorstName2009To2016(){
        return parisData.getRecords().stream()
                                     .filter(records -> (records.getFields().getPrenoms()!=null) &&
                                                        (records.getFields().getAnnee() >= 2009) &&
                                                        (records.getFields().getAnnee() <= 2016))
                                     .collect(Collectors.groupingBy(t -> t.getFields().getPrenoms(), Collectors.summingInt(records -> records.getFields().getNombre())))
                                     .entrySet().stream()
                                                .sorted(Map.Entry.comparingByValue())
                                                .limit(10).map(Map.Entry::getKey)
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
    public Map<String,Set<String>> allNamesByGender(){
        return parisData.getRecords().stream()
                                     .collect(
                                             Collectors.groupingBy(t -> t.getFields().getSexe(),
                                                                   Collectors.mapping(t -> t.getFields().getPrenoms(),
                                                                                      Collectors.toSet())));

    }

    public List<String> nameJust2011() {
        List<String> data = parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() == 2011)
                .map(records -> records.getFields().getPrenoms())
                .collect(Collectors.toList());

        List<String> fullNames = parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() != 2011)
                .map(records -> records.getFields().getPrenoms())
                .distinct()
                .collect(Collectors.toList());

        return data.stream().filter(s -> !fullNames.contains(s)).collect(Collectors.toList());

    }

    public Map<Integer, List<String>> top5BestFirstLetter(){
        Map<Integer, List<Records>> recordByYear =
                parisData.getRecords().stream()
                                      .filter(records -> records.getFields().getPrenoms() != null)
                                      .collect(Collectors.groupingBy(t -> t.getFields().getAnnee()));
        Map<Integer,List<String>> res = new HashMap<>();
        for (Integer oneYear : recordByYear.keySet()) {
            List<String> fiveBest = recordByYear.get(oneYear).stream().collect(Collectors.groupingBy(t -> t.getFields().getPrenoms().substring(0, 1),
                                                                                   Collectors.summingInt(t -> t.getFields().getNombre())))
                                                                 .entrySet().stream()
                                                                            .sorted((o1, o2) -> o2.getValue() - o1.getValue())
                                                                            .limit(5)
                                                                            .map(Map.Entry::getKey)
                                                                            .collect(Collectors.toList());
            res.put(oneYear,fiveBest);
        }

        return res;
    }

    public List<String> top24letter2009to2016() {
        List<Fields> data = parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() >= 2009 && records.getFields().getAnnee() <= 2016)
                .map(Records::getFields)
                .collect(Collectors.toList());

        Map<String, Integer> groupedMap = data.stream()
                .filter(fields -> fields.getPrenoms() != null)
                .collect(Collectors.groupingBy(fields -> fields.getPrenoms().substring(0, 1), Collectors.summingInt(Fields::getNombre)));

        return groupedMap.entrySet().stream()
                .sorted((o1, o2) -> o2.getValue() - o1.getValue())
                .limit(24)
                .map(stringIntegerEntry -> stringIntegerEntry.getKey())
                .collect(Collectors.toList());
    }
}
