package Legemidler;

// P-resepter - Metoder: prisAaBetale, toString

class PResept extends HvitResept{

    PResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientID, int reit){
        super(legemiddel, utskrivendeLege, pasientID, reit);
    }

    @Override // Returnerer rabatt på prevensjonsmidler: 108 kr avslag. Men aldri mindre enn 0 kr
    public int prisAaBetale(){
        if ((legemiddel.hentPris() - 108) < 0){
            return 0;
        } else {
            return (legemiddel.hentPris() - 108);
        }
    }

    @Override 
    public String toString(){
        return (super.toString() + " - Rabatt: -108kr.");
    }
}
