package models;

public class Records {

    public Fields getFields() {
        return fields;
    }

    public char getFirstLetter(){
        return fields.getPrenoms().charAt(0);
    }

    public int getAnnee(){
        return fields.getAnnee();
    }
    private final Fields fields;

    private Records(Fields fields) {
        this.fields = fields;
    }
}
