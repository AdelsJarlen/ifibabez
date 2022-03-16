/* Klassedefinisjon for Parent-classen Lege.
Er IKKE abstract fordi det skal vaere mulig aa opprette objekter av klassen, 
altsaa vanlige leger. Men hvis legen er spesialist skal det i stedet opprettes
et objekt av child-klassen Spesialist.
*/

public class Lege implements Comparable<Lege>{

    /*******************/
    /* KLASSEVARIABLER */
    /*******************/

    private String navn;
    private IndeksertListe<Resept> utskrevneResepter = new IndeksertListe<>();

    /****************/
    /* KONSTRUKTOER */
    /****************/

    public Lege(String navn) {
        this.navn = navn;
    }

    /***********/
    /* METODER */
    /***********/

    public String hentNavn() {
        return navn;
    }

    @Override
    public String toString() {
        return "Navn: " + hentNavn();
    }

    /**
     * Sammenlikner to strenger basert på hver karakters unicode-verdi. 
     * Metoden fungerer ikke pålitelig med navn som inneholder spesialkarakterer som 'æ, ø og å'.
     * return et heltall med differansen mellom unicode-verdien til Lege 'a' subtrahert 'b'.
     */
    @Override
    public int compareTo(Lege lege) {
        String a = this.navn.toLowerCase();
        String b = lege.navn.toLowerCase();

        return a.compareTo(b);
    }

    public void nyResept(Resept ny_resept){
        this.utskrevneResepter.leggTil(ny_resept);
    }

    public IndeksertListe<Resept> hentResepter() {
        return utskrevneResepter;
    }

    public String printResepter() {
        return utskrevneResepter.toString();
    }

    /*
    HvitResept skrivHvitResept (Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift

    MilResept skrivMilResept (Legemiddel legemiddel, Pasient pasient) throws UlovligUtskrift

    PResept skrivPResept (Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift

    BlaaResept skrivBlaaResept (Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift


    class UlovligUtskrift extends Exception {
        UlovligUtskrift (Lege l, Legemiddel lm) {
        super(“Legen “+l.hentNavn()+“ har ikke lov til aa skrive ut ”+lm.hentNavn());
        } }
    
        (Denne klassen må også legges til i besvarelsen din.)
        
    */

}
