package com.ym.mindorkskotlin.model.db

import com.ym.mindorkskotlin.model.db.entity.Option
import com.ym.mindorkskotlin.model.db.entity.Question
import com.ym.mindorkskotlin.model.db.entity.User
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDbDataManager @Inject constructor(private val appDatabase: AppDatabase) {
    fun getAllUsers() = appDatabase.userDao().loadAll().toObservable()

    fun insertUser(user: User) = Observable.fromCallable { appDatabase.userDao().insert(user); return@fromCallable true }

    fun getAllQuestions() = appDatabase.questionDao().loadAll().toObservable()

    fun isQuestionEmpty() = appDatabase.questionDao().loadAll().flatMapObservable { questions -> Observable.just(questions.isEmpty()) }

    fun getOptionsForQuestionId(questionId : Long) = appDatabase.optionDao().loadAllByQuestionId(questionId).toObservable()

    fun isOptionsEmpty() = appDatabase.optionDao().loadAll().flatMapObservable { options -> Observable.just(options.isEmpty()) }

    fun saveQuestion(question: Question) = Observable.fromCallable { appDatabase.questionDao().insert(question); return@fromCallable true }

    fun saveQuestionList(questions: List<Question>) = Observable.fromCallable { appDatabase.questionDao().insertAll(questions); return@fromCallable true }

    fun saveOption(option: Option) = Observable.fromCallable { appDatabase.optionDao().insert(option); return@fromCallable true }

    fun saveOptionList(options: List<Option>) = Observable.fromCallable { appDatabase.optionDao().insertAll(options); return@fromCallable true }
}