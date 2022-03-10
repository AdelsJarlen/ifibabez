package Legemidler;

// Spesialister - Metoder: hentKontrollID, toString()

class Spesialist extends Lege implements LegeGodkjenningsfritak{

    String kontrollID;

    Spesialist(String navn, String kontrollID){
        super(navn);
        this.kontrollID = kontrollID;
    }

    public String hentKontrollID(){
        return kontrollID;
    }

    @Override
    public String toString(){
        return (super.toString() + " - Spesialist KontrollID: " + kontrollID);
    }
}