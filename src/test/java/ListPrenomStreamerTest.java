import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.contains;

import org.junit.Test;

import java.util.List;
import java.util.Map;

public class ListPrenomStreamerTest {

    @Test
    public void size_should_be_15324() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");

        // Then
        assertThat(listPrenomStreamer.getSize(), is(15324));
    }

    @Test
    public void top3NamesIn2010() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");
        List<String> top3name = listPrenomStreamer.top3Names2010();

        // Then
        assertThat(top3name.size(), is(3));
        assertThat(top3name, contains("Gabriel", "Louise", "Arthur"));
    }

    @Test
    public void top3NamesGirl2009() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");
        List<String> top3name = listPrenomStreamer.top3NamesGirl2009();

        // Then
        assertThat(top3name.size(), is(3));
        assertThat(top3name, contains("Louise", "Camille", "Chloe"));
    }

    @Test
    public void top3NamesBoys2012() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");
        List<String> top3name = listPrenomStreamer.top3NamesBoys2012();

        // Then
        assertThat(top3name.size(), is(3));
        assertThat(top3name, contains("Gabriel", "Adam", "Arthur"));
    }

    @Test
    public void top5NamesBetween2009To2016() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");
        List<String> top3name = listPrenomStreamer.top5Names2009to2016();

        // Then
        assertThat(top3name.size(), is(5));
        assertThat(top3name, contains("Gabriel", "Adam", "Louise", "Raphaël", "Arthur"));
    }

    @Test
    public void TestAllNamesPresentFrom2009To2016() throws Exception {

        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        List<String> AllNamesPresentFrom2009To2016 = listPrenomStreamer.AllNamesPresentFrom2009To2016();

        assertThat(AllNamesPresentFrom2009To2016.size(), is(10));
        assertThat(AllNamesPresentFrom2009To2016, contains("Adam", "Alexandre", "Victor", "Liam", "Ethan", "Ismaël", "Noé", "Baptiste", "Maël", "Ibrahim"));
    }

    @Test
    public void top10WorstNamesBetween2009To2016() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");
        List<String> top3name = listPrenomStreamer.top10WorstNames2009to2016();

        // Then
        assertThat(top3name.size(), is(10));
        assertThat(top3name, contains("Aaliyah", "Aaron", "Abby", "Abd", "Abdallah", "Abdel", "Abdelkader", "Abderrahmane", "Abdou", "Abdoul"));
    }

    @Test
    public void TestTop5ofTheBestFirstLetterByYear() throws Exception {

        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        Map Top5ofTheBestFirstLetterByYear = listPrenomStreamer.Top5ofTheBestFirstLetterByYear();

        assertThat(Top5ofTheBestFirstLetterByYear.size(), is(1));
    }

    @Test
    public void TestTop24ofBestLettersFrom2009To2016() throws Exception {

        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012.json");
        List<Character> Top24ofBestLettersFrom2009To2016 = listPrenomStreamer.Top24ofBestLettersFrom2009To2016();

        assertThat(Top24ofBestLettersFrom2009To2016.size(), is(24));
        assertThat(Top24ofBestLettersFrom2009To2016, contains('A', 'M', 'L', 'E', 'C', 'S', 'J', 'N', 'R', 'G', 'I', 'T', 'H', 'V', 'P', 'B', 'Y', 'D', 'O', 'F', 'K', 'Z', 'W', 'É'));
    }

}
