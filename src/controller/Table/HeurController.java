package controller.Table;

import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

import boiteModale.BoiteModale;
import interfaceModel.Imodel;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Model;
import model.Releve;
import utilitaireController.UtilitaireController;

/**
 * cette class permet d'afficher un releve d'un horaire dans un tableau
 * 
 * @author REMILA Yanis
 * @version 1.1
 *
 */
public class HeurController implements Initializable {
	@FXML
	private TableView<Releve> tableViewHeur;
	@FXML
	private TableColumn<Releve, Float> colTemperatureHeur, colHumiditeHeur, colNebulositeHeur;
	@FXML
	private TableColumn<Releve, Integer> colHoraireHeur;

	@FXML
	private ChoiceBox<String> tabHeurStation;
	@FXML
	private Label tabHeurMessageProgression;
	@FXML
	private ProgressBar tabHeurprogressBar;
	@FXML
	private ChoiceBox<Integer> Heure;
	@FXML
	private DatePicker tabHeurDate;
	@FXML
	private Button afficherHeur;
	@FXML
	private CheckBox check;

	ObservableList<Releve> listeAttendue = FXCollections.observableArrayList();
	UtilitaireController initialiserVue = new UtilitaireController();
	BoiteModale boiteModale = new BoiteModale();
	LocalDate dateLocal = LocalDate.now();
	Imodel model = new Model();
	Thread monThread;

	/**
	 * cette methode permet lancé une tache dans un thread et cette tache a pour
	 * role de récupérer un releve d'une heur et de l'afficher dans un tableau
	 * et suivre l'évolution du chargement des données sur un progresse bar
	 * 
	 * @param stationChercher
	 *            : station chercher
	 * @param anneeChercher
	 *            : annee chercher
	 * @param moisChercher
	 *            : mois chercher
	 * @param jourChercher
	 *            : jour chercher
	 * @param heurChercher
	 *            : heur chercher
	 */
	private void tacheDonneeHeur(int stationChercher, int anneeChercher, int moisChercher, int jourChercher,
			int heurChercher) {
		Task<ObservableList<Releve>> tache = new Task<ObservableList<Releve>>() {
			@Override
			protected ObservableList<Releve> call() throws Exception {
				listeAttendue.clear();
				try {
					Releve DonneeHeur = model.donneeHoraire(stationChercher, anneeChercher, moisChercher, jourChercher,
							heurChercher);
					listeAttendue.add(DonneeHeur);
					tableViewHeur.setItems((ObservableList<Releve>) listeAttendue);
					updateProgress(1, 1);
					updateMessage("chargement terminer");
				} catch (Exception e) {
					updateMessage(e.getMessage());
					updateProgress(0, 0);
					boiteModale.erreur("erreur", e.getMessage());
				}
				;
				return null;
			}
		};
		initialiserVue.lancerThread(monThread, tache, tabHeurMessageProgression, tabHeurprogressBar);
	}

	/**
	 * permet de récuperer les valeurs des champs sélectionner dans l'IHM et les
	 * passer en parametres a la methode tacheDonneeHeur lors du clic sur le btn
	 * afficherHeur
	 * 
	 * @param event
	 *            : event
	 */
	@FXML
	void afficherHeur(ActionEvent event) {
		LocalDate datePiker = tabHeurDate.getValue();
		int station = model.getidMapStation(tabHeurStation.getValue());
		int annee = datePiker.getYear();
		int mois = datePiker.getMonthValue();
		int jour = datePiker.getDayOfMonth();
		int heur = Heure.getValue();
		tacheDonneeHeur(station, annee, mois, jour, heur);
	}

	/**
	 * permet ce crée une relation maitre esclave entre le btn afficherHeur et
	 * les autres champs de l'IHM tel que le btn afficherHeur reste désactiver
	 * tant que tout les champs ne soit pas remplit et que le checkBox ne soit
	 * pas selectionner pour dire que les listener sont désactiver
	 */
	public void desableBntHeur() {
		BooleanBinding bindAfficheAnnee = new BooleanBinding() {
			{
				super.bind(tabHeurStation.valueProperty(), tabHeurDate.valueProperty(), Heure.valueProperty(),
						check.selectedProperty());
			}

			@Override
			protected boolean computeValue() {
				if (tabHeurStation.getValue() != null && tabHeurDate.getValue() != null && Heure.getValue() != null
						&& check.isSelected()) {
					return false;
				}
				return true;
			}
		};
		afficherHeur.disableProperty().bind(bindAfficheAnnee);
	}

