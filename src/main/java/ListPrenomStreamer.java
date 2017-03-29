import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

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
        System.out.println(listPrenomStreamer.top3Name2010());
    }

    public int getSize() {
        return parisData.getRecords().size();
    }

    public List<String> top3Name2010() {
        return parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() == 2010)
                .sorted((field1, field2) -> field2.getFields().getNombre() - field1.getFields().getNombre())
                .map(records -> records.getFields().getPrenoms())
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<String> top3GirlsName2009() {
        return parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() == 2009)
                .filter(records -> records.getFields().getSexe().equals("F"))
                .sorted((field1, field2) -> field2.getFields().getNombre() - field1.getFields().getNombre())
                .map(records -> records.getFields().getPrenoms())
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<String> top3BoysName2012() {
        return parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() == 2012)
                .filter(records -> records.getFields().getSexe().equals("M"))
                .sorted((field1, field2) -> field2.getFields().getNombre() - field1.getFields().getNombre())
                .map(records -> records.getFields().getPrenoms())
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<String> top5Name2009To2016() {
        Map<String, Integer> sum = parisData.getRecords().stream()
                .filter(records -> records.getFields().getPrenoms() != null)
                .filter(records -> records.getFields().getAnnee() >= 2009 && records.getFields().getAnnee() <= 2016)
                .collect(Collectors.groupingBy(t -> t.getFields().getPrenoms(), Collectors.summingInt(records -> records.getFields().getNombre())));

        Map<String, Integer> result = new LinkedHashMap<>();
        sum.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));

        return result.entrySet().stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<String> top10Worst2009To2016() {
        return parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() >= 2009)
                .filter(records -> records.getFields().getAnnee() <= 2016)
                .sorted(Comparator.comparingInt(field -> field.getFields().getNombre()))
                .map(records -> records.getFields().getPrenoms())
                .distinct()
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<String> top12WorstGirls2016() {
        return parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() == 2016)
                .filter(records -> records.getFields().getSexe().equals("F"))
                .sorted(Comparator.comparingInt(field -> field.getFields().getNombre()))
                .map(records -> records.getFields().getPrenoms())
                .limit(12)
                .collect(Collectors.toList());
    }

    public Map<String, List<String>> allNamesByGender() {
        return parisData.getRecords().stream()
                .map(Records::getFields)
                .collect(Collectors.groupingBy(
                        Fields::getSexe,
                        Collectors.mapping(Fields::getPrenoms, Collectors.toList())
                ));
    }
}
