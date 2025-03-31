package ee.oop.retseptid.scraper;

import java.util.ArrayList;
import java.util.List;

public class Toiduaine {
    private  String nimetus;
    private List<String> retsetid = new ArrayList<>();

    public Toiduaine(String nimetus) {
        this.nimetus = nimetus;
    }

    public String getNimetus() {
        return nimetus;
    }

    public List<String> getRetsetid() {
        return retsetid;
    }

    public void setRetsetid(List<String> retsetid) {
        this.retsetid = retsetid;
    }
}
