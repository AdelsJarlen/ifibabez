package Legemidler;

// Militærresepter - Metoder: prisAaBetale, toString

class MilResept extends HvitResept{

    MilResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientID, int reit){
        super(legemiddel, utskrivendeLege, pasientID, 3); // utskrives alltid med 3 reit
    }

    @Override // Militærresept er gratis
    public int prisAaBetale(){
        return 0;
    }

    @Override 
    public String toString(){
        return (super.toString() + " - Rabatt: 100%.");
    }
}