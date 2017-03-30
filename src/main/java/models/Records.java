package models;

public class Records {

    public Fields getFields() {
        return fields;
    }

    private final Fields fields;

    private Records(Fields fields) {
        this.fields = fields;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Records && ((Records) obj).fields == this.fields;
    }
}
