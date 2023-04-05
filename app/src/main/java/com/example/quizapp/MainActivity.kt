package com.example.quizapp

import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.view.View.OnClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {
    private var index: Int =0
    private var score = 0

    private fun AssetManager.readFile(fileName: String) = open(fileName)
        .bufferedReader()
        .use{it.readText()}

    private fun isCorrect(click: Boolean, correctAns: Boolean): Int{
        //isCorrect will check if the selected and is right or wrong
        index++
        return if(click == correctAns) 1 //if right answer selected return 1
        else 0
    }

    private fun showNextQuestion(questionList: JsonArray, questionId:TextView){
        // showNextQuestion will show the next question after selection of true or false
        if(index<5){
            questionId.text = questionList[index].asJsonObject["question"].toString()
            //if it is not the last question show next question
        }
        else{
            Toast.makeText(this@MainActivity, "total score $score", Toast.LENGTH_LONG).show()
            //if it is last question show the score
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        setContentView(R.layout.quiz_layout)

        val jsonToString = this.assets.readFile("data.json")
        val jsonParser: JsonParser = JsonParser()
        val jsonElement: JsonElement = jsonParser.parse(jsonToString)
        val listOfQuestion: JsonArray = jsonElement.asJsonArray
        Questions.listOfQuestion = listOfQuestion //updating the json array in a singleton class Questions

        val trueButton: Button = findViewById<Button>(R.id.buttonTrue) //finding ids from XML
        val falseButton: Button = findViewById<Button>(R.id.buttonFalse) //finding ids from XML
        val question: TextView = findViewById(R.id.question) //finding ids from XML

        question.text = listOfQuestion[index].asJsonObject["question"].toString()
        //updating the first question ONLY on the phone screen

        falseButton.setOnClickListener{
            score += isCorrect(false, listOfQuestion[index].asJsonObject["answer"].asBoolean)
            //if correct option selected score+1 else score+0
            showNextQuestion(listOfQuestion,question)

        }

        trueButton.setOnClickListener{
            score += isCorrect(true, listOfQuestion[index].asJsonObject["answer"].asBoolean)
            //if correct option selected score+1 else score+0
            showNextQuestion(listOfQuestion,question)
        }

    }
}

















//        class OnTrue: OnClickListener{
//         override fun onClick(v: View?) {
//             Log.d(loggingTag, "correct opetion selected")
//             Toast.makeText(this@MainActivity, "Correct option", Toast.LENGTH_LONG).show()
//         }
//        }
//
//        trueButton.setOnClickListener(OnTrue())

//        class OnFalse: OnClickListener{
//            override fun onClick(v: View?) {
//                Log.d(loggingTag, "Wrong option selected")
//                Toast.makeText(this@MainActivity, "Incorrect option", Toast.LENGTH_LONG).show()
//            }
//        }
//
//        falseButton.setOnClickListener(OnFalse())


        //annonymous function
//        trueButton.setOnClickListener(object:OnClickListener{
//            override fun onClick(v:View?){
//                Log.d(loggingTag, "correct opetion selected")
//                Toast.makeText(this@MainActivity, "Correct option", Toast.LENGTH_LONG).show()
//            }
//        })
//
//
//        falseButton.setOnClickListener(object:OnClickListener{
//            override fun onClick(v: View?) {
//                Log.d(loggingTag, "Wrong option selected")
//                Toast.makeText(this@MainActivity, "Incorrect option", Toast.LENGTH_LONG).show()
//            }
//        })


//        trueButton.setOnClickListener{
//            Log.d(loggingTag, "Right option selected")
//            Toast.makeText(this@MainActivity, "Correct option", Toast.LENGTH_LONG).show()
//        }
//        falseButton.setOnClickListener{
//            Log.d(loggingTag, "Wrong option selected")
//            Toast.makeText(this@MainActivity, "Incorrect option", Toast.LENGTH_LONG).show()
//        }

//    }
//}