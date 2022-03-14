class Pasient {
    private String navn;
    private String fnr;
    private int id;

    static int antallID = 0;

    Stabel<Resept> reseptliste = new Stabel<>();


    public Pasient(String navn, String fnr)
    {
        this.navn = navn;
        this.fnr = fnr;
        this.id = antallID;
        oppdaterIdTeller();
    }


    void oppdaterIdTeller()
    {   antallID++;
    }


    Stabel<Resept> hentReseptliste()
    {
        return this.reseptliste;
    }


    void leggTilResept(Resept nyResept)
    {
        this.reseptliste.leggTil(nyResept);
    }


    public String hentNavn()
    {
        return this.navn;
    }


    public int hentId()
    {
        return this.id;
    }


    public String hentFnr()
    {
        return this.fnr;
    }


    //Metoder
  
    @Override
    public String toString(){
      return ("PASIENT\n" +
              "ID: " + hentId() + ", Navn: " + hentNavn() +
              "\nFodselsnr: " + hentFnr() +
              "\nAntall resepter: " + hentReseptliste().storrelse() );
    }
}
  
   