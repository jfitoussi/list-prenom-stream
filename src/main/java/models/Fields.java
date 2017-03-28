package models;

public class Fields {

    private final int nombre;
    private final int annee;
    private final String prenoms;
    private final String sexe;

    public int getNombre() {
        return nombre;
    }

    public int getAnnee() {
        return annee;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public String getSexe() {
        return sexe;
    }

    public Fields(int nombre, int annee, String prenoms, String sexe) {
        this.nombre = nombre;
        this.annee = annee;
        this.prenoms = prenoms;
        this.sexe = sexe;
    }
}
