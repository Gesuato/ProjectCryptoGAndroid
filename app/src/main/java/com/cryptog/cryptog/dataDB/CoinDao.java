package com.cryptog.cryptog.dataDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CoinDao {
    @Query("SELECT * FROM coindb")
    List<CoinDB> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CoinDB> coinDBList);

    @Delete()
    void removeCoin(CoinDB coinDB);

}
