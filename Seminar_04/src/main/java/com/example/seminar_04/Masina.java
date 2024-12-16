package com.example.seminar_04;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Masina implements Parcelable {
    @PrimaryKey
    @NonNull
    private String model;
    private int an;
    private String brand;
    private boolean esteElectrica;
    private int caiPutere;

    public Masina(String model, int an, String brand, boolean esteElectrica, int caiPutere) {
        this.model = model;
        this.an = an;
        this.brand = brand;
        this.esteElectrica = esteElectrica;
        this.caiPutere = caiPutere;
    }

    protected Masina(Parcel in) {
        model = in.readString();
        an = in.readInt();
        brand = in.readString();
        esteElectrica = in.readByte() != 0;
        caiPutere = in.readInt();
    }

    public static final Creator<Masina> CREATOR = new Creator<Masina>() {
        @Override
        public Masina createFromParcel(Parcel in) {
            return new Masina(in);
        }

        @Override
        public Masina[] newArray(int size) {
            return new Masina[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(model);
        dest.writeInt(an);
        dest.writeString(brand);
        dest.writeByte((byte) (esteElectrica ? 1 : 0));
        dest.writeInt(caiPutere);
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isEsteElectrica() {
        return esteElectrica;
    }

    public void setEsteElectrica(boolean esteElectrica) {
        this.esteElectrica = esteElectrica;
    }

    public int getCaiPutere() {
        return caiPutere;
    }

    public void setCaiPutere(int caiPutere) {
        this.caiPutere = caiPutere;
    }

    public String getKey() {
        return model;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Masina{");
        sb.append("model='").append(model).append('\'');
        sb.append(", an=").append(an);
        sb.append(", brand='").append(brand).append('\'');
        sb.append(", esteElectrica=").append(esteElectrica);
        sb.append(", caiPutere=").append(caiPutere);
        sb.append('}');
        return sb.toString();
    }


}
