package com.novadev.emperorencryption

import java.text.Normalizer

fun String.stripAccents() = Normalizer
    .normalize(this, Normalizer.Form.NFD)
    .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")