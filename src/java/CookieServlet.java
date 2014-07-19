
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 * Použití cookies pro počítání přístupů daného klienta na webový server.
 *
 * @author Stefan Veres
 */
public class CookieServlet extends HttpServlet {

    private List<Pom> zoznam_klientovC = new ArrayList();
    //private int pocet;
    String pocet = "0";

    @Override
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {

        // přečtení parametru od klienta		
        String meno, priezvisko, pohlavie;
        int rokN;

        meno = request.getParameter("meno");
        priezvisko = request.getParameter("priezvisko");
        rokN = Integer.parseInt(request.getParameter("rok"));
        pohlavie = request.getParameter("pohlavie");

        boolean muz = false;
        if (pohlavie.equals("MUZ")) {
            muz = true;
        }

        // zjistíme seznam všech cookies
        Cookie[] cookies = request.getCookies();

        String str2 = "";//, str3 = "", str4 = "";

        if (cookies != null) {
            //str3 = "" + Integer.parseInt(cookies[0].getValue());
            if (Integer.parseInt(cookies[0].getValue()) >= 5) {
                str2 = "Pretoze sa tu stale dokola jak saleny registrujes, posledna registracia sa neulozi";

            } else {
                this.zoznam_klientovC.add(new Pom(meno, priezvisko, rokN, muz));
            }
        } else {
            pocet = "0";
            this.zoznam_klientovC.add(new Pom(meno, priezvisko, rokN, muz));
        }

        double avgAge = this.getAverageAge();
        int pocetAll = this.zoznam_klientovC.size();
        int pocetZ = this.getPocetZien();
        int pocetM = pocetAll - pocetZ;

        String value = String.format("Pocet klientov: "
            + "%d, z toho %d zien a %d muzov. Priemerny vek klientov: "
            + "%3.1f rokov", pocetAll, pocetZ, pocetM, avgAge);
        // příprava odpovědi pro klienta
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        // vytvoříme novou cookie a vložíme ji do hlavičky odpovědi
        try {
            pocet = "" + (Integer.parseInt(pocet) + 1);
        } catch (NumberFormatException nfe) {
            pocet = "999";
        }
        //str4 = pocet;
            
        Cookie c = new Cookie("pocet", pocet);
        response.addCookie(c);

        // konec práce s hlavičkami, tvorba těla odpovědi
        PrintWriter out = response.getWriter();
        out.printf("<p>%s</p><br/>", value);
        out.printf("<p>%s</p><br/>", str2);
        //out.printf("<p>COK VALUE: %s, STR3: %s, %s</p><br/>", str4, str3, str2);

        //odakz na stranku zpet:
        out.println("<a href=\"Get_DU4.xhtml\">ZPĚT</a>");
        out.println("</body>");
        out.println("</html>");
    }

    //1.
    /**
     * Vrati vek zaregistrovanej zo zadaneho roku narodenia a aktualneho roku.
     *
     * @param rokNarodenia rok narodenia.
     * @param aktualYear aktualny rok.
     * @return vek danej osoby.
     */
    private int zistiVek(int rokNarodenia) {
        int aktualYear = Calendar.getInstance().get(Calendar.YEAR);
        return aktualYear - rokNarodenia;
    }

    //2.
    /**
     * Vypocita aktualny priemerny vek zaregistrovanych ludi.
     *
     * @return priemerny vek vsetkych zaregistrovanych ludi.
     */
    private double getAverageAge() {
        double mean;

        Iterator it = this.zoznam_klientovC.iterator();

        Pom ser;
        int vek, sumaV = 0, citac = 0;

        while (it.hasNext()) {
            citac++;
            ser = (Pom) it.next();
            vek = zistiVek(ser.getRokN());
            sumaV += vek;
        }
        mean = (double) sumaV / (double) citac;

        return mean;
    }

    //3.
    /**
     * Zisti pocet zaregistrovanych zien.
     *
     * @return pocet zaregistrovanych zien.
     */
    private int getPocetZien() {
        Iterator it = this.zoznam_klientovC.iterator();

        Pom ser;
        int pocetZien = 0, citac = 0;
        boolean jeZena;

        while (it.hasNext()) {
            citac++;
            ser = (Pom) it.next();
            jeZena = !(ser.getPohlavie());
            if (jeZena) {
                pocetZien++;
            }
        }
        return pocetZien;
    }
}
