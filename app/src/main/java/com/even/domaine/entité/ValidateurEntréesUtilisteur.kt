package com.even.domaine.entité

import android.text.TextUtils
import android.util.Patterns
import com.even.testOuvert
import java.util.regex.Pattern

/**
 * Permets de valider les entrées d'un utilisateur
 *
 */
@testOuvert
class ValidateurTextuel {

    /**
     * Valide le nom d'un utilisateur
     *
     * @param nomUsager Le nom à valider
     * @return Vrai si le nom est valide
     */
    fun validerNomUsager(nomUsager: CharSequence): Boolean {
        val usernamePattern = Pattern.compile(
            "^(?=.{4,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$"
        )
        val estRempli = !TextUtils.isEmpty(nomUsager)
        val estAccepté = usernamePattern.matcher(nomUsager).matches()
        return estRempli && estAccepté
    }

    /**
     * Valide le mot de passe d'un utilisateur
     *
     * @param motDePasse Le mot de passe à valider
     * @return Vrai si le mot de passe est valide
     */
    fun validerMotDePasse(motDePasse: CharSequence): Boolean {
        val passwordPattern = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$]{4,24}")
        val estRempli = !TextUtils.isEmpty(motDePasse)
        val estAccepté = passwordPattern.matcher(motDePasse).matches()
        return estRempli && estAccepté
    }

    /**
     * Valide le courriel d'un utilisateur
     *
     * @param courriel Le courriel à valider
     * @return Vrai si le courriel est valide
     */
    fun validerCourriel(courriel: CharSequence): Boolean {
        val estRempli = !TextUtils.isEmpty(courriel)
        val estAccepté = Patterns.EMAIL_ADDRESS.matcher(courriel).matches()
        return estRempli && estAccepté
    }

    /**
     * Valide le telephone d'un utilisateur
     *
     * @param telephone Le téléphone à valider
     * @return Vrai si le telephone est valide
     */
    fun validerTelephone(telephone: CharSequence): Boolean {
        val estRempli = !TextUtils.isEmpty(telephone)
        val estAccepté = Patterns.PHONE.matcher(telephone).matches()
        return estRempli && estAccepté
    }
}