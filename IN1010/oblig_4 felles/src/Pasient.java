public class Pasient {

    /*******************/
    /* KLASSEVARIABLER */
    /*******************/    

    private String navn;
    private String fnr; // foedselsnummer (String)
    protected int pasientID; // unik ID per pasient
    static int tellerID = 1; // starter paa 1
    Stabel<Resept> reseptliste = new Stabel<>(); // stabel med resepter per pasient

    /****************/
    /* KONSTRUKTOER */
    /****************/

    public Pasient(String navn, String fnr) {
        this.navn = navn;
        this.fnr = fnr;
        pasientID = tellerID;
        tellerID++;
    }

    /***********/
    /* GETTERE */
    /***********/

    Stabel<Resept> hentReseptliste() {
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

    /*****************/
    /* ANDRE METODER */
    /*****************/
  
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
}
  
   