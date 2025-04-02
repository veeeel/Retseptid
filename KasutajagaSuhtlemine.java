package ee.oop.retseptid.scraper;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;

public class KasutajagaSuhtlemine {
    private static final Scanner sc = new Scanner(System.in, "UTF-8");
    private static String üldinfo = "Tere tulemast retseptiotsija programmi!\n" +
            "\n" +
            "See programm aitab sul leida maitsvaid retsepte, kui annad ette ühe toiduaine.\n" +
            "Näiteks kui sul on kodus \"kana\", saad teada, milliseid roogi võiksid sellega valmistada.\n" +
            "\n" +
            "Sisesta lihtsalt üks koostisosa (näiteks: kartul, porgand, õun) ja saad juhuse teel retsepti,\n" +
            "milles seda koostisosa kasutatakse.\n" +
            "\n"; // +
            //"Alustamiseks sisesta koostisosa ja vajuta Enter. ";

    /**
     * Programmi tutvustava teksti kuvamine
     */
    public static void väljastaÜldinfo() {
        System.out.println(üldinfo);
    }

    /**
     * Kasutajalt koostisos küsimine
     * @return Kasutaja sisestatud toiduaine
     */
    public static String küsiKoostisosa() {
        System.out.println("Retseptisoovituse saamiseks sisesta koostisosa.\n" +
                "Lõpetamiseks sisesta 'q'.");

        String toiduaine = sc.nextLine();

        /*
        String toiduaine = JOptionPane.showInputDialog(null,
                 "Retseptisoovituse saamiseks sisesta toiduaine.\n" +
                         "Lõpetamiseks sisesta 'q' ja vajuta enter.",
                    "Andmete sisestamine",  JOptionPane.QUESTION_MESSAGE);*/
        return toiduaine;
    }

    /**
     * Kasutajale valiku täpsustamise võimaldamine nami-nami lehel koostisosa järgi otsides avanevast rippmenüüst
     * @param võimalused Valikus olevatele toiduainetele vastavate objetide kogum
     * @return Kasutaja valitud tuiduaine
     */
    public  static int täpsustaKoostisosa(ElementsCollection võimalused) {
        System.out.println("Millist toiduainet sa silmas pead?");
        int järjeNr = 1;
        for (SelenideElement võimalus : võimalused) {
            System.out.println(" ".repeat(4) + järjeNr++ + ". " + võimalus.getText());
        }
        System.out.println("Sisesta number (1, ..., " + võimalused.size() + ")");
        int valik = 0;
        while (true) {
            valik = Integer.parseInt(sc.nextLine()) - 1;
            if (valik > võimalused.size() - 1) {
                System.out.println("Palun sisesta number, mis jääb vahemikku (1, ..., " + võimalused.size() + ")");
            } else break;
        }
        return valik;
    }

    /**
     * Koostisosa järgi otsimisele kehtivate nõuete väljastamine
     */
    public static void väljastaNõudedSisendile(){
        System.out.println("sisesta vähemalt 3 tähemärki");
    }

    /**
     * Kasutajale jätkamiseks võimalike tegevuste pakkumine
     * @param retsept Viimati leitud retseptile vastav klassi Retsept isend
     * @return Kasutaja valitud tegevusele vastav täisarv
     */
    public static int küsiMidaEdasiTeha(Retsept retsept){
        System.out.println("Leitud Retsept: " + retsept.getPealkiri());
        System.out.println(
                "\n Kuidas soovid jätkata\n" +
                "    1. Vaata koostisosi\n" +
                "    2. Vaata koostisosi ja valmistamise juhiseid\n" +
                "    3. Otsi sama toiduaine jaoks uus retsept\n" +
                "    4. Vali uus toiduaine\n" +
                "    5. Välju\n" +
                "Sisesta number (1, 2, 3, ...)"
                );

        String numberEdasi = sc.nextLine();
/*
        String nummberEdasi = JOptionPane.showInputDialog(null,
                "Leitud retsept: " + retsept.getPealkiri() + "\n\n Kuidas soovid jätkata?\n" +
                        "1. Vaata koostisosi\n" +
                        "2. Vaata koostisosi ja valmistamise juhiseid\n" +
                        "3. Otsi sama toiduaine jaoks uus retsept\n" +
                        "4. Vali uus toiduaine\n" +
                        "5. Välju\n\n Sisesta number (1, 2, 3, ...)",
                 "Andmete sisestamine",  JOptionPane.QUESTION_MESSAGE);*/

        return Integer.parseInt(numberEdasi);
    }

    /**
     * Viimati leitud retsepti koostisosade väljastamine konsoolile
     * @param retsept Vastav klassi Retsept isend
     */
    public static void kuvaKoostisosad(Retsept retsept){
        System.out.println( "\nKoostisosad:");
        System.out.println("=".repeat(50));
        List<String> koostisosad = retsept.getKoostisosad();
        for (String k : koostisosad) {
            System.out.println(k);
        }
        System.out.println("=".repeat(50));
    }

    /**
     * Viimati leitud retsepti koostisosade ja valmistamise juhiste väljastamine konsoolile
     * @param retsept Vastav klassi retsept isend
     */
    public static void kuvaKoostisosadJaJuhised(Retsept retsept){
        System.out.println(retsept.getPealkiri().toUpperCase());
        kuvaKoostisosad(retsept);
        System.out.println( "\nValmistamine:");
        System.out.println("=".repeat(50));
        List<String> juhised = retsept.getJuhised();
        for (String k : juhised) {
            System.out.println(" ".repeat(4) + k);
        }
        System.out.println("=".repeat(50));
    }

    /**
     * Kasutajale veateate väljastamine
     */
    public static void veateade() {
        System.out.printf("Vabandame, midagi läks valesti");
        /*
        JOptionPane.showMessageDialog(null,
                "Vabandame, midagi läks valesti",
                "Viga",
                JOptionPane.ERROR_MESSAGE);*/
    }
}
