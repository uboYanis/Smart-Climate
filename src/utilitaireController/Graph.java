package utilitaireController;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.XYChart;
import model.Releve;

/**
 * cette class permet de crée des courbes (Series) d'un graphe
 * 
 * @author REMILA Yanis
 * @version 1.4
 *
 */
public final class Graph {

	static DateFormatSymbols mois = new DateFormatSymbols();
	static String[] tabMois = mois.getMonths();
	static List<XYChart.Series<String, Number>> listSeries = new ArrayList<XYChart.Series<String, Number>>();
	static XYChart.Series<String, Number> series1 = new XYChart.Series<>();
	static XYChart.Series<String, Number> series2 = new XYChart.Series<>();
	static XYChart.Series<String, Number> series3 = new XYChart.Series<>();
	static XYChart.Series<String, Number> series4 = new XYChart.Series<>();

	/**
	 * renvoie une liste de Series (courbes) qui correspondent a la liste de
	 * liste de relevé ainsi qu'au autres parametres
	 * 
	 * @param listDonnee
	 *            : une liste de liste des releve
	 * @param parametre
	 *            : si c'est 1 :convertire en celsius sinon kelvin
	 * @param comparer
	 *            : si c'est 1 calculer ecart type si c'est 2 calculer la
	 *            Différance
	 * @param op
	 *            : 1 pour crée une series année sinon autre series
	 * @param titre
	 *            : titre de la courbe (series)
	 * @return une liste de Series (courbes) qui correspondent a la liste de
	 *         liste de relevé ainsi qu'au autres parametres
	 */
	static public List<XYChart.Series<String, Number>> createListSerie(List<List<Releve>> listDonnee, int parametre,
			int comparer, int op, String titre) {
		listSeries.clear();
		if (parametre == 1) {
			series1 = createSerie(converteToCel(listDonnee.get(0)), parametre, op, titre + "1");
			series2 = createSerie(converteToCel(listDonnee.get(1)), parametre, op, titre + "2");
		}if (parametre == 2) {
			series1 = createSerie(converteToKel(listDonnee.get(0)), parametre, op, titre + "1");
			series2 = createSerie(converteToKel(listDonnee.get(1)), parametre, op, titre + "2");
		} else {
			series1 = createSerie(listDonnee.get(0), parametre, op, titre + "1");
			series2 = createSerie(listDonnee.get(1), parametre, op, titre + "2");
		}
		switch (comparer) {
		case 1:
			series3 = createSerie(listDonnee.get(2), parametre, op, "Ecart type " + titre + "1");
			series4 = createSerie(listDonnee.get(3), parametre, op, "Ecart type " + titre + "2");
			listSeries.add(series4);
			break;
		case 2:
			series3 = createSerie(listDonnee.get(2), parametre, op, "Différance");
			break;
		default:
			break;
		}
		listSeries.add(series1);
		listSeries.add(series2);
		listSeries.add(series3);
		return listSeries;
	}

	/**
	 * renvoie une series
	 * 
	 * @param listDonnee
	 *            : liste de releve
	 * @param parametre
	 *            : si c'est 1 : crée une série pour une temperature celsius, si
	 *            c'est 2 : crée une série pour une temperature Kelvin,si c'est
	 *            3 : crée une série pour Humidite,si c'est 4 : crée une série
	 *            pour Nebulosite,
	 * @param op
	 *            : 1 pour crée une series d'une année , autre pour crée une
	 *            series pour mois et jour
	 * @param titre
	 *            : titre des la courbe
	 * @return une series
	 */
	static public XYChart.Series<String, Number> createSerie(List<Releve> listDonnee, int parametre, int op,
			String titre) {
		switch (parametre) {
		case 1:
			return createSerieTemperatureC(listDonnee, op, titre);
		case 2:
			return createSerieTemperatureK(listDonnee, op, titre);
		case 3:
			return createSerieHumidite(listDonnee, op, titre);
		case 4:
			return createSerieNebulosite(listDonnee, op, titre);
		}
		return null;
	}

