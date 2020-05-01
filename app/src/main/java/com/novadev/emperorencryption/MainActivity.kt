package com.novadev.emperorencryption

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var alphabet26Letters = mutableListOf("A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
    private var alphabet27Letters = mutableListOf("A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")

    private var displacement = 10
    private var displacementAlphabet = mutableListOf<String>()
    private var displacementAlphabetRest = mutableListOf<String>()
    private var messageToDecrypt = "".toUpperCase().stripAccents()
    private var messageToEncrypt = "".toUpperCase().stripAccents()
//    wsxyb wkhswew cybbi tewz
    private var encryptMessage = "wsxyb wkhswew cybbi tewz".toUpperCase().stripAccents()
    private var decryptMessage = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var selectedList = alphabet26Letters
        prepareList(selectedList)

        encrypt(selectedList, messageToEncrypt)
        decrypt(selectedList, encryptMessage)

    }

    private fun prepareList(selectedList: MutableList<String>) {
        selectedList.forEachIndexed { index, letter ->
            // get letter rest
            if (index < displacement) {
                displacementAlphabetRest.add(letter)
            } else {
                // obtain rest of letters
                displacementAlphabet.add(letter)
            }
        }
        // add rest in the last postion of array
        displacementAlphabetRest.forEach {
            displacementAlphabet.add((displacementAlphabet.size - 1) + 1, it)

        }

        displacementAlphabet.forEach {
            Log.d("alphabet", it)
        }

    }

    private fun decrypt(selectedList: MutableList<String>, message: String) {
        val sb = StringBuilder()

        var messageLengthIndx = message.length
        var messageLength = message.length
        var zipList = selectedList.zip(displacementAlphabet)
        var ind = 0
        var decryptChars = message.toList()


        while (messageLengthIndx != 0) {
            zipList.forEachIndexed { index, it ->
                if (ind != messageLength) {
                    if (decryptChars[ind].isWhitespace()) {
                        decryptMessage = sb.append(" ").toString()
                        messageLengthIndx--
                        ind++
                    }else{
                        if (it.second == message[ind].toString()) {
                            var calculate = index + (selectedList.size - displacement)
                            decryptMessage = if(calculate < 0){
                                var positive = -calculate
                                var newCalculate = ((selectedList.size ) - positive)
                                sb.append(zipList[newCalculate].first).toString()
                            }else{
                                sb.append(it.first).toString()
                            }

                            messageLengthIndx--
                            ind++
                        }
                    }

                }

            }
        }

        textview.text = decryptMessage
    }

    private fun encrypt(selectedList: MutableList<String>, message: String) {
        val sb = StringBuilder()
        var chars = message.toList()

        var messageLengthIndx = message.length
        var messageLength = message.length
        var zipList = selectedList.zip(displacementAlphabet)
        var ind = 0

        while (messageLengthIndx != 0) {
            zipList.forEachIndexed { index, it ->
                if (ind != messageLength) {
                    if (chars[ind].isWhitespace()) {
                        encryptMessage = sb.append(" ").toString()
                        messageLengthIndx--
                        ind++
                    }else{
                        if (it.first == message[ind].toString()) {
                            var calculate = (index * 4).rem(selectedList.size)
                            encryptMessage = if(calculate < 0){
                                var positive = -calculate
                                var newCalculate = ((selectedList.size) - positive)
                                sb.append(zipList[newCalculate].second).toString()
                            }else{
                                sb.append(it.second).toString()
                            }
                            messageLengthIndx--
                            ind++
                        }
                    }
                }
            }
        }

        textview2.text = encryptMessage
    }

}
