import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
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

        System.out.println("Size : " + listPrenomStreamer.getSize());
        System.out.println("Top 3 names 2010 : " + listPrenomStreamer.top3Names2010());
        System.out.println("Top 3 names girls 2009 : " + listPrenomStreamer.top3NamesGirl2009());
        System.out.println("Top 3 names boys 2012 : " + listPrenomStreamer.top3NamesBoys2012());
        System.out.println("Top 5 names between 2009 and 2016: " + listPrenomStreamer.top5Names2009to2016());
        System.out.println("Top 10 worst names between 2009 and 2016: " + listPrenomStreamer.top10WorstNames2009to2016());
        System.out.println("All names present from 2009 to 2016 :" + "(Size -> " + listPrenomStreamer.AllNamesPresentFrom2009To2016().size() + ")" + listPrenomStreamer.AllNamesPresentFrom2009To2016());
        System.out.println("Top5OfTheBestFirstLetterByYear :" + "(Size -> " + listPrenomStreamer.Top5ofTheBestFirstLetterByYear().size() + ")" + listPrenomStreamer.Top5ofTheBestFirstLetterByYear());
        System.out.println("Top24ofBestLettersFrom2009To2016 :" + "(Size -> " + listPrenomStreamer.Top24ofBestLettersFrom2009To2016().size() + ")" + listPrenomStreamer.Top24ofBestLettersFrom2009To2016());
    }

    public int getSize() {
        return parisData.getRecords().size();
    }

    public List<String> top3Names2010() {
        return parisData.getRecords().stream().filter(record -> record.getFields().getAnnee() == 2010)
                .sorted((o1, o2) -> o2.getFields().getNombre() - o1.getFields().getNombre())
                .limit(3)
                .map(record -> record.getFields().getPrenoms())
                .collect(Collectors.toList());
    }

    public List<String> top3NamesGirl2009() {
        return parisData.getRecords().stream().filter(record -> record.getFields().getAnnee() == 2009)
                .filter(records -> records.getFields().getSexe().equals("F"))
                .sorted((o1, o2) -> o2.getFields().getNombre() - o1.getFields().getNombre())
                .limit(3)
                .map(record -> record.getFields().getPrenoms())
                .collect(Collectors.toList());
    }

    public List<String> top3NamesBoys2012() {
        return parisData.getRecords().stream().filter(record -> record.getFields().getAnnee() == 2012)
                .filter(records -> records.getFields().getSexe().equals("M"))
                .sorted((o1, o2) -> o2.getFields().getNombre() - o1.getFields().getNombre())
                .limit(3)
                .map(record -> record.getFields().getPrenoms())
                .collect(Collectors.toList());
    }

    public List<String> top5Names2009to2016() {
        Comparator<Fields> topOrder = Comparator.comparingInt(Fields::getNombre);
        Stream<String> top5name2009to2016Stream = parisData.getRecords()
                .stream()
                .map(Records::getFields)
                .sorted(topOrder.reversed())
                .filter(field -> field.getAnnee() >= 2009 && field.getAnnee() <= 2016)
                .map(Fields::getPrenoms).distinct().limit(5);
        return top5name2009to2016Stream.collect(Collectors.toList());
    }

    public List<String> AllNamesPresentFrom2009To2016() {
        List<String> AllNamesPresentFrom2009To2016 = parisData.getRecords().stream()
                .map(records -> records.getFields())
                .filter(fields -> fields.getAnnee() >= 2009 && fields.getAnnee() <= 2016)
                .map(fields -> fields.getPrenoms())
                .distinct()
                .collect(Collectors.toList());
        return AllNamesPresentFrom2009To2016;
    }

    public List<String> top10WorstNames2009to2016() {
        Comparator<Fields> topOrder = Comparator.comparingInt(Fields::getNombre);
        Stream<String> top10WorstName2009to2016Stream = parisData.getRecords()
                .stream()
                .map(Records::getFields)
                .filter(field -> null != field.getPrenoms())
                .sorted(topOrder)
                .filter(field -> field.getAnnee() >= 2009 && field.getAnnee() <= 2016)
                .map(Fields::getPrenoms)
                .distinct()
                .sorted()
                .limit(10);

        return top10WorstName2009to2016Stream.collect(Collectors.toList());
    }

    public Map Top5ofTheBestFirstLetterByYear() {
        Map<Integer, List<Character>> top5ofTheBestFirstLetterByYear = new HashMap<>();
        Map<Integer, Map<Character, Integer>> firstCharOfPrenomsByYear =
                parisData.getRecords().stream()
                        .filter(records -> records.getFields().getPrenoms() != null)
                        .collect(Collectors.groupingBy(r -> r.getFields().getAnnee(), Collectors.groupingBy(p -> p.getFields().getPrenoms().charAt(0), Collectors.summingInt(p -> p.getFields().getNombre()))));
        for (Integer year : firstCharOfPrenomsByYear.keySet()) {
            List<Character> top5 = firstCharOfPrenomsByYear.get(year).entrySet().stream()
                    .sorted((o1, o2) -> o2.getValue() - o1.getValue())
                    .limit(5)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            top5ofTheBestFirstLetterByYear.put(year, top5);
        }
        return top5ofTheBestFirstLetterByYear;
    }

    public List<Character> Top24ofBestLettersFrom2009To2016() {
        Map<Character, Integer> prenomFrom2009To2016 = parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() >= 2009
                        && records.getFields().getAnnee() <= 2016
                        && records.getFields().getPrenoms() != null)
                .collect(Collectors.groupingBy(t -> t.getFields().getPrenoms().charAt(0),
                        Collectors.summingInt(records -> records.getFields().getNombre())));

        return prenomFrom2009To2016.entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .limit(24)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}
