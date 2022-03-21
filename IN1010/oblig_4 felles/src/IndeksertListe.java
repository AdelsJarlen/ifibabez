/* IMPLEMENTASJON AV LINKEDLIST MED INDEKSMETODER UTEN JAVA.UTIL
Har alle de samme variabler og metoder som Lenkeliste, 
men har i tillegg mulighet for aa legge til, hente og fjerne vha. indeks.

- leggTil(int pos, T x) legger til element paa gitt indeks
- hent(int pos) returnerer elementet paa gitt indeks uten aa endre det 
- fjern(int pos) sletter og returnerer elementet paa gitt indeks 
- sett(int pos) oppdaterer verdien for elementet paa gitt indeks */

public class IndeksertListe<T> extends Lenkeliste<T> {
    
    /* KONSTRUKTOER */
    public IndeksertListe() {
        super(); // kaller konstruktoeren til Lenkeliste
    }
    
    /**
     * LEGGER TIL OBJEKT PAA GITT INDEKS
     * @param pos = indeks der det skal legges til 
     * @param x = objektet som skal inn i node.data
     */
    public void leggTil(int pos, T x) {
        // 1. lage midlertidig node med ny data
        Node ny_node = new Node(x);

        // 2. sjekke om pos er gyldig
        // lovlig pos: 0<=pos<=stoerrelse(), 
        if (pos < 0 || pos > super.stoerrelse()) {
            throw new UgyldigListeindeks(pos);

        } else if (pos == 0) {
            // 2.1 legger til foran hvis indeks er 0
            super.leggTilFoerst(x);

        } else if (pos == super.stoerrelse()) {
            // 2.2 legger til sist hvis indeksen er lik stoerrelsen
            super.leggTil(x); 

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

    /**
     * METODE FOR AA RETURNERE ELEMENT (DATA) PAA OPPGITT INDEKS
     * @param pos = indeks som skal hentes
     * @return = dataen til noden paa gitt indeks
     */
    public T hent(int pos) {
        // 1. sjekker om indeksen er gyldig
        if(pos < 0 || pos >= super.stoerrelse() || super.stoerrelse() == 0){
            throw new UgyldigListeindeks(pos);

        } else {
            // 2. setter midlertidig node som start
            Node skal_hentes = start;

            // 3. loekke frem til riktig indeksplass
            for (int i = 0; i < pos; i++) {
                skal_hentes = skal_hentes.neste;
            }

            // 4. returnerer dataen til den midlertidige noden
            return skal_hentes.data;
          }
    }

    /**
     * METODE FOR AA RETURNERE ELEMENT (SELVE NODEN) PAA OPPGITT INDEKS
     * @param pos = indeks som skal hentes
     * @return = Node-objektet paa gitt indeks
     */
    public Node hentNode(int pos) {
        // 1. setter midlertidig node, begynner paa start
        Node skal_hentes = start;

        // 2. gaar gjennom listen frem til riktig indeks
        for (int i = 0; i < pos; i++) {
            skal_hentes = skal_hentes.neste;
        }

        // 3. returnerer den midlertidige noden
        return skal_hentes;
    }

    /* METODE FOR AA OPPDATERE ELEMENT PAA OPPGITT INDEKS */
    public void sett(int pos, T x) {
        // 1. sjekker om indeksen er gyldig eller om listen er tom
        if (pos < 0 || pos >= stoerrelse || stoerrelse() == 0) {
            throw new UgyldigListeindeks(pos);

        } else {   
            // 2. endrer data paa noden, men beholder pekerne til forrige og neste
            Node skal_endres = hentNode(pos);
            skal_endres.data = x;
        }
   }

    /* METODE FOR AA FJERNE ELEMENT PAA OPPGITT INDEKS */
    public T fjern(int pos) {
        // 1. setter midlertidig node, begynner paa start
        Node skal_fjernes = start;

        // 2. sjekker om indeksen er gyldig
        if (pos < 0 || pos >= super.stoerrelse() || super.stoerrelse() == 0) {
            throw new UgyldigListeindeks(pos);

        } else if (pos == 0) {
            // 2.1 hvis indeks = 0, fjerner foerste element
            start = start.neste;

            // hvis start finnes, fjerner pekeren
            if (start != null){
                start.forrige = null;
            }

            stoerrelse--; // reduserer stoerrelse med 1

        } else if (pos == stoerrelse-1) {
            // 2.2 hvis indeks er sist, fjerner siste element
            skal_fjernes = slutt;
            slutt = slutt.forrige;
            slutt.neste = null;
            stoerrelse--; // reduserer stoerrelse med 1

        } else {
            // 3. loekke frem til riktig indeks
            for (int i = 0; i < pos; i++) {
                skal_fjernes = skal_fjernes.neste;
            }

            // 3.1 lagrer noden som er foran indeksplassen
            Node foer = skal_fjernes.forrige;

            // 3.2 lagrer noden som er etter indeksplassen
            Node etter = skal_fjernes.neste;

            // 3.3 den forrige noden peker naa paa noden etter indeks
            foer.neste = skal_fjernes.neste;

            // 3.4 noden etter indeks peker naa paa den som var foer indeks
            etter.forrige = foer;

            // 3.5 reduserer stoerrelse med 1
            stoerrelse--;
        }
        
        // 3. returnerer dataen til den midlertidige noden som har blitt fjernet
        return skal_fjernes.data;
    }

}
