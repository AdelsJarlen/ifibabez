package Legemidler;

// Legemiddel - Metoder: hentId, hentNavn, hentPris, hentVirkestoff, settNyPris, toString

abstract class Legemiddel {

    protected String navn; // navnet til legemidlet
    protected int pris; // pris i hele kr
    protected double virkestoff; // virkestoff i mg
    protected int legemiddelID; // hvert objekt av klassen skal ha id som ingen andre objekter av samme klassen har
    static int tellerID = 1; // variabel som teller ID

    Legemiddel(String navn, int pris, double virkestoff){
        this.navn = navn;
        this.pris = pris;
        this.virkestoff = virkestoff;
        legemiddelID = tellerID;
        tellerID ++;
    } 

    public int hentID(){
        return legemiddelID; }

    public String hentNavn(){
        return navn; }

    public int hentPris(){
        return pris; }

    public double hentVirkestoff(){
        return virkestoff; }

    public void settNyPris(int nyPris){ 
        pris = nyPris; }

    @Override
    public String toString(){
        return ("Legemiddelnavn: " + navn + " - Pris: " + pris + " - Virkestoff: " + virkestoff + "mg - legemiddelID: " + legemiddelID); }
}
