package com.example.seminar7_dam_2;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Masina.class}, version = 1)
abstract class MasinaDatabase extends RoomDatabase {
    public abstract MasinaDAO masinaDao();
}