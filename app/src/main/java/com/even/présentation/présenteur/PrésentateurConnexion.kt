package com.even.présentation.présenteur

import com.even.domaine.interacteur.IConnexion
import com.even.présentation.modèle.ModèleConnexion
import com.even.présentation.modèle.ModèleEnregistrement

class PrésentateurConnexion(
    var vue: IConnexion.IVue,
    var modèleEnregistrment: ModèleConnexion
) {
}