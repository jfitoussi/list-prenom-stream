import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Predicate;
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
        System.out.println(listPrenomStreamer.top3name2010());
        System.out.println(listPrenomStreamer.top3boyname2012());
        System.out.println(listPrenomStreamer.top5name2009to2016());
    }

    public int getSize() {
        return parisData.getRecords().size();
    }

    public List<String> top3name2010() {
        return parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() == 2010)
                .map(records -> records.getFields().getPrenoms())
                .limit(3)
                .collect(Collectors.toList());

    }

    public List<String> top3boyname2012() {
        return parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() == 2012)
                .filter(records -> records.getFields().getSexe().equals("M"))
                .map(records -> records.getFields().getPrenoms())
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<String> top5name2009to2016() {

        List<Fields> data = parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() >= 2009 && records.getFields().getAnnee() <= 2016)
                .map(Records::getFields)
                .collect(Collectors.toList());

        Map<String, Integer> test = data.stream()
                .filter(fields -> fields.getPrenoms() != null)
                .collect(Collectors.groupingBy(Fields::getPrenoms, Collectors.summingInt(Fields::getNombre)));


        return test.entrySet().stream()
                .sorted((o1, o2) -> o2.getValue() - o1.getValue())
                .limit(5)
                .map(stringIntegerEntry -> stringIntegerEntry.getKey())
                .collect(Collectors.toList());
    }

}
