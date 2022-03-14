public class TestLege {
    public static void main(String[] args) {
        Lege bob = new Lege("Dr. Bob Kåre Ytrearne");
        Lege yasmin = new Lege("Dr. Yasmin Urdula");
        Lege aese = new Lege("Dr. Æse Bergeland");
        Lege zara = new Lege("Dr. Zara Zulu");

        zara.nyResept(new HvitResept(new Vanedannende("Zoritox", 200, 245, 67), zara, 1008658765, 3));
        zara.nyResept(new HvitResept(new Vanlig("Paracet", 1000, 199), zara, 612658765, 2));
        zara.nyResept(new BlaaResept(new Narkotisk("Morfex", 3, 798, 200), zara, 69986, 25));



        System.out.println(bob.compareTo(yasmin));
        System.out.println(yasmin.compareTo(bob));
        System.out.println(bob.compareTo(bob));
        System.out.println(aese.compareTo(zara));

        System.out.println(zara.hentResepter());
    }
}
