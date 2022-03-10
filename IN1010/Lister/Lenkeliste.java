package Lister;

abstract class Lenkeliste <T> implements Liste<T> {

    protected Node start = null; // start refererer til Node-objektet med forste dataelement

    class Node { // deklarerer indre klasse Node i Lenkeliste
        Node neste = null; // velger å lage dobbeltlenkede lister
        Node forrige = null;
        T data;
        Node(T x) { 
            data = x;  }

        // metoder for å sette og hente noder og data
        void settNeste(Node neste) {
            this.neste = neste; }

        void settForrige(Node forrige) {
            this.forrige = forrige; }
        
        Node hentNeste() {
            return neste; }

        Node hentForrige(){
            return forrige; }
        
        T hentData(){
            return data; }
    }


    // METODER: stoerrelse(), leggTil(T x), hent(), fjern(), toString()

    public int stoerrelse() { // returnere hvor mange elementer det er i listen
        int teller = 0;
        Node peker = start;
        while (peker != null){
            teller ++;
            peker = peker.neste;
        }
        return teller;
    }

    public void leggTil(T x) { // legger inn nytt element (på slutten av listen)
        Node nyNode = new Node(x);
        if (start == null) { // hvis listen er tom
            start = nyNode;
        } else {
            Node node = start;
            while (node.hentNeste() != null) {
                node = node.hentNeste();
            }
            node.settNeste(nyNode);
            nyNode.settForrige(node);
        }
    }

    public T hent() { // returnerer første element i listen, men fjerner det ikke
        return start.hentData(); 
    }

    public T fjern() throws UgyldigListeindeks{ // fjerne det første elementet i listen og returnerer det
        Node midl = start;
        if (midl == null) { // hvis det ikke går 
            throw new UgyldigListeindeks(0);
        }
        Node nyStart = midl.hentNeste();
        if (nyStart == null) {
            start = null;
            return midl.hentData();
        } else {
        nyStart.forrige = null;
        start = nyStart;
        return midl.hentData();
        }
    }
    
    @Override
    public String toString() { // bygger opp en svarstreng av elementene i listen
        Node node = start;
        String toString = "Data: [";

        for (int i = 0; i < this.stoerrelse(); i++) {
            toString = toString + node.hentData().toString() + ", "; // blir ikke så pent med et komma på slutten..
            node = node.neste;
        }
        return toString + "]";
    } 
}
