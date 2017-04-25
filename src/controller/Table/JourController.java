package controller.Table;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
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
 * cette class permet d'afficher la liste des releve sur une durée d'une journée
 * dans un tableau
 * 
 * @author REMILA Yanis
 * @version 1.1
 *
 */
public class JourController implements Initializable {
	@FXML
	private TableView<Releve> tableViewJour;
	@FXML
	private TableColumn<Releve, Integer> colHoraireJour;
	@FXML
	private TableColumn<Releve, Float> colTemperatureJour, colHumiditeJour, colNebulositeJour;

	@FXML
	private ChoiceBox<String> tabJourStation;
	@FXML
	private Label tabJourMessageProgression;
	@FXML
	private ProgressBar tabJourprogressBar;
	@FXML
	private DatePicker tabJourDate;
	@FXML
	private Button afficherJour;
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
	 * role de récupérer la liste de releve d'une journée et de les afficher
	 * dans un tableau et suivre l'évolution du chargement des données sur un
	 * progresse bar
	 * 
	 * @param stationChercher
	 *            : station chercher
	 * @param anneeChercher
	 *            : annee chercher
	 * @param moisChercher
	 *            : mois chercher
	 * @param jourChercher
	 *            : jour chercher
	 */
	private void tacheDonneeJour(int stationChercher, int anneeChercher, int moisChercher, int jourChercher) {
		Task<ObservableList<Releve>> tache = new Task<ObservableList<Releve>>() {
			@Override
			protected ObservableList<Releve> call() throws Exception {
				listeAttendue.clear();
				try {
					List<Releve> listDonneeJour = model.donneeJour(stationChercher, anneeChercher, moisChercher,
							jourChercher);
					listeAttendue.addAll(listDonneeJour);
					tableViewJour.setItems((ObservableList<Releve>) listeAttendue);
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
		initialiserVue.lancerThread(monThread, tache, tabJourMessageProgression, tabJourprogressBar);
	}

	/**
	 * permet de récuperer les valeurs des champs sélectionner dans l'IHM et les
	 * passer en parametres a la methode tacheDonneeJour lors du clic sur le btn
	 * afficherJour
	 * 
	 * @param event
	 *            : event
	 */
	@FXML
	void afficherJour(ActionEvent event) {
		LocalDate datePiker = tabJourDate.getValue();
		int station = model.getidMapStation(tabJourStation.getValue());
		int annee = datePiker.getYear();
		int mois = datePiker.getMonthValue();
		int jour = datePiker.getDayOfMonth();
		tacheDonneeJour(station, annee, mois, jour);

	}

	/**
	 * vérifie si tout les champs de l'IHM sont non vide
	 * 
	 * @return false si tout les champs non vide sinon true
	 */
	private Boolean isChoiceBoxEmpty() {
		if (tabJourStation.getValue() != null && tabJourDate.getValue() != null && !check.isSelected()) {
			return false;
		}
		return true;
	}

	/**
	 * permet ce crée une relation maitre esclave entre le btn afficherJour et
	 * les autres champs de l'IHM tel que le btn afficherJour reste désactiver
	 * tant que tout les champs ne soit pas remplit et que le checkBox ne soit
	 * pas selectionner pour dire que les listener sont désactiver
	 */
	public void desableBntJour() {
		BooleanBinding bindAfficheAnnee = new BooleanBinding() {
			{
				super.bind(tabJourStation.valueProperty(), tabJourDate.valueProperty(), check.selectedProperty());
			}

			@Override
			protected boolean computeValue() {
				if (tabJourStation.getValue() != null && tabJourDate.getValue() != null && check.isSelected()) {
					return false;
				}
				return true;
			}
		};
		afficherJour.disableProperty().bind(bindAfficheAnnee);
	}

	/**
	 * cette methode notifie le checkBox tabJourStation de sort que lorsque il
	 * change de valeur si tout les champs sont rempli la methode
	 * tacheDonneeJour sera appelé avec la nouvelle valeur du champ
	 */
	private void obsChoiceBoxStation() {
		tabJourStation.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				LocalDate dateObtenue = tabJourDate.getValue();
				if (!isChoiceBoxEmpty()) {
					tacheDonneeJour(model.getidMapStation(newValue), dateObtenue.getYear(), dateObtenue.getMonthValue(),
							dateObtenue.getDayOfMonth());
				}

			}
		});
	}

	/**
	 * cette methode notifie le DatePicker tabJourDate de sort que lorsque il
	 * change de valeur si tout les champs sont rempli la methode
	 * tacheDonneeJour sera appelé avec la nouvelle valeur du champ
	 */
	private void obsDatePiker() {
		tabJourDate.valueProperty().addListener(new ChangeListener<LocalDate>() {

			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {
				if (!isChoiceBoxEmpty()) {
					tacheDonneeJour(model.getidMapStation(tabJourStation.getValue()), newValue.getYear(),
							newValue.getMonthValue(), newValue.getDayOfMonth());
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

		initialiserVue.initTableView(colHoraireJour, colTemperatureJour, colHumiditeJour, colNebulositeJour);
		initialiserVue.initListeStation(tabJourStation);
		initialiserVue.controlleDate(tabJourDate);
		initialiserVue.checkBox(check);
		check.setText("Listener\nActiver");
		desableBntJour();
		obsChoiceBoxStation();
		obsDatePiker();
	}

}
