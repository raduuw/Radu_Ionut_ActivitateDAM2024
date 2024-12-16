package com.example.seminar_04;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MasinaDAO {
    @Query("SELECT * FROM Masina")
    List<Masina> getAll();

    @Insert
    void insert(Masina masina);

    @Update
    void update(Masina masina);
}