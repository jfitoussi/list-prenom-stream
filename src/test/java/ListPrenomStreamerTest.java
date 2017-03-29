import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import java.util.List;

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
    public void top3girls_should_be_louise_camille_chloe() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");

        // Then
        List<String> top3girl = listPrenomStreamer.top3girlname2009();
        assertThat(top3girl.size(), is(3));
        assertThat(top3girl, contains("Louise", "Camille", "Chloe"));
    }

    @Test
    public void top3boys_should_be_gabriel_adam_arthur() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");

        // Then
        List<String> top3boy = listPrenomStreamer.top3boyname2012();
        assertThat(top3boy.size(), is(3));
        assertThat(top3boy, contains("Gabriel", "Adam", "Arthur"));
    }

    @Test
    public void top_3_name_2010() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");

        List<String> top3 = listPrenomStreamer.top3name2010();
        // Then
        assertThat(top3.size(), is(3));
        assertThat(top3, contains("Gabriel","Louise","Arthur"));
    }

    @Test
    public void top_10_worst_name_2009_2016() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");

        List<String> top10 = listPrenomStreamer.top10worstname2009_2016();
        // Then
        assertThat(top10.size(), is(10));
        assertThat(top10, contains("Gabriel","Louise","Arthur"));
    }

}