/* ABSTRACT "MASTER"-KLASSE FOR ALLE KLASSENE I OBLIGEN
IMPLEMENTASJON AV LINKEDLIST UTEN AA BRUKE JAVA.UTIL
Implementerer interfacet Liste<T>
Bruker hjelpeklassen Node for aa haandtere elementer

int stoerrelse = antall elementer i listen
Node start = foerste element i listen
Node slutt = siste element i listen

- metoden stoerrelse() returnerer variabelen stoerrelse
- leggTil(T x) legger til et element sist i listen og oppdaterer var slutt
- leggTilFoerst(T x) legger til et element foerst i listen og oppdaterer var start
- leggTil(int pos, T x) legger til et element paa oppgitt indeks og forskyver resten
- hent() returnerer det foerste elementet i listen uten aa endre det
- fjern() sletter det foerste elementet i listen og returnere det 
- toString() gaar gjennom listen og skriver ut informasjon om elementene */

import java.util.Iterator;

abstract class Lenkeliste <T> implements Liste <T> { 
    
    /* GLOBALE VARIABLER FOR LENKELISTEN */
    protected int stoerrelse;
    protected Node start;
    protected Node slutt;

    /* HJELPEKLASSE NODE SOM HAANDTERER ELEMENTENE OG PEKERE */  
    public class Node {

        /* INSTANSVARIABLER FOR HVER NODE */
        T data;
        Node neste = null;
        Node forrige = null;

        /* KONSTRUKTOER FOR NODE */
        Node(T data) {
            this.data = data;
        }
    }

    public class LenkeListeIterator implements Iterator<T> {

        private Node nesteNode = start;

        @Override
        public T next() {
            Node denneNode = nesteNode;
            nesteNode = nesteNode.neste;

            return denneNode.data;
        }

        @Override
        public boolean hasNext() {
            return nesteNode != null;
        }

    }

    /* KONSTRUKTOER FOR LENKELISTEN */
    public Lenkeliste() {
        this.stoerrelse = 0; // default for stoerrelse
        this.start = null; // default = tom
        this.slutt = null; // default = tom
    }

    public Iterator<T> iterator() {
        LenkeListeIterator iterator = new LenkeListeIterator();
        return iterator;
    }

    /* RETURNERER ANTALL ELEMENTER I LENKELISTEN */
    public int stoerrelse() {
        return stoerrelse;
    }

    /* SETTER INN NY NODE AV X SIST I LENKELISTEN */
    public void leggTil(T x) {
        // 1. lage den nye noden med data fra argument
        Node ny_node = new Node(x);

        // 3. sette neste for den nye noden til null, siden den skal vaere sist
        ny_node.neste = null;

        // 4. hvis den start noden allerede er null, sett inn noden her
        if (start == null) {
            ny_node.forrige = null; // bare 1 node, saa forrige er null
            start = ny_node; // start node er naa den nye noden
            stoerrelse++; // oeker stoerrelse med 1
            return;
        }

        // 2. lagre midlertidig node for aa loekke gjennom listen 
        Node gjeldende = start;

        // 5. ellers maa vi gaa gjennom hele listen
        // saa lenge nodens neste ikke er null er vi fortsatt midt i listen
        // naar nodens neste == null har vi naadd slutt
        while (gjeldende.neste != null) {
            gjeldende = gjeldende.neste;
        }
       
        // 6. ny node blir den "neste" av den slutt noden
        gjeldende.neste = ny_node;  

        // 7. ny node peker tilbake til den slutt noden
        ny_node.forrige = gjeldende; 

        // 8. den nye noden blir den siste noden
        slutt = ny_node; 

        // 9. oeker stoerrelse med 1
        stoerrelse++; 
    }

