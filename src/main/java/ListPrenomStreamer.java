import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
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
        System.out.println(listPrenomStreamer.top3NameGirl2009());
//        System.out.println(listPrenomStreamer.top3name2008());
        System.out.println("******** AG ******** ");
        //System.out.println(listPrenomStreamer.exemple().size());
        System.out.println("******** AG ********");
        
        System.out.println("******** AG1 ******** ");
        System.out.println(listPrenomStreamer.namesIntervall());
        System.out.println("******** AG1 ******** ");
    
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

    public List<String> top3NameGirl2009() {
        return parisData.getRecords().stream().filter(record -> record.getFields().getAnnee() == 2009)
                .filter(records -> records.getFields().getSexe().equals("F"))
                .sorted((o1, o2) -> o2.getFields().getNombre() - o1.getFields().getNombre())
                .limit(3)
                .map(record -> record.getFields().getPrenoms())
                .collect(Collectors.toList());
    }

    /**
     * Alexandre : exemple
     */
    public List<String> exemple() {
    	
    		//list declaration
    		List<Records> recordsList = this.parisData.getRecords();
    	
    		    		
    		//predicat
    		Predicate<Records> namesIntervall20092016 = records -> records.getFields().getAnnee() >= 2009 && records.getFields().getAnnee() <= 2016 ;
    		
    		//stream + return 
    		return recordsList.stream().filter(namesIntervall20092016).map(records -> records.getFields().getPrenoms()).collect(Collectors.toList());
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
}
