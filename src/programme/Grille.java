package programme;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * classe gerant les mouvements dans la grille du SoloNoble
 * 
 * @author DEMANGE Alessi - NICOL Benoit S3C
 *
 */
public class Grille {

	/**
	 * attribut donnant le nombre de lignes de la grille
	 */
	private int nbLignes;

	/**
	 * attribut donnant le nombre de colonnes de la grille
	 */
	private int nbColonnes;

	/**
	 * attribut donnant la grille evaluee
	 */
	private String[][] grille;

	/**
	 * attribut donnant le dernier deplacement effectue pour un trou donne, debut si
	 * il n y en a pas encore eu, fin si on a teste tous les deplacements
	 */
	private String deplacement;

	/**
	 * constructeur d une Grille avec un nom de fichier en parametre
	 * 
	 * @param nomFichier le nom de la grille a evaluer
	 * @throws IOException en cas d erreur de lecture de fichier
	 */
	public Grille(String nomFichier) throws IOException {

		// On cr�e un BufferedReader qui va lire le fichier
		BufferedReader br = new BufferedReader(new FileReader(new File(nomFichier)));

		// On cr�e une variable ligne qui va contenir chaque ligne du fichier
		String ligne;

		// On cr�e une liste de listes de String afin de pouvoir instancier la grille
		// Cela nous sert aussi � stocker chaque �l�ment de la liste dans la grille
		List<List<String>> l1 = new ArrayList<List<String>>();

		// Tant que l'on est pas arriv� � la fin du fichier
		// La variable ligne est actualis�e
		while ((ligne = br.readLine()) != null) {

			// On cr�e une liste de String
			List<String> l2 = new ArrayList<String>();

			// On ajoute chaque �l�ment de la ligne � la deuxi�me liste
			for (int j = 0; j < ligne.length(); j++) {
				l2.add(Character.toString(ligne.charAt(j)));
			}

			// On ajoute la deuxi�me liste � la premi�re
			l1.add(l2);
		}

		// On ferme le BufferedReader
		br.close();

		// On instancie la grille
		grille = new String[l1.size()][l1.get(0).size()];

		// On stocke chaque caract�re de la liste dans la grille
		for (int i = 0; i < l1.size(); i++) {
			for (int j = 0; j < l1.get(0).size(); j++) {
				grille[i][j] = l1.get(i).get(j);
			}
		}

		// On initialise le nombre de colonnes, de lignes et de d�placements
		nbColonnes = grille[0].length;
		nbLignes = grille.length;
		deplacement = "debut";
	}

	/**
	 * constructeur d une Grille par defaut avec le tablier1
	 * 
	 * @throws IOException
	 */
	public Grille() throws IOException {

		// On instancie la grille
		grille = new String[7][7];

		// On effectue cette op�ration pour les 2 premi�res lignes
		for (int i = 0; i < 2; i++) {
			grille[i][0] = " ";
			grille[i][1] = " ";
			for (int j = 2; j < 5; j++) {
				grille[i][j] = "o";
			}
			grille[i][5] = " ";
			grille[i][6] = " ";
		}

		// On effectue cette op�ration pour les lignes 2, 3 et 4
		for (int i = 2; i < 5; i++) {
			for (int j = 0; j < grille[0].length; j++) {
				grille[i][j] = "o";
			}
		}
		// On cr�e l'unique trou au centre de la grille
		grille[3][3] = ".";

		// On effectue cette op�ration pour les 2 derni�res lignes
		for (int i = 5; i < grille.length; i++) {
			grille[i][0] = " ";
			grille[i][1] = " ";
			for (int j = 2; j < 5; j++) {
				grille[i][j] = "o";
			}
			grille[i][5] = " ";
			grille[i][6] = " ";
		}

		// On initialise le nombre de colonnes, de lignes et de d�placements
		nbColonnes = grille[0].length;
		nbLignes = grille.length;
		deplacement = "debut";
	}

	/**
	 * methode qui calcule le nombre de billes de la grille
	 * 
	 * @return
	 */
	public int calculerNombreBilles() {

		// On cr�e un compteur
		int somme = 0;

		// On parcourt la grille
		for (int i = 0; i < nbLignes; i++) {
			for (int j = 0; j < nbColonnes; j++) {

				// Si le caract�re est une bille, on incr�ment le compteur
				if (grille[i][j].equals("o"))
					somme++;
			}
		}

		// On retourne le compteur
		return somme;
	}