	/**
	 * vérifie si tout les champs de l'IHM sont non vide
	 * 
	 * @return false si tout les champs non vide sinon true
	 * 
	 */
	private Boolean isChoiceBoxEmpty() {
		if (tabHeurStation.getValue() != null && tabHeurDate.getValue() != null && Heure.getValue() != null
				&& !check.isSelected()) {
			return false;
		}
		return true;
	}

	/**
	 * cette methode notifie le checkBox tabHeurDate
	 */
	public void choiceBoxHeur() {
		tabHeurDate.valueProperty().addListener(new ChangeListener<LocalDate>() {

			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {
				Calendar rightNow = Calendar.getInstance();
				int heur = rightNow.get(Calendar.HOUR_OF_DAY);
				ObservableList<Integer> obsListeHeur = FXCollections.observableArrayList();
				if (newValue.getYear() == dateLocal.getYear() && newValue.getMonth() == dateLocal.getMonth()
						&& newValue.getDayOfMonth() == dateLocal.getDayOfMonth()) {
					for (int index = 0; index <= heur; index += 3) {
						obsListeHeur.add(index);
					}
				} else {
					for (int index = 0; index < 24; index += 3) {
						obsListeHeur.add(index);
					}
				}
				Heure.setItems(obsListeHeur);
			}

		});
	}

	/**
	 * cette methode notifie le checkBox tabHeurStation de sort que lorsque il
	 * change de valeur si tout les champs sont rempli la methode
	 * tacheDonneeheur sera appelé avec la nouvelle valeur du champ
	 */
	private void obsChoiceBoxStation() {
		tabHeurStation.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				LocalDate dateObtenue = tabHeurDate.getValue();
				if (!isChoiceBoxEmpty()) {
					tacheDonneeHeur(model.getidMapStation(newValue), dateObtenue.getYear(), dateObtenue.getMonthValue(),
							dateObtenue.getDayOfMonth(), Heure.getSelectionModel().getSelectedIndex() + 1);
				}

			}
		});
	}

	/**
	 * cette methode notifie le datepicker tabHeurDate de sort que lorsque il
	 * change de valeur si tout les champs sont rempli la methode
	 * tacheDonneeheur sera appelé avec la nouvelle valeur du champ
	 */
	private void obsDatePiker() {
		tabHeurDate.valueProperty().addListener(new ChangeListener<LocalDate>() {

			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {
				if (!isChoiceBoxEmpty()) {
					tacheDonneeHeur(model.getidMapStation(tabHeurStation.getValue()), newValue.getYear(),
							newValue.getMonthValue(), newValue.getDayOfMonth(),
							Heure.getSelectionModel().getSelectedIndex() + 1);
				}

			}
		});
	}

	/**
	 * cette methode notifie le ChoiceBoxe Heure de sort que lorsque il change
	 * de valeur si tout les champs sont rempli la methode tacheDonneeheur sera
	 * appelé avec la nouvelle valeur du champ
	 */
	private void obsChoiceBoxHeur() {
		Heure.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				LocalDate dateObtenue = tabHeurDate.getValue();
				if (!isChoiceBoxEmpty()) {
					tacheDonneeHeur(model.getidMapStation(tabHeurStation.getValue()), dateObtenue.getYear(),
							dateObtenue.getMonthValue(), dateObtenue.getDayOfMonth(), newValue);
				}

			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initialiserVue.initTableView(colHoraireHeur, colTemperatureHeur, colHumiditeHeur, colNebulositeHeur);
		initialiserVue.initListeStation(tabHeurStation);
		initialiserVue.controlleDate(tabHeurDate);
		initialiserVue.checkBox(check);
		desableBntHeur();
		choiceBoxHeur();
		obsChoiceBoxHeur();
		obsChoiceBoxStation();
		obsDatePiker();
	}

}
