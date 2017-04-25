package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * la class Mois represente un Mois par un numero de Mois et une liste de jour ,
 * elle permet de construire un Mois et de lui associer une liste de jours et
 * d'ajouter des jour à cette dernière,elle permet de mieux géré la création des
 * jours avec la methode {@link#getOrCreatJour} et définie des méthodes pour
 * récupérer une liste de releve ou un releve d'une journée et
 * </p>
 * <p>
 * elle définie les methodes de calcul suivante :
 * </p>
 * <ul>
 * <li>la moyenne d'un Mois</li>
 * <li>l'Ecart Type d'un Mois</li>
 * <li>le maximum d'un Mois</li>
 * <li>le minimum d'un Mois</li>
 * </ul>
 * <ul>
 * <li>moyennes des jours du mois</li>
 * <li>l'Ecart Type des jours du mois</li>
 * <li>le maximum des jours du mois</li>
 * <li>le minimum des jours du mois</li>
 * </ul>
 * 
 * @author REMILA Yanis
 * @version 1.1
 * @see Releve
 * @see Jour
 * @see Annee
 * @see Station
 * @see Model
 */
public class Mois {
	/**
	 * le numéro du mois
	 */
	private int mois;
	/**
	 * liste de jours du mois
	 */
	private List<Jour> listesJour = new ArrayList<Jour>();

	/**
	 * constructeur avec parametre
	 * 
	 * @param mois:numéro
	 *            du mois
	 */
	public Mois(int mois) {
		this.mois = mois;
	}

	/**
	 * vérifie si le jour demander existe deja dans la liste de jour du mois
	 * s'il existe alors elle renvoie le jour existant sinon elle crée un jour
	 * et renvoie ce dernier
	 * 
	 * @param idJour
	 *            :numero du jour
	 * @return si le jour existe alors return le jour existant sinon crée un
	 *         jour et return new jour
	 * @see Jour
	 */
	public Jour getOrCreatJour(int idJour) {
		for (Jour jourChercher : this.getListesJour()) {
			if (jourChercher.getJour() == idJour) {
				return jourChercher;
			}
		}
		Jour newJour = new Jour(idJour);
		this.ajoutJour(newJour);
		return newJour;
	}

	/**
	 * Permet d'ajouter un Jour a la liste des jours d'un Mois
	 * 
	 * @param jour
	 *            : jour
	 * @see Jour
	 */
	public void ajoutJour(Jour jour) {
		listesJour.add(jour);
	}

	/**
	 * renvoie la liste des releves d'une journée
	 * 
	 * @param jourChercher
	 *            : numéro jour
	 * 
	 * @return si le jour existe alors return la liste des releves sinon return
	 *         null
	 * @see Jour
	 * @see Releve
	 */
	public List<Releve> getReleve(int jourChercher) {
		for (Jour jour : listesJour) {
			if (jour.getJour() == jourChercher) {
				return jour.getListesReleve();
			}
		}
		return null;
	}

	/**
	 * renvoie un releve d'une journée
	 * 
	 * @param jourChercher : jour chercher 
	 *            : jourChercher :numéro du jour
	 * @param horaireChercher:
	 *            heur du releve
	 * @return si le jour existe dans la liste de Mois et le releve existe dans
	 *         la liste de Jour alors return le releve chercher sinon return
	 *         null
	 * @see Jour
	 * @see Releve
	 */
	public Releve getParNumReleve(int jourChercher, int horaireChercher) {
		for (Jour jour : this.getListesJour()) {
			return jour.getParNumReleve(horaireChercher);
		}
		return null;
	}

	/**
	 * renvoie le numéro du mois
	 * 
	 * @return numéro du mois
	 */
	public int getMois() {
		return mois;
	}

	/**
	 * renvoie la liste des jours d'un Mois
	 * 
	 * @return liste des jours d'un Mois
	 * @see Jour
	 */
	public List<Jour> getListesJour() {
		return listesJour;
	}

	// -------------------------------------------//
	// ---------- fonction de calcul ------------//
	// -----------------------------------------//

	/**
	 * renvoie une liste des moyennes des jours du mois
	 * 
	 * @return la liste des moyennes des jours du mois
	 * @see Jour
	 */
	public List<Releve> getMoyenneParJour() {
		List<Releve> listMoyenneparJour = new ArrayList<Releve>();
		for (Jour jour : this.getListesJour()) {
			listMoyenneparJour.add(jour.calculMoyenneJour());
		}
		return listMoyenneparJour;
	}

	/**
	 * renvoie une liste des Ecart Type des jours du mois
	 * 
	 * @return liste des Ecart Type des jours du mois
	 * @see Jour
	 */
	public List<Releve> getEcartTypeParJour() {
		List<Releve> listEcartTypeParJour = new ArrayList<Releve>();
		for (Jour jour : this.getListesJour()) {
			listEcartTypeParJour.add(jour.calculEcartType());
		}
		return listEcartTypeParJour;
	}

	/**
	 * renvoie une liste des Max des jours du mois
	 * 
	 * @return liste des Max des jours du mois
	 * @see Jour
	 */
	public List<Releve> getMaxParJour() {
		List<Releve> listMaxparJour = new ArrayList<Releve>();
		for (Jour jour : this.getListesJour()) {
			listMaxparJour.add(jour.calculMaxJour());
		}
		return listMaxparJour;
	}

	/**
	 * renvoie une liste des Min des jours du mois
	 * 
	 * @return liste des Min des jours du mois
	 * @see Jour
	 */
	public List<Releve> getMinParJour() {
		List<Releve> listMinparJour = new ArrayList<Releve>();
		for (Jour jour : this.getListesJour()) {
			listMinparJour.add(jour.calculMinJour());
		}
		return listMinparJour;
	}

	/**
	 * renvoie la moyenne d'un mois
	 * 
	 * @return la moyenne d'un mois
	 * @see Releve
	 */
	public Releve calculMoyenneMois() {
		float temperature = 0, humidite = 0, nebulosite = 0;
		for (Jour jour : this.getListesJour()) {
			temperature += jour.calculMoyenneJour().getTemperature();
			humidite += jour.calculMoyenneJour().getHumidite();
			nebulosite += jour.calculMoyenneJour().getNebulosite();
		}
		temperature = temperature / this.getListesJour().size();
		humidite = humidite / this.getListesJour().size();
		nebulosite = nebulosite / this.getListesJour().size();
		return new Releve(this.getMois(), temperature, humidite, nebulosite);
	}

	/**
	 * renvoie l'Ecart Type d'un mois
	 * 
	 * @return l'Ecart Type d'un mois
	 * @see Releve
	 */
	public Releve calculeEcartTypeMois() {
		float temperature = 0, humidite = 0, nebulosite = 0;
		for (Jour jour : this.getListesJour()) {
			temperature = (float) Math
					.pow(jour.calculMoyenneJour().getTemperature() - this.calculMoyenneMois().getTemperature(), 2);
			humidite = (float) Math.pow(jour.calculMoyenneJour().getHumidite() - this.calculMoyenneMois().getHumidite(),
					2);
			nebulosite = (float) Math
					.pow(jour.calculMoyenneJour().getNebulosite() - this.calculMoyenneMois().getNebulosite(), 2);
		}
		temperature = (float) Math.sqrt(temperature / this.getListesJour().size());
		humidite = (float) Math.sqrt(humidite / this.getListesJour().size());
		nebulosite = (float) Math.sqrt(nebulosite / this.getListesJour().size());
		return new Releve(this.getMois(), temperature, humidite, nebulosite);
	}

	/**
	 * renvoie le MAX d'un mois
	 * 
	 * @return le MAX d'un mois
	 * @see Releve
	 */
	public Releve calculMaxMois() {
		List<Float> listTemperature = new ArrayList<Float>();
		List<Float> listHumidite = new ArrayList<Float>();
		List<Float> listNebulosite = new ArrayList<Float>();
		for (Jour jour : this.getListesJour()) {
			listTemperature.add(new Float(jour.calculMaxJour().getTemperature()));
			listHumidite.add(new Float(jour.calculMaxJour().getHumidite()));
			listNebulosite.add(new Float(jour.calculMaxJour().getNebulosite()));
		}
		return new Releve(this.getMois(), Collections.max(listTemperature), Collections.max(listHumidite),
				Collections.max(listNebulosite));
	}

	/**
	 * renvoie le MIN d'un mois
	 * 
	 * @return le MIN d'un mois
	 * @see Releve
	 */
	public Releve calculMinMois() {
		List<Float> listTemperature = new ArrayList<Float>();
		List<Float> listHumidite = new ArrayList<Float>();
		List<Float> listNebulosite = new ArrayList<Float>();
		for (Jour jour : this.getListesJour()) {
			listTemperature.add(new Float(jour.calculMinJour().getTemperature()));
			listHumidite.add(new Float(jour.calculMinJour().getHumidite()));
			listNebulosite.add(new Float(jour.calculMinJour().getNebulosite()));
		}
		return new Releve(this.getMois(), Collections.min(listTemperature), Collections.min(listHumidite),
				Collections.min(listNebulosite));
	}

}
