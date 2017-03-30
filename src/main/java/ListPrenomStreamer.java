import com.google.gson.Gson;
import models.ParisData;
import models.Records;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class ListPrenomStreamer {

    private static final Gson GSON = new Gson();
    private ParisData parisData;

    public ListPrenomStreamer(String filename) {
        InputStreamReader inputStreamReader = new InputStreamReader(getClass().getResourceAsStream(filename));
        this.parisData = GSON.fromJson(inputStreamReader, ParisData.class);
    }

    public static void main(String[] args) throws IOException {
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");

        System.out.println("Number of records : ");
        System.out.println(listPrenomStreamer.getSize());

        System.out.println(" \nTop 3 of best name in 2010 : ");
        System.out.println(listPrenomStreamer.top3name2010());

        System.out.println(" \nTop 3 of best girl name in 2009 : ");
        System.out.println(listPrenomStreamer.top3girlname2009());

        System.out.println(" \nTop 3 of best boy name in 2012 : ");
        System.out.println(listPrenomStreamer.top3boyname2012());

        System.out.println(" \nTop 5 of best name from 2009 to 2016 : ");
        System.out.println(listPrenomStreamer.top5bestname2009_2016());

        System.out.println(" \nTop 10 of worst name from 2009 to 2016 : ");
        System.out.println(listPrenomStreamer.top10worstname2009_2016());

        System.out.println(" \nTop 12 of worst girl name in 2016 : ");
        System.out.println(listPrenomStreamer.top12WorstGirlName2016());

        System.out.println(" \nAll names by gender : ");
        System.out.println(listPrenomStreamer.allnamebygender());

        System.out.println(" \nName appear just in 2011 : ");
        System.out.println(listPrenomStreamer.nameappearjustin20112016());

        System.out.println(" \nAll names present from 2009 to 2016 : ");
        System.out.println(listPrenomStreamer.allnamepresentfrom2009to2016());

        System.out.println(" \nTop 5 of the best first letter by year : ");
        System.out.println(listPrenomStreamer.top5_of_best_first_letter_by_year());

        System.out.println(" \nTop 24 of best letters from 2009 to 2016 : ");
        System.out.println(listPrenomStreamer.top24_best_letters_from_2009_to_2016());
    }

    public int getSize() {
        return parisData.getRecords().size();
    }

    public List<String> top3name2010() {
        return parisData.getRecords().stream().filter(records -> records.getFields().getAnnee() == 2010).sorted((obj1, obj2) -> obj2.getFields().getNombre() - obj1.getFields().getNombre()).limit(3).map(records -> records.getFields().getPrenoms()).collect(Collectors.toList());
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
                .filter(records -> records.getFields().getPrenoms() != null)
                .filter(records -> records.getFields().getAnnee() >= 2009 && records.getFields().getAnnee() <= 2016)
                .collect(Collectors.groupingBy(t -> t.getFields().getPrenoms(), Collectors.summingInt(records -> records.getFields().getNombre())));

        return sum.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<String> top10worstname2009_2016() {
        Map<String, Integer> sum = parisData.getRecords().stream()
                .filter(records -> records.getFields().getPrenoms() != null)
                .filter(records -> records.getFields().getAnnee() >= 2009 && records.getFields().getAnnee() <= 2016)
                .collect(Collectors.groupingBy(t -> t.getFields().getPrenoms(), Collectors.summingInt(records -> records.getFields().getNombre())));

        return sum.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue())
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }

    public Map<String, Set<String>> allnamebygender() {

        return parisData.getRecords().stream()
                .collect(Collectors.groupingBy(records -> records.getFields().getSexe(), Collectors.mapping(records
                        -> records.getFields().getPrenoms(), Collectors.toSet())));

    }


    public List<String> nameappearjustin20112016() {

        List<String> nameIn2011 = parisData.getRecords().stream().map(records -> records.getFields()).filter(records -> records.getAnnee() == 2011).map(records -> records.getPrenoms()).distinct().collect(Collectors.toList());
        List<String> nameNotIn2011 = parisData.getRecords().stream().map(records -> records.getFields()).filter(records -> records.getAnnee() != 2011).map(records -> records.getPrenoms()).distinct().collect(Collectors.toList());
        List<String> name2011 = nameIn2011.stream().filter(records -> !nameNotIn2011.contains(records)).collect(Collectors.toList());
        return name2011;
    }

    public List<String> allnamepresentfrom2009to2016() {

        return null;

    }


    public  Map<Integer, Map<Character, Integer>> top5_of_best_first_letter_by_year() {

        return parisData.getRecords().stream()
                .filter(records -> records.getFields().getPrenoms()!=null)
                .collect(Collectors.groupingBy(t -> t.getFields().getAnnee(), Collectors.groupingBy(records -> records.getFields().getPrenoms().charAt(0), Collectors.summingInt(records -> records.getFields().getNombre()))));


    }

    public List<String> top24_best_letters_from_2009_to_2016() {

        return null;

    }

    public List<String> top12WorstGirlName2016() {
        return parisData.getRecords().stream().filter(records -> records.getFields().getSexe().equals("F"))
                .filter(records -> records.getFields().getAnnee() == 2016)
                .sorted((obj1, obj2) -> obj1.getFields().getNombre() - obj2.getFields().getNombre())
                .limit(12)
                .map(records -> records.getFields().getPrenoms())
                .collect(Collectors.toList());
    }

}
