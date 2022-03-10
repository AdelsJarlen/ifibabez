class MillResept extends HvitResept 
{ //arver egenskapene til HvitResept-klassen

   
    MillResept(Legemiddel legemiddel, Lege utskrivingsLege, int pasientId){
      super(legemiddel, utskrivingsLege, pasientId, 3);
    } //kaller HvitResept-klassens konstruktør og setter reit til 3 uttak.
  
  
    @Override
    public String farge(){
      return RESEPTENS_FARGE + " - Militaer resept " ;
    } //Henter info fra HvitResept-klassen ("Hvit")
  
    @Override
    public int prisAaBetale()
    {
        int pris = legemiddel.hentPris();
        return pris*0; 
    }    //Resepten skal være gratis

}

