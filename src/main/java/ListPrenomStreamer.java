import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import models.Fields;
import models.Pair;
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
        System.out.println(listPrenomStreamer.allNamesPresent2009To2016());
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
                .filter(records -> records.getFields().getAnnee() == 2009 && records.getFields().getSexe().equals("F"))
                .sorted((field1, field2) -> field2.getFields().getNombre() - field1.getFields().getNombre())
                .map(records -> records.getFields().getPrenoms())
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<String> top3BoysName2012() {
        return parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() == 2012 && records.getFields().getSexe().equals("M"))
                .sorted((field1, field2) -> field2.getFields().getNombre() - field1.getFields().getNombre())
                .map(records -> records.getFields().getPrenoms())
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<String> top5Name2009To2016() {
        Map<String,Integer> map = new HashMap<>();
        Function<Records, Pair<String, Integer>> addValue = r -> {
            String k = r.getFields().getPrenoms();
            Integer value = map.getOrDefault(k, 0) + r.getFields().getNombre();
            map.put(k,value);
            return new Pair<>(k,value);
        };
        Comparator<Pair<String, Integer>> comp = (pair1, pair2) -> pair2.last - pair1.last;
        return parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() >= 2009 && records.getFields().getAnnee() <= 2016)
                .map(addValue)
                .sorted(comp)
                .map(pair -> pair.first)
                .distinct()
                .limit(5)
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
                .filter(records -> records.getFields().getAnnee() == 2016 && records.getFields().getSexe().equals("F"))
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

    public List<String> namesOnlyIn2011() {
        Function<Records, Pair<Integer, String>> toTuple = r -> {
            String prenom = r.getFields().getPrenoms();
            Integer annee = r.getFields().getAnnee();
            return new Pair<Integer, String>(annee,prenom) {
                @Override
                public boolean equals(Object obj) {
                    return obj instanceof Pair;
                }
                @Override
                public int hashCode() {
                    return this.last.hashCode();
                }
            };
        };
        return parisData.getRecords().stream()
                .map(toTuple)
                .distinct()
                .filter(integerStringPair -> integerStringPair.first == 2011)
                .map(integerStringPair -> integerStringPair.last)
                .collect(Collectors.toList());
    }

    public List<String> allNamesPresent2009To2016() {
        Map<String, List<Integer>> map = new HashMap<>();
        Function<Records, Pair<String,List<Integer>>>  toMap = r -> {
            Integer annee = r.getFields().getAnnee();
            String nom = r.getFields().getPrenoms();
            List<Integer> list = map.getOrDefault(nom, new ArrayList<>());
            list.add(annee);
            map.put(nom,list);
            return new Pair<>(nom, list);
        };
        return parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() >= 2009 && records.getFields().getAnnee() <= 2016)
                .map(toMap)
                .filter(stringListPair -> stringListPair.last.size() == 8)
                .map(stringListPair -> stringListPair.first)
                .collect(Collectors.toList());
    }

    public Map<Integer, List<Character>> top5BestFirstLetterByYear() {
        List<Integer> annees = new ArrayList<>();
        Function<Records, Pair<Integer,Pair<Character,Integer>>> toTuple = r -> {
            int annee = r.getFields().getAnnee();
            char c = r.getFields().getPrenoms().charAt(0);
            int nb = r.getFields().getNombre();
            Pair<Character,Integer> pair = new Pair<>(c,nb);
            pair.last += r.getFields().getNombre();
            if (!annees.contains(annee)) {
                annees.add(annee);
            }
            return new Pair<>(annee,pair);
        };
        Map<Integer, List<Character>> result = new HashMap<>();
        List<Pair<Integer,Pair<Character,Integer>>> pairs = parisData.getRecords().stream()
                .map(toTuple)
                .sorted((t1, t2) -> t2.last.last - t1.last.last)
                .collect(Collectors.toList());
        for (int annee : annees) {
            pairs.stream()
                    .filter(pairPair -> pairPair.first == annee)
                    .limit(5)
                    .forEach(pairPair -> {
                        int year = pairPair.first;
                        List<Character> list = result.getOrDefault(year, new ArrayList<>());
                        if (list.size() < 5) {
                            list.add(pairPair.last.first);
                        }
                        result.put(year, list);
                    });
        }
        return result;
    }
}
