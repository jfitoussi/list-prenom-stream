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
        assertThat(listPrenomStreamer.getSize(), is(20));
    }
    
    @Test
    public void top_3_2010() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        List<String> top32010 = listPrenomStreamer.top3name2010();
        // Then
        assertThat(top32010, contains("Patrick", "Ben", "Gerard"));
    }
    
    @Test
    public void top_5_2009_to_2016() throws Exception {
    	// Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        List<String> top5name2009to2016 = listPrenomStreamer.top5name2009to2016();
        // Then
        assertThat(top5name2009to2016, contains("Patrick", "Martin", "Ben", "Adam", "Alexia"));
    }

    @Test
    public void top_3_girl_name() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        
        List<String> top3GirlNameIn2009 = listPrenomStreamer.top3NameByGenderAndYear("F", 2009);

        // Then
        assertThat(top3GirlNameIn2009, contains("Alexia","Victoria","Eve"));
    }

    @Test
    public void top_3_boy_name() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        
        List<String> top3GirlNameIn2009 = listPrenomStreamer.top3NameByGenderAndYear("M", 2012);

        // Then
        assertThat(top3GirlNameIn2009, contains("Martin","Philippe","Jhon"));
    }

}