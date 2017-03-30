import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.util.List;

public class ListPrenomStreamerTest {

    @Test
    public void size_should_be_10() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        // Then
        assertThat(listPrenomStreamer.getSize(), is(18));
    }

    @Test
    public void best3_name2010() {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        List<String> top3_2010 = listPrenomStreamer.top3name2010();
        // Then
        assertThat(top3_2010, containsInAnyOrder("Adam", "Alexandre", "Victor"));
    }

    @Test
    public void best3_girl2009() {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        List<String> top3_girl = listPrenomStreamer.top3girl2009();
        // Then
        assertThat(top3_girl, containsInAnyOrder("Shahana", "Emilie", "Andrea"));
    }

    @Test
    public void best3_boy2012() {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        List<String> top3_boy = listPrenomStreamer.top3boy2012();
        // Then
        assertThat(top3_boy, containsInAnyOrder("Ibrahim", "Ismael", "Flo"));
    }
    @Test
    public void best5_2009_2016() {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        List<String> top5 = listPrenomStreamer.top52009_2016();
        // Then
        assertThat(top5, containsInAnyOrder("Adam", "Alexandre", "Victor", "Harouna","Juan"));
    }

    @Test
    public void worst10_2009_2016() {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        List<String> worst10 = listPrenomStreamer.worst10_2009_2016();
        // Then
        assertThat(worst10, containsInAnyOrder( "Andrea","Emilie","Shahana", "Dom", "Baptiste","Flo", "Ibrahim","Ismael","Pauline","Cathy","Florianne","Sarah","Islem"));
    }

    @Test
    public void worst12girl_2009_2016() {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        List<String> worst10 = listPrenomStreamer.worst12girl_2009_2016();
        // Then
        assertThat(worst10, containsInAnyOrder( "Cathy","Florianne","Sarah"));
    }

}