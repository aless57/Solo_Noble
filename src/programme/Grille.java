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

		// On crée un BufferedReader qui va lire le fichier
		BufferedReader br = new BufferedReader(new FileReader(new File(nomFichier)));

		// On crée une variable ligne qui va contenir chaque ligne du fichier
		String ligne;

		// On crée une liste de listes de String afin de pouvoir instancier la grille
		// Cela nous sert aussi à stocker chaque élément de la liste dans la grille
		List<List<String>> l1 = new ArrayList<List<String>>();

		// Tant que l'on est pas arrivé à la fin du fichier
		// La variable ligne est actualisée
		while ((ligne = br.readLine()) != null) {

			// On crée une liste de String
			List<String> l2 = new ArrayList<String>();

			// On ajoute chaque élément de la ligne à la deuxième liste
			for (int j = 0; j < ligne.length(); j++) {
				l2.add(Character.toString(ligne.charAt(j)));
			}

			// On ajoute la deuxième liste à la première
			l1.add(l2);
		}

		// On ferme le BufferedReader
		br.close();

		// On instancie la grille
		grille = new String[l1.size()][l1.get(0).size()];

		// On stocke chaque caractère de la liste dans la grille
		for (int i = 0; i < l1.size(); i++) {
			for (int j = 0; j < l1.get(0).size(); j++) {
				grille[i][j] = l1.get(i).get(j);
			}
		}

		// On initialise le nombre de colonnes, de lignes et de déplacements
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

		// On effectue cette opération pour les 2 premières lignes
		for (int i = 0; i < 2; i++) {
			grille[i][0] = " ";
			grille[i][1] = " ";
			for (int j = 2; j < 5; j++) {
				grille[i][j] = "o";
			}
			grille[i][5] = " ";
			grille[i][6] = " ";
		}

		// On effectue cette opération pour les lignes 2, 3 et 4
		for (int i = 2; i < 5; i++) {
			for (int j = 0; j < grille[0].length; j++) {
				grille[i][j] = "o";
			}
		}
		// On crée l'unique trou au centre de la grille
		grille[3][3] = ".";

		// On effectue cette opération pour les 2 dernières lignes
		for (int i = 5; i < grille.length; i++) {
			grille[i][0] = " ";
			grille[i][1] = " ";
			for (int j = 2; j < 5; j++) {
				grille[i][j] = "o";
			}
			grille[i][5] = " ";
			grille[i][6] = " ";
		}

		// On initialise le nombre de colonnes, de lignes et de déplacements
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

		// On crée un compteur
		int somme = 0;

		// On parcourt la grille
		for (int i = 0; i < nbLignes; i++) {
			for (int j = 0; j < nbColonnes; j++) {

				// Si le caractère est une bille, on incrément le compteur
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

		// booléen initialisé à false qui vaut true si un déplacement a déjà été
		// effectué
		boolean deplacementEffectue = false;

		// Si on est arrivé à la fin au dernier appel de la méthode, alors on actualise
		// l'attribut deplacement à "debut"
		if (deplacement.equals("fin"))
			deplacement = "debut";

		// On effectue d'abord le déplacement à gauche
		// On teste si on est au début et que le déplacement est valide
		if (deplacement.equals("debut") && (i + 2) < nbLignes) {

			if (grille[i + 1][j].equals("o") && grille[i + 2][j].equals("o")) {

				grille[i][j] = "o";
				grille[i + 1][j] = ".";
				grille[i + 2][j] = ".";

				deplacement = "haut";
				deplacementEffectue = true;
			}
		}

		// On effectue ensuite le déplacement vers la gauche
		// Seulement si on a pas effectué de déplacement durant cet appel de méthode
		// Et qu'on n'a pas encore effectué de déplacement pour ce trou
		// Ou si on a effectué le déplacement vers le haut pour ce trou
		// Et si le déplacement est valide
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

		// On effectue ensuite le déplacement vers la droite
		// Seulement si on a pas effectué de déplacement durant cet appel de méthode
		// Et qu'on n'a pas effectué de déplacement vers la droite pour ce trou
		// Et qu'on n'a pas effectué de déplacement vers le bas pour ce trou
		// Et que le déplacement est valide
		if (!deplacementEffectue && !deplacement.equals("droite") && !deplacement.equals("bas") && (j - 2) >= 0) {

			if (grille[i][j - 1].equals("o") && grille[i][j - 2].equals("o")) {

				grille[i][j - 2] = ".";
				grille[i][j - 1] = ".";
				grille[i][j] = "o";

				deplacement = "droite";
				deplacementEffectue = true;
			}
		}

		// On effectue enfin le déplacement vers le bas
		// Seulement si on a pas effectué de déplacement durant cet appel de méthode
		// Et qu'on n'a pas effectué de déplacement vers le bas pour ce trou
		// Et que le déplacement est valide
		if (!deplacementEffectue && !deplacement.equals("bas") && (i - 2) >= 0) {

			if (grille[i - 2][j].equals("o") && grille[i - 1][j].equals("o")) {

				grille[i - 2][j] = ".";
				grille[i - 1][j] = ".";
				grille[i][j] = "o";

				deplacement = "bas";
				deplacementEffectue = true;
			}
		}

		// Si aucun déplacement n'a été possible durant cet appel, on actualise le
		// déplacement à "fin"
		// Ce qui permet d'arrêter la boucle while dans la méthode resoudreSoloNoble()
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
