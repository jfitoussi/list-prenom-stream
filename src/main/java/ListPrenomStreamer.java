import java.io.IOException;
import java.io.InputStreamReader;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;

import com.sun.org.apache.regexp.internal.RE;
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
        System.out.println(listPrenomStreamer.top3name2008());

    }

    public int getSize() {
        return parisData.getRecords().size();
    }

    public List<String> top3name2008() {

        return null;
    }

    public List<String> top3name2010() {

        Comparator<Records> comparator = (Records r1, Records r2)-> {
            return -((Integer)r1.getFields().getNombre()).compareTo(r2.getFields().getNombre());
        };

        Function<Records, String> getNameFunction = records -> {return records.getFields().getPrenoms();};

        Stream<Records> stream2010 = parisData.getRecords().stream().filter(records -> records.getFields().getAnnee() == 2010);

        stream2010 = stream2010.sorted(comparator).limit(3);
       
        List<Records> recordsList = stream2010.collect(Collectors.toList());
        List<String> nameList = recordsList.stream().map(getNameFunction).collect(Collectors.toList());
        return nameList;
    }

    public List<String> top3GirlName2009() {
        Comparator<Records> comparator = (Records r1, Records r2)-> {
            return -((Integer)r1.getFields().getNombre()).compareTo(r2.getFields().getNombre());
        };

        Function<Records, String> getNameFunction = records -> {return records.getFields().getPrenoms();};

        Stream<Records> stream2009 = parisData.getRecords().stream().filter(records -> (records.getFields().getAnnee() == 2009 && records.getFields().getSexe().equals("F")));

        stream2009 = stream2009.sorted(comparator).limit(3);
        
        List<Records> recordsList = stream2009.collect(Collectors.toList());
        List<String> nameList = recordsList.stream().map(getNameFunction).collect(Collectors.toList());
        return nameList;
    }

    public List<String> top3MenName2012() {
        Comparator<Records> comparator = (Records r1, Records r2)-> {
            return -((Integer)r1.getFields().getNombre()).compareTo(r2.getFields().getNombre());
        };

        Function<Records, String> getNameFunction = records -> {return records.getFields().getPrenoms();};

        Stream<Records> stream = parisData.getRecords().stream().filter(records -> (records.getFields().getAnnee() == 2012 && records.getFields().getSexe().equals("M")));

        stream = stream.sorted(comparator).limit(3);
        
        List<Records> recordsList = stream.collect(Collectors.toList());
        List<String> nameList = recordsList.stream().map(getNameFunction).collect(Collectors.toList());
        return nameList;

    }

    public List<String> top5Name2009_2016() {
        Comparator<Records> comparator = (Records r1, Records r2)-> {
            return -((Integer)r1.getFields().getNombre()).compareTo(r2.getFields().getNombre());
        };

        Function<Records, String> getNameFunction = records -> {return records.getFields().getPrenoms();};

        Stream<Records> stream2012 = parisData.getRecords().stream().filter(records -> (records.getFields().getAnnee() >= 2009 && records.getFields().getAnnee() <= 2016));

        stream2012 = stream2012.sorted(comparator).limit(5);
        
        List<Records> recordsList = stream2012.collect(Collectors.toList());
        List<String> nameList = recordsList.stream().map(getNameFunction).collect(Collectors.toList());
        return nameList;
    }

    public List<String> worst10Name2009_2016() {
        Comparator<Records> comparator = (Records r1, Records r2)-> {
            return ((Integer)r1.getFields().getNombre()).compareTo(r2.getFields().getNombre());
        };
        Function<Records, String> getNameFunction = records -> {return records.getFields().getPrenoms();};

        Stream<Records> stream = parisData.getRecords().stream().filter(records -> (records.getFields().getAnnee() >= 2009 && records.getFields().getAnnee() <= 2016));

        stream = stream.sorted(comparator).limit(10);

        List<Records> recordsList = stream.collect(Collectors.toList());
        List<String> nameList = recordsList.stream().map(getNameFunction).collect(Collectors.toList());
        return nameList;

    }

    public List<String> worst10GilName2016() {
        Comparator<Records> comparator = (Records r1, Records r2)-> {
            return ((Integer)r1.getFields().getNombre()).compareTo(r2.getFields().getNombre());
        };
        Function<Records, String> getNameFunction = records -> {return records.getFields().getPrenoms();};
        
        Stream<Records> stream = parisData.getRecords().stream().filter(records -> (records.getFields().getAnnee() == 2016 && records.getFields().getSexe().equals("F")));

        stream = stream.sorted(comparator).limit(12);
        List<Records> recordsList = stream.collect(Collectors.toList());
        List<String> nameList = recordsList.stream().map(getNameFunction).collect(Collectors.toList());
        return nameList;
    }

    public Map<Integer, Long> top5ofBestFirstLettersByYear(){

        List<Records> recordsList = parisData.getRecords();
        Function<Records, String> getNameFunction = records -> {return records.getFields().getPrenoms();};
        Comparator<Records> comparatorPrenom = (Records r1, Records r2)-> {
                return ((Character)r1.getFields().getPrenoms().charAt(0)).compareTo(r2.getFields().getPrenoms().charAt(0));
        };


        Map<Integer, List<Character>> result = new HashMap<>();



        Stream<Records> streamRecords = parisData.getRecords().stream();
        Stream<Records> streamRecords2 = parisData.getRecords().stream();
//        System.out.println(streamRecords.collect(Collectors.groupingBy(Records::getAnnee, Collectors.mapping(Records::getFields, Collectors.toList()))));
        // ok System.out.println(streamRecords.collect(Collectors.groupingBy(Records::getFirstLetter, Collectors.mapping(Records::getFields, Collectors.summingInt(value -> value.getNombre())))));
        System.out.println(streamRecords.collect(Collectors.groupingBy(Records::getFirstLetter, Collectors.mapping(Records::getFields, Collectors.summingInt(value -> value.getNombre())))));
        // ok 2 System.out.println(streamRecords2.collect(Collectors.groupingBy(Records::getAnnee, Collectors.mapping(Records::getFields, Collectors.toList()))));
        streamRecords2.collect(Collectors.groupingBy(Records::getAnnee, Collectors.mapping(Records::getFields, Collectors.toList()))).entrySet().stream().
                sorted();
        return null;
        /*
        Stream<Records> prenoms = recordsList.stream().sorted(comparatorPrenom);


        Map<Integer, List<String>> ageDistribution =
                recordsList.stream().collect(Collectors.groupingBy( , listPrenom));
        //Map<Integer, Long> ageDistribution = recordsList.stream().sorted(comparator).collect(Collectors.groupingBy(Records::getAnnee, Collectors.counting()));



        return ageDistribution;
        */
    }
}
