import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.Predicate;

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
	}

	public int getSize() {
		return parisData.getRecords().size();
	}



    public List<String> nameAppearFrom2009To2016(){
        List<String> in2009 = this.parisData.getRecords().stream().map(fields -> fields.getFields()).filter( person -> person.getAnnee() == 2009).map( person -> person.getPrenoms()).collect(Collectors.toList());
        List<String> in2010 = this.parisData.getRecords().stream().map(fields -> fields.getFields()).filter( person -> person.getAnnee() == 2010).map( person -> person.getPrenoms()).collect(Collectors.toList());
        List<String> in2011 = this.parisData.getRecords().stream().map(fields -> fields.getFields()).filter( person -> person.getAnnee() == 2011).map( person -> person.getPrenoms()).collect(Collectors.toList());
        List<String> in2012 = this.parisData.getRecords().stream().map(fields -> fields.getFields()).filter( person -> person.getAnnee() == 2012).map( person -> person.getPrenoms()).collect(Collectors.toList());
        List<String> in2013 = this.parisData.getRecords().stream().map(fields -> fields.getFields()).filter( person -> person.getAnnee() == 2013).map( person -> person.getPrenoms()).collect(Collectors.toList());
        List<String> in2014 = this.parisData.getRecords().stream().map(fields -> fields.getFields()).filter( person -> person.getAnnee() == 2014).map( person -> person.getPrenoms()).collect(Collectors.toList());
        List<String> in2015 = this.parisData.getRecords().stream().map(fields -> fields.getFields()).filter( person -> person.getAnnee() == 2015).map( person -> person.getPrenoms()).collect(Collectors.toList());
        List<String> in2016 = this.parisData.getRecords().stream().map(fields -> fields.getFields()).filter( person -> person.getAnnee() == 2016).map( person -> person.getPrenoms()).collect(Collectors.toList());
        List<String> nameAppearsFrom2009To2016= in2009.stream().filter(person ->   in2010.contains(person) &&
                                                                    in2011.contains(person) &&
                                                                    in2012.contains(person) &&
                                                                    in2013.contains(person) &&
                                                                    in2014.contains(person) &&
                                                                    in2015.contains(person) &&
                                                                    in2016.contains(person) ).collect(Collectors.toList());
        return nameAppearsFrom2009To2016;
    }

    public List<String> top3NameByGenderAndYear(String gender, int year) {

		Predicate<Records> nameIn2009 = r -> r.getFields().getAnnee() == year && r.getFields().getSexe().equals(gender);
		Comparator<Fields> topOrder = (Fields c1, Fields c2) -> Integer.compare(c1.getNombre(), c2.getNombre());

		Stream<String> top3NameIn2009 = this.parisData.getRecords().stream().filter(nameIn2009).map(r -> r.getFields()).sorted(topOrder.reversed()).map(f -> f.getPrenoms()).limit(3);
		List<String> listTop3GirlName = top3NameIn2009.collect(Collectors.toList());

		return listTop3GirlName;
	}
	public List<String> top3name2010() {  	
		Comparator<Fields> topOrder = (Fields c1, Fields c2) ->  Integer.compare(c1.getNombre(), c2.getNombre());
		Stream<String> top3Prenoms2010Stream = this.parisData.getRecords().stream().map(fields -> fields.getFields()).sorted(topOrder.reversed()).filter(field -> field.getAnnee() == 2010).map(person -> person.getPrenoms()).limit(3);
		return top3Prenoms2010Stream.collect(Collectors.toList());
	}

	public List<String> top5name2009to2016(){
		Comparator<Fields> topOrder = (Fields c1, Fields c2) ->  Integer.compare(c1.getNombre(), c2.getNombre());
		Stream<String> top5Prenoms2009to2016Stream = this.parisData.getRecords().stream().map(fields -> fields.getFields()).sorted(topOrder.reversed()).filter(field -> field.getAnnee() > 2008 && field.getAnnee() < 2017).map(person -> person.getPrenoms()).distinct().limit(5);
		return top5Prenoms2009to2016Stream.collect(Collectors.toList());
	}

	public List<String> top10WorstName2009to2016(){
		Comparator<Fields> topOrder = (Fields c1, Fields c2) ->  Integer.compare(c1.getNombre(), c2.getNombre());
		Stream<String> top10WorstName2009to2016Stream = this.parisData.getRecords().stream().map(fields -> fields.getFields()).sorted(topOrder).filter(field -> field.getAnnee() > 2008 && field.getAnnee() < 2017).map(person -> person.getPrenoms()).limit(10);
		return top10WorstName2009to2016Stream.collect(Collectors.toList());
	}

	public List<String> top12WorstGirl2016(){
		Comparator<Fields> topOrder = (Fields c1, Fields c2) ->  Integer.compare(c1.getNombre(), c2.getNombre());
		Stream<String> top12WorstGirl2016Stream = this.parisData.getRecords().stream().map(fields -> fields.getFields()).sorted(topOrder).filter(field -> field.getAnnee() == 2016 && field.getSexe().equals("F")).map(person -> person.getPrenoms()).limit(12);
		return top12WorstGirl2016Stream.collect(Collectors.toList());
	}

	public Map<String,Map<String,List<Fields>>> namesByGenre(){
		Map<String,Map<String,List<Fields>>> genderMap = this.parisData.getRecords().stream().map(fields -> fields.getFields()).collect(Collectors.groupingBy(Fields::getSexe,Collectors.groupingBy(Fields::getPrenoms, Collectors.toList())));
		return genderMap;
	}

	public List<String> nameAppearIn2011(){
		List<String> notIn2011 = this.parisData.getRecords().stream().map(fields -> fields.getFields()).filter( person -> person.getAnnee() != 2011).map( person -> person.getPrenoms()).collect(Collectors.toList());
		List<String> in2011 = this.parisData.getRecords().stream().map(fields -> fields.getFields()).filter(person -> person.getAnnee() == 2011).map(person -> person.getPrenoms()).distinct().collect(Collectors.toList());
		List<String> justIn2011= in2011.stream().filter(person -> !notIn2011.contains(person)).collect(Collectors.toList());
		return justIn2011;
	}


	public Map<Integer, Map<String, Integer>> best5Letters() {

        Map<Integer, Map<String, Integer>> namesByYear = this.parisData.getRecords().stream()
                .map(fields -> fields.getFields())
                .collect(Collectors.groupingBy(
                        Fields::getAnnee, Collectors.groupingBy(
                                f -> f.getPrenoms().charAt(0) + "", Collectors.summingInt(f -> f.getNombre())
                                )
                        ));
        for(Entry<Integer, Map<String,Integer>> mapNames : namesByYear.entrySet()){
            namesByYear.put(mapNames.getKey(), sortByValue(namesByYear.get(mapNames.getKey()), 5));
        }
        
//        System.out.println("letters by year " + namesByYear);
        return namesByYear;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, int limit) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, 
                        Map.Entry::getValue, 
                        (e1, e2) -> e1, 
                        LinkedHashMap::new
                        ));

    }
    

	public Map<String, Integer> top24LetterInRange(int yearFrom, int yearTo) {

		Predicate<Fields> letterInRange = f -> f.getAnnee() >= yearFrom && f.getAnnee() <= yearTo;

		Map<String, Integer> top24LetterInRange = this.parisData.getRecords().stream()
				.map(r -> r.getFields())
				.filter(letterInRange)
				.limit(24)
				.collect(Collectors.groupingBy(p -> p.getPrenoms().charAt(0) + "", Collectors.summingInt(p -> p.getNombre())));
		
		top24LetterInRange = sortByValue(top24LetterInRange, 24);
		
//		System.out.println("top24 " + top24LetterInRange);

		return top24LetterInRange;
	}

}