    /* LEGGER TIL NY NODE AV X HELT FOERST I LISTEN */
    public void leggTilFoerst(T x) {
        // 1. lager ny node med dataen fra argumentet
        Node ny_node = new Node(x);

        // 2. skal vaere foerst i listen, saa neste maa vaere den gamle
        // start. Nodens forrige skal vaere null
        ny_node.neste = start;
        ny_node.forrige = null;

        // 3. hvis start finnes, setter starts forrige til den nye noden
        if (start != null) {
            start.forrige = ny_node;
        }

        // 4. setter den nye noden som ny start
        start = ny_node;

        // 5. oeker stoerrelse med 1
        stoerrelse++;
    }

    /* LEGGER TIL NODE AV X PAA GITT INDEKS I LISTEN */
    public void leggTil(int pos, T x) {
        // 1. lage midlertidig node med ny data
        Node ny_node = new Node(x);

        // 2. sjekke om pos er gyldig
        // lovlig pos: 0<=pos<=stoerrelse(), 
        if (pos < 0 || pos > stoerrelse()) {
            throw new UgyldigListeindeks(pos);

        } else if (pos == 0) {
            // 2.1 legger til foran hvis indeks er 0
            leggTilFoerst(x);

        } else if (pos == stoerrelse()) {
            // 2.2 legger til sist hvis indeksen er lik stoerrelsen
            leggTil(x); 

        } else {
            // 3. setter midlertidig node som den start
            Node gjeldende = start;

            // 3.1 gaar gjennom listen frem til riktig indeks
            for (int i = 0; i < pos; i++) {
                gjeldende = gjeldende.neste;
            }

            // 3.2 lagrer noden som var foer indeksplassen
            Node forrige_node = gjeldende.forrige;

            // 3.3 den forrige noden peker paa ny_node
            forrige_node.neste = ny_node;

            // 3.4 den nye noden peker tilbake paa forrige
            ny_node.forrige = forrige_node;

            // 3.5 den nye noden peker frem til indeksplassen
            ny_node.neste = gjeldende;

            // 3.6 noden paa indeksplassen peker tilbake paa den nye noden
            gjeldende.forrige = ny_node;

            // 3.7 oeker stoerrelsen med 1
            stoerrelse++;
        }
    }

    /* RETURNERER DEN FOERSTE NODEN I LENKELISTEN */
    public T hent() {
        // 1. throw exception hvis listen er tom (ingenting aa hente)
        if (stoerrelse() == 0) {
            throw new UgyldigListeindeks(0);

        // 2. ellers returnerer den dataen til foerste element
        } else {
            return start.data;
        }
    }

    /* FJERNER OG RETURNERER DEN FOERSTE NODEN I LENKELISTEN */
    public T fjern() {
        // 1. lagre noden midlertidig for aa kunne returnere den
        Node skal_fjernes = start;

        // 2. throw UgyldigListeindeks hvis listen er tom (ingenting aa fjerne)
        if (stoerrelse() == 0) {
            throw new UgyldigListeindeks(0);

        // 3. ellers oppdater "start" og endre stoerrelse
        } else {
            // 3.1 ny start = den neste i listen
            start = start.neste; 

            // 3.2 hvis ny start finnes skal forrige settes til null
            if (start != null) {
                start.forrige = null; 
            }

            // 3.3 reduserer stoerrelse med 1
            stoerrelse--;

            // 3.4 returnerer dataen til noden som ble fjernet
            return skal_fjernes.data;
        }
    }

    /* LAGRER ALL DATAEN I LISTEN I EN STRENG */
    @Override 
    public String toString() {
        // 1. definerer en streng for aa lagre hver linje i listen
        String resultat = "####################\n";
        
        // 2. setter midlertidig node som den foerste
        Node gjeldende = start;

        // 3. definerer en teller til loekken
        int teller = 1;

        // 4. loekke saa lenge det er flere elementer i listen
        while (gjeldende != null) {
            resultat = resultat.concat(teller + ". " + gjeldende.data + "\n"); // oppdaterer resultat med dataen fra elementet
            gjeldende = gjeldende.neste; // gaar videre til neste element
            teller++; // oeker telleren med 1
        }

        // 5. legger til en skillelinje til
        resultat = resultat.concat("####################");

        // 6. returnerer den ferdige Stringen
        return resultat;
    }
}
