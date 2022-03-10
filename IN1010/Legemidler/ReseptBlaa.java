package Legemidler;

// Blå resepter - Metoder: reseptFarge, prisAaBetale, toString

class BlaaResept extends Resept{

    private String farge = "Blaa";

    BlaaResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientID, int reit){
        super(legemiddel, utskrivendeLege, pasientID, reit);
    }

    @Override // Skal returnere fargen blaa
    public String farge(){
        return farge;
    }

    @Override // Blå resepter 75% subsidiert: skal returnere 25% av prisen
    public int prisAaBetale(){
        return (legemiddel.hentPris()/4);
    }

    @Override 
    public String toString(){
        return (super.toString() + " - Farge: " + farge + " - Rabatt: 75%.");
    }
}