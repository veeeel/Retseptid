package ee.oop.retseptid.scraper;

public class Avaleht {
    private static String üldInfo = "Tere tulemast retseptiotsija programmi!\n" +
            "\n" +
            "See programm aitab sul leida maitsvaid retsepte, kui annad ette ühe toiduaine.\n" +
            "Näiteks kui sul on kodus \"kana\", saad teada, milliseid roogi võiksid sellega valmistada.\n" +
            "\n" +
            "Sisesta lihtsalt üks koostisosa (näiteks: kartul, porgand, õun) ja saad nimekirja retseptidest,\n" +
            "milles seda koostisosa kasutatakse.\n" +
            "\n" +
            "Alustamiseks sisesta koostisosa ja vajuta Enter. ";

    public static String getÜldInfo() {
        return üldInfo;
    }

    public static void setÜldInfo(String üldInfo) {
        Avaleht.üldInfo = üldInfo;
    }
}
