package com.novadev.emperorencryption

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var alphabet26Letters = mutableListOf("A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
    private var alphabet27Letters = mutableListOf("A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")

    private var displacement = 3
    private var displacementAlphabet = mutableListOf<String>()
    private var displacementAlphabetRest = mutableListOf<String>()
    private var messageToDecrypt = "HOLA QUE TAL ESTÁS".toUpperCase().stripAccents()
    private var encryptMessage = ""
    private var decryptMessage = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var selectedList = alphabet26Letters
        prepareList(selectedList)

        encrypt(selectedList, messageToDecrypt)
        decrypt(selectedList, messageToDecrypt, encryptMessage)

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
    }

    private fun decrypt(selectedList: MutableList<String>, message: String, encryptedMessage:String) {
        val sb = StringBuilder()

        var messageLengthIndx = message.length
        var messageLength = message.length
        var zipList = selectedList.zip(displacementAlphabet)
        var ind = 0
        var decryptChars = encryptedMessage.toList()


        while (messageLengthIndx != 0) {
            zipList.forEachIndexed { _, it ->
                if (ind != messageLength) {
                    if (decryptChars[ind].isWhitespace()) {
                        decryptMessage = sb.append(" ").toString()
                        messageLengthIndx--
                        ind++
                    }else{
                        if (it.second == encryptedMessage[ind].toString()) {
                            decryptMessage = sb.append(it.first).toString()
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
        var chars = messageToDecrypt.toList()

        var messageLengthIndx = messageToDecrypt.length
        var messageLength = messageToDecrypt.length
        var zipList = selectedList.zip(displacementAlphabet)
        var ind = 0

        while (messageLengthIndx != 0) {
            zipList.forEachIndexed { i, it ->
                if (ind != messageLength) {
                    if (chars[ind].isWhitespace()) {
                        encryptMessage = sb.append(" ").toString()
                        messageLengthIndx--
                        ind++
                    }else{
                        if (it.first == messageToDecrypt[ind].toString()) {
                            encryptMessage = sb.append(it.second).toString()
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
