package Lister;

class Prioritetskoe<T extends Comparable<T>> extends Lenkeliste<T> {

    @Override // setter inn elementer i sortert rekkefølge fra minst til størst
    public void leggTil(T x) {
      Node nyNode = new Node(x);

        if (this.stoerrelse() == 0) { // hvis listen er tom
            super.leggTil(x);
            return; }

        if (this.stoerrelse() == 1) {
            if (start.data.compareTo(x) > 0) {
                nyNode.settNeste(start);
                start.settForrige(nyNode);
                super.start = nyNode;
                return; } }
        
        Node node = start;
        while (node != null) { 
            if (node.data.compareTo(x) > 0 ) { //hvis data på posisjonen skal foran x
                if (node.hentForrige() == null) {
                    super.start = nyNode;
                } else {
                    Node prevNode = node.hentForrige();
                    prevNode.settNeste(nyNode);
                    nyNode.settForrige(prevNode);
                }
            nyNode.settNeste(node);
            node.settForrige(nyNode);
            return;
          }
          node = node.hentNeste();
        }
        // listen er ikke er tom og ingen verdier i listen er større:
        super.leggTil(x);
    }
}