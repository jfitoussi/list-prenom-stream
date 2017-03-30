import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class ListPrenomStreamerTest {

    @Test
    public void size_should_be_10() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        // Then
        assertThat(listPrenomStreamer.getSize(), is(10));
    }

    @Test
    public void top3girls_2009() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("top3girls_2009.json");

        // Then
        List<String> top3girl = listPrenomStreamer.top3girlname2009();
        assertThat(top3girl.size(), is(3));
        assertThat(top3girl, contains("Coralie", "Pauline", "Claire"));
    }

    @Test
    public void top3boys_2012() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("top3boys_2012.json");

        // Then
        List<String> top3boy = listPrenomStreamer.top3boyname2012();
        assertThat(top3boy.size(), is(3));
        assertThat(top3boy, contains("Tatane", "Ting", "Florian"));
    }

    @Test
    public void top_3_name_2010() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("top_3_name_2010.json");

        List<String> top3 = listPrenomStreamer.top3name2010();
        // Then
        assertThat(top3.size(), is(3));
        assertThat(top3, contains("Ting","Florian","Adam"));
    }

    @Test
    public void top_10_worst_name_2009_2016() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("top_10_worst_name_2009_a_2016.json");

        List<String> top10 = listPrenomStreamer.top10worstname2009_2016();
        // Then
        assertThat(top10.size(), is(10));
        assertThat(top10, contains("Kry","Lipton","Yanick","Andrew","christopher","christ","chris","eldii","Coralie","Tatane"));
        // 2 - 3 - 20 - 20 - 50 - 100 - 100 - 100 - 240 -
    }

    @Test
    public void top_5_best_name_2009_2016() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("top_5_prenoms_2009_a_2016.json");

        List<String> top5 = listPrenomStreamer.top5bestname2009_2016();
        // Then
        assertThat(top5.size(), is(5));
        assertThat(top5, contains("Adam","Tatane","Coralie","chris","eldii"));
        // 350 - 240 - 100 - 100 - 100
    }

    @Test
    public void all_name_by_gender() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("all_name_by_gender.json");

        Map<String, Set<String>> allnamebygender = listPrenomStreamer.allnamebygender();
        // Then
        assertThat(allnamebygender.get("F").size(), is(1));
        assertThat(allnamebygender.get("M").size(), is(13));
        assertThat(allnamebygender.get("X").size(), is(4));
        assertThat(allnamebygender.get("F"), contains("Coralie"));
        assertThat(allnamebygender.get("X"), contains("Ely","Dominique","Paris","Noha"));
        assertThat(allnamebygender.get("M"), contains("Yanick","christ","Andrew","Yani","Lipton", "Adam", "Lip", "Kry", "chris", "Tatane", "Florian", "eldii", "christopher"));
    }

    @Test
    public void top_12_worst_girl_name_2016() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("top_12_worst_girl_name_2016.json");

        List<String> top5 = listPrenomStreamer.top12WorstGirlName2016();
        // Then
        assertThat(top5.size(), is(12));
        assertThat(top5, contains("Coralie","Louise","Anna","Manon","Sofia","Lou","Mathilde","Maya","Agathe","Léna","Léonie","Garance"));
        // 350 - 240 - 100 - 100 - 100
    }

}