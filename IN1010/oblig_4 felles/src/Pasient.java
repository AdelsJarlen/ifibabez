public class Pasient {

    /* KLASSEVARIABLER */
    private String navn;
    private String fnr; // foedselsnummer (String)
    protected int pasientID; // unik ID per pasient
    static int tellerID = 1; // starter paa 1
    IndeksertListe<Resept> reseptliste = new IndeksertListe<>(); // stabel med resepter per pasient

    /* KONSTRUKTOER */
    public Pasient(String navn, String fnr) {
        this.navn = navn;
        this.fnr = fnr;
        pasientID = tellerID;
        tellerID++;
    }

    /* METODER */
    IndeksertListe<Resept> hentReseptliste() {
        return this.reseptliste;
    }

    public String hentNavn() {
        return this.navn;
    }

    public int hentID() {
        return this.pasientID;
    }

    public String hentFnr() {
        return this.fnr;
    }

    void leggTilResept(Resept nyResept) {
        this.reseptliste.leggTil(nyResept);
    }

    @Override
    public String toString() {
      return ("PASIENT\n" +
              "ID: " + hentID() + ", Navn: " + hentNavn() +
              "\nFodselsnr: " + hentFnr() +
              "\nAntall resepter: " + hentReseptliste().stoerrelse());
    }

    public String enkelString() {
        return (hentNavn() + " (fnr. " + hentFnr() + ")");
    }

    public String reseptlisteString() {
        String resultat = "";
        int teller = 1;
        for (Resept resept : reseptliste) {
            resultat += (teller + ". " + resept.hentLegemiddel().hentNavn() + " (" + resept.hentReit() + " reit)");
            teller++;
        }
        return resultat;
    }
}
  
   