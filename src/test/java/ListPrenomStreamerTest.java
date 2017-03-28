import org.junit.Test;

import models.ParisData;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class ListPrenomStreamerTest {

    @Test
    public void getSize() throws Exception {
        // Given
        ListPrenomStreamer listPrenomStreamer = new ListPrenomStreamer();

        // When
        ParisData data = listPrenomStreamer.getData(false);

        // Then
        assertThat(data.getRecords(), hasSize(10));
    }

}