package com.example.seminar_04;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Masina.class}, version = 1)
public abstract class MasinaDatabase extends RoomDatabase {
    public abstract MasinaDAO masinaDao();
}