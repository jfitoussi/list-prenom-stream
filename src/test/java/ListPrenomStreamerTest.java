import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import java.util.List;
import java.util.Map;

/**
 * ListPrenomStreamerTest
 */
public class ListPrenomStreamerTest {
    @Test
    public void numberOfRecordsTest() throws Exception {
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("number_of_records.json");

        assertThat(listPrenomStreamer.getSize(), is(10));
    }

    @Test
    public void top3BestNameIn2010Test() throws Exception {
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("top_3_best_name_in_2010.json");

        assertThat(listPrenomStreamer.top3Name2010(), contains("Adam", "Alexandre", "Victor"));
    }

    @Test
    public void top3BestGirlNameIn2009Test() throws Exception {
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("top_3_best_girl_name_in_2009.json");

        assertThat(listPrenomStreamer.top3GirlsName2009(), contains("Juliette", "Sarah", "Catalina"));
    }

    @Test
    public void top3BestBoyNameIn2012Test() throws Exception {
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("top_3_boys_name_in_2012.json");

        assertThat(listPrenomStreamer.top3BoysName2012(), contains("Rodolphe", "Francis", "Robert"));
    }

    @Test
    public void top5Name2009To2016Test() throws Exception {
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("top_5_name_2009_to_2016.json");

        assertThat(listPrenomStreamer.top5Name2009To2016(), contains("Alexandre", "Adam", "Ethan", "Liam", "Victoria"));
    }

    @Test
    public void top10Worst2009To2016Test() throws Exception {
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("top_10_worst_2009_to_2016.json");

        assertThat(listPrenomStreamer.top10Worst2009To2016(), contains(
                "Alexandre",
                "Michel",
                "Martin",
                "John",
                "Rodolphe",
                "Marcel",
                "Pierre",
                "Marc",
                "Alexis",
                "José"
        ));
    }

    @Test
    public void top12WorstGirls2016Test() throws Exception {
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("top_12_worst_girls_name_in_2016.json");

        assertThat(listPrenomStreamer.top12WorstGirls2016(), contains(
                "Rosalie",
                "Julie",
                "Juliette",
                "Flavie",
                "Emeline",
                "Josianne",
                "Brigitte",
                "Rose",
                "Joséphine",
                "Armelle",
                "Cynthia",
                "Elsa"
        ));
    }

    @Test
    public void allNamesByGenderTest() throws Exception {
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("all_names_by_gender.json");
        Map<String, List<String>> result = listPrenomStreamer.allNamesByGender();

        assertThat(result.size(), is(3));
        assertTrue(result.containsKey("F"));
        assertTrue(result.containsKey("M"));
        assertTrue(result.containsKey("X"));
        assertThat(result.get("F").size(), is(2));
        assertThat(result.get("M").size(), is(1));
        assertThat(result.get("X").size(), is(1));
        assertThat(result.get("F"), containsInAnyOrder("Juliette", "Catalina"));
        assertThat(result.get("M"), containsInAnyOrder("Rodolphe"));
        assertThat(result.get("X"), containsInAnyOrder("Jacquie-Michel"));
    }

    @Test
    public void namesOnlyIn2011Test() throws Exception {
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("names_only_in_2011.json");

        assertThat(listPrenomStreamer.namesOnlyIn2011(), containsInAnyOrder("Lisa", "Alexandre"));
    }

    @Test
    public void allNamesPresent2009To2016Test() throws Exception {
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("all_names_present_2009_to_2016.json");

        assertThat(listPrenomStreamer.allNamesPresent2009To2016(), containsInAnyOrder("Lisa", "Adam"));
    }

    @Test
    public void top5BestFirstLetterByYearTest() throws Exception {
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("top_5_best_first_letter_by_year.json");
        Map<Integer, List<Character>> result = listPrenomStreamer.top5BestFirstLetterByYear();

        assertThat(result.size(), is(8));

        for (int i = 2009; i <= 2016; i++) {
            assertTrue(result.containsKey(i));
            assertThat(result.get(i).size(), is(5));
            assertThat(result.get(i), contains('A', 'B', 'C', 'D', 'E'));
        }
    }
//    @Test
//    public void top24BestLettersTest() throws Exception {
//        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("top_24_best_letters.json");
//
//        assertThat(listPrenomStreamer.top24BestLetters(), contains(
//                "A",
//                "B",
//                "C",
//                "D",
//                "E",
//                "F",
//                "G",
//                "H",
//                "I",
//                "J",
//                "K",
//                "L",
//                "M",
//                "N",
//                "O",
//                "P",
//                "Q",
//                "R",
//                "S",
//                "T",
//                "U",
//                "V",
//                "W",
//                "X"
//        ));
//    }
}
