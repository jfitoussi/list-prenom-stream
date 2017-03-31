import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.contains;

import org.junit.Test;

import java.util.List;
import java.util.Map;

public class ListPrenomStreamerTest {

    String JsonFile = "liste_des_prenoms_2004_a_2012.json";
    String JsonFileShort = "liste_des_prenoms_2004_a_2012_short.json";

    @Test
    public void sizeOfRecords() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);

        // Then
        assertThat(listPrenomStreamer.getSize(), is(15324)); //A changer à la fin
    }

    @Test
    public void top3NamesIn2010() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top3name = listPrenomStreamer.top3Names2010();

        // Then
        assertThat(top3name.size(), is(3));
        assertThat(top3name, contains("Gabriel", "Louise", "Arthur"));
    }

    @Test
    public void top3NamesGirl2009() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top3name = listPrenomStreamer.top3NamesGirl2009();

        // Then
        assertThat(top3name.size(), is(3));
        assertThat(top3name, contains("Louise", "Camille", "Chloe"));
    }

    @Test
    public void top3NamesBoys2012() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top3name = listPrenomStreamer.top3NamesBoys2012();

        // Then
        assertThat(top3name.size(), is(3));
        assertThat(top3name, contains("Gabriel", "Adam", "Arthur"));
    }

    @Test
    public void top5NamesBetween2009To2016() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top3name = listPrenomStreamer.top5Names2009to2016();

        // Then
        assertThat(top3name.size(), is(5));
        assertThat(top3name, contains("Gabriel", "Adam", "Louise", "Raphaël", "Arthur"));
    }

    @Test
    public void TestAllNamesPresentFrom2009To2016() throws Exception {

        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> AllNamesPresentFrom2009To2016 = listPrenomStreamer.AllNamesPresentFrom2009To2016();

        // Then
        assertThat(AllNamesPresentFrom2009To2016.size(), is(10));
        assertThat(AllNamesPresentFrom2009To2016, contains("Adam", "Alexandre", "Victor", "Liam", "Ethan", "Ismaël", "Noé", "Baptiste", "Maël", "Ibrahim"));
    }

    @Test
    public void top10WorstNamesBetween2009To2016() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top3name = listPrenomStreamer.top10WorstNames2009to2016();

        // Then
        assertThat(top3name.size(), is(10));
        assertThat(top3name, contains("Aaliyah", "Aaron", "Abby", "Abd", "Abdallah", "Abdel", "Abdelkader", "Abderrahmane", "Abdou", "Abdoul"));
    }

    @Test
    public void TestGetNamesAppearsIn2011() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<String> top3name = listPrenomStreamer.getNamesAppearsIn2011();

        // Then
        assertThat(top3name.size(), is(3));
        assertThat(top3name, contains("Harold", "Malik", "Vlad"));
    }

    @Test
    public void TestTop5ofTheBestFirstLetterByYear() throws Exception {

        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        Map<Integer, List<Character>> Top5ofTheBestFirstLetterByYear = listPrenomStreamer.Top5ofTheBestFirstLetterByYear();

        assertThat(Top5ofTheBestFirstLetterByYear.size(), is(8));
        assertThat(Top5ofTheBestFirstLetterByYear.get(2016), contains('L', 'V', 'B', 'M', 'N'));
        assertThat(Top5ofTheBestFirstLetterByYear.get(2009), contains('M', 'F', 'L', 'C', 'B'));
        assertThat(Top5ofTheBestFirstLetterByYear.get(2010), contains('B', 'J', 'N', 'E', 'A'));
        assertThat(Top5ofTheBestFirstLetterByYear.get(2011), contains('B', 'C', 'M', 'V', 'H'));
        assertThat(Top5ofTheBestFirstLetterByYear.get(2012), contains('L', 'N', 'B', 'E', 'C'));
        assertThat(Top5ofTheBestFirstLetterByYear.get(2013), contains('P', 'B', 'S', 'D'));
        assertThat(Top5ofTheBestFirstLetterByYear.get(2014), contains('B'));
        assertThat(Top5ofTheBestFirstLetterByYear.get(2015), contains('C', 'B'));
    }

    @Test
    public void TestTop24ofBestLettersFrom2009To2016() throws Exception {

        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer(JsonFileShort);
        List<Character> Top24ofBestLettersFrom2009To2016 = listPrenomStreamer.Top24ofBestLettersFrom2009To2016();

        assertThat(Top24ofBestLettersFrom2009To2016.size(), is(16));
        assertThat(Top24ofBestLettersFrom2009To2016, contains('B', 'M', 'N', 'L', 'C', 'P', 'J', 'E', 'A', 'F', 'V', 'I', 'H', 'S', 'D', 'K'));
    }

}
