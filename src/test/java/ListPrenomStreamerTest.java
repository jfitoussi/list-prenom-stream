import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.util.*;

public class ListPrenomStreamerTest {

    @Test
    public void size_should_be_10() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2016_short.json");

        // Then
        assertThat(listPrenomStreamer.getSize(), is(10));
    }

    @Test
    public void top_3_name_2010() throws Exception{
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        assertThat(listPrenomStreamer.top3name2010(),is(Arrays.asList("Louise", "Arthur", "Raphaël")));
    }

    @Test
    public void top_3_name_boy_2012() throws Exception{
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2012_short.json");

        assertThat(listPrenomStreamer.top3NameBoy2012(),is(Arrays.asList("Gabriel", "Adam", "Arthur")));
    }

    @Test
    public void top_3_name_girl_2009() throws Exception{
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        assertThat(listPrenomStreamer.top3NameGirl2009(),is(Arrays.asList("Louise", "Camille", "Chloe")));
    }

    @Test
    public void top_5_Name_2009_To_2016() throws Exception{
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2009_a_2016.json");

        assertThat(listPrenomStreamer.top5name2009to2016(),is(Arrays.asList("Gabriel",
                "Adam",
                "Louise",
                "Raphaël",
                "Arthur")));

    }

    @Test
    public void top_10_Worst_Name_2009_To_2016() throws Exception{
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        assertThat(listPrenomStreamer.top10WorstName2009To2016(),is(Arrays.asList("Ibrahim",
                                                                                  "Maël",
                                                                                  "Baptiste",
                                                                                  "Noé",
                                                                                  "Ismaël",
                                                                                  "Ethan",
                                                                                  "Liam",
                                                                                  "Victor",
                                                                                  "Louis",
                                                                                  "Camille")));

    }

    @Test
    public void top_12_Worst_Girl_Name_2016() throws Exception{
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2016.json");
        assertThat(listPrenomStreamer.top12WorstGirlName2016(),is(Arrays.asList("Alyah",
                                                                                "Appoline",
                                                                                "Athéna",
                                                                                "Audrey",
                                                                                "Elyana",
                                                                                "Geneviève",
                                                                                "Hadja",
                                                                                "Kendra",
                                                                                "Laya",
                                                                                "Maï",
                                                                                "Maxime",
                                                                                "Saja")));
    }

    @Test
    public void sort_By_Gender() throws Exception{
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2016_short.json");
        Map<String,Set<String>> mapGenderName = listPrenomStreamer.allNamesByGender();

        assertThat(mapGenderName.get("F"),is(new HashSet<>(Arrays.asList("Alice","Louise","Emma"))));
        assertThat(mapGenderName.get("M"),is(new HashSet<>(Arrays.asList("Adam","Arthur","Alexandre","Raphaël","Gabriel","Paul","Louis"))));
    }

    @Test
    public void top_5_letter_by_year() throws Exception{
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2016.json");

        assertThat(listPrenomStreamer.top5BestFirstLetter().get(2016),is(Arrays.asList("A", "M", "L", "E", "S")));
    }

    @Test
    public void top_24_letter_2009_to_2016() throws Exception{
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2009_a_2016.json");

        assertThat(listPrenomStreamer.top24letter2009to2016(),is(Arrays.asList("A",
                "M",
                "L",
                "E",
                "C",
                "S",
                "J",
                "N",
                "R",
                "G",
                "I",
                "T",
                "H",
                "V",
                "P",
                "B",
                "Y",
                "D",
                "O",
                "F",
                "K",
                "Z",
                "W",
                "É")));
    }
}