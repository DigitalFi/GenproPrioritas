package com.example.j_zone.genproprioritas;

/**
 * Created by Muhammad on 12/9/2018.
 */

public class Bisnis {
    private String nmbisnislain;
    private String nmusaha;
    private String tglTerdaftar;

    public Bisnis(){

    }
    public Bisnis(String nmbisnislain, String nmusaha, String tglTerdaftar){
        this.nmbisnislain = nmbisnislain;
        this.nmusaha = nmusaha;
        this.tglTerdaftar = tglTerdaftar;
    }

    public String getNmbisnislain() {
        return nmbisnislain;
    }

    public void setNmbisnislain(String nmbisnislain) {
        this.nmbisnislain = nmbisnislain;
    }

    public String getNmusaha() {
        return nmusaha;
    }

    public void setNmusaha(String nmusaha) {
        this.nmusaha = nmusaha;
    }

    public String getTglTerdaftar() {
        return tglTerdaftar;
    }

    public void setTglTerdaftar(String tglTerdaftar) {
        this.tglTerdaftar = tglTerdaftar;
    }
}
