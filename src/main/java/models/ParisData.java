package models;

import java.util.List;

public class ParisData {

    public List<Records> getRecords() {
        return records;
    }

    private final List<Records> records;

    ParisData(List<Records> recordss) {
        this.records = recordss;
    }
}
