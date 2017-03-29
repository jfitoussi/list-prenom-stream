import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;

import com.google.gson.Gson;

import models.ParisData;

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
        System.out.println(listPrenomStreamer.top3girlname2009());
        System.out.println(listPrenomStreamer.top3boyname2012());
        System.out.println(listPrenomStreamer.top5bestname());
    }

    public int getSize() {
        return parisData.getRecords().size();
    }

    public List<String> top3name2010() {
        return null;
    }
    public List<String> top3girlname2009() {
        return parisData.getRecords().stream().filter(records -> records.getFields().getSexe().equals("F"))
                .filter(records -> records.getFields().getAnnee() == 2009)
                .sorted((obj1, obj2) -> obj2.getFields().getNombre() - obj1.getFields().getNombre())
                .limit(3)
                .map(records -> records.getFields().getPrenoms())
                .collect(Collectors.toList());
    }
    public List<String> top3boyname2012() {
        return parisData.getRecords().stream().filter(records -> records.getFields().getSexe().equals("M"))
                .filter(records -> records.getFields().getAnnee() == 2012)
                .sorted((obj1, obj2) -> obj2.getFields().getNombre() - obj1.getFields().getNombre())
                .limit(3)
                .map(records -> records.getFields().getPrenoms())
                .collect(Collectors.toList());
    }
    public List<String> top5bestname() {
        List<Integer> annees = Arrays.asList(2010, 2011, 2012, 2013, 2014, 2015, 2016);
        return parisData.getRecords().stream().filter(records -> annees.contains(records.getFields().getAnnee()))
                .sorted((obj1, obj2) -> obj2.getFields().getNombre() - obj1.getFields().getNombre())
                .limit(10)
                .map(records -> records.getFields().getPrenoms())
                .distinct()
                .collect(Collectors.toList());
    }

}
