package com.example.quanlyxe.Viewmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViTriDiaLy {


    @SerializedName("KinhTuyen")
    @Expose
    private Double kinhTuyen;
    @SerializedName("ViTuyen")
    @Expose
    private Double viTuyen;



    public Double getKinhTuyen() {
        return kinhTuyen;
    }

    public void setKinhTuyen(Double kinhTuyen) {
        this.kinhTuyen = kinhTuyen;
    }

    public Double getViTuyen() {
        return viTuyen;
    }

    public void setViTuyen(Double viTuyen) {
        this.viTuyen = viTuyen;
    }

}