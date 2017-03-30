import java.io.IOException;
import java.io.InputStreamReader;
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
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");
        System.out.println(listPrenomStreamer.getSize());
        //System.out.println(listPrenomStreamer.top3name2008());
        //System.out.println(listPrenomStreamer.getTop3GirlName2009());
        System.out.println("La liste des 12 pires prenoms feminins en 2016 : ");
        System.out.println(listPrenomStreamer.getTop12WorstGirlName2016());
        System.out.println("----------------------");
        System.out.println("La liste des prenoms triés par genre : ");
        System.out.println(listPrenomStreamer.getAllNamesByGender2());
        System.out.println("----------------------");
        System.out.println("La liste des prenoms apparus uniquement en 2011 : ");
        System.out.println(listPrenomStreamer.getNamesAppearsIn2011());
        System.out.println("----------------------");

        System.out.println("Size : " + listPrenomStreamer.getSize());
        System.out.println("Top 3 names 2010 : " + listPrenomStreamer.top3Names2010());
        System.out.println("Top 3 names girls 2009 : " + listPrenomStreamer.top3NamesGirl2009());
        System.out.println("Top 3 names boys 2012 : " + listPrenomStreamer.top3NamesBoys2012());
        System.out.println("Top 5 names between 2009 and 2016: " + listPrenomStreamer.top5Names2009to2016());
        System.out.println("Top 10 worst names between 2009 and 2016: " + listPrenomStreamer.top10WorstNames2009to2016());
        System.out.println("All names present from 2009 to 2016 :" + "(Size -> " + listPrenomStreamer.AllNamesPresentFrom2009To2016().size() + ")" + listPrenomStreamer.AllNamesPresentFrom2009To2016());
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


    public Map<String, List<String>> getAllNamesByGender2(){

        return parisData.getRecords().stream()
                .map(Records::getFields)
                .collect(Collectors.groupingBy(Fields::getSexe, Collectors.mapping(Fields::getPrenoms, Collectors.toList()) ));

    }

    public List<String> getNamesAppearsIn2011(){

        List<String> nameOf20042016= parisData.getRecords().stream()
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

        }
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


}