	/**
	 * renvoie une serie pour Temperature celsius
	 * 
	 * @param listDonnee
	 *            : liste de releve
	 * @param op
	 *            : 1 pour crée une series d'une année , autre pour crée une
	 *            series pour mois et jour
	 * @param titre
	 *            : titre de la courbe
	 * @return une serie pour Temperature celsius
	 */
	private static XYChart.Series<String, Number> createSerieTemperatureC(List<Releve> listDonnee, int op,
			String titre) {
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		int i = 0;
		if (op == 1) {
			for (Releve releve : listDonnee) {
				series.getData().add(new XYChart.Data<>(tabMois[i], releve.getTemperature()));
				i++;
			}
			series.setName("Temperature celsius " + titre);
			return series;
		} else {
			for (Releve releve : listDonnee) {
				series.getData()
						.add(new XYChart.Data<>(String.valueOf(releve.getHoraireReleve()), releve.getTemperature()));
			}
			series.setName("Temperature celsius " + titre);
			return series;
		}
	}

	/**
	 * renvoie une serie pour Temperature Kelvin
	 * 
	 * @param listDonnee
	 *            : liste de releve
	 * @param op
	 *            : 1 pour crée une series d'une année , autre pour crée une
	 *            series pour mois et jour
	 * @param titre
	 *            : titre de la courbe
	 * @return une serie pour Temperature Kelvin
	 */
	private static XYChart.Series<String, Number> createSerieTemperatureK(List<Releve> listDonnee, int op,
			String titre) {
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		int i = 0;
		if (op == 1) {
			for (Releve releve : listDonnee) {
				series.getData().add(new XYChart.Data<>(tabMois[i], releve.getTemperature()));
				i++;
			}
			series.setName("Temperature kelvin " + titre);
			return series;
		} else {
			for (Releve releve : listDonnee) {
				series.getData()
						.add(new XYChart.Data<>(String.valueOf(releve.getHoraireReleve()), releve.getTemperature()));
			}
			series.setName("Temperature kelvin " + titre);
			return series;
		}
	}

	/**
	 * renvoie une serie pour Humidite
	 * 
	 * @param listDonnee
	 *            : liste de releve
	 * @param op
	 *            : 1 pour crée une series d'une année , autre pour crée une
	 *            series pour mois et jour
	 * @param titre
	 *            : titre de la courbe
	 * @return une serie pour Humidite
	 */
	private static XYChart.Series<String, Number> createSerieHumidite(List<Releve> listDonnee, int op, String titre) {
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		int i = 0;
		if (op == 1) {
			for (Releve releve : listDonnee) {
				series.getData().add(new XYChart.Data<>(tabMois[i], releve.getHumidite()));
				i++;
			}
			series.setName("% Humidite " + titre);
			return series;
		} else {
			for (Releve releve : listDonnee) {
				series.getData()
						.add(new XYChart.Data<>(String.valueOf(releve.getHoraireReleve()), releve.getHumidite()));
			}
			series.setName("% Humidite " + titre);
			return series;
		}
	}

	/**
	 * renvoie une serie pour Nebulosite
	 * 
	 * @param listDonnee
	 *            : liste de releve
	 * @param op
	 *            : 1 pour crée une series d'une année , autre pour crée une
	 *            series pour mois et jour
	 * @param titre
	 *            : titre de la courbe
	 * @return une serie pour Nebulosite
	 */
	private static XYChart.Series<String, Number> createSerieNebulosite(List<Releve> listDonnee, int op, String titre) {
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		int i = 0;
		if (op == 1) {
			for (Releve releve : listDonnee) {
				series.getData().add(new XYChart.Data<>(tabMois[i], releve.getNebulosite()));
				i++;
			}
			series.setName("% Nebulosite " + titre);
			return series;
		} else {
			for (Releve releve : listDonnee) {
				series.getData()
						.add(new XYChart.Data<>(String.valueOf(releve.getHoraireReleve()), releve.getNebulosite()));
			}
			series.setName("% Nebulosite " + titre);
			return series;
		}
	}

	/**
	 * renvoie une liste de releve converti en celsius
	 * 
	 * @param liste
	 *            : liste de releve
	 * @return une liste de releve converti en celsius
	 */
	public static List<Releve> converteToCel(List<Releve> liste) {
		for (Releve releve : liste) {
			if (releve.getTemperature() > 100) {
				releve.setTemperature((float) (releve.getTemperature() - 273.15));
			}
		}
		return liste;
	}

	/**
	 * renvoie une liste de releve converti en kelvin
	 * 
	 * @param liste
	 *            : liste de releve
	 * @return une liste de releve converti en Kelvin
	 */
	public static List<Releve> converteToKel(List<Releve> liste) {
		for (Releve releve : liste) {
			if (releve.getTemperature() < 100) {
				releve.setTemperature((float) (releve.getTemperature() + 273.15));
			}
		}
		return liste;
	}
}
