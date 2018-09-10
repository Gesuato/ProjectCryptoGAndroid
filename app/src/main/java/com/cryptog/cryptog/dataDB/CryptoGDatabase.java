package com.cryptog.cryptog.dataDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {CoinDB.class}, version = 1,exportSchema = false)
public abstract class CryptoGDatabase extends RoomDatabase{
    public abstract CoinDao coinDao();
}
