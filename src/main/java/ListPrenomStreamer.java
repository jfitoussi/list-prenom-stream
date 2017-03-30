import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;

import models.ParisData;
import models.Records;

public class ListPrenomStreamer {

    private static final Gson GSON = new Gson();
    private ParisData parisData;

    public ListPrenomStreamer(String filename) {
        InputStreamReader inputStreamReader = new InputStreamReader(getClass().getResourceAsStream(filename));
        this.parisData = GSON.fromJson(inputStreamReader, ParisData.class);
    }

    public ListPrenomStreamer(String res_api, int toto){
        this.parisData = GSON.fromJson(res_api, ParisData.class);
    }

    public static void main(String[] args) throws IOException {
        try {
            String res_api = sendGet();
            ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(res_api,0);
            System.out.println(res_api);
            System.out.print(listPrenomStreamer.parisData.getRecords().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");
        //System.out.println(listPrenomStreamer.getSize());
        //System.out.println(listPrenomStreamer.top3name2008());
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");
        System.out.println(listPrenomStreamer.getSize());
        System.out.println(listPrenomStreamer.top3name2008());

        ListPrenomStreamer listPrenomStreamer2 = new ListPrenomStreamer("liste_des_prenoms_2009_2016.json");
        System.out.println(" \nAll names from 2009 to 2016 : ");
        System.out.println(listPrenomStreamer2.allnamepresentfrom2009to2016());
    }

    public int getSize() {
        return parisData.getRecords().size();
    }

    public List<String> top3name2008() {

        return null;
    }

    public List<Records> top3name2010() {

        Comparator<Records> comparator = (Records r1, Records r2)-> {
            return -((Integer)r1.getFields().getNombre()).compareTo(r2.getFields().getNombre());
        };
        Stream<Records> stream2010 = parisData.getRecords().stream().filter(records -> records.getFields().getAnnee() == 2010);

        stream2010 = stream2010.sorted(comparator).limit(3);
        return stream2010.collect(Collectors.toList());
    }

    public List<Records> top3GirlName2009() {
        Comparator<Records> comparator = (Records r1, Records r2)-> {
            return -((Integer)r1.getFields().getNombre()).compareTo(r2.getFields().getNombre());
        };
        Stream<Records> stream2009 = parisData.getRecords().stream().filter(records -> (records.getFields().getAnnee() == 2009 && records.getFields().getSexe().equals("F")));

        stream2009 = stream2009.sorted(comparator).limit(3);
        return stream2009.collect(Collectors.toList());
    }

    public List<Records> top3MenName2012() {
        Comparator<Records> comparator = (Records r1, Records r2)-> {
            return -((Integer)r1.getFields().getNombre()).compareTo(r2.getFields().getNombre());
        };
        Stream<Records> stream = parisData.getRecords().stream().filter(records -> (records.getFields().getAnnee() == 2012 && records.getFields().getSexe().equals("M")));

        stream = stream.sorted(comparator).limit(3);
        return stream.collect(Collectors.toList());

    }

    public List<Records> top5Name2009_2016() {
        Comparator<Records> comparator = (Records r1, Records r2)-> {
            return -((Integer)r1.getFields().getNombre()).compareTo(r2.getFields().getNombre());
        };

        Stream<Records> stream2012 = parisData.getRecords().stream().filter(records -> (records.getFields().getAnnee() >= 2009 && records.getFields().getAnnee() <= 2016));

        stream2012 = stream2012.sorted(comparator).limit(5);
        return stream2012.collect(Collectors.toList());
    }

    public List<Records> worst10Name2009_2016() {
        Comparator<Records> comparator = (Records r1, Records r2)-> {
            return ((Integer)r1.getFields().getNombre()).compareTo(r2.getFields().getNombre());
        };

        Stream<Records> stream = parisData.getRecords().stream().filter(records -> (records.getFields().getAnnee() >= 2009 && records.getFields().getAnnee() <= 2016));

        stream = stream.sorted(comparator).limit(10);
        return stream.collect(Collectors.toList());
    }

    public List<Records> worst10GilName2016() {
        Comparator<Records> comparator = (Records r1, Records r2)-> {
            return ((Integer)r1.getFields().getNombre()).compareTo(r2.getFields().getNombre());
        };

        Stream<Records> stream = parisData.getRecords().stream().filter(records -> (records.getFields().getAnnee() == 2016 && records.getFields().getSexe().equals("F")));

        stream = stream.sorted(comparator).limit(12);
        return stream.collect(Collectors.toList());
    }

    public List<String> allnamepresentfrom2009to2016() {

        List<String> nameFrom2009to2016 = parisData.getRecords().stream()
                .map(records -> records.getFields())
                .filter(fields -> fields.getAnnee() >= 2009 && fields.getAnnee() <= 2016)
                .map(fields -> fields.getPrenoms())
                .distinct().collect(Collectors.toList());
        return nameFrom2009to2016;

    }
    //public List<String> namePresent2009and2016(){

      //  Stream<Records> stream = parisData.getRecords().stream().filter(records -> (records.getFields().getAnnee()>=2009 && records.getFields().getAnnee()<=2016));
       // List<String> stream1=stream.map(records -> records.getFields().getPrenoms()).distinct().collect(Collectors.toList());
        //return stream1;
    //}

    public  List<Records> worst12GirlName2016() {
        Comparator<Records> comparator = (Records r1, Records r2)-> {
            return ((Integer)r1.getFields().getNombre()).compareTo(r2.getFields().getNombre());
        };
        Stream<Records> stream = parisData.getRecords().stream().filter(records -> (records.getFields().getAnnee() == 2016 && records.getFields().getSexe().equals("F")));
        stream = stream.sorted(comparator).limit(12);
        return stream.collect(Collectors.toList());
    }

    public static String sendGet() throws Exception {
        String url = "https://opendata.paris.fr/api/records/1.0/search/?dataset=liste_des_prenoms_2004_a_2012&facet=prenoms&facet=sexe&facet=annee";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine = "";
        StringBuffer response = new StringBuffer();
        System.out.print(con.getResponseCode());
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
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

    public ParisData getParisData() {
        return parisData;
    }
}
