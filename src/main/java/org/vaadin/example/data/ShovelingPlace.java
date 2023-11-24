package org.vaadin.example.data;

public class ShovelingPlace {

    private Long id;
    private String ward;
    private String town;
    private String jyo;
    private String tyo;
    private String ban;
    private String gou;
    private String others;
    private boolean availability;
    private Long snow;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getJyo() {
        return jyo;
    }

    public void setJyo(String jyo) {
        this.jyo = jyo;
    }

    public String getTyo() {
        return tyo;
    }

    public void setTyo(String tyo) {
        this.tyo = tyo;
    }

    public String getBan() {
        return ban;
    }

    public void setBan(String ban) {
        this.ban = ban;
    }

    public String getGou() {
        return gou;
    }

    public void setGou(String gou) {
        this.gou = gou;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public Long getSnow() {
        return snow;
    }

    public void setSnow(Long snow) {
        this.snow = snow;
    }
}
