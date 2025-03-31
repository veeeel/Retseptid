package ee.oop.retseptid.scraper;

import com.codeborne.selenide.WebDriverRunner;

public class Retseptiotsing {
    private boolean jätkaSamaToiduainega = false;
    private boolean jätkaSamaRetseptiga = false;
    private String toiduaine = "";
    private String menüüUrl = Andmekoguja.getVaikemenüüURL();
    private Retsept retsept = null;

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
            if (toiduaine.equals("u")) {
                KasutajagaSuhtlemine.väljastaNõudedSisendile();
                continue;
            }

            uuendaMenüü();
            System.out.println("toiduaine " + toiduaine);

            uuendaRetsept();

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

    private void uuendaMenüü() {
        if (!jätkaSamaToiduainega) {
            while (true) {
                this.toiduaine = KasutajagaSuhtlemine.küsiKoostisosa();
                try {
                    menüüUrl = Andmekoguja.otsiKoostisosaJärgi(toiduaine);
                    System.out.println(menüüUrl);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("viga menüü uuendamisel");
                    KasutajagaSuhtlemine.veateade();
                }
            }
        }
        System.out.println("jätkan sama toiduainega");
    }

    private void uuendaRetsept() {
        if (!jätkaSamaRetseptiga) {
            System.out.println("jätkan uue retseptiga");
            for (int i = 0; i < 100; i++) {
                try {
                    retsept = Andmekoguja.valiJuhuslikRetsept(menüüUrl, toiduaine);
                    //if (retsept != null) System.out.println("sain retsepti. " + retsept.getPealkiri());
                    return;
                } catch (Exception e) {
                    System.out.println("viga retsepti uuendamisel");
                    e.printStackTrace();
                    break;
                }
            }

            System.out.println("viga retsepti uuendamisel");
            KasutajagaSuhtlemine.veateade();
            uuendaMenüü();
            }


    }
}
