package com.vatsalya.monash.monster.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

/**
 * Created by vatsalyagoel on 10/4/17.
 */
public class Monster implements Parcelable {

    private long id;
    private String name;
    private int age;
    private String species;
    private int attackPower;
    private int health;

    public Monster() {
        this.id = 0;
        this.name = "Default User";
        this.age = 0;
        this.species = "Human";
        this.attackPower = 0;
        this.health = 0;
    }

    public Monster(String name, int age, String species, int attackPower, int health) {
        this.name = name;
        this.age = age;
        this.species = species;
        this.attackPower = attackPower;
        this.health = health;
    }

    private Monster(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.age = in.readInt();
        this.species = in.readString();
        this.attackPower = in.readInt();
        this.health = in.readInt();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        dest.writeLong(getId());
        dest.writeString(getName());
        dest.writeInt(getAge());
        dest.writeString(getSpecies());
        dest.writeInt(getAttackPower());
        dest.writeInt(getHealth());
    }

    /**
     * A parcel creator to implement monster as a parcel
     */
    public static final Parcelable.Creator<Monster> CREATOR
            = new Parcelable.Creator<Monster>() {
        public Monster createFromParcel(Parcel in) {
            return new Monster(in);
        }

        public Monster[] newArray(int size) {
            return new Monster[size];
        }
    };

    /**
     *
     * @return Returns the description of the monster
     */
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Name: %s\nAge: %d\nSpecies: %s\nAttackPower: %d\nHealth: %d",
                getName(), getAge(), getSpecies(), getAttackPower(), getHealth());
    }

    /**
     *
     * @return Returns a random value generated from attack power
     */
    public int getAttackValue() {
        Double random = Math.random()*100;
        return getAttackPower() + random.intValue();
    }
}
