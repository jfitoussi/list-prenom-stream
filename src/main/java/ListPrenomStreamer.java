import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        System.out.println("Il y a  : " + listPrenomStreamer.getSize() + " records");
        System.out.println("----------------------");

        System.out.println("La liste des 3 meilleurs prenoms en 2010 : ");
        System.out.println(listPrenomStreamer.top3Names2010());
        System.out.println("----------------------");

        System.out.println("La liste des 3 meilleurs prenoms feminins en 2009 : ");
        System.out.println(listPrenomStreamer.top3NamesGirl2009());
        System.out.println("----------------------");

        System.out.println("La liste des 3 meilleurs prenoms masculins en 2012 : ");
        System.out.println(listPrenomStreamer.top3NamesBoys2012());
        System.out.println("----------------------");

        System.out.println("La liste des 5 meilleurs prenoms entre 2009 et 2016 : ");
        System.out.println(listPrenomStreamer.top5Names2009to2016());
        System.out.println("----------------------");

        System.out.println("La liste des 10 pires prenoms entre 2009 et 2016 : ");
        System.out.println(listPrenomStreamer.top10WorstNames2009to2016());
        System.out.println("----------------------");

        System.out.println("La liste des 12 pires prenoms feminins en 2016 : ");
        System.out.println(listPrenomStreamer.getTop12WorstGirlName2016());
        System.out.println("----------------------");

        System.out.println("La liste des prenoms triés par genre : ");
        System.out.println(listPrenomStreamer.getAllNamesByGender());
        System.out.println("----------------------");

        System.out.println("La liste des prenoms apparus uniquement en 2011 : ");
        System.out.println(listPrenomStreamer.getNamesAppearsIn2011());
        System.out.println("----------------------");

        System.out.println("La liste des prenoms presents entre 2009 et 2016 : ");
        System.out.println(listPrenomStreamer.AllNamesPresentFrom2009To2016());
        System.out.println("----------------------");

        System.out.println("Les listes des 5 meilleures premieres lettres par année : ");
        System.out.println(listPrenomStreamer.listPrenomStreamer.Top5ofTheBestFirstLetterByYear());
        System.out.println("----------------------");

        System.out.println("La liste des 24 meilleures lettres de 2009 à 2016 : ");
        System.out.println(listPrenomStreamer.Top24ofBestLettersFrom2009To2016());
        System.out.println("----------------------");

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

    public List<String> getTop12WorstGirlName2016(){

        return parisData.getRecords().stream()
                .map(Records::getFields)
                .filter(fields -> fields.getAnnee() == 2016)
                .filter(fields -> fields.getSexe().equals("F"))
                .sorted(Comparator.comparingInt(Fields::getNombre))
                .map(Fields::getPrenoms)
                .limit(12)
                .collect(Collectors.toList());

    }


    public Map<String, List<String>> getAllNamesByGender(){

        return parisData.getRecords().stream()
                .map(Records::getFields)
                .collect(Collectors.groupingBy(Fields::getSexe, Collectors.mapping(Fields::getPrenoms, Collectors.toList())));
        //Il faut un distinct sur les prenoms

    }

    public List<String> getNamesAppearsIn2011() {

        List<String> nameOf20042016 = parisData.getRecords().stream()
                .map(Records::getFields)
                .filter(fields -> fields.getAnnee() != 2011)
                .map(Fields::getPrenoms)
                .collect(Collectors.toList());

        return parisData.getRecords().stream()
                .map(Records::getFields)
                .filter(fields -> fields.getAnnee() == 2011)
                .filter(fields -> !nameOf20042016.contains(fields.getPrenoms()))
                .map(Fields::getPrenoms)
                .collect(Collectors.toList());

    }

    public List<String> AllNamesPresentFrom2009To2016() {

        Map<Integer, List<String>> mapByYear2009to2016 = parisData.getRecords().stream()
                .map(Records::getFields)
                .filter(fields -> fields.getAnnee() >= 2009 && fields.getAnnee() <= 2016 && fields.getPrenoms()!= null)
                .collect(Collectors.groupingBy(Fields::getAnnee, Collectors.mapping(Fields::getPrenoms, Collectors.toList()) ));

        int howManyYear = mapByYear2009to2016.size();

        return parisData.getRecords().stream()
                .map(Records::getFields)
                .filter(f -> mapByYear2009to2016.keySet().stream()
                        .filter(integer -> mapByYear2009to2016.get(integer).contains(f.getPrenoms()))
                        .count() == howManyYear)
                .map(Fields::getPrenoms)
                .distinct()
                .collect(Collectors.toList());

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
