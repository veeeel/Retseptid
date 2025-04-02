package ee.oop.retseptid.scraper;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.SetValueOptions;
import com.codeborne.selenide.TypeOptions;

import java.util.ArrayList;
import java.util.List;

public class Retsept {
    private String pealkiri;
    private String url;
    private List<String> koostisosad = new ArrayList<>();
    private List<String> juhised = new ArrayList<>();

    public Retsept(String pealkiri, String url, ElementsCollection koostisosad, ElementsCollection juhised) {
        for (SelenideElement koostisosa : koostisosad) this.koostisosad.add(koostisosa.getText());
        for (SelenideElement lõik : juhised) this.juhised.add(lõik.getText());

        this.pealkiri = pealkiri;
        this.url = url;
    }

    public String getPealkiri() {
        return pealkiri.charAt(0) + pealkiri.substring(1).toLowerCase();
    }

    public String getUrl() {
        return url;
    }

    public List<String> getKoostisosad() {
        return new ArrayList<String>(koostisosad);
    }

    public List<String> getJuhised() {
        return juhised;
    }
}
