/* IMPLEMENTASJON AV QUEUE SOM LINKEDLIST UTEN AA BRUKE JAVA.UTIL
Har alle de samme variablene og metodene som Lenkeliste, fordi
lenkelisten i denne obligen fungerer akkurat som en koe, altsa FIFO
(first-in-first-out). Man legger inn bakerst, og tar ut forrest */

public class Koe <T> extends Lenkeliste <T> {
    
    /* KONSTRUKTOER */
    public Koe() {
        super(); // kaller konstruktoeren til Lenkeliste
    }
}
