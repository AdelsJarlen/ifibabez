package Legemidler;

// Vanedannende - Metoder: hentId, hentVanedannendeStyrke, toString()

class Vanedannende extends Legemiddel {

    private int vanedannendeStyrke;

    Vanedannende(String navn, int pris, double virkestoff, int vanedannendeStyrke){
        super(navn, pris, virkestoff);
        this.vanedannendeStyrke = vanedannendeStyrke;
    }

    public int hentVanedannendeStyrke(){
        return vanedannendeStyrke; }

    @Override
    public String toString(){
        return (super.toString() + " - Vanedannende styrke: " + vanedannendeStyrke); }
}