package Legemidler;

// Vanlig - Metoder: toString()

class Vanlig extends Legemiddel {

    Vanlig(String navn, int pris, double virkestoff){
        super(navn, pris, virkestoff);
    }

    @Override
    public String toString(){
        return super.toString(); }
}