/* IMPLEMENTASJON AV STACK SOM LINKEDLIST UTEN JAVA.UTIL
Har alle de samme variabler og metoder som Lenkeliste, men det er
en viktig endring. Fordi en stack (stabel) er en LIFO-struktur, 
(last-in-first-out) maa leggTil-metoden legge til elementet paa 
"toppen" av stabelen, altsaa foerst i listen. 

- leggTil(T x) setter inn et element helt foerst i listen */

public class Stabel<T> extends Lenkeliste<T> {
    
    /* KONSTRUKTOER */
    public Stabel() {
        super(); // kaller konstruktoeren til Lenkeliste
    }

    /* OVERSKRIVER leggTil(T x) FRA LENKELISTE */
    /* LEGGER INN ELEMENTET FOERST I STEDET FOR SIST */
    @Override
    public void leggTil(T x) {
        
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

}
