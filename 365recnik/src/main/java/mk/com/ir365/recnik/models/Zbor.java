package mk.com.ir365.recnik.models;

import java.util.ArrayList;

public class Zbor {

    private String prevod;
    private String desc;
    private ArrayList<String> similarWords;

    public Zbor() {
        super();
        similarWords = new ArrayList<String>();
    }

    public Zbor(String prevod, String desc, ArrayList<String> similarWords) {
        super();
        this.prevod = prevod;
        this.desc = desc;
        this.similarWords = similarWords;
    }

    public String getPrevod() {
        return prevod;
    }

    public void setPrevod(String prevod) {
        this.prevod = prevod;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ArrayList<String> getSimilarWords() {
        return similarWords;
    }

    public void setSimilarWords(ArrayList<String> similarWords) {
        this.similarWords = similarWords;
    }

    @Override
    public String toString() {
        String s = "";
        for (String z : similarWords) {
            s += z + ",";
        }

        return "Zbor [prevod=" + prevod + ", desc=" + desc + "]" + s;
    }


}