package models;

public class Records {

    public Fields getFields() {
        return fields;
    }

    private final Fields fields;

    private Records(Fields fields) {
        this.fields = fields;
    }

    public int getAnnee(){
        return fields.getAnnee();
    }

    public char getFirstLetter(){
        return fields.getPrenoms().charAt(0);
    }

}
