package com.example.j_zone.genproprioritas;

/**
 * Created by Muhammad on 12/9/2018.
 */

public class Bisnis {
    private String id_bisnis_info;
    private String nmbisnislain;
    private String nmusaha;
    private String tglTerdaftar;
    private String merk;
    private String nm_usaha;
    private String jumlahkaryawan;
    private String jumlahcabang;
    private String omsettahunan;
    private String telepon;
    private String facebooks;
    private String instagrams;
    private String tentang;
    private String namalain;

    public Bisnis(){

    }
    public Bisnis(String id_bisnis_info, String nmbisnislain, String nmusaha, String tglTerdaftar,String merk,String nm_usaha,String jml_karyawan,String jml_cabang,String omset_tahunan,String no_tlp,String facebook,String instagram,String nm_usaha_lain,String tentang_usaha){
        this.id_bisnis_info = id_bisnis_info;
        this.nmbisnislain = nmbisnislain;
        this.nmusaha = nmusaha;
        this.merk = merk;
        this.tglTerdaftar = tglTerdaftar;
        this.nm_usaha = nm_usaha;
        this.jumlahkaryawan = jml_karyawan;
        this.jumlahcabang = jml_cabang;
        this.omsettahunan = omset_tahunan;
        this.telepon = no_tlp;
        this.facebooks = facebook;
        this.instagrams = instagram;
        this.tentang = tentang_usaha;
        this.namalain = nm_usaha_lain;

    }

    public String getTentang() {
        return tentang;
    }

    public void setTentang(String tentang) {
        this.tentang = tentang;
    }

    public String getNamalain() {
        return namalain;
    }

    public void setNamalain(String namalain) {
        this.namalain = namalain;
    }

    public String getId_bisnis_info() {
        return id_bisnis_info;
    }

    public void setId_bisnis_info(String id_bisnis_info) {
        this.id_bisnis_info = id_bisnis_info;
    }

    public String getNm_usaha() {
        return nm_usaha;
    }

    public void setNm_usaha(String nm_usaha) {
        this.nm_usaha = nm_usaha;
    }


    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
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

    public String getJumlahkaryawan() {
        return jumlahkaryawan;
    }

    public void setJumlahkaryawan(String jumlahkaryawan) {
        this.jumlahkaryawan = jumlahkaryawan;
    }

    public String getJumlahcabang() {
        return jumlahcabang;
    }

    public void setJumlahcabang(String jumlahcabang) {
        this.jumlahcabang = jumlahcabang;
    }

    public String getOmsettahunan() {
        return omsettahunan;
    }

    public void setOmsettahunan(String omsettahunan) {
        this.omsettahunan = omsettahunan;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getFacebooks() {
        return facebooks;
    }

    public void setFacebooks(String facebooks) {
        this.facebooks = facebooks;
    }

    public String getInstagrams() {
        return instagrams;
    }

    public void setInstagrams(String instagrams) {
        this.instagrams = instagrams;
    }
}
