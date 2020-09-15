package com.application.a4_school;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME= "db_4school";
    private static final String TABLE_CONTACTS = "jadwal_pelajaran";
    private static final String KEY_ID = "id";
    private static final String KEY_KELAS = "kelas";
    private static final String KEY_PELAJARAN = "pelajaran";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTRACTS_TABLE = "CREATE TABLE" + TABLE_CONTACTS + "{"
                + KEY_ID + "INTEGER PRIMARY KEY" + KEY_KELAS + " TEXT " + KEY_PELAJARAN + " TEXT " + "}";
        db.execSQL(CREATE_CONTRACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXIST " + TABLE_CONTACTS);

        onCreate(db);
    }

    void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_KELAS, contact.getKelas());
        values.put(KEY_PELAJARAN, contact.getPelajaran());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] {KEY_ID, KEY_KELAS, KEY_PELAJARAN}, KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));

        return contact;
    }

    public List<Contact> getAllContacts(){
        List<Contact> contactList = new ArrayList<>();

        String selectQuery = "SELECT * FROM" + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setKelas(cursor.getString(1));
                contact.setPelajaran(cursor.getString(2));

                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }

    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_KELAS, contact.getKelas());
        values.put(KEY_PELAJARAN, contact.getPelajaran());

        return db.update(TABLE_CONTACTS, values, KEY_ID + "=?",
                new String[] {String.valueOf(contact.getId())});
    }

    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    public int getContactsCount(){
        String countQuery = "SELECT * FROM" + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}
