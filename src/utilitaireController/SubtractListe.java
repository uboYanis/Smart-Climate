package utilitaireController;

import java.util.ArrayList;
import java.util.List;

import model.Releve;

/**
 * cette class permet de faire la soustraction entre deux liste de releve
 * 
 * @author REMILA Yanis
 * @version 1.1
 *
 */
public final class SubtractListe {
	/**
	 * renvoie une liste de Releve qui est le resultat de la soustraction entre
	 * deux liste
	 * 
	 * @param liste1
	 *            : liste1
	 * @param liste2
	 *            : liste2
	 * @return une liste de Releve qui est le resultat de la soustraction entre
	 *         deux liste
	 */
	public static List<Releve> subtractList(List<Releve> liste1, List<Releve> liste2) {
		List<Releve> resultat = new ArrayList<Releve>();
		if (liste1.size() != liste2.size()) {
			controleSizeListe(liste1, liste2);
		}
		for (int i = 0; i <= liste1.size() - 1; i++) {
			Releve newReleve = new Releve();
			newReleve.setHoraireReleve(Integer.max(liste1.get(i).getHoraireReleve(), liste2.get(i).getHoraireReleve()));
			newReleve.setTemperature(sub(liste1.get(i).getTemperature(), liste2.get(i).getTemperature()));
			newReleve.setHumidite(sub(liste1.get(i).getHumidite(), liste2.get(i).getHumidite()));
			newReleve.setNebulosite(sub(liste1.get(i).getNebulosite(), liste2.get(i).getNebulosite()));
			resultat.add(newReleve);
		}
		return resultat;
	}

	/**
	 * la soustraction entre deux nombre
	 * 
	 * @param param1
	 *            : param1
	 * @param param2
	 *            : param2
	 * @return
	 */
	private static Float sub(Float param1, Float param2) {
		if (param1 >= 0 && param2 >= 0 || param1 >= 0 && param2 <= 0 || param2 >= 0 && param1 <= 0) {
			return (Float.max(param1, param2) - Float.min(param1, param2));
		} else if (param1 <= 0 && param2 <= 0) {
			return (Float.min(param1, param2) - Float.max(param1, param2));
		}
		return null;
	}

	/**
	 * egaliser la taille de deux liste de Releve
	 * 
	 * @param liste1
	 *            : liste1
	 * @param liste2
	 *            : liste2
	 */
	private static void egaliserSizeListes(List<Releve> liste1, List<Releve> liste2) {
		int dif = liste1.size() - liste2.size();
		for (int i = 1; i <= dif; i++) {
			liste2.add(new Releve());
		}
	}

	/**
	 * controller la taille de deux liste
	 * 
	 * @param liste1
	 *            : liste1
	 * 
	 * @param liste2
	 *            : liste2
	 */
	private static void controleSizeListe(List<Releve> liste1, List<Releve> liste2) {
		if (liste1.size() > liste2.size()) {
			egaliserSizeListes(liste1, liste2);
		}
		if (liste2.size() > liste1.size()) {
			egaliserSizeListes(liste2, liste1);
		}
	}

}
