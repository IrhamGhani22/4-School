package com.application.a4_school;

public class Contact {

    int id;
    String kelas;
    String pelajaran;

    public  Contact(){

    }

    public Contact(String kelas, String pelajaran){
        this.kelas = kelas;
        this.pelajaran = pelajaran;
    }
    public Contact(int id, String kelas, String pelajaran){
        this.id = id;
        this.kelas = kelas;
        this.pelajaran = pelajaran;
    }

    public int getId() {
        return id;
    }

    public String getKelas() {
        return kelas;
    }

    public String getPelajaran() {
        return pelajaran;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public void setPelajaran(String pelajaran) {
        this.pelajaran = pelajaran;
    }
}
