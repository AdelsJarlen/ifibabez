public class Main {
    public static void main(String[] args) throws Exception {

        /* KODE FOR AA KJOERE HELE LEGESYSTEMET */

        Legesystem legesystem = new Legesystem();

        legesystem.lesFraFil("legedata.txt");

        legesystem.kjoer();
    }
}
