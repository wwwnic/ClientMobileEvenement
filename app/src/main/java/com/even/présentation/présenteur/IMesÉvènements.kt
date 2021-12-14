package com.even.présentation.présenteur

import com.even.domaine.entité.Événement

/**
 * Une liste de contact représentant un lien entre la vue mes evenements et le présentateur mes evenements
 *
 */
interface IMesÉvènements {
    interface IVue {
        /**
         * Affiche la liste d'évènements
         *
         * @param listeEvens une liste d'évènement à afficher
         * @param imageUrl le lien de l'image de l'évènement
         */
        fun afficherListeEvenements(listeEvens: List<Événement>, imageUrl: (Int) -> String)

        /**
         * Affiche un message indiquant à l'utilisateur qu'il y a
         * aucun évènement à afficher selon le filtre selectionné
         *
         * @param listeEvens une liste d'évènement à afficher
         * @param imageUrl le lien de l'image de l'évènement
         */
        fun afficherAucunRésultatRecherche(estErreurConnexion: Boolean)

        /**
         * Navigue vers les détails de l'évènement ou la modification
         * de l'évènement selon le filtre actif.
         */
        fun afficherÉvénementSelectionné()
        fun afficherAucunEvenementCree()
    }

    interface IPrésentateur {
        /**
         * Lance la coroutine qui permet de recuperer la liste
         * d'évènement selon le filtre en cours
         *
         * @param estSurOngletMesÉvènement Vrai si l'utilisateur est sur l'onglet mes évènements
         */
        fun traiterRequêtelancerCoroutine(estSurOngletMesÉvènement: Boolean)


        /**
         * Redirige vers l'évènement selectionné
         *
         * @param idÉvénement
         */
        fun traiterRequêteAfficherÉvénement(idÉvénement: Int)
    }
}