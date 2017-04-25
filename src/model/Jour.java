package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * la class Jour represente un jour par un num�ro de jour et une liste de releve
 * et elle permet de construire un jour et de lui ajouter des releves et
 * </p>
 * <p>
 * elle d�finie les methodes de calcul suivante :
 * </p>
 * <ul>
 * <li>la moyenne d'une journ�e</li>
 * <li>l'Ecart Type d'une journ�e</li>
 * <li>le maximum d'une journ�e</li>
 * <li>le minimum d'une journ�e</li>
 * </ul>
 * 
 * @author REMILA Yanis
 * @version 1.1
 * @see Releve
 * @see Mois
 * @see Annee
 * @see Station
 * @see Model
 */
public class Jour {
	/**
	 * le num�ro du jour
	 */
	private int jour;
	/**
	 * la liste des releves d'une journ�e
	 */
	private List<Releve> listesReleve = new ArrayList<Releve>();

	/**
	 * constructeur
	 * 
	 * @param jour
	 *            :le numero du jour
	 */
	public Jour(int jour) {
		this.jour = jour;
	}

	/**
	 * Permet d'ajouter un Releve a la liste des releves d'une journ�e
	 * 
	 * @param releve
	 *            : releve
	 * @see Releve
	 */
	public void ajoutReleve(Releve releve) {
		listesReleve.add(releve);
	}

	/**
	 * Chercher un releve dans la liste des releves d'une journ�e selon
	 * l'horaire du releve
	 * 
	 * @param horaireReleve
	 *            : horaire du relev�
	 * @return si l'horaire existe alors return le releve sinon return null
	 * @see Releve
	 */
	public Releve getParNumReleve(int horaireReleve) {
		for (Releve releveChercher : this.getListesReleve()) {
			if (releveChercher.getHoraireReleve() == horaireReleve) {
				return releveChercher;
			}
		}
		return null;
	}

	/**
	 * renvoie le num�ro du jour
	 * 
	 * @return num�ro du jour
	 */
	public int getJour() {
		return jour;
	}

	/**
	 * renvoie la liste des releve d'une journ�e
	 * 
	 * @return la liste des releve d'une journ�e
	 * @see Releve
	 */
	public List<Releve> getListesReleve() {
		return listesReleve;
	}

	// -------------------------------------------//
	// ---------- fonction de calcul ------------//
	// -----------------------------------------//
	/**
	 * renvoie la moyenne d'une journ�e
	 * 
	 * @return la moyenne d'une journ�e
	 * @see Releve
	 */
	public Releve calculMoyenneJour() {
		float temperature = 0, humidite = 0, nebulosite = 0;
		for (Releve releve : this.getListesReleve()) {
			temperature += releve.getTemperature();
			humidite += releve.getHumidite();
			nebulosite += releve.getNebulosite();
		}
		temperature = temperature / this.getListesReleve().size();
		humidite = humidite / this.getListesReleve().size();
		nebulosite = nebulosite / this.getListesReleve().size();
		return new Releve(this.getJour(), temperature, humidite, nebulosite);
	}

	/**
	 * renvoie l'Ecart Type d'une journ�e
	 * 
	 * @return l'Ecart Type d'une journ�e
	 * @see Releve
	 */
	public Releve calculEcartType() {
		float temperature = 0, humidite = 0, nebulosite = 0;
		for (Releve releve : this.getListesReleve()) {
			temperature += Math.pow(releve.getTemperature() - this.calculMoyenneJour().getTemperature(), 2);
			humidite += Math.pow(releve.getHumidite() - this.calculMoyenneJour().getHumidite(), 2);
			nebulosite += Math.pow(releve.getNebulosite() - this.calculMoyenneJour().getNebulosite(), 2);
		}
		temperature = (float) Math.sqrt(temperature / this.getListesReleve().size());
		humidite = (float) Math.sqrt(humidite / this.getListesReleve().size());
		nebulosite = (float) Math.sqrt(nebulosite / this.getListesReleve().size());
		return new Releve(this.getJour(), temperature, humidite, nebulosite);
	}

	/**
	 * renvoie le MAX d'une journ�e
	 * 
	 * @return le MAX d'une journ�e
	 */
	public Releve calculMaxJour() {
		List<Float> listTemperature = new ArrayList<Float>();
		List<Float> listHumidite = new ArrayList<Float>();
		List<Float> listNebulosite = new ArrayList<Float>();
		for (Releve releve : this.getListesReleve()) {
			listTemperature.add(new Float(releve.getTemperature()));
			listHumidite.add(new Float(releve.getHumidite()));
			listNebulosite.add(new Float(releve.getNebulosite()));
		}

		return new Releve(this.getJour(), Collections.max(listTemperature), Collections.max(listHumidite),
				Collections.max(listNebulosite));
	}

	/**
	 * renvoie le MIN d'une journ�e
	 * 
	 * @return le MIN d'une journ�e
	 * @see Releve
	 */
	public Releve calculMinJour() {
		List<Float> listTemperature = new ArrayList<Float>();
		List<Float> listHumidite = new ArrayList<Float>();
		List<Float> listNebulosite = new ArrayList<Float>();
		for (Releve releve : this.getListesReleve()) {
			listTemperature.add(new Float(releve.getTemperature()));
			listHumidite.add(new Float(releve.getHumidite()));
			listNebulosite.add(new Float(releve.getNebulosite()));
		}

		return new Releve(this.getJour(), Collections.min(listTemperature), Collections.min(listHumidite),
				Collections.min(listNebulosite));
	}

}
