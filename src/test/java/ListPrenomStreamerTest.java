import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.contains;

import org.junit.Test;

import java.util.List;

public class ListPrenomStreamerTest {

    @Test
    public void size_should_be_15324() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");

        // Then
        assertThat(listPrenomStreamer.getSize(), is(15324));
    }

    @Test
    public void top3NameIn2010() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");
        List<String> top3name = listPrenomStreamer.top3name2010();

        // Then
        assertThat(top3name.size(), is(3));
        assertThat(top3name, contains("Gabriel", "Louise", "Arthur"));
    }

    @Test
    public void top3NameGirl2009() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");
        List<String> top3name = listPrenomStreamer.top3NameGirl2009();

        // Then
        assertThat(top3name.size(), is(3));
        assertThat(top3name, contains("Louise", "Camille", "Chloe"));
    }

    @Test
    public void top3NameBoys2012() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");
        List<String> top3name = listPrenomStreamer.top3NameBoys2012();

        // Then
        assertThat(top3name.size(), is(3));
        assertThat(top3name, contains("Gabriel", "Adam", "Arthur"));
    }

}
