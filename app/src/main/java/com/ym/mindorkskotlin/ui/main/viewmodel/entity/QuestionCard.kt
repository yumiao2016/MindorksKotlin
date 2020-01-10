package com.ym.mindorkskotlin.ui.main.viewmodel.entity

import android.graphics.Color
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mindorks.placeholderview.annotations.*
import com.ym.mindorkskotlin.R

/**
 * Created by amitshekhar on 08/07/17.
 */

@NonReusable
@Layout(R.layout.card_layout)
class QuestionCard(private val mQuestionCardData: QuestionCardData) {

    @View(R.id.btn_option_1)
    lateinit var mOption1Button: Button

    @View(R.id.btn_option_2)
    lateinit var mOption2Button: Button

    @View(R.id.btn_option_3)
    lateinit var mOption3Button: Button

    @View(R.id.iv_pic)
    lateinit var mPicImageView: ImageView

    @View(R.id.tv_question_txt)
    lateinit var mQuestionTextView: TextView

    @Click(R.id.btn_option_1)
    fun onOption1Click() {
        showCorrectOptions()
    }

    @Click(R.id.btn_option_2)
    fun onOption2Click() {
        showCorrectOptions()
    }

    @Click(R.id.btn_option_3)
    fun onOption3Click() {
        showCorrectOptions()
    }

    @Resolve
    private fun onResolved() {
        mQuestionTextView.text = mQuestionCardData.question.questionText
        if (mQuestionCardData.mShowCorrectOptions) {
            showCorrectOptions()
        }

        for (i in 0..2) {
            var button: Button? = null
            when (i) {
                0 -> button = mOption1Button
                1 -> button = mOption2Button
                2 -> button = mOption3Button
            }

            button?.text = mQuestionCardData.options[i].optionText

            if (mQuestionCardData.question.imgUrl != null) {
                Glide.with(mPicImageView.context).load(mQuestionCardData.question.imgUrl).into(mPicImageView)
            }
        }
    }

    private fun showCorrectOptions() {
        mQuestionCardData.mShowCorrectOptions = true
        for (i in 0..2) {
            val option = mQuestionCardData.options[i]
            var button: Button? = null
            when (i) {
                0 -> button = mOption1Button
                1 -> button = mOption2Button
                2 -> button = mOption3Button
            }
            if (button != null) {
                if (option.isCorrect) {
                    button.setBackgroundColor(Color.GREEN)
                } else {
                    button.setBackgroundColor(Color.RED)
                }
            }
        }
    }
}
