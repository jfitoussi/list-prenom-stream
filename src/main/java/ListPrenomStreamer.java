import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import com.sun.javafx.collections.MappingChange;
import models.ParisData;

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
      //  System.out.println(listPrenomStreamer.top5name2009_2016());
    }

    public int getSize() {
        return parisData.getRecords().size();
    }

    public List<String> top3name2010() {
        return parisData.getRecords().stream().filter(records -> records.getFields().getAnnee()==2010).sorted((obj1,obj2) -> obj2.getFields().getNombre() - obj1.getFields().getNombre()).limit(3).map(records -> records.getFields().getPrenoms()).collect(Collectors.toList());
    }

}
