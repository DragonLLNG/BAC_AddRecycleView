package edu.uncc.hw04.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class Drink implements Parcelable {
    Date addedOn;
    double drinkSize, alcoholPercentage;

    public Drink() {
    }

    public Drink(Date addedOn, double drinkSize, double alcoholPercentage) {
        this.addedOn = addedOn;
        this.drinkSize = drinkSize;
        this.alcoholPercentage = alcoholPercentage;
    }

    protected Drink(Parcel in) {
        drinkSize = in.readDouble();
        alcoholPercentage = in.readDouble();
    }

    public static final Creator<Drink> CREATOR = new Creator<Drink>() {
        @Override
        public Drink createFromParcel(Parcel in) {
            return new Drink(in);
        }

        @Override
        public Drink[] newArray(int size) {
            return new Drink[size];
        }
    };

    @Override
    public String toString() {
        return "Drink{" +
                "addedOn=" + addedOn +
                ", drinkSize=" + drinkSize +
                ", alcoholPercentage=" + alcoholPercentage +
                '}';
    }

    public Date getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }

    public double getDrinkSize() {
        return drinkSize;
    }

    public void setDrinkSize(double drinkSize) {
        this.drinkSize = drinkSize;
    }

    public double getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public void setAlcoholPercentage(double alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(drinkSize);
        dest.writeDouble(alcoholPercentage);
    }
}
