package ee.oop.retseptid.scraper;

import com.codeborne.selenide.WebDriverRunner;

public class Peaklass {
    public static void main(String[] args) {
        Retseptiotsing retseptiotsing = new Retseptiotsing();
        retseptiotsing.start();
    }

    /*public static void main(String[] args) {
        System.out.println(Avaleht.getÜldInfo());
        boolean jätkaSamaToiduainega = false;
        boolean jätkaSamaRetseptiga = false;
        String toiduaine = "q";
        String menüüUrl = Andmekoguja.getVaikemenüüURL();
        // vaikeväärtus
        Retsept juhuslikRetsept = null;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Program exiting, closing browser...");
            WebDriverRunner.closeWebDriver();
        }));

        while(true){
            if (!jätkaSamaToiduainega){
                toiduaine = KasutajagaSuhtlemine.küsiKoostisosa();
                try {
                    menüüUrl = Andmekoguja.otsiKoostisosaJärgi(toiduaine);

                } catch (Exception e) {
                    KasutajagaSuhtlemine.veateade();
                    continue;
                }
            }
            if (toiduaine.equals("q")) {
                Andmekoguja.lõpeta();
                break;
            }
            if (toiduaine.equals("u")) {
                KasutajagaSuhtlemine.väljastaNõudedSisendile();
                continue;
            }
            if (!jätkaSamaRetseptiga) {
                try {
                    juhuslikRetsept = Andmekoguja.valiJuhuslikRetsept(menüüUrl);
                } catch (Exception e) {
                    KasutajagaSuhtlemine.veateade();
                    continue;
                }

            }
            if (juhuslikRetsept == null) {
                KasutajagaSuhtlemine.veateade();
                continue;
            }

            int jätkamiseNumber = KasutajagaSuhtlemine.küsiMidaEdasiTeha(juhuslikRetsept);
            switch (jätkamiseNumber)
                {
                case 1:
                    KasutajagaSuhtlemine.kuvaKoostisosad(juhuslikRetsept);
                    jätkaSamaRetseptiga = true;
                    jätkaSamaToiduainega = true;
                    break;
                case 2:
                    KasutajagaSuhtlemine.kuvaKoostisosadJaJuhised(juhuslikRetsept);
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
                    break;
                case 5:
                    jätkaSamaToiduainega = true;
                    toiduaine = "q";
                    break;
                }
        }
    }*/
}
