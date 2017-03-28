import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;

import models.ParisData;

public class ListPrenomStreamer {

    private static final Gson GSON = new Gson();

    public static void main(String[] args) throws IOException {
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer();
        ParisData parisData = listPrenomStreamer.getData(true);
        System.out.println(listPrenomStreamer.getSize(parisData));
        System.out.println(listPrenomStreamer.top3name2008(parisData));
    }

    public int getSize(ParisData parisData) {
        return parisData.getRecords().size();
    }

    public List<String> top3name2008(ParisData parisData) {
        return null;
    }

    public ParisData getData(boolean full) {
        String s = full ? "" : "_short";
        InputStreamReader inputStreamReader = new InputStreamReader(getClass().getResourceAsStream
                        ("liste_des_prenoms_2004_a_2012" + s + ".json"));
        return GSON.fromJson(inputStreamReader, ParisData.class);
    }

}
