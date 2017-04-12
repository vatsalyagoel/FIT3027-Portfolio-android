package com.vatsalya.monash.monster.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vatsalya.monash.monster.models.Monster;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vatsalyagoel on 10/4/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "monsterDB";

    // Monsters table name
    private static final String TABLE_MONSTER = "monster";

    // Monsters Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";
    private static final String KEY_SPECIES = "species";
    private static final String KEY_ATTACK_POWER = "attackPower";
    private static final String KEY_HEALTH = "health";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MONSTER_TABLE = "CREATE TABLE " + TABLE_MONSTER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_AGE + " INTEGER," + KEY_SPECIES + " TEXT,"
                + KEY_ATTACK_POWER + " INTEGER," +  KEY_HEALTH + " INTEGER" + ")";
        db.execSQL(CREATE_MONSTER_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONSTER);

        // Create tables again
        onCreate(db);
    }

    // Adding new monster
    public long addMonster(Monster monster) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, monster.getName()); // Contact Name
        values.put(KEY_AGE, monster.getAge());
        values.put(KEY_SPECIES, monster.getSpecies());
        values.put(KEY_ATTACK_POWER, monster.getAttackPower());
        values.put(KEY_HEALTH, monster.getHealth());

        // Inserting Row
        long id = db.insert(TABLE_MONSTER, null, values);
        db.close(); // Closing database connection

        return id;
    }

    // Getting single monster
    public Monster getMonster(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MONSTER, new String[] { KEY_ID,
                        KEY_NAME, KEY_AGE, KEY_SPECIES, KEY_ATTACK_POWER, KEY_HEALTH }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Monster monster = new Monster(cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                cursor.getString(3), Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(2))
        );
        monster.setId(Integer.parseInt(cursor.getString(0)));
        // return contact
        return monster;
    }

    // Getting All Monsters
    public List<Monster> getAllMonsters() {
        List<Monster> monsterList = new ArrayList<Monster>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MONSTER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Monster monster = new Monster(cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                        cursor.getString(3), Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(2))
                );
                monster.setId(Integer.parseInt(cursor.getString(0)));
                // Adding contact to list
                monsterList.add(monster);
            } while (cursor.moveToNext());
        }

        // return contact list
        return monsterList;
    }

    public List<Monster> searchMonstersWithName(String queryString) {
        List<Monster> monsterList = new ArrayList<Monster>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_MONSTER , new String[] { KEY_ID,
                KEY_NAME, KEY_AGE, KEY_SPECIES, KEY_ATTACK_POWER, KEY_HEALTH }, KEY_NAME + " LIKE ?",
                new String[] {"%"+ DatabaseUtils.sqlEscapeString(queryString) + "%" }, null, null, null, null );

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Monster monster = new Monster(cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                        cursor.getString(3), Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(2))
                );
                monster.setId(Integer.parseInt(cursor.getString(0)));
                // Adding contact to list
                monsterList.add(monster);
            } while (cursor.moveToNext());
        }

        // return contact list
        return monsterList;
    }

    // Getting monsters Count
    public int getMonstersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MONSTER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    // Updating single monster
    public int updateMonster(Monster monster) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, monster.getName()); // Contact Name
        values.put(KEY_AGE, monster.getAge());
        values.put(KEY_SPECIES, monster.getSpecies());
        values.put(KEY_ATTACK_POWER, monster.getAttackPower());
        values.put(KEY_HEALTH, monster.getHealth());

        // updating row
        return db.update(TABLE_MONSTER, values, KEY_ID + " = ?",
                new String[] { String.valueOf(monster.getId()) });
    }

    // Deleting single monster
    public void deleteMonster(Monster monster) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MONSTER, KEY_ID + " = ?",
                new String[] { String.valueOf(monster.getId()) });
        db.close();
    }
}
