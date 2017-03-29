import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;

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
        System.out.println(listPrenomStreamer.top3name2008());
    }

    public int getSize() {
        return parisData.getRecords().size();
    }

    public List<String> top3name2008() {
        return null;
    }

}
