import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import models.Records;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import static org.hamcrest.Matchers.contains;
public class ListPrenomStreamerTest {

    @Test
    public void size_should_be_10() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        // Then
        assertThat(listPrenomStreamer.getSize(), is(35));
    }

    @Test
    public void top_3_best_2010(){

        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        List<String> listTop3_2010 = listPrenomStreamer.top3name2010();
        assertThat(listTop3_2010, containsInAnyOrder("Abraham", "Harouna", "Ismael"));
        //listPrenomStreamer.
    }

    @Test
    public void top_3_best_girl_2009(){
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        List<String> listTop3Girl_2009 = listPrenomStreamer.top3GirlName2009();
        assertThat(listTop3Girl_2009, containsInAnyOrder( "Imane", "Islem", "Pauline"));

    }

    @Test
    public void top_3_best_men_2012(){
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        List<String> listTop3Men_2012 = listPrenomStreamer.top3MenName2012();
        assertThat(listTop3Men_2012, containsInAnyOrder( "Kevin", "Ismo", "Flo"));

    }

    @Test
    public void top_5_best_name_2009_2016(){
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        List<String> listTop5 = listPrenomStreamer.top5Name2009_2016();
        assertThat(listTop5, containsInAnyOrder( "Adam", "Alexandre", "Victor", "Liam", "Ethan"));
    }

    @Test
    public void worst_10_name_2009_2016(){
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        List<String> listWorst10 = listPrenomStreamer.worst10Name2009_2016();
        assertThat(listWorst10, containsInAnyOrder( "Assa", "Vanina", "Laure", "Marianne", "Candice", "Eva", "Aissatou", "Aurelie", "Aline", "Dom"));
    }

    @Test
    public void worst_10_name_2016(){
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");
        List<String> listWorst10Girl = listPrenomStreamer.worst10GilName2016();
        assertThat(listWorst10Girl, containsInAnyOrder( "Assa", "Vanina", "Laure", "Marianne", "Candice", "Eva", "Aissatou", "Aurelie", "Aline", "Amelie", "Aisse", "Dior"));
    }

    @Test
    public void top5ofBestFirstLettersByYear(){
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer("liste_des_prenoms_2004_a_2012_short.json");

        Map<Integer, Long> map =  listPrenomStreamer.top5ofBestFirstLettersByYear();

        System.out.println(map);
    }

}