/***********************************
 * Objektorienteeritud programmeerimine. LTAT.03.003
 * 2024/2025 kevadsemester
 *
 * Rühmatöö 1
 * Teema: Retsepti scraper
 *
 * Autorid: Gloria Veskimeister, Sandra-Liis Mägi
 *
 **********************************/

package ee.oop.retseptid.scraper;

/**
 * Programmi käivitamine
 */
public class Peaklass {
    public static void main(String[] args) {
        KasutajagaSuhtlemine.väljastaÜldinfo();
        Retseptiotsing retseptiotsing = new Retseptiotsing();
        retseptiotsing.start();
    }
}
