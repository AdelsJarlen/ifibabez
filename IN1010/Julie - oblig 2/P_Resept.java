class P_Resept extends HvitResept
{//arver egenskapene til HvitResept-klassen


  P_Resept(Legemiddel legemiddel, Lege utskrivingsLege, int pasientID, int reit)
  {
    super(legemiddel, utskrivingsLege, pasientID, reit );
  } //kaller HvitResept-klassens konstruktør
  
  
  
    @Override
    public String farge(){
      return RESEPTENS_FARGE + " - P-Resept";
    } //Henter info fra HvitResept-klassen ("Hvit")
  

    @Override
    public int prisAaBetale()
    {
      int pris = hentLegemiddel().pris;
      pris -= 108;  //Resepten skal få 108kr rabattfradrag
  
      if (pris < 0)
         {pris = 0;
         }
      return pris; 
    }


    
  }