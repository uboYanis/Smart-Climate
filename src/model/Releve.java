package model;

import java.util.List;

/**
 * <p>
 * represente un Releve qui est constitue de :
 * </p>
 * <ul>
 * <li>horaireReleve</li>
 * <li>temperature</li>
 * <li>humidite</li>
 * <li>nebulosite</li>
 * </ul>
 * 
 * @author REMILA Yanis
 * @version 1.1
 * @see Jour
 * @see Mois
 * @see Annee
 * @see Station
 * @see Model
 */
public class Releve {
	private int horaireReleve;
	private float temperature;
	private float humidite;
	private float nebulosite;

	/**
	 * constructeur par défaut
	 */
	public Releve() {
		super();
	}

	/**
	 * contructeur avec parametre
	 * 
	 * @param horaireReleve
	 *            : horaire du releve
	 * @param temperature
	 *            : temperature
	 * @param humidite
	 *            : humidite
	 * @param nebulosite
	 *            : nebulosite
	 */
	public Releve(int horaireReleve, float temperature, float humidite, float nebulosite) {
		super();
		this.horaireReleve = horaireReleve;
		this.temperature = temperature;
		this.humidite = humidite;
		this.nebulosite = nebulosite;
	}

	/**
	 * renvoie l'horaire du releve
	 * 
	 * @return l'horaire du releve
	 */
	public int getHoraireReleve() {
		return horaireReleve;
	}

	/**
	 * renvoie la temperature d'un Releve
	 * 
	 * @return temperature d'un Releve
	 */
	public float getTemperature() {
		return temperature;
	}

	/**
	 * renvoie l'humidite d'un Releve
	 * 
	 * @return l'humidite d'un Releve
	 */
	public float getHumidite() {
		return humidite;
	}

	/**
	 * renvoie la nebulosite d'un Releve
	 * 
	 * @return nebulosite d'un Releve
	 */
	public float getNebulosite() {
		return nebulosite;
	}

	/**
	 * modifier l'horaire d'un Releve
	 * 
	 * @param horaireReleve
	 *            : horaire du releve
	 */
	public void setHoraireReleve(int horaireReleve) {
		this.horaireReleve = horaireReleve;
	}

	/**
	 * modifier la temperature d'un Releve
	 * 
	 * @param temperature
	 *            : température
	 */
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	/**
	 * modifier l'humidite d'un Releve
	 * 
	 * @param humidite
	 *            : humidite
	 */
	public void setHumidite(float humidite) {
		this.humidite = humidite;
	}

	/**
	 * modifier la nebulosite d'un Releve
	 * 
	 * @param nebulosite
	 *            : nebulosite
	 */
	public void setNebulosite(float nebulosite) {
		this.nebulosite = nebulosite;
	}

	/**
	 * permet de crée un Releve à partir des paramètres CSV recuperer en
	 * vérifiant les parametre manquants
	 * 
	 * @param releveObtenue:
	 *            releve
	 * @param temperature
	 *            : temperature
	 * @param nebulosite
	 *            : nebulosite
	 * @param humidite
	 *            : humidite
	 * @param heur
	 *            : heur
	 */
	public void setReleve(String temperature, String nebulosite, String humidite, String heur) {
		this.setHoraireReleve(Integer.parseInt(heur.substring(8, 10)));
		try {
			this.setTemperature(Float.parseFloat(temperature));
		} catch (NumberFormatException e) {
			this.setTemperature(0);
		}
		try {
			this.setNebulosite(Float.parseFloat(nebulosite));
		} catch (NumberFormatException e) {
			this.setNebulosite(0);
		}
		try {
			this.setHumidite(Integer.parseInt(humidite));
		} catch (NumberFormatException e) {
			this.setHumidite(0);
		}

	}

	/**
	 * compare entre deux Releve
	 * 
	 * @param releve
	 *            : releve
	 * @return true si les deux releve sont egaux sinon false
	 */
	public Boolean Equals(Releve releve) {
		if (this.getHoraireReleve() == releve.getHoraireReleve()) {
			if (this.getTemperature() == releve.getTemperature()) {
				if (this.getHumidite() == releve.getHumidite()) {
					if (this.getNebulosite() == releve.getNebulosite()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * * compare entre deux Liste de Releve
	 * 
	 * @param liste1
	 *            : liste1
	 * @param liste2
	 *            : liste2
	 * @return true si les deux Liste sont egaux sinon false
	 */
	public Boolean Equals(List<Releve> liste1, List<Releve> liste2) {
		for (int i = 0; i < liste1.size(); i++) {
			if (!liste1.get(i).Equals(liste2.get(i))) {
				return false;
			}
		}
		return true;
	}
}
