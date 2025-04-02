package ee.oop.retseptid.scraper;

import com.codeborne.selenide.WebDriverRunner;

public class Retseptiotsing {
    private boolean jätkaSamaToiduainega = false;
    private boolean jätkaSamaRetseptiga = false;
    private String toiduaine = "";
    private String menüüUrl = "";
    private Retsept retsept = null;

    /**
     * Programmi töö juhtimine while-true tsüklis
     */
    public void start() {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Program exiting, closing browser...");
            WebDriverRunner.closeWebDriver();
        }));
        while(true){
            if (toiduaine.equals("q")) {
                Andmekoguja.lõpeta();
                break;
            }
            if (toiduaine.equals("u")) { // juhul kui koostisosa sisendiks antakse välem kui 3 tähte
                KasutajagaSuhtlemine.väljastaNõudedSisendile();
                continue;
            }

            uuendaMenüü(); // vajadusel valime uue koostisosa

            if (toiduaine.equals("q")) {
                Andmekoguja.lõpeta();
                break;
            }
            if (toiduaine.equals("u")) { // juhul kui koostisosa sisendiks antakse välem kui 3 tähte
                KasutajagaSuhtlemine.väljastaNõudedSisendile();
                continue;
            }

            uuendaRetsept(); // vajadusel valime uue retsepti

            if (retsept == null) {
                KasutajagaSuhtlemine.veateade();
                continue;
            }

            int jätkamiseNumber = KasutajagaSuhtlemine.küsiMidaEdasiTeha(retsept);
            switch (jätkamiseNumber)
            {
                case 1:
                    KasutajagaSuhtlemine.kuvaKoostisosad(retsept);
                    jätkaSamaRetseptiga = true;
                    jätkaSamaToiduainega = true;
                    break;
                case 2:
                    KasutajagaSuhtlemine.kuvaKoostisosadJaJuhised(retsept);
                    jätkaSamaRetseptiga = true;
                    jätkaSamaToiduainega = true;
                    break;
                case 3:
                    jätkaSamaRetseptiga = false;
                    jätkaSamaToiduainega = true;
                    break;
                case 4:
                    jätkaSamaRetseptiga = false;
                    jätkaSamaToiduainega = false;
                    Andmekoguja.getLeitudRetseptid().clear();
                    break;
                case 5:
                    Andmekoguja.lõpeta();
                    toiduaine = "q";
                    break;
            }
        }

    }

    /**
     * Kasutajalt uue koostisosa saamine ja selle järgi valikus olevate retseptide uuendamine
     */
    private void uuendaMenüü() {
        if (!jätkaSamaToiduainega) {
            while (true) {
                this.toiduaine = KasutajagaSuhtlemine.küsiKoostisosa();
                try {
                    menüüUrl = Andmekoguja.otsiKoostisosaJärgi(toiduaine);
                    break;
                } catch (Exception e) {
                    //e.printStackTrace();
                    System.out.println("viga menüü uuendamisel");
                    //KasutajagaSuhtlemine.veateade();
                }
            }
        }
    }

    /**
     * Valikus olevatest retseptidest uue juhusliku valiku tegemine
     */
    private void uuendaRetsept() {
        if (!jätkaSamaRetseptiga) {
            for (int i = 0; i < 5; i++) {
                try {
                    retsept = Andmekoguja.valiJuhuslikRetsept();
                    //if (retsept != null) System.out.println("sain retsepti. " + retsept.getPealkiri());
                    return;
                } catch (Exception e) {
                    System.out.println("viga retsepti leidmisel");
                    //e.printStackTrace();
                    break;
                }
            }

            System.out.println("viga retsepti leidmisel");
            KasutajagaSuhtlemine.veateade();
            uuendaMenüü();
            }


    }
}
