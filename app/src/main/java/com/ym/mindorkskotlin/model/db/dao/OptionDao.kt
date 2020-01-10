package com.ym.mindorkskotlin.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ym.mindorkskotlin.model.db.entity.Option
import io.reactivex.Single

@Dao
interface OptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(option: Option)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(options: List<Option>)

    @Query("SELECT * FROM options")
    fun loadAll(): Single<List<Option>>

    @Query("SELECT * FROM options WHERE question_id = :questionId")
    fun loadAllByQuestionId(questionId: Long?): Single<List<Option>>
}
