package com.vatsalya.monash.monster.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;
import java.util.UUID;

/**
 * Created by vatsalyagoel on 10/4/17.
 */

public class Monster implements Parcelable {

    private String id;
    private String name;
    private int age;
    private String species;
    private int attackPower;
    private int health;

    public Monster() {
        this.id = "Default";
        this.name = "Default USer";
        this.age = 0;
        this.species = "Human";
        this.attackPower = 0;
        this.health = 0;
    }

    public Monster(String name, int age, String species, int attackPower, int health) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
        this.species = species;
        this.attackPower = attackPower;
        this.health = health;
    }

    private Monster(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.age = in.readInt();
        this.species = in.readString();
        this.attackPower = in.readInt();
        this.health = in.readInt();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());
        dest.writeString(getName());
        dest.writeInt(getAge());
        dest.writeString(getSpecies());
        dest.writeInt(getAttackPower());
        dest.writeInt(getHealth());
    }

    public static final Parcelable.Creator<Monster> CREATOR
            = new Parcelable.Creator<Monster>() {
        public Monster createFromParcel(Parcel in) {
            return new Monster(in);
        }

        public Monster[] newArray(int size) {
            return new Monster[size];
        }
    };

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Name: %s\nAge: %d\nSpecies: %s\nAttackPower: %d\nHealth: %d",
                getName(), getAge(), getSpecies(), getAttackPower(), getHealth());
    }

    public int getAttackValue() {
        Double random = Math.random()*100;
        return getAttackPower() + random.intValue();
    }
}
