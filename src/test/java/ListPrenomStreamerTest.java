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
    public void sizeOfRecordsTest() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);

        // Then
        assertThat(listPrenomStreamer.getSize(), is(59));
    }

    @Test
    public void top3NamesIn2010Test() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top3name = listPrenomStreamer.top3Names2010();

        // Then
        assertThat(top3name.size(), is(3));
        assertThat(top3name, contains("Bilal", "Brandon", "Joy"));
    }

    @Test
    public void top3NamesGirl2009Test() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top3name = listPrenomStreamer.top3NamesGirl2009();

        // Then
        assertThat(top3name.size(), is(3));
        assertThat(top3name, contains("Marine", "Melina", "Liam"));
    }

    @Test
    public void top3NamesBoys2012Test() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top3name = listPrenomStreamer.top3NamesBoys2012();

        // Then
        assertThat(top3name.size(), is(3));
        assertThat(top3name, contains("Nanti", "Leo", "Etienne"));
    }

    @Test
    public void top5NamesBetween2009To2016Test() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top3name = listPrenomStreamer.top5Names2009to2016();

        // Then
        assertThat(top3name.size(), is(5));
        assertThat(top3name, contains("Bilal", "Brandon", "Nanti", "Leo", "Marine"));
    }

    @Test
    public void top10WorstNamesBetween2009To2016Test() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top3name = listPrenomStreamer.top10WorstNames2009to2016();

        // Then
        assertThat(top3name.size(), is(10));
        assertThat(top3name, contains("Alexandre", "Alice", "Anatole", "Baptiste", "Bilal", "Boule", "Brandon", "Brian", "Carl", "Catherine"));
    }

    @Test
    public void top12WorstGirlNamesIn2016Test() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top12WorstGirlNameIn2016 = listPrenomStreamer.getTop12WorstGirlName2016();

        // Then
        assertThat(top12WorstGirlNameIn2016.size(), is(12));
        assertThat(top12WorstGirlNameIn2016, contains("Pierra", "Nadine", "Mariane", "Michelle", "Claire", "Catherine", "Henrietta", "Isabelle", "Noemie", "Melanie", "Pauline", "Lucie"));
    }

    @Test
    public void NamesByGenderTest() throws Exception {
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
    public void getNamesAppearsIn2011Test() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top3name = listPrenomStreamer.getNamesAppearsIn2011();

        // Then
        assertThat(top3name.size(), is(3));
        assertThat(top3name, contains("Harold", "Malik", "Vlad"));
    }

    @Test
    public void allNamesPresentFrom2009To2016Test() throws Exception {

        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> AllNamesPresentFrom2009To2016 = listPrenomStreamer.AllNamesPresentFrom2009To2016();

        // Then
        assertThat(AllNamesPresentFrom2009To2016.size(), is(1));
        assertThat(AllNamesPresentFrom2009To2016, contains("Brandon"));
    }

    @Test
    public void top5ofTheBestFirstLetterByYearTest() throws Exception {

        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        Map<Integer, List<Character>> Top5ofTheBestFirstLetterByYear = listPrenomStreamer.Top5ofTheBestFirstLetterByYear();

        assertThat(Top5ofTheBestFirstLetterByYear.size(), is(8));
        assertThat(Top5ofTheBestFirstLetterByYear.get(2016), contains('B', 'M', 'V', 'L', 'N'));
        assertThat(Top5ofTheBestFirstLetterByYear.get(2009), contains('M', 'F', 'L', 'C', 'B'));
        assertThat(Top5ofTheBestFirstLetterByYear.get(2010), contains('B', 'J', 'N', 'E', 'A'));
        assertThat(Top5ofTheBestFirstLetterByYear.get(2011), contains('B', 'C', 'M', 'V', 'H'));
        assertThat(Top5ofTheBestFirstLetterByYear.get(2012), contains('L', 'N', 'B', 'E', 'C'));
        assertThat(Top5ofTheBestFirstLetterByYear.get(2013), contains('P', 'B', 'S', 'D'));
        assertThat(Top5ofTheBestFirstLetterByYear.get(2014), contains('B'));
        assertThat(Top5ofTheBestFirstLetterByYear.get(2015), contains('C', 'B'));
    }

    @Test
    public void top24ofBestLettersFrom2009To2016Test() throws Exception {

        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<Character> Top24ofBestLettersFrom2009To2016 = listPrenomStreamer.Top24ofBestLettersFrom2009To2016();

        assertThat(Top24ofBestLettersFrom2009To2016.size(), is(16));
        assertThat(Top24ofBestLettersFrom2009To2016, contains('B', 'M', 'N', 'L', 'C', 'P', 'J', 'E', 'A', 'F', 'V', 'I', 'H', 'S', 'D', 'K'));
    }

}
