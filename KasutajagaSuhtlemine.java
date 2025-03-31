package ee.oop.retseptid.scraper;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;

public class KasutajagaSuhtlemine {
    private static final Scanner sc = new Scanner(System.in, "UTF-8");

    public static String küsiKoostisosa() {
        String toiduaine = JOptionPane.showInputDialog(null,
                 "Retseptisoovituse saamiseks sisesta toiduaine.\n" +
                         "Lõpetamiseks sisesta 'q' ja vajuta enter.",
                    "Andmete sisestamine",  JOptionPane.QUESTION_MESSAGE);
        return toiduaine;
    }

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

        //String valitudNumber = JOptionPane.showInputDialog(null,
                //"Sisesta number (1, 2, 3, ...) ", "Andmete sisestamine", JOptionPane.QUESTION_MESSAGE);

        return valik;
    }

    public static void väljastaNõudedSisendile(){
        System.out.println("sisesta vähemalt 3 tähemärki");
    }

    public static int küsiMidaEdasiTeha(Retsept retsept){
        /*System.out.println("\n Kuidas soovid jätkata\n" +
                "1. Vaata koostisosi\n" +
                "2. Vaata koostisosi ja valmistamise juhiseid\n" +
                "3. Otsi sama toiduaine jaoks uus retsept\n" +
                "4. Vali uus toiduaine\n" +
                "5. Välju");*/

        String nummberEdasi = JOptionPane.showInputDialog(null,
                "Leitud retsept: " + retsept.getPealkiri() + "\n\n Kuidas soovid jätkata?\n" +
                        "1. Vaata koostisosi\n" +
                        "2. Vaata koostisosi ja valmistamise juhiseid\n" +
                        "3. Otsi sama toiduaine jaoks uus retsept\n" +
                        "4. Vali uus toiduaine\n" +
                        "5. Välju\n\n Sisesta number (1, 2, 3, ...)",
                 "Andmete sisestamine",  JOptionPane.QUESTION_MESSAGE);

        return Integer.parseInt(nummberEdasi);
    }

    public static void kuvaKoostisosad(Retsept retsept){
        System.out.println( "\nKoostisosad:");
        System.out.println("=".repeat(50));
        List<String> koostisosad = retsept.getKoostisosad();
        for (String k : koostisosad) {
            System.out.println(" ".repeat(4) + k);
        }
        System.out.println("=".repeat(50));
    }

    public static void kuvaKoostisosadJaJuhised(Retsept retsept){
        kuvaKoostisosad(retsept);
        System.out.println( "\nValmistamine:");
        System.out.println("=".repeat(50));
        List<String> juhised = retsept.getJuhised();
        for (String k : juhised) {
            System.out.println(" ".repeat(4) + k);
        }
        System.out.println("=".repeat(50));
    }

    public static void veateade() {
        JOptionPane.showMessageDialog(null,
                "Vabandame, midagi läks valesti",
                "Viga",
                JOptionPane.ERROR_MESSAGE);
    }
}
