class HvitResept extends Resept 
{  //arver egenskapene til Resept-klassen

    protected final String RESEPTENS_FARGE = "Hvit";
    //Setter fargen som en konstant for subklassen. 
  
  
    public HvitResept(Legemiddel legemiddel, Lege utskrivingsLege, int pasientID, int reit)
    {
      super(legemiddel, utskrivingsLege, pasientID, reit);
      //kaller superklassens konstruktør
    }
  
    @Override
    public String farge()
    {
      return RESEPTENS_FARGE;
    }// returnerer strengen "Hvit"
  
    @Override
    public int prisAaBetale()
    {
      int pris = legemiddel.hentPris();
      return pris;
    }
    

    @Override
    public String toString()
    {
      String hvitReseptInfo = super.toString();   // Stringen ReseptInfo printes først.
      hvitReseptInfo  += ("\nPris a betale: " + prisAaBetale() + "kr") +  //legger til info om pris
                         "\nFarge: " + farge();  //legger til info om reseptfarge
      return hvitReseptInfo;  
    }   //returnerer ny string med felles innhold
  


}
