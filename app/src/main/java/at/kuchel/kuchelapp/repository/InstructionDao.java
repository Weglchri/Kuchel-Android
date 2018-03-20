package at.kuchel.kuchelapp.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import at.kuchel.kuchelapp.model.Instruction;
import at.kuchel.kuchelapp.model.Recipe;

/**
 * Created by bernhard on 19.03.2018.
 */

@Dao
public interface InstructionDao {

    @Query("SELECT * FROM instruction")
    List<Instruction> getAll();

    @Insert
    void insertAll(List<Instruction> instruction);

    @Update
    void update(Instruction instruction);

    @Delete
    void delete(Instruction instruction);
}

