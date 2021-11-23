package com.even.présentation.présenteur

import com.even.domaine.interacteur.IDétailÉvenement
import com.even.domaine.interacteur.IEnregistrement
import com.even.domaine.interacteur.IntDétailÉvenement
import com.even.présentation.modèle.ModèleDétailÉvenement

class PrésentateurDétailÉvenement(
    var vue : IDétailÉvenement,
    var modèleDétailÉvenement : ModèleDétailÉvenement
    ) : IDétailÉvenement.IPrésentateur {
}