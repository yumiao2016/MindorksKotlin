package com.ym.mindorkskotlin.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ym.mindorkskotlin.model.db.dao.OptionDao
import com.ym.mindorkskotlin.model.db.dao.QuestionDao
import com.ym.mindorkskotlin.model.db.dao.UserDao
import com.ym.mindorkskotlin.model.db.entity.Option
import com.ym.mindorkskotlin.model.db.entity.Question
import com.ym.mindorkskotlin.model.db.entity.User

@Database(entities = [User::class, Question::class, Option::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    companion object{
        const val DB_NAME = "mindorks_mvvm.db"
    }

    abstract fun optionDao(): OptionDao

    abstract fun questionDao(): QuestionDao

    abstract fun userDao(): UserDao
}
