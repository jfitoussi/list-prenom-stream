import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;


public class ListPrenomStreamerTest {

    @Test
    public void size_should_be_10() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        // Then
        assertThat(listPrenomStreamer.getSize(), is(30));
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
    
    @Test
    public void top_5_2009_to_2016() throws Exception {
    	// Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        List<String> top5name2009to2016 = listPrenomStreamer.top5name2009to2016();
        // Then
        assertThat(top5name2009to2016, contains("Sandra93","Patrick", "Martin", "Ben", "Adam"));
    }
    
    @Test
    public void top_10_worst_2009_to_2016() throws Exception {
    	// Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        List<String> top10WorstName2009to2016 = listPrenomStreamer.top10WorstName2009to2016();
        // Then
        assertThat(top10WorstName2009to2016, contains("Alexandrine","Fatima","Angelique","Alexandra","Marine","Eve", "Jhon", "Ibrahim", "Ma�l", "Baptiste"));
    }
    
    @Test
    public void top_12_worst_girl_2016() throws Exception {
    	// Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        List<String> top12WorstGirlName2016 = listPrenomStreamer.top12WorstGirl2016();
        // Then
        assertThat(top12WorstGirlName2016, contains("Alexandrine","Fatima","Angelique","Alexandra","Marine","Filipini","Rose","Sandra93"));
    }
    
    @Test
    public void namesByGender() throws Exception {
    	// Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        Map<String,List<String>> genderNamesMap = listPrenomStreamer.namesByGenre();
        for(int i = 0 ; i < genderNamesMap.get("M").size(); i++){
        	System.out.println(genderNamesMap.get("M").get(i));
        }
        // Then
        assertThat(genderNamesMap.get("M").size(), is(18));
    }
    
    @Test
    public void justIn2011() throws Exception {
    	// Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        List<String> justIn2011 = listPrenomStreamer.nameAppearIn2011();
        // Then
        assertThat(justIn2011, contains("Patricia","Tulipe"));
    }


    @Test
    public void nameAppearFrom2009To2016() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        List<String> nameAppearFrom2009To2016 = listPrenomStreamer.nameAppearFrom2009To2016();
        // Then
        assertThat(nameAppearFrom2009To2016, contains("A"));
    }

}