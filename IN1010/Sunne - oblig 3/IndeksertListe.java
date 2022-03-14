package Lister;

class IndeksertListe <T> extends Lenkeliste<T> {

    // setter inn x på posisjon pos der 0<=pos<=stoerrelse(), alle elementer bak i listen forskyves
    public void leggTil(int pos, T x) throws UgyldigListeindeks{ 
        Node nyNode = new Node(x);
        Node node = start;
        int teller = 0;

        if (pos == 0) { // hvis posisjonen om blir gitt er null
            if (super.start != null) { // hvis listen ikke er tom
                nyNode.settNeste(super.start);
                super.start.settForrige(nyNode);
            }
            super.start = nyNode;
            return;
        }

        if (pos > this.stoerrelse() || pos < 0){ // hvis det ikke går å sette inn
            throw new UgyldigListeindeks(pos); }

        while(node.hentNeste() != null && teller != pos) { // finner fremt til riktig node
            node = node.hentNeste();
            teller ++; 
        }

        if (node.hentNeste() == null && this.stoerrelse() - 1 < pos) {
            nyNode.settForrige(node);
            node.settNeste(nyNode);
        } else { // setter inn node på posisjon og forskyver de andre nodene etter
            Node prevNode = node.hentForrige();
            prevNode.settNeste(nyNode);
            node.settForrige(nyNode);
            nyNode.settNeste(node);
            nyNode.settForrige(prevNode);
        }
    }
    
    // erstatte elementet i posisjon pos med x. Lovlig pos er 0<=pos<stoerrelse().
    public void sett (int pos, T x) throws UgyldigListeindeks{
        Node node = start;
        int teller = 0;

        if (pos > this.stoerrelse() - 1 || pos < 0) { // hvis det ikke går å sette inn
            throw new UgyldigListeindeks(pos); } 

        while(teller != pos) { // finner frem til riktig node
            node = node.hentNeste();
            teller ++; }

        node.data = x; // bytter ut innholdet i noden med nytt innhold 
    }

    // henter elementet i gitt posisjon der 0<=pos<stoerrelse(). Elementet skal bli stående i listen.
    public T hent(int pos) {
        Node node = start;
        int teller = 0;

        if (pos > this.stoerrelse() - 1 || pos < 0) { // hvis det ikke går å sette inn
            throw new UgyldigListeindeks(pos); }

        while(teller != pos) {  // finner fremt til riktig node
            node = node.hentNeste();
            teller ++; }
        
        return node.hentData(); // returnerer elementet på gitt posisjon
    }

    // fjerne elementet i posisjon pos (der0<=pos<stoerrelse()) og returnere det.
    public T fjern(int pos) {
        Node node = start;
        int teller = 0;

        if (pos > this.stoerrelse() - 1 || pos < 0) { // hvis det ikke går å sette inn
            throw new UgyldigListeindeks(pos); }

        while(teller != pos) { // finner fremt til riktig node
            node = node.hentNeste();
            teller ++; }

        if (start == null) { // listen er tom
            return null; 
        } else if (node.hentForrige() == null) { // hvis noden er først i listen
            return this.fjern();
        } else if ( node.hentNeste() == null) { // hvis noden er sist
            Node prevNode = node.hentForrige();
            prevNode.settNeste(null);
            return node.hentData();
        } else { 
            Node prevNode = node.hentForrige();
            Node nesteNode = node.hentNeste();

            prevNode.settNeste(nesteNode);
            nesteNode.settForrige(prevNode);
            return node.hentData();
        }
    }
}
