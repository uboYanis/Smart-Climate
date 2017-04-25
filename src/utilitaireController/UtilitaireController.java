package utilitaireController;

import java.time.LocalDate;
import java.util.Map;

import interfaceModel.Imodel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Model;
import model.Releve;

/**
 * cette class permet d'initialiser les tableView , les ChoiceBox ,les
 * DatePicker des différentes vue, ainsi que des methode pour attribuer un index
 * au valeurs des ChoiceBox pour les utiliser dans les listener
 * 
 * @author REMILA Yanis
 * @version 1.2
 *
 */
public class UtilitaireController {
	Imodel model = new Model();
	LocalDate dateLocal = LocalDate.now();

	/**
	 * permet d'initialiser les colonnes d'un tableView
	 * 
	 * @param colHoraire
	 *            : colonne horaire
	 * @param colTemperature
	 *            : colonne température
	 * @param colHumidite
	 *            : colonne humidité
	 * @param colNebulosite
	 *            : colonne nebulosité
	 */
	public void initTableView(TableColumn<Releve, Integer> colHoraire, TableColumn<Releve, Float> colTemperature,
			TableColumn<Releve, Float> colHumidite, TableColumn<Releve, Float> colNebulosite) {
		colHoraire.setCellValueFactory(new PropertyValueFactory<>("horaireReleve"));
		colTemperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));
		colHumidite.setCellValueFactory(new PropertyValueFactory<>("humidite"));
		colNebulosite.setCellValueFactory(new PropertyValueFactory<>("nebulosite"));
	}

	/**
	 * permet d'inisialiser le ChoiceBox station
	 * 
	 * @param station
	 *            : ChoiceBox station
	 */
	public void initListeStation(ChoiceBox<String> station) {
		ObservableList<String> obsListeStation = FXCollections.observableArrayList();
		for (Map.Entry<Integer, String> stations : model.getMapStation().entrySet()) {
			obsListeStation.add(stations.getValue());
		}
		station.setItems(obsListeStation);
	}

	/**
	 * permet d'initialiser le ChoiceBox année
	 * 
	 * @param annees
	 *            : ChoiceBox année
	 */
	public void initListeAnnee(ChoiceBox<Integer> annees) {
		ObservableList<Integer> obsListeAnnee = FXCollections.observableArrayList();
		for (int annee = dateLocal.getYear(); annee >= 1996; annee--) {
			obsListeAnnee.add(annee);
		}
		annees.setItems(obsListeAnnee);
	}

	/**
	 * permet d'initialiser le ChoiceBox mois
	 * 
	 * @param choiceBoxMois
	 *            : ChoiceBox mois
	 */
	public void initListeMois(ChoiceBox<Integer> choiceBoxMois) {
		ObservableList<Integer> obsListeAnnee = FXCollections.observableArrayList();
		for (int mois = 1; mois <= 12; mois++) {
			obsListeAnnee.add(mois);
		}
		choiceBoxMois.setItems(obsListeAnnee);
	}

	/**
	 * permet d'initialiser le ChoiceBox operation
	 * 
	 * @param operation
	 *            : ChoiceBox operation
	 */
	public void initListeOperation(ChoiceBox<String> operation) {
		ObservableList<String> obsListeOp = FXCollections.observableArrayList();
		obsListeOp.add("Moyenne");
		obsListeOp.add("Maximum");
		obsListeOp.add("Minimum");
		operation.setItems(obsListeOp);
	}

	/**
	 * permet d'initialiser le ChoiceBox parametres
	 * 
	 * @param parametres
	 *            : ChoiceBox parametres
	 */
	public void initListeParametre(ChoiceBox<String> parametres) {
		ObservableList<String> obsListeOp = FXCollections.observableArrayList();
		obsListeOp.add("Temperature ºC");
		obsListeOp.add("Temperature ºK");
		obsListeOp.add("Humidite");
		obsListeOp.add("Nebulosite");
		parametres.setItems(obsListeOp);
	}

	/**
	 * permet d'initialiser le ChoiceBox comparaison
	 * 
	 * @param comparaison
	 *            : ChoiceBox comparaison
	 * @param op
	 *            : true pour ne pas afficher l'ecart type et afficher juste la
	 *            différance dans le ChoiceBox pour la comparaison de deux jours
	 *            false pour toute afficher
	 */
	public void initListeComparaison(ChoiceBox<String> comparaison, boolean op) {
		ObservableList<String> obsListeComp = FXCollections.observableArrayList();
		if (!op) {
			obsListeComp.add("Écart-type");
		}
		obsListeComp.add("Différance");
		comparaison.setItems(obsListeComp);
		if (op) {
			comparaison.getSelectionModel().selectFirst();
		}
	}

	/**
	 * permet d'initialiser un DatePicker et de désactiver les date postérieur a
	 * 01/01/1996 et antérieur a la date actuelle
	 * 
	 * @param datePicker
	 *            : date piker
	 */
	public void controlleDate(DatePicker datePicker) {
		datePicker.setValue(LocalDate.now());
		LocalDate localDate = LocalDate.of(1996, 1, 1);
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						if (item.isAfter(LocalDate.now().plusDays(0))) {
							setDisable(true);
							setStyle("-fx-background-color: #16a085;");
						}
						if (item.isBefore(localDate.plusDays(0))) {
							setDisable(true);
							setStyle("-fx-background-color: #16a085;");
						}
					}
				};
			}
		};
		datePicker.setDayCellFactory(dayCellFactory);
		datePicker.setValue(null);
	}

	/**
	 * permet d'inisialiser un listener sur un CheckBox et d'afficher "Listener
	 * Désactiver" lorsqu'il est sélectionner sinon afficher "Listener Activer"
	 * 
	 * @param check
	 *            : CheckBox
	 */
	public void checkBox(CheckBox check) {
		check.selectedProperty().addListener(new ChangeListener<Object>() {

			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				if (check.isSelected()) {
					check.setText("Listener \nDésactiver");
				} else {
					check.setText("Listener \nActiver");
				}

			}
		});

	}

	/**
	 * renvoie un index attribuer au nom d'un parametre
	 * 
	 * @param parametre
	 *            : le nom de l'operation
	 * @return un index attribuer a une operation
	 */
	public Integer SelectedIndexParametre(String parametre) {
		switch (parametre) {
		case "Temperature ºC":
			return 1;
		case "Temperature ºK":
			return 2;
		case "Humidite":
			return 3;
		case "Nebulosite":
			return 4;
		default:
			break;
		}
		return null;
	}

	/**
	 * renvoie un index attribuer au nom de l'opération de comparaison passé en
	 * parametre
	 * 
	 * @param comparaisonChoisi
	 *            : le nom de l'operation de comparaison
	 * @return un index attribuer a une operation de comparaison
	 */
	public Integer SelectedIndexComparer(String comparaisonChoisi) {
		switch (comparaisonChoisi) {
		case "Écart-type":
			return 1;
		case "Différance":
			return 2;
		default:
			break;
		}

		return null;
	}

	/**
	 * renvoie un index attribuer au nom de l'opération passé en parametre
	 * 
	 * @param operationChoisi
	 *            : le nom de l'operation
	 * @return un index attribuer a une operation
	 */
	public Integer SelectedIndexOperation(String operationChoisi) {
		switch (operationChoisi) {
		case "Moyenne":
			return 1;
		case "Maximum":
			return 2;
		case "Minimum":
			return 3;
		default:
			break;
		}
		return null;
	}

	/**
	 * @param teread
	 *            : thread
	 * @param tache
	 *            : tache
	 * @param label
	 *            : label
	 * @param bar
	 *            : ProgressBar
	 */
	public void lancerThread(Thread teread, Task<ObservableList<Releve>> tache, Label label, ProgressBar bar) {
		label.textProperty().bind(tache.messageProperty());
		bar.progressProperty().bind(tache.progressProperty());
		teread = new Thread(tache);
		teread.start();
	}

}
