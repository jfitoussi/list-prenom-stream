import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.contains;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListPrenomStreamerTest {

    String JsonFile = "liste_des_prenoms_2004_a_2012.json";
    String JsonFileShort = "liste_des_prenoms_2004_a_2012_short.json";

    @Test
    public void sizeOfRecords() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);

        // Then
        assertThat(listPrenomStreamer.getSize(), is(59));
    }

    @Test
    public void top3NamesIn2010() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top3name = listPrenomStreamer.top3Names2010();

        // Then
        assertThat(top3name.size(), is(3));
        assertThat(top3name, contains("Bilal", "Brandon", "Joy"));
    }

    @Test
    public void top3NamesGirl2009() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top3name = listPrenomStreamer.top3NamesGirl2009();

        // Then
        assertThat(top3name.size(), is(3));
        assertThat(top3name, contains("Marine", "Melina", "Liam"));
    }

    @Test
    public void top3NamesBoys2012() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top3name = listPrenomStreamer.top3NamesBoys2012();

        // Then
        assertThat(top3name.size(), is(3));
        assertThat(top3name, contains("Nanti", "Leo", "Etienne"));
    }

    @Test
    public void top5NamesBetween2009To2016() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top3name = listPrenomStreamer.top5Names2009to2016();

        // Then
        assertThat(top3name.size(), is(5));
        assertThat(top3name, contains("Bilal", "Brandon", "Nanti", "Leo", "Marine"));
    }

    @Test
    public void top10WorstNamesBetween2009To2016() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top3name = listPrenomStreamer.top10WorstNames2009to2016();

        // Then
        assertThat(top3name.size(), is(10));
        assertThat(top3name, contains("Alexandre", "Alice", "Anatole", "Baptiste", "Bilal", "Boule", "Brandon", "Brian", "Carl", "Catherine"));
    }

    @Test
    public void top12WorstGirlNamesIn2016() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top12WorstGirlNameIn2016 = listPrenomStreamer.getTop12WorstGirlName2016();

        // Then
        assertThat(top12WorstGirlNameIn2016.size(), is(12));
        assertThat(top12WorstGirlNameIn2016, contains("Pierra", "Nadine", "Mariane", "Michelle", "Claire", "Catherine", "Henrietta", "Isabelle", "Noemie", "Melanie", "Pauline", "Lucie"));
    }

    @Test
    public void NamesByGender() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        Map<String, Set<String>> namesByGender = listPrenomStreamer.getAllNamesByGender();

        // Then
        assertThat(namesByGender.get("F").size(), is(23));
        assertThat(namesByGender.get("F"), contains("Celine", "Chloé", "Noemie",
                "Michelle", "Pauline", "Melanie", "Henrietta", "Catherine", "Paula",
                "Paul", "Liam", "Melina", "Pierra", "Claire", "Nadine", "Mariane",
                "Nadia", "Marine", "Manon", "Lucie", "Joy", "Isabelle", "Elodie"));
        assertThat(namesByGender.get("X").size(), is(3));
        assertThat(namesByGender.get("X"), contains("Bilal", "Alice", "Alexandre"));
        assertThat(namesByGender.get("M").size(), is(24));
        assertThat(namesByGender.get("M"), contains("Marc", "Baptiste", "Carl",
                "Nolan", "Harold", "Brandon", "Nanti", "Etienne", "Victor", "Malik",
                "Brian", "Maël", "Ibrahim", "Vlad", "Damien", "Kris", "Fabrice", "Sakris",
                "Léonard", "Hadrien", "Boule", "Leo", "Félix", "Anatole"));
    }

    @Test
    public void TestAllNamesPresentFrom2009To2016() throws Exception {

        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> AllNamesPresentFrom2009To2016 = listPrenomStreamer.AllNamesPresentFrom2009To2016();

        // Then
        assertThat(AllNamesPresentFrom2009To2016.size(), is(1));
        assertThat(AllNamesPresentFrom2009To2016, contains("Brandon"));
    }



    @Test
    public void TestTop5ofTheBestFirstLetterByYear() throws Exception {

        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        Map Top5ofTheBestFirstLetterByYear = listPrenomStreamer.Top5ofTheBestFirstLetterByYear();

        assertThat(Top5ofTheBestFirstLetterByYear.size(), is(1));
    }

    @Test
    public void TestTop24ofBestLettersFrom2009To2016() throws Exception {

        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<Character> Top24ofBestLettersFrom2009To2016 = listPrenomStreamer.Top24ofBestLettersFrom2009To2016();

        assertThat(Top24ofBestLettersFrom2009To2016.size(), is(24));
        assertThat(Top24ofBestLettersFrom2009To2016, contains('A', 'M', 'L', 'E', 'C', 'S', 'J', 'N', 'R', 'G', 'I', 'T', 'H', 'V', 'P', 'B', 'Y', 'D', 'O', 'F', 'K', 'Z', 'W', 'É'));
    }

}
