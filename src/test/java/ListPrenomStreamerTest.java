import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ListPrenomStreamerTest {

    @Test
    public void size_should_be_10() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        // Then
        assertThat(listPrenomStreamer.getSize(), is(10));
    }

    @Test
    public void top_3_name_2010() throws Exception{
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        assertThat(listPrenomStreamer.top3name2010(),is(Arrays.asList("Louise", "Arthur", "Raphaël")));
    }

    @Test
    public void top_3_name_girl_2009() throws Exception{
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        assertThat(listPrenomStreamer.top3NameGirl2009(),is(Arrays.asList("Louise", "Camille", "Chloe")));
    }
    
    @Test 
    public void names_Present_2009_To_2016() throws Exception{
    	ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
    	assertThat(listPrenomStreamer.namesIntervall(),is(Arrays.asList("Adam","Alexandre")));
    }
}