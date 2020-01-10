package com.ym.mindorkskotlin.model.db.dao

import androidx.room.*
import com.ym.mindorkskotlin.model.db.entity.User
import io.reactivex.Single

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM users")
    fun loadAll(): Single<List<User>>
}
