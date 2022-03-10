
// Abstrakte klasser kan ikke bli "instantiated", men de kan bli "subclassed"
// Lager dermed denne klassen som en abstrakt klasse da den skal ha tilhørende subklasser. 

abstract class Legemiddel {

    // Instansvariabler fra oppgavetekst:
    protected String navn;
    protected int pris;
    protected double virkestoff;
    protected int iD = 0;
    
    protected static int tellAntall = 0; //static som er protected og bare tilgjengelig for klassen og subklassen
    // protected for å brukes i klassen og subklassen


    protected Legemiddel(String navn, int pris, double virkestoff)
    {
        this.navn = navn;
        this.pris = pris;
        this.virkestoff = virkestoff;
        tellAntall+= 1;  //Når id opprettes øker antallet med 1 
        iD = tellAntall;
    }


    // Metoder for klassen Legemiddel. Setter disse til protected. 
    protected int hentId()
    { 
        return iD;
    }

    protected String hentNavn()
    {
        return navn;
    }

    protected int hentPris()
    {
        return pris;
    }

    protected double hentVirkestoff()
    {
        return virkestoff;
    }

    protected void settNyPris(int nyP)
    {
        pris = nyP; //pris blir satt til ny pris
        return;
    }



    public String toString() //Lager utskriftsvennlig string av info:
    {
        String legemiddelInfo = 
                                "\nID: " + hentId() +
                                "\nNavn: " + hentNavn() +
                                "\nPris: " + hentPris() +
                                "\nVirkestoff: " + hentVirkestoff();
        return legemiddelInfo;
    }

    // Her kunne man kanskje også bare refferert til selve variablene, altså "ID: " + iD, "Navn: " + navn, etc.. ? 


}