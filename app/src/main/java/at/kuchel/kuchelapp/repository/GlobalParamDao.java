package at.kuchel.kuchelapp.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import at.kuchel.kuchelapp.model.GlobalParamEntity;

/**
 * Created by bernhard on 19.03.2018.
 */

@Dao
public interface GlobalParamDao {

    @Query("SELECT * FROM GlobalParamEntity WHERE `key` LIKE :key LIMIT 1")
    GlobalParamEntity findByKey(String key);

    @Query("SELECT * FROM GlobalParamEntity")
    List<GlobalParamEntity> getAll();

    @Insert
    void insertAll(List<GlobalParamEntity> globalParamEntities);

    @Insert
    void insert(GlobalParamEntity globalParamEntity);

    @Update
    void update(GlobalParamEntity globalParamEntity);

    @Delete
    void delete(GlobalParamEntity globalParamEntity);

    @Query("DELETE FROM GlobalParamEntity WHERE `key` LIKE :key")
    void deleteByKey(String key);

}

