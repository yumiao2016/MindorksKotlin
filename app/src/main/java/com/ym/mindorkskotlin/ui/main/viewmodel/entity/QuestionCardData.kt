package com.ym.mindorkskotlin.ui.main.viewmodel.entity

import com.ym.mindorkskotlin.model.db.entity.Option
import com.ym.mindorkskotlin.model.db.entity.Question

data class QuestionCardData(val question: Question, val options: List<Option>, var mShowCorrectOptions: Boolean = false)