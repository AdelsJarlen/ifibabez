package Lister;

class Stabel <T> extends Lenkeliste<T> {

    @Override
    public void leggTil(T x) { // legge inn et nytt element (først i listen)
        Node nyNode = new Node(x);
        if (start == null) { // hvis listen er tom
            start = nyNode;
        } else {
            Node node = start;
            start = nyNode;
            node.settForrige(nyNode);
            nyNode.settNeste(node);
        }
    }
}