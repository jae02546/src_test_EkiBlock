package io.github.jae02546.test_ekiblock

import kotlin.math.pow

object Question {

    var question: MutableList<Int> = mutableListOf()


    fun makeQuestion(numDigits: Int): MutableList<Int> {

        question = mutableListOf()
        for (v in 0 until numDigits) {
            question += v
        }

        return question
    }

    fun shl(pos: Int, numDigits: Int): Int {
        question[pos] = question[pos].shl(1) and (2.0.pow(numDigits).toInt() - 1)

        return question[pos]
    }

    fun inv(pos: Int, numDigits: Int): Int {
        question[pos] = question[pos].inv() and (2.0.pow(numDigits).toInt() - 1)

        return question[pos]
    }

    fun shr(pos: Int, numDigits: Int): Int {
        question[pos] = question[pos].ushr(1) and (2.0.pow(numDigits).toInt() - 1)

        return question[pos]
    }


}