package Legemidler;

// Hvite resepter - Metoder: reseptFarge, prisAaBetale, toString

class HvitResept extends Resept{

    private String farge = "Hvit";

    HvitResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientID, int reit){
        super(legemiddel, utskrivendeLege, pasientID, reit);
    }

    @Override // Skal returnere fargen hvit
    public String farge(){
        return farge;
    }

    @Override // Returnerer pris på Hvit resept som ikke er Militærresept eller PResept
    public int prisAaBetale(){
        return (legemiddel.hentPris());
    }

    @Override 
    public String toString(){
        return (super.toString() + " - Farge: " + farge);
    }
}