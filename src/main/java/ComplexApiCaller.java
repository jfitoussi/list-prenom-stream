import com.google.gson.Gson;
import models.ParisData;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ComplexApiCaller {
    private List<String> params = new ArrayList<>();
    private String q;
    private String lang;
    private String rows;
    private String start;
    private String sort;
    private String facet = "";
    private String refine = "";
    private String exclude = "";
    private String distance;
    private String polygon;
    private String timezone;

    public ComplexApiCaller() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("q : ");
            String param;
            param = sc.nextLine();
            if (!param.equals("")) {
                params.add("&q=" + URLEncoder.encode(param, "UTF-8"));
            }
            System.out.print("lang : ");
            param = sc.nextLine();
            if (!param.equals("")) {
                params.add("&lang=" + URLEncoder.encode(param, "UTF-8"));
            }
            System.out.print("rows : ");
            param = sc.nextLine();
            if (!param.equals("")) {
                params.add("&rows=" + URLEncoder.encode(param, "UTF-8"));
            }
            System.out.print("start : ");
            param = sc.nextLine();
            if (!param.equals("")) {
                params.add("&start=" + URLEncoder.encode(param, "UTF-8"));
            }
            System.out.print("sort : ");
            param = sc.nextLine();
            if (!param.equals("")) {
                params.add("&sort=" + URLEncoder.encode(param, "UTF-8"));
            }
            System.out.println("Entrez la liste des facets (Entrée pour quitter)");
            while (true) {
                System.out.print("Entrez un facet : ");
                String line = sc.nextLine();
                if (line.equals("")) {
                    break;
                } else {
                    params.add("&facet=" + URLEncoder.encode(param, "UTF-8"));
                }
                System.out.println("=============");
            }
            System.out.println("Entrez des paires de refine : (Entrée pour quitter)");
            while (true) {
                System.out.print("nom : ");
                String nom = sc.nextLine();
                if (nom.equals("")) {
                    break;
                }
                System.out.print("valeur : ");
                String val = sc.nextLine();
                if (nom.equals("")) {
                    break;
                }
                params.add("&refine." + URLEncoder.encode(nom,"UTF-8") + "=" + URLEncoder.encode(val,"UTF-8"));
                System.out.println("============");
            }
            System.out.println("Entrez des paires de exclude : (Entrée pour quitter)");
            while (true) {
                System.out.print("nom : ");
                String nom = sc.nextLine();
                if (nom.equals("")) {
                    break;
                }
                System.out.print("valeur : ");
                String val = sc.nextLine();
                if (nom.equals("")) {
                    break;
                }
                params.add("&exclude." + URLEncoder.encode(nom,"UTF-8") + "=" + URLEncoder.encode(val,"UTF-8"));
                System.out.println("============");
            }
            System.out.print("distance : ");
            param = sc.nextLine();
            if (!param.equals("")) {
                params.add("&geofilter.distance=" + URLEncoder.encode(param, "UTF-8"));
            }
            System.out.print("polygon : ");
            param = sc.nextLine();
            if (!param.equals("")) {
                params.add("&geofilter.polygon=" + URLEncoder.encode(param, "UTF-8"));
            }
            System.out.print("timezone : ");
            param = sc.nextLine();
            if (!param.equals("")) {
                params.add("&timezone=" + URLEncoder.encode(param, "UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ParisData get() {
        ParisData result = null;
        try {
            Gson gson = new Gson();
            String baseUrl = "https://opendata.paris.fr/api/records/1.0/search/?dataset=liste_des_prenoms_2004_a_2012";
            StringBuffer buffer = new StringBuffer();
            for (String param : params) {
                buffer.append(param);
            }
            String params = buffer.toString();
            String finalUrl = baseUrl+params;
            System.out.println("==================");
            System.out.println("==================");
            System.out.println("Querying : "+finalUrl);
            System.out.println("....");
            URL url = new URL(finalUrl);
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
