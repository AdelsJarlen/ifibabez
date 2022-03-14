public class Pasient {
    private String navn;
    private String fnr; // foedselsnummer (String)
    protected int pasientID; // unik ID per pasient
    static int tellerID = 1; // starter paa 1
    Stabel<Resept> reseptliste = new Stabel<>(); // stabel med resepter per pasient

    public Pasient(String navn, String fnr) {
        this.navn = navn;
        this.fnr = fnr;
        pasientID = tellerID;
        tellerID++;
    }

    Stabel<Resept> hentReseptliste() {
        return this.reseptliste;
    }

    void leggTilResept(Resept nyResept) {
        this.reseptliste.leggTil(nyResept);
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

    //Metoder
  
    @Override
    public String toString() {
      return ("PASIENT\n" +
              "ID: " + hentID() + ", Navn: " + hentNavn() +
              "\nFodselsnr: " + hentFnr() +
              "\nAntall resepter: " + hentReseptliste().stoerrelse());
    }
}
  
   