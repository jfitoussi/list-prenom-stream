import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import com.sun.javaws.exceptions.ErrorCodeResponseException;
import models.Fields;
import models.ParisData;
import models.Records;

public class ListPrenomStreamer {

    private static final Gson GSON = new Gson();
    private ParisData parisData;

    // Constructor to read from file
    public ListPrenomStreamer(String filename) {
        InputStreamReader inputStreamReader = new InputStreamReader(getClass().getResourceAsStream(filename));
        this.parisData = GSON.fromJson(inputStreamReader, ParisData.class);
    }

    // Constructor to load from the API with no specified of number data
    public ListPrenomStreamer(String url, String dataset){
        this(url,dataset,10000);
    }

    // Constructor where all parameters are passed
    public ListPrenomStreamer(String url, String dataset,int Nb_Data){
        String fullUrl = url + "?dataset=" + dataset + "&rows=" + Nb_Data ;
        try {
            URL urlObj = new URL(fullUrl);
            try {
                HttpURLConnection connection = ((HttpURLConnection) urlObj.openConnection());
                connection.setRequestMethod("GET");

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    BufferedReader receiveData = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    this.parisData = GSON.fromJson(receiveData, ParisData.class);

                }else{
                    System.err.println("Error in the passed parameter");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        // Read from file
//        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");
        // Read from API
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("https://opendata.paris.fr/api/records/1.0/search/","liste_des_prenoms_2004_a_2012");

        // Print of differents results
        System.out.println("TOTAL SIZE : " + listPrenomStreamer.getSize());
        System.out.println("TOP 3 NAME 2010 : " + listPrenomStreamer.top3name2010());
        System.out.println("TOP 3 NAME BOY 2012 : " + listPrenomStreamer.top3NameBoy2012());
        System.out.println("TOP 3 NAME GIRL 2009 : " + listPrenomStreamer.top3NameGirl2009());
        System.out.println("TOP 5 NAME 2009 to 2016 : " + listPrenomStreamer.top5name2009to2016());
        System.out.println("TOP 10 WORST NAME 2009 to 2016 : " + listPrenomStreamer.top10WorstName2009To2016());
        System.out.println("TOP 12 WORST NAME GIRL 2016 : " + listPrenomStreamer.top12WorstGirlName2016());
        System.out.println("ALL BY GENDER : " + listPrenomStreamer.allNamesByGender().get("F"));
        System.out.println("NAME JUST IN 2011 : " + listPrenomStreamer.nameJust2011());
        System.out.println("NAME PRESENT 2009 to 2016 : " + listPrenomStreamer.namesIntervall());
        System.out.println("TOP 5 BEST LETTER BY YEAR : " + listPrenomStreamer.top5BestFirstLetter());
        System.out.println("TOP 24 BEST LETTER 2009 to 2016 : " + listPrenomStreamer.top24letter2009to2016());

    }

    public int getSize() {
        return parisData.getRecords().size();
    }

    public List<String> top3name2010() {
        return parisData.getRecords().stream().filter(record -> record.getFields().getAnnee() == 2010)
                                              .sorted((o1, o2) -> o2.getFields().getNombre() - o1.getFields().getNombre())
                                              .limit(3)
                                              .map(record -> record.getFields().getPrenoms())
                                              .collect(Collectors.toList());
    }

    public List<String> top3NameBoy2012() {
        return parisData.getRecords().stream().filter(record -> record.getFields().getAnnee() == 2012)
                .filter(records -> records.getFields().getSexe().equals("M"))
                .sorted((o1, o2) -> o2.getFields().getNombre() - o1.getFields().getNombre())
                .limit(3)
                .map(record -> record.getFields().getPrenoms())
                .collect(Collectors.toList());
    }

    public List<String> top3NameGirl2009() {
        return parisData.getRecords().stream().filter(record -> record.getFields().getAnnee() == 2009)
                .filter(records -> records.getFields().getSexe().equals("F"))
                .sorted((o1, o2) -> o2.getFields().getNombre() - o1.getFields().getNombre())
                .limit(3)
                .map(record -> record.getFields().getPrenoms())
                .collect(Collectors.toList());
    }
    
	/**
	 * Alexandre : All names present from 2009 to 2016
	 */
    public List<String> namesIntervall() {
    	
    	//list declaration
		List<Records> recordsList = this.parisData.getRecords();
		
		//predicat
		Predicate<Records> namesIntervall20092016 = records -> records.getFields().getAnnee() >= 2009 && records.getFields().getAnnee() <= 2016 ;
		
		Map<Integer, List<String>> recordsByYear = 
		recordsList.stream()
		           .filter(namesIntervall20092016)
		           .collect(Collectors.groupingBy(t -> t.getFields().getAnnee(),
		        		                          Collectors.mapping(t1 -> t1.getFields().getPrenoms(),
		        		                        		             Collectors.toList())));
	
		List<Integer> years = new ArrayList<>(recordsByYear.keySet()); 
		List<String> res = new ArrayList<>(recordsByYear.get(years.get(0)));
		
		for (Integer year : years) {
			res.retainAll(recordsByYear.get(year));
		}
		
		
		return res;	
    }

    public List<String> top5name2009to2016() {

        List<Fields> data = parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() >= 2009 && records.getFields().getAnnee() <= 2016)
                .map(Records::getFields)
                .collect(Collectors.toList());

        Map<String, Integer> groupedMap = data.stream()
                .filter(fields -> fields.getPrenoms() != null)
                .collect(Collectors.groupingBy(Fields::getPrenoms, Collectors.summingInt(Fields::getNombre)));


        return groupedMap.entrySet().stream()
                .sorted((o1, o2) -> o2.getValue() - o1.getValue())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<String> top10WorstName2009To2016(){
        return parisData.getRecords().stream()
                                     .filter(records -> (records.getFields().getPrenoms()!=null) &&
                                                        (records.getFields().getAnnee() >= 2009) &&
                                                        (records.getFields().getAnnee() <= 2016))
                                     .collect(Collectors.groupingBy(t -> t.getFields().getPrenoms(), Collectors.summingInt(records -> records.getFields().getNombre())))
                                     .entrySet().stream()
                                                .sorted(Map.Entry.comparingByValue())
                                                .limit(10).map(Map.Entry::getKey)
                                                .collect(Collectors.toList());

    }

    // Top 12 of worst girl name in 2016
    public List<String> top12WorstGirlName2016(){
        return parisData.getRecords().stream()
                                     .filter(records -> records.getFields().getSexe().equals("F") &&
                                                        records.getFields().getAnnee() == 2016)
                                     .sorted(Comparator.comparingInt(value -> value.getFields().getNombre()))
                                     .limit(12)
                                     .map(records -> records.getFields().getPrenoms())
                                     .collect(Collectors.toList());
    }


    // All names by gender
    public Map<String,Set<String>> allNamesByGender(){
        return parisData.getRecords().stream()
                                     .collect(
                                             Collectors.groupingBy(t -> t.getFields().getSexe(),
                                                                   Collectors.mapping(t -> t.getFields().getPrenoms(),
                                                                                      Collectors.toSet())));

    }

    public List<String> nameJust2011() {
        List<String> data = parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() == 2011)
                .map(records -> records.getFields().getPrenoms())
                .collect(Collectors.toList());

        List<String> fullNames = parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() != 2011)
                .map(records -> records.getFields().getPrenoms())
                .distinct()
                .collect(Collectors.toList());

        return data.stream().filter(s -> !fullNames.contains(s)).collect(Collectors.toList());

    }

