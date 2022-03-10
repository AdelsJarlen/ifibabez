class BlaResept extends Resept 
{  //arver egenskapene til Resept-klassen

    protected final String RESEPTENS_FARGE = "Bla";
    //Setter fargen som en konstant for subklassen. 
  
  
    public BlaResept(Legemiddel legemiddel, Lege utskrivingsLege, int pasientID, int reit)
    {
      super(legemiddel, utskrivingsLege, pasientID, reit);
      //kaller superklassens konstruktør
    }
  

    @Override
    public String farge()
    {
      return RESEPTENS_FARGE;
    }// returnerer strengen "Bla"
  

    @Override
    public int prisAaBetale()
    {
      int pris = legemiddel.hentPris();
      return pris*25/100;
    } // pasienten må betale 25% av prisen på legemidlet.
    

    @Override
    public String toString()
    {
      String blaReseptInfo = super.toString();   // Stringen ReseptInfo printes først.
      blaReseptInfo  += ("\nPris a betale: " + prisAaBetale() + "kr") +  //legger til info om pris
                         "\nFarge: " + farge();  //legger til info om reseptfarge
      return blaReseptInfo;  
    }   //returnerer ny string med felles innhold
  


}
