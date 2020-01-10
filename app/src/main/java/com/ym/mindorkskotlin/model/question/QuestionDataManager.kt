package com.ym.mindorkskotlin.model.question

import android.content.Context
import com.google.gson.Gson
import com.google.gson.internal.`$Gson$Types`
import com.ym.mindorkskotlin.model.db.AppDbDataManager
import com.ym.mindorkskotlin.model.db.entity.Option
import com.ym.mindorkskotlin.model.db.entity.Question
import com.ym.mindorkskotlin.ui.main.viewmodel.entity.QuestionCardData
import com.ym.mindorkskotlin.utils.CommonUtils
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionDataManager @Inject constructor(private val dbDataManager: AppDbDataManager, private val context: Context) {
    companion object {
        private const val SEED_DATABASE_QUESTIONS = "seed/questions.json"
        private const val SEED_DATABASE_OPTIONS = "seed/options.json"
    }

    fun seedDatabaseQuestions(): Observable<Boolean> =
        dbDataManager.isQuestionEmpty()
            .flatMap{ isEmpty ->
                if (isEmpty) {
                    val type = `$Gson$Types`.newParameterizedTypeWithOwner(null, List::class.java, Question::class.java)
                    val questionList = Gson().fromJson<List<Question>>(CommonUtils.loadJSONFromAsset(context, SEED_DATABASE_QUESTIONS), type)
                    return@flatMap dbDataManager.saveQuestionList(questionList)
                }
                Observable.just(false)
            }

    fun seedDatabaseOptions(): Observable<Boolean> =
        dbDataManager.isOptionsEmpty()
            .flatMap{ isEmpty ->
                if (isEmpty) {
                    val type = `$Gson$Types`.newParameterizedTypeWithOwner(null, List::class.java, Option::class.java)
                    val optionList = Gson().fromJson<List<Option>>(CommonUtils.loadJSONFromAsset(context, SEED_DATABASE_OPTIONS), type)
                    return@flatMap dbDataManager.saveOptionList(optionList)
                }
                Observable.just(false)
            }

    fun getQuestionCardData() : Observable<List<QuestionCardData>> =
            dbDataManager.getAllQuestions()
                .flatMap { questions -> Observable.fromIterable(questions) }
                .flatMap { question -> Observable.zip<List<Option>, Question, QuestionCardData>(dbDataManager.getOptionsForQuestionId(question.id),
                    Observable.just(question),
                    BiFunction { options, question1 -> QuestionCardData(question1, options) }) }
                .toList()
                .toObservable()
}