    public Map<Integer, List<String>> top5BestFirstLetter(){
        Map<Integer, Map<String,Integer>> recordByYear =
                parisData.getRecords().stream()
                                      .filter(records -> records.getFields().getPrenoms() != null)
                                      .collect(Collectors.groupingBy(t -> t.getFields().getAnnee(),
                                               Collectors.groupingBy(t -> t.getFields().getPrenoms().substring(0, 1),
                                                                     Collectors.summingInt(t -> t.getFields().getNombre()))));
        Map<Integer,List<String>> res = new HashMap<>();
        for (Integer oneYear : recordByYear.keySet()) {
            List<String> fiveBest = recordByYear.get(oneYear).entrySet().stream()
                                                                        .sorted((o1, o2) -> o2.getValue() - o1.getValue())
                                                                        .limit(5)
                                                                        .map(Map.Entry::getKey)
                                                                        .collect(Collectors.toList());
            res.put(oneYear,fiveBest);
        }

        return res;
    }

    public List<String> top24letter2009to2016() {
        List<Fields> data = parisData.getRecords().stream()
                .filter(records -> records.getFields().getAnnee() >= 2009 && records.getFields().getAnnee() <= 2016)
                .map(Records::getFields)
                .collect(Collectors.toList());

        Map<String, Integer> groupedMap = data.stream()
                .filter(fields -> fields.getPrenoms() != null)
                .collect(Collectors.groupingBy(fields -> fields.getPrenoms().substring(0, 1), Collectors.summingInt(Fields::getNombre)));

        return groupedMap.entrySet().stream()
                .sorted((o1, o2) -> o2.getValue() - o1.getValue())
                .limit(24)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }
}
