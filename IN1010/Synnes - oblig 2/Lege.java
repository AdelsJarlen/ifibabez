package Legemidler;

// Leger - Metoder: hentLege, toString()

public class Lege {

    String navn;

    Lege(String navn){
        this.navn = navn;
    }

    public String hentLege(){
        return navn; }

    @Override
    public String toString(){
        return ("Legenavn: " + navn);
    }
}
