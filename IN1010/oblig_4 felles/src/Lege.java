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
     * @return et heltall med differansen mellom unicode-verdien til Lege 'a' subtrahert 'b'.
     */
    @Override
    public int compareTo(Lege o) {
        String a = this.navn.toLowerCase();
        String b = o.navn.toLowerCase();

        int r = a.compareTo(b);

        return r;
    }


    public void nyResept(Resept o){
        this.utskrevneResepter.leggTil(o);
    }

    public String hentResepter() {
        return utskrevneResepter.toString();
    }
}
