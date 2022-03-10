public class Spesialist extends Lege 
{ //arver egenskapene til Lege-klassen


    private String kontrollid; //setter variabelen privat så den bare kan brukes i denne subklassen.
    
    //Konstruktøren i Spesialist skal ta imot navn og en String kontrollID.
    public Spesialist(String navn, String kontrollID)
    {           
        super(navn); //kaller superklassens konstruktør
        kontrollid = kontrollID; // legger til kontrollid som tillegg til konstruktøren fra superklassen
    }


    public String hentKontrollID()
    {
        return kontrollid;
    }

    
    @Override
    public String toString()
    { 
        String spesialistInfo = super.toString();
        spesialistInfo += " kontroll ID: " + hentKontrollID();
        return spesialistInfo; 
    }    
    
}
