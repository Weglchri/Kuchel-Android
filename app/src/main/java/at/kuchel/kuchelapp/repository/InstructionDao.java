package at.kuchel.kuchelapp.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import at.kuchel.kuchelapp.model.InstructionEntity;

/**
 * Created by bernhard on 19.03.2018.
 */

@Dao
public interface InstructionDao {

    @Query("SELECT * FROM InstructionEntity")
    List<InstructionEntity> getAll();

    @Insert
    void insertAll(List<InstructionEntity> instructionEntity);

    @Update
    void update(InstructionEntity instructionEntity);

    @Delete
    void delete(InstructionEntity instructionEntity);
}

