package com.even.domaine.entité

import android.text.TextUtils
import android.util.Patterns
import com.even.testOuvert
import java.util.regex.Pattern

@testOuvert
class ValidateurTextuel {
    fun validerNomUsager(nomUsager: CharSequence): Boolean {
        val usernamePattern = Pattern.compile(
            "^(?=.{4,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$"
        )
        val estRempli = !TextUtils.isEmpty(nomUsager)
        val estAccepté = usernamePattern.matcher(nomUsager).matches()
        return estRempli && estAccepté
    }

    fun validerMotDePasse(motDePasse: CharSequence): Boolean {
        val passwordPattern = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$]{4,24}")
        val estRempli = !TextUtils.isEmpty(motDePasse)
        val estAccepté = passwordPattern.matcher(motDePasse).matches()
        return estRempli && estAccepté
    }

    fun validerCourriel(courriel: CharSequence): Boolean {
        val estRempli = !TextUtils.isEmpty(courriel)
        val estAccepté = Patterns.EMAIL_ADDRESS.matcher(courriel).matches()
        return estRempli && estAccepté
    }

    fun validerTelephone(telephone: CharSequence): Boolean {
        val estRempli = !TextUtils.isEmpty(telephone)
        val estAccepté = Patterns.PHONE.matcher(telephone).matches()
        return estRempli && estAccepté
    }
}