package com.novadev.emperorencryption

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var alphabet26Letters = mutableListOf("A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
    var alphabet27Letters = mutableListOf("A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")


    var displacement = 3
    var displacementAlphabet = mutableListOf<String>()
    var displacementAlphabetRest = mutableListOf<String>()

    // TODO:  MIRAR LOS ESPACIOS QUE NO LOS COGE
    var messageToDecrypt = "HOLA QUE TAL"

    var encryptMessage = ""
    var decryptMessage = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var selectedList = alphabet27Letters
        prepareList()

        encrypt(selectedList)
        decrypt(selectedList)

    }

    private fun prepareList() {
        alphabet27Letters.forEachIndexed { index, letter ->
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
    }

    private fun decrypt(selectedList: MutableList<String>) {
        val sb = StringBuilder()

        var messageLengthIndx = messageToDecrypt.length
        var messageLength = messageToDecrypt.length
        var zipList = selectedList.zip(displacementAlphabet)
        var ind = 0

        while (messageLengthIndx != 0) {
            zipList.forEachIndexed { _, it ->
                if (ind != messageLength) {
                    if (it.second == encryptMessage[ind].toString()) {
                        decryptMessage = sb.append(it.first).toString()
                        messageLengthIndx--
                        ind++
                    }
                }

            }
        }

        textview.text = decryptMessage
    }

    private fun encrypt(selectedList: MutableList<String>) {
        val sb = StringBuilder()

        var messageLengthIndx = messageToDecrypt.length
        var messageLength = messageToDecrypt.length
        var zipList = selectedList.zip(displacementAlphabet)
        var ind = 0

        while (messageLengthIndx != 0) {
            zipList.forEachIndexed { i, it ->
                if (ind != messageLength) {
                    if (it.first == messageToDecrypt[ind].toString()) {
                        encryptMessage = sb.append(it.second).toString()
                        messageLengthIndx--
                        ind++
                    }
                }
            }
        }

        textview2.text = encryptMessage
    }
}
