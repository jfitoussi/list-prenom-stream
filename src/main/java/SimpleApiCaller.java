import com.google.gson.Gson;
import models.ParisData;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimpleApiCaller {

    public ParisData get() {
        ParisData result = null;
        try {
            Gson gson = new Gson();
            URL url = new URL("https://opendata.paris.fr/api/records/1.0/search/?dataset=liste_des_prenoms_2004_a_2012&rows=10000");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode < 400) {
                result = gson.fromJson(new InputStreamReader(con.getInputStream()), ParisData.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
