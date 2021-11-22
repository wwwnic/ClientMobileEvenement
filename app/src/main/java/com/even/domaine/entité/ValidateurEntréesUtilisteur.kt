package com.even.domaine.entité

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Pattern

class ValidateurEntréesTextuel {
    companion object {
        fun validerNomUsager(nomUsager: CharSequence): Boolean {
            val usernamePattern = Pattern.compile(
                "^(?=.{4,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$"
            )
            var estRempli = !TextUtils.isEmpty(nomUsager)
            var estAccepté = usernamePattern.matcher(nomUsager).matches()
            return estRempli && estAccepté
        }

        fun validerMotDePasse(motDePasse: CharSequence): Boolean {
            val passwordPattern = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$]{4,24}")
            var estRempli = !TextUtils.isEmpty(motDePasse)
            var estAccepté = passwordPattern.matcher(motDePasse).matches()
            return estRempli && estAccepté
        }

        fun validerCourriel(courriel: CharSequence): Boolean {
            var estRempli = !TextUtils.isEmpty(courriel)
            var estAccepté = Patterns.EMAIL_ADDRESS.matcher(courriel).matches()
            return estRempli && estAccepté
        }

        fun validerTelephone(telephone: CharSequence): Boolean {
            var estRempli = !TextUtils.isEmpty(telephone)
            var estAccepté = Patterns.PHONE.matcher(telephone).matches()
            return estRempli && estAccepté
        }
    }
}