	/**
	 * Methode gerant le deplacement de la bille selon un certain ordre 
	 * cette methode verifie egalement si le deplacement est possible
	 * on utilise l attribut deplacement afin de savoir si on a deja effectue tel ou tel
	 * deplacement pour un trou donne
	 * 
	 * @param i ligne du trou
	 * @param j colonne du trou
	 */
	public void deplacerBille(int i, int j) {

		// bool�en initialis� � false qui vaut true si un d�placement a d�j� �t�
		// effectu�
		boolean deplacementEffectue = false;

		// Si on est arriv� � la fin au dernier appel de la m�thode, alors on actualise
		// l'attribut deplacement � "debut"
		if (deplacement.equals("fin"))
			deplacement = "debut";

		// On effectue d'abord le d�placement � gauche
		// On teste si on est au d�but et que le d�placement est valide
		if (deplacement.equals("debut") && (i + 2) < nbLignes) {

			if (grille[i + 1][j].equals("o") && grille[i + 2][j].equals("o")) {

				grille[i][j] = "o";
				grille[i + 1][j] = ".";
				grille[i + 2][j] = ".";

				deplacement = "haut";
				deplacementEffectue = true;
			}
		}

		// On effectue ensuite le d�placement vers la gauche
		// Seulement si on a pas effectu� de d�placement durant cet appel de m�thode
		// Et qu'on n'a pas encore effectu� de d�placement pour ce trou
		// Ou si on a effectu� le d�placement vers le haut pour ce trou
		// Et si le d�placement est valide
		if (!deplacementEffectue && (deplacement.equals("debut") || deplacement.equals("haut"))
				&& (j + 2) < nbColonnes) {

			if (grille[i][j + 1].equals("o") && grille[i][j + 2].equals("o")) {

				grille[i][j] = "o";
				grille[i][j + 1] = ".";
				grille[i][j + 2] = ".";

				deplacement = "gauche";
				deplacementEffectue = true;
			}
		}

		// On effectue ensuite le d�placement vers la droite
		// Seulement si on a pas effectu� de d�placement durant cet appel de m�thode
		// Et qu'on n'a pas effectu� de d�placement vers la droite pour ce trou
		// Et qu'on n'a pas effectu� de d�placement vers le bas pour ce trou
		// Et que le d�placement est valide
		if (!deplacementEffectue && !deplacement.equals("droite") && !deplacement.equals("bas") && (j - 2) >= 0) {

			if (grille[i][j - 1].equals("o") && grille[i][j - 2].equals("o")) {

				grille[i][j - 2] = ".";
				grille[i][j - 1] = ".";
				grille[i][j] = "o";

				deplacement = "droite";
				deplacementEffectue = true;
			}
		}

		// On effectue enfin le d�placement vers le bas
		// Seulement si on a pas effectu� de d�placement durant cet appel de m�thode
		// Et qu'on n'a pas effectu� de d�placement vers le bas pour ce trou
		// Et que le d�placement est valide
		if (!deplacementEffectue && !deplacement.equals("bas") && (i - 2) >= 0) {

			if (grille[i - 2][j].equals("o") && grille[i - 1][j].equals("o")) {

				grille[i - 2][j] = ".";
				grille[i - 1][j] = ".";
				grille[i][j] = "o";

				deplacement = "bas";
				deplacementEffectue = true;
			}
		}

		// Si aucun d�placement n'a �t� possible durant cet appel, on actualise le
		// d�placement � "fin"
		// Ce qui permet d'arr�ter la boucle while dans la m�thode resoudreSoloNoble()
		if (!deplacementEffectue)
			deplacement = "fin";
	}

	/**
	 * methode permettant d effectuer le deplacement inverse pour la grille, en
	 * fonction du deplacement que l on vient d effectuer
	 * 
	 * @param i la ligne du trou
	 * @param j la colonne du trou
	 */
	public void retourArriere(int i, int j) {

		switch (deplacement) {

		case "gauche":
			grille[i][j] = ".";
			grille[i][j + 1] = "o";
			grille[i][j + 2] = "o";
			break;

		case "droite":
			grille[i][j - 2] = "o";
			grille[i][j - 1] = "o";
			grille[i][j] = ".";
			break;

		case "bas":
			grille[i - 2][j] = "o";
			grille[i - 1][j] = "o";
			grille[i][j] = ".";
			break;

		case "haut":
			grille[i][j] = ".";
			grille[i + 1][j] = "o";
			grille[i + 2][j] = "o";
			break;
		}
	}

	/**
	 * getter de la grille
	 * 
	 * @return la grille
	 */
	public String[][] getGrille() {
		return grille;
	}

	/**
	 * getter du nombre de lignes
	 * 
	 * @return le nombre de lignes
	 */
	public int getNbLignes() {
		return nbLignes;
	}

	/**
	 * getter du nombre de colonnes
	 * 
	 * @return le nombre de colonnes
	 */
	public int getNbColonnes() {
		return nbColonnes;
	}

	/**
	 * getter du dernier deplacement effectue
	 * 
	 * @return le deplacement
	 */
	public String getDeplacement() {
		return deplacement;
	}

	/**
	 * setter du deplacement
	 * 
	 * @param deplacement le nouveau deplacement
	 */
	public void setDeplacement(String deplacement) {
		this.deplacement = deplacement;
	}

}
