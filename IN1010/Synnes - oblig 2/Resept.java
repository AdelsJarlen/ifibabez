package Legemidler;

// Resept - Referanse til: legemiddel, legen som har skrevet ut resepten, ID-en til den pasienten som eier resepten
// Resept - Metoder: hentId, hentLegemiddel, hentLege, hentPasientId, hentReit, bruk, reseptFarge, prisAaBetale, toString

abstract class Resept {
    
    private int reseptID; // hvert objekt av klassen skal ha id som ingen andre objekter av samme klassen har
    protected Legemiddel legemiddel; // referanse til et legemiddel
    protected Lege utskrivendeLege; // referanse til legen som har skrevet ut resepten
    protected int pasientID; // ID-en til den pasienten som eier resepten
    protected int reit; // antall ganger som er igjen på resepten
    static int tellerID = 1; // variabel som teller ID

    Resept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientID, int reit){
        this.legemiddel = legemiddel;
        this.utskrivendeLege = utskrivendeLege;
        this.pasientID = pasientID;
        this.reit = reit;
        reseptID = tellerID;
        tellerID ++;
    }

    public int hentId(){
        return reseptID; }

    public Legemiddel hentLegemiddel(){ // henter tilhørende Legemiddel
        return legemiddel; }

    public Lege hentLege(){ // henter utskrivende Lege
        return utskrivendeLege; }

    public int hentPasientId(){
        return pasientID; }

    public int hentReit(){
        return reit; }

    //Forsøker å bruke resepten én gang. Returner false om resepten alt er oppbrukt, ellers returnerer den true.
    public boolean bruk(){
        if (reit > 0){
            reit -= 1;
            return true;
        } else {
            return false;
        } }

    abstract public String farge(); // Returnerer reseptens farge. Enten “hvit” eller "blaa"

    abstract public int prisAaBetale(); // Returnerer prisen pasienten må betale.

    @Override // Skriver ut resept-informasjon 
    public String toString(){
        return(legemiddel.toString() + " - reseptID: " + reseptID + " - Utskrivende Lege : " + utskrivendeLege + " - pasientID: " + pasientID + " - Reit: " + reit);
    }
}
