/* EGEN IMPLEMENTASJON AV JAVA.UTILS PRIORITYQUEUE 
- elementer legges inn i listen basert paa prioritet (fra Comparable)
- i denne obligen er prioritert rekkefoelge fra "minst" til "stoerst"
- hent() og fjern() henter eller fjerner det foerste elementet i listen, 
altsaa det minste */

public class Prioritetskoe<T extends Comparable<T>> extends Lenkeliste<T> {
    
    /* KONSTRUKTOER */
    public Prioritetskoe() {
        super(); // kaller paa konstruktoeren til lenkeliste
    }

    /* LEGGER TIL I SORTERT REKKEFOELGE, FRA MINST TIL STOERST */
    @Override
    public void leggTil(T x) {

        // 1. legger til hvis listen er tom
        if (super.stoerrelse() == 0) {
            super.leggTil(x);
            return;
        }

        // 2. lagrer midlertidig node for loekken
        Node gjeldende = start;

        // 3. loekke gjennom listen
        for (int i = 0; i < stoerrelse ; i++) {
            
            // 3.1 gaar videre til neste node alle ganger unntatt foerste
            if (i != 0) {
                gjeldende = gjeldende.neste;
            }

            // 3.2 legger til paa indeks naar gjeldende er stoerre enn x
            if (gjeldende.data.compareTo(x) > 0) {
                super.leggTil(i, x); // metoden fra IndeksertListe, ogsaa inkludert i Lenkeliste
                return;
            }
        }

        // 4. legger til sist dersom x er stoerre enn alle noder i listen
        super.leggTil(x);
    }      
}
