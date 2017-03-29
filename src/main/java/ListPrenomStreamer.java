import com.google.gson.Gson;
import models.ParisData;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
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
        System.out.println(listPrenomStreamer.top3girlname2009());
        System.out.println(listPrenomStreamer.top3boyname2012());
        System.out.println(listPrenomStreamer.top5bestname2009_2016());
        System.out.println(listPrenomStreamer.top10worstname2009_2016());
    }

    public int getSize() {
        return parisData.getRecords().size();
    }

    public List<String> top3name2010() {
        return parisData.getRecords().stream().filter(records -> records.getFields().getAnnee()==2010).sorted((obj1,obj2) -> obj2.getFields().getNombre() - obj1.getFields().getNombre()).limit(3).map(records -> records.getFields().getPrenoms()).collect(Collectors.toList());
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
    public List<String> top5bestname2009_2016() {
        Map<String, Integer> sum = parisData.getRecords().stream()
                .filter(records -> records.getFields().getPrenoms()!=null)
                .filter(records -> records.getFields().getAnnee() >= 2009 &&  records.getFields().getAnnee() <= 2016 )
                .collect(Collectors.groupingBy(t -> t.getFields().getPrenoms(), Collectors.summingInt(records -> records.getFields().getNombre())));

        return sum.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<String> top10worstname2009_2016() {
        Map<String, Integer> sum = parisData.getRecords().stream()
                .filter(records -> records.getFields().getPrenoms()!=null)
                .filter(records -> records.getFields().getAnnee() >= 2009 &&  records.getFields().getAnnee() <= 2016 )
                .collect(Collectors.groupingBy(t -> t.getFields().getPrenoms(), Collectors.summingInt(records -> records.getFields().getNombre())));

        return sum.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue())
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }

}
