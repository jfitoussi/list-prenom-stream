import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.List;

import org.junit.Test;


public class ListPrenomStreamerTest {

    @Test
    public void size_should_be_10() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        // Then
        assertThat(listPrenomStreamer.getSize(), is(13));
    }
    
    @Test
    public void top_3_2010() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        List<String> top32010 = listPrenomStreamer.top3name2010();
        // Then
        assertThat(top32010, contains("Adam", "Alexandre", "Victor"));
    }

}