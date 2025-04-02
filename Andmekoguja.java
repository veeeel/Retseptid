package ee.oop.retseptid.scraper;

import com.codeborne.selenide.*;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.WebDriverRunner;

import java.util.ArrayList;
import java.util.List;

public class Andmekoguja {
    private static String pealehtURL = "https://nami-nami.ee/";
    //private static List<Retsept> retseptid = new ArrayList<>();
    private static List<String> leitudRetseptid = new ArrayList<>();

    /**
     * Viimasena otsitud toiduaine jaoks leitud retseptide listi tagastamine
     * @return String tüüpi list
     */
    public static List<String> getLeitudRetseptid() {
        return leitudRetseptid;
    }

    /**
     * nami-nami.ee veebilehe avamine
     */
    private static void avaVeeileht() {
        Configuration.headless = true; // me ei näe kuidas programm aknaid avab
        //if (WebDriverRunner.hasWebDriverStarted()) {
        //    WebDriverRunner.closeWindow();
        //}
        Selenide.open(pealehtURL);
        Selenide.sleep(2000);
        lubaKüpsised();
    }

    /**
     * Detailse otsingu avamine ja otsitava toiduaine sisestamine (abimeetod)
     * @param toiduaine Kasutaja poolt sisestatud toiduaine
     */
    private static void kasutaDetailsetOtsingut(String toiduaine) {
        $(By.id("search-extend")).scrollIntoView(true).shouldBe(visible).click(); // kontrollime, et nupp on nähtav
        SelenideElement koostisosadSisend = $("input[placeholder='Koostisosad']")
                .shouldBe(visible, enabled); // kontrollime, et tekstisisestus väli on aktivne
        koostisosadSisend.click();
        koostisosadSisend.setValue(toiduaine);
    }

    /**
     * Toiduaine järgi otsimisel avanenud rippmenüüst valiku tegemine kasutaja soovi järgi (abimeetod)
     */
    private static void teeValikRippmenüüst() {
        ElementsCollection rippmenüü = null;
        for (int i = 0; i < 50; i++) { // kontrollime, et rippmenüü valikud on veebilehel laadinud
            rippmenüü = $$("li.select2-results__option");
            if (rippmenüü.get(0).getText().equals("Otsin…")) sleep(500); // vajadusel proovime uuesti
            else break;
        }

        if (rippmenüü.size() == 1) { // kui on vaid üks võimalus valime selle automaatselt
            SelenideElement vaikevalik = rippmenüü.get(0);
            vaikevalik.scrollIntoView(true);
            vaikevalik.click();
        } else { // kui on mitu võimalust, küsime kasutaja sisendit
            SelenideElement kasutajaValik = rippmenüü.get(KasutajagaSuhtlemine.täpsustaKoostisosa(rippmenüü));
            kasutajaValik.scrollIntoView(true);
            kasutajaValik.click();
        }
    }

    /**
     * Retseptide otsimine toiduaine põhjal
     * @param toiduaine Kasutaja sisestatud toiduaine
     * @return Tagastab leitud retseptide menüü url-i
     */
    public static String otsiKoostisosaJärgi(String toiduaine) {
        if (toiduaine.equals("q")) return "q";
        if (toiduaine.length() < 3) return "u";

        avaVeeileht();
        kasutaDetailsetOtsingut(toiduaine);
        teeValikRippmenüüst();

        $(".btn-orange.btn-block.btn-lg").click(); // vajutame 'otsi' nupule
        return WebDriverRunner.getWebDriver().getCurrentUrl();
    }

    /**
     * Vajutab leitud retseptide menüüs korduvalt kuva rohkem (ei ole kasutusel, sest annab tihti errori)
     * @param n Arv, mitu korda kuva rohkem vajutada
     */
    private static void vajutaKuvaRohkem(int n) {
        // vajutame korduvalt 'kuva rohkem tulemusi' nuppu
        SelenideElement kuvaRohkem = null;
        for (int i = 0; i < n; i++) {
            kuvaRohkem = $("#more_recipes");
            if (!kuvaRohkem.exists() || !kuvaRohkem.isDisplayed()) break;
            sleep(500);
            if (!kuvaRohkem.isDisplayed()) break;
            try {
                kuvaRohkem.scrollIntoView(true).shouldBe(visible).click();
                sleep(500);
            } catch (Exception e) {
                System.out.println("viga kuva rohkem vajutamisel");
                e.printStackTrace();
            }
        }
    }

    /**
     * Leitud retseptidest juhusliku valiku tagastamine
     * @return Juhuslik retsept, milles esineb kasutaja poolt sisestatud koostisosa
     */
    public static Retsept valiJuhuslikRetsept(){
        String retseptiUrl = "";

        if (leitudRetseptid.isEmpty()) { // kui otsime antud toiduaine jaoks retsepti esimest korda loeme leitud ...
            //vajutaKuvaRohkem(10); ... leitud retseptide lingid listi
            ElementsCollection nähtavadRetseptid = $$("div.recipe_brick section.item-link a");
            for (SelenideElement i : nähtavadRetseptid) {
                //System.out.println(i.getText());
                String link = (i.getAttribute("href"));
                if (link != null) leitudRetseptid.add( i.getAttribute("href"));
            }

            if (leitudRetseptid.isEmpty()) {
                System.out.println("ühtegi retsepti ei leitud");
                return null;
            }
        }

        //int juhuslikNumber = new java.util.Random().nextInt(leitudRetseptid.size());
        int juhuslikNumber = (int)(Math.random() * leitudRetseptid.size()); // genereerime juhusliku numbri...
        retseptiUrl = leitudRetseptid.get(juhuslikNumber); // ... vahemikus nullist kuni leitud retseptide arv miinus 1

        Retsept retsept = loeRetseptiAndmed(retseptiUrl);
        return retsept;
    }

    /**
     * Veebilehelt retsepti andmete lugemine ning programmis vastava retsepti isendi loomine
     * @param url Retsepti aadress nami-nami lehel
     * @return Klassi Retsept isend
     */
    public static Retsept loeRetseptiAndmed(String url){
        open(url);
        sleep(500);
        String pealkiri = $("h1.title").shouldBe(visible).getText();
        ElementsCollection koostisosad = $$("section.block.text-center");
        SelenideElement valmistamine = $$("section.block")
                .filterBy(attribute("class", "block"))
                .first();
        ElementsCollection juhised = valmistamine.$$("p");

        Retsept retsept = new Retsept(pealkiri, url, koostisosad, juhised);
        return retsept;
    }

    /**
     * Veebilehe küpsiste aksepteerimine
     */
    private static void lubaKüpsised() {
        try {
            SelenideElement terms = Selenide.$(By.id("cookie-consent-btn-accept-all"));
            if (terms.exists() && terms.isDisplayed()) terms.click();
        } catch (Exception e) {}
    }

    public static void lõpeta(){
        closeWebDriver();
    }
}
