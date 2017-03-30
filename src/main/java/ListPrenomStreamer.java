import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    }

    public int getSize() {
        return parisData.getRecords().size();
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
    }

}
