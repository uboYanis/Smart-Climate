package model;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;

import interfaceModel.Imodel;
import mapStation.MapSation;

/**
 * implemente l'interface Imodel
 * 
 * @author REMILA Yanis
 * @version 1.8
 *
 */
public class Model implements Imodel {

	Utilitaire utilitaire = new Utilitaire();
	private List<Station> listesStation = new ArrayList<Station>();
	LocalDate dateLocal = LocalDate.now();

	/**
	 * <p>
	 * constructeur par defaut
	 * </p>
	 * charger les station a l'instantiation de la class model
	 */
	public Model() {
		MapSation.remplirMap();
	}

	/**
	 * renvoie la liste des stations
	 * 
	 * @return liste des stations
	 */
	private List<Station> getListesStation() {
		return listesStation;
	}

	/**
	 * ajouter une station a la liste de station
	 * 
	 * @param ajoutStation
	 *            : la station a ajouter
	 */
	private void ajoutStation(Station ajoutStation) {
		listesStation.add(ajoutStation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaceModel.Imodel#getNomMapStation(int)
	 */
	@Override
	public String getNomMapStation(int idStation) {
		return MapSation.getNomStation(idStation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaceModel.Imodel#getidMapStation(java.lang.String)
	 */
	@Override
	public Integer getidMapStation(String nomStation) {
		return MapSation.getIdStation(nomStation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaceModel.Imodel#getMapStation()
	 */
	@Override
	public Map<Integer, String> getMapStation() {
		return MapSation.getStationMap();
	}

	/**
	 * vérifie si la station demander existe deja dans la liste des station du
	 * model si la station existe alors elle renvoie la station existante sinon
	 * elle crée une station et renvoie cet derniere
	 * 
	 * @param idStation
	 *            : id station
	 * @return si la station existe alors return a station existante sinon crée
	 *         une station et return new station
	 * @see Station
	 */
	private Station getOrCreatStation(int idStation) {
		for (Station stationChercher : this.getListesStation()) {
			if (stationChercher.getIdStation() == idStation) {
				return stationChercher;
			}
		}
		Station newStation = new Station(idStation, getNomMapStation(idStation));
		this.ajoutStation(newStation);
		return newStation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaceModel.Imodel#donneeHoraire(int, int, int, int, int)
	 */
	@Override
	public Releve donneeHoraire(int stationChercher, int anneeChercher, int moisChercher, int jourChercher,
			int horaireChercher) throws Exception {
		if (!isDonneeJour(stationChercher, anneeChercher, moisChercher, jourChercher)) {
			if (!utilitaire.isFileExist(utilitaire.parsDateToString(anneeChercher, moisChercher, 00))) {
				try {
					utilitaire.telecharger(utilitaire.parsDateToString(anneeChercher, moisChercher, 00));
				} catch (IOException e) {
					throw new IOException("fichier demander inexistant problème de connexion internet");
				}
			}
			construireDonnees(utilitaire.parsIntToString(stationChercher),
					utilitaire.parsDateToString(anneeChercher, moisChercher, jourChercher));
		}
		for (Station station : this.getListesStation()) {
			if (station.getIdStation() == stationChercher) {
				return station.getParNumReleve(anneeChercher, moisChercher, jourChercher, horaireChercher);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaceModel.Imodel#donneeJour(int, int, int, int)
	 */
	@Override
	public List<Releve> donneeJour(int stationChercher, int anneeChercher, int moisChercher, int jourChercher)
			throws Exception {
		if (!isDonneeJour(stationChercher, anneeChercher, moisChercher, jourChercher)) {
			if (!utilitaire.isFileExist(utilitaire.parsDateToString(anneeChercher, moisChercher, 00))) {
				try {
					utilitaire.telecharger(utilitaire.parsDateToString(anneeChercher, moisChercher, 00));
				} catch (IOException e) {
					throw new IOException("fichier demander inexistant problème de connexion internet");
				}
			}
			construireDonnees(utilitaire.parsIntToString(stationChercher),
					utilitaire.parsDateToString(anneeChercher, moisChercher, jourChercher));
		}

		for (Station station : this.getListesStation()) {
			if (station.getIdStation() == stationChercher) {
				return station.getReleveJour(anneeChercher, moisChercher, jourChercher);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaceModel.Imodel#donneeMois(int, int, int, int)
	 */
	@Override
	public List<Releve> donneeMois(int stationChercher, int anneeChercher, int moisChercher, int operation)
			throws Exception {
		if (!isdonneeMois(stationChercher, anneeChercher, moisChercher, operation)) {
			if (!utilitaire.isFileExist(utilitaire.parsDateToString(anneeChercher, moisChercher, 00))) {
				try {
					utilitaire.telecharger(utilitaire.parsDateToString(anneeChercher, moisChercher, 00));
				} catch (IOException e) {
					throw new IOException("fichier demander inexistant problème de connexion internet");
				}
			}
			construireDonnees(utilitaire.parsIntToString(stationChercher),
					utilitaire.parsDateToString(anneeChercher, moisChercher, 00));
		}

		for (Station station : this.getListesStation()) {
			if (station.getIdStation() == stationChercher) {
				switch (operation) {
				case 1:
					return station.getMoyenneParJour(anneeChercher, moisChercher);
				case 2:
					return station.getMaxParJour(anneeChercher, moisChercher);
				case 3:
					return station.getMinParJour(anneeChercher, moisChercher);
				case 4:
					return station.getEcartTypeParJour(anneeChercher, moisChercher);
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaceModel.Imodel#donneeAnnee(int, int, int)
	 */
	@Override
	public List<Releve> donneeAnnee(int stationChercher, int anneeChercher, int operation) throws Exception {
		if (!isDonneeAnnee(stationChercher, anneeChercher, operation)) {
			List<String> listMoisManquant = utilitaire
					.FileMissingAnnee(utilitaire.parsDateToString(anneeChercher, 00, 00));
			if (!listMoisManquant.isEmpty()) {
				try {
					utilitaire.telechargerAnnee(utilitaire.parsDateToString(anneeChercher, 00, 00), listMoisManquant);
				} catch (IOException e) {
					throw new IOException("fichier demander inexistant problème de connexion internet");
				}
			}
			construireDonneesAnnee(utilitaire.parsIntToString(stationChercher),
					utilitaire.parsDateToString(anneeChercher, 00, 00));
		}

		for (Station station : this.getListesStation()) {
			if (station.getIdStation() == stationChercher) {
				switch (operation) {
				case 1:
					return station.getMoyenneParMois(anneeChercher);
				case 2:
					return station.getMaxParMois(anneeChercher);
				case 3:
					return station.getMinParMois(anneeChercher);
				case 4:
					return station.getEcartTypeParMois(anneeChercher);
				}
			}
		}
		return null;
	}

	/**
	 * permet de recupéré les donnée d'une année
	 * 
	 * @param StationChercher
	 *            : station chercher
	 * @param dateChercher
	 *            :yyyymmdd
	 * @throws Exception
	 *             si un fichier demander n'existe pas
	 */
	private void construireDonneesAnnee(String StationChercher, String dateChercher) throws Exception {
		int nombreMois = (dateLocal.getYear() == Integer.parseInt(dateChercher.substring(0, 4)))
				? dateLocal.getMonthValue() : 12;
		for (int mois = 1; mois <= nombreMois; mois++) {
			construireDonnees(StationChercher, dateChercher.substring(0, 4) + utilitaire.parsIntToString(mois));
		}
	}

	/**
	 * verifie si les donnée d'une journée son deja existante dans la liste
	 * 
	 * @param stationChercher
	 *            : station chercher
	 * @param anneeChercher
	 *            : annee charcher : annee charcher
	 * @param moisChercher
	 *            : mois chercher
	 * @param jourChercher
	 *            : jour chercher
	 * @return true si les données existe sinon return false
	 */
	private boolean isDonneeJour(int stationChercher, int anneeChercher, int moisChercher, int jourChercher) {
		for (Station station : this.getListesStation()) {
			if (station.getIdStation() == stationChercher) {
				try {
					if (!station.getReleveJour(anneeChercher, moisChercher, jourChercher).isEmpty()) {
						return true;
					}
				} catch (Exception e) {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * verifie si les données d'un mois son deja existante dans la liste
	 * 
	 * @param stationChercher
	 *            : station chercher
	 * @param anneeChercher
	 *            : annee charcher
	 * @param moisChercher
	 *            : mois chercher
	 * @param operation
	 *            : operation (moyenne /ecart type / max / min )
	 * @return true si les données existe sinon return false
	 */
	private boolean isdonneeMois(int stationChercher, int anneeChercher, int moisChercher, int operation) {
		for (Station station : this.getListesStation()) {
			if (station.getIdStation() == stationChercher) {
				try {
					switch (operation) {
					case 1:
						if (!station.getMoyenneParJour(anneeChercher, moisChercher).isEmpty())
							return true;
					case 2:
						if (!station.getMaxParJour(anneeChercher, moisChercher).isEmpty())
							return true;
					case 3:
						if (!station.getMinParJour(anneeChercher, moisChercher).isEmpty())
							return true;
					case 4:
						if (!station.getEcartTypeParJour(anneeChercher, moisChercher).isEmpty())
							return true;
					}
				} catch (Exception e) {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * verifie si les données d'une année son deja existante dans la liste
	 * 
	 * @param stationChercher
	 *            : station chercher
	 * @param anneeChercher
	 *            : annee charcher
	 * @param operation
	 *            : operation (moyenne /ecart type / max / min )
	 * @return true si les données existe sinon return false
	 */
	private boolean isDonneeAnnee(int stationChercher, int anneeChercher, int operation) {
		for (Station station : this.getListesStation()) {
			if (station.getIdStation() == stationChercher) {
				try {
					switch (operation) {
					case 1:
						if (!station.getMoyenneParMois(anneeChercher).isEmpty())
							return true;
					case 2:
						if (!station.getMaxParMois(anneeChercher).isEmpty())
							return true;
					case 3:
						if (!station.getMinParMois(anneeChercher).isEmpty())
							return true;
					case 4:
						if (!station.getEcartTypeParMois(anneeChercher).isEmpty())
							return true;
					}
				} catch (Exception e) {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * permet de recupérer les donneés d'un releve dans les fichier CSV et de
	 * faire correspondre une station,une année,un mois, un jour a ce releve
	 * 
	 * @param StationChercher
	 *            : station chercher
	 * @param dateChercher
	 *            : date chercher
	 * @throws Exception
	 *             si le fichier CSV n'existe pas
	 */
	private void construireDonnees(String StationChercher, String dateChercher) throws Exception {
		String station, temperature, humidite, nebulosite, date;
		CSVReader reader = new CSVReader(new FileReader(utilitaire.newPahtFile(dateChercher)), ';', ' ', 1);
		List<String[]> allRows = reader.readAll();
		for (String[] row : allRows) {
			station = row[0];
			date = row[1];
			temperature = row[7];
			humidite = row[9];
			nebulosite = row[14];
			if (station.equals(StationChercher)) {
				if (date.startsWith(dateChercher)) {
					Releve releveObtenue = new Releve();
					releveObtenue.setReleve(temperature, nebulosite, humidite, date);
					Station stationObtenue = getOrCreatStation(Integer.parseInt(station));
					Annee anneeObtenue = stationObtenue.getOrCreatAnnee(Integer.parseInt(date.substring(0, 4)));
					Mois moisObtenue = anneeObtenue.getOrCreatMois(Integer.parseInt(date.substring(4, 6)));
					Jour jourObtenue = moisObtenue.getOrCreatJour(Integer.parseInt(date.substring(6, 8)));
					jourObtenue.ajoutReleve(releveObtenue);
				}
			}
		}
		reader.close();
	}
}