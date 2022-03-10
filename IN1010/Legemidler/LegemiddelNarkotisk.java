package Legemidler;

// Narkotisk - Metoder: hentNarkotiskStyrke, toString()

class Narkotisk extends Legemiddel {

    private int narkotiskStyrke;

    Narkotisk(String navn, int pris, double virkestoff, int narkotiskStyrke){
        super(navn, pris, virkestoff);
        this.narkotiskStyrke = narkotiskStyrke;
    }

    public int hentNarkotiskStyrke(){
        return narkotiskStyrke; }

    @Override
    public String toString(){
        return (super.toString() + " - Narkotisk styrke: " + narkotiskStyrke); }
}