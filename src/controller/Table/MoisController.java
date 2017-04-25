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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Model;
import model.Releve;
import utilitaireController.UtilitaireController;

/**
 * cette class permet d'afficher la liste des releve sur une durée d'un mois
 * dans un tableau
 * 
 * @author REMILA Yanis
 * @version 1.2
 *
 */
public class MoisController implements Initializable {
	@FXML
	private TableView<Releve> tableViewMois;
	@FXML
	private TableColumn<Releve, Float> colTemperatureMois, colHumiditeMois, colNebulositeMois;
	@FXML
	private TableColumn<Releve, Integer> colHoraireMois;

	@FXML
	private ChoiceBox<String> tabMoisStation, tabMoisOperation;
	@FXML
	private ChoiceBox<Integer> tabMoisAnnee, tabMoisMois;
	@FXML
	private Label tabMoisMessageProgression;
	@FXML
	private ProgressBar tabMoisprogressBar;
	@FXML
	private Button afficherMois;
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
	 * role de récupérer la liste de releve d'un mois et de les afficher dans un
	 * tableau et suivre l'évolution du chargement des données sur un progresse
	 * bar
	 * 
	 * @param stationChercher
	 *            : station chercher
	 * @param anneeChercher
	 *            : annee chercher
	 * @param moisChercher
	 *            : mois chercher
	 * @param operation
	 *            : operation
	 */
	private void tacheDonneeMois(int stationChercher, int anneeChercher, int moisChercher, int operation) {
		Task<ObservableList<Releve>> tache = new Task<ObservableList<Releve>>() {
			@Override
			protected ObservableList<Releve> call() throws Exception {
				listeAttendue.clear();
				try {
					List<Releve> listDonneeMois = model.donneeMois(stationChercher, anneeChercher, moisChercher,
							operation);
					listeAttendue.addAll(listDonneeMois);
					tableViewMois.setItems((ObservableList<Releve>) listeAttendue);
					updateMessage("chargement terminer");
					updateProgress(1, 1);
				} catch (Exception e) {
					updateMessage(e.getMessage());
					updateProgress(0, 0);
					boiteModale.erreur("erreur", e.getMessage());
				}
				;
				return null;
			}
		};
		initialiserVue.lancerThread(monThread, tache, tabMoisMessageProgression, tabMoisprogressBar);
	}

	/**
	 * permet de récuperer les valeurs des champs sélectionner dans l'IHM et les
	 * passer en parametres a la methode tacheDonneeMois lors du clic sur le btn
	 * afficherMois
	 * 
	 * @param event
	 *            : event
	 */
	@FXML
	void afficherMois(ActionEvent event) {
		int station = model.getidMapStation(tabMoisStation.getValue());
		int annee = tabMoisAnnee.getValue();
		int mois = tabMoisMois.getValue();
		int op = tabMoisOperation.getSelectionModel().getSelectedIndex() + 1;
		tacheDonneeMois(station, annee, mois, op);

	}

	/**
	 * vérifie si tout les champs de l'IHM sont non vide
	 * 
	 * @return false si tout les champs non vide sinon true
	 */
	private Boolean isChoiceBoxEmpty() {
		if (tabMoisStation.getValue() != null && tabMoisAnnee.getValue() != null && tabMoisMois.getValue() != null
				&& tabMoisOperation.getValue() != null && !check.isSelected()) {
			return false;
		}
		return true;
	}

	/**
	 * permet ce crée une relation maitre esclave entre le btn afficherMois et
	 * les autres champs de l'IHM tel que le btn afficherMois reste désactiver
	 * tant que tout les champs ne soit pas remplit et que le checkBox ne soit
	 * pas selectionner pour dire que les listener sont désactiver
	 */
	public void desableBntMois() {
		BooleanBinding bindAfficheMois = new BooleanBinding() {
			{
				super.bind(tabMoisStation.valueProperty(), tabMoisAnnee.valueProperty(), tabMoisMois.valueProperty(),
						tabMoisOperation.valueProperty(), check.selectedProperty());
			}

			@Override
			protected boolean computeValue() {
				if (tabMoisStation.getValue() != null && tabMoisAnnee.getValue() != null
						&& tabMoisMois.getValue() != null && tabMoisOperation.getValue() != null
						&& check.isSelected()) {
					return false;
				}
				return true;
			}
		};
		afficherMois.disableProperty().bind(bindAfficheMois);
	}

	/**
	 * cette methode notifie le checkBox tabMoisAnnee
	 */
	public void choiceboxMois() {
		tabMoisAnnee.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				ObservableList<Integer> obsListeMois = FXCollections.observableArrayList();
				int oldValeurMois = -1;
				if (tabMoisMois.getValue() != null) {
					oldValeurMois = tabMoisMois.getValue() - 1;
				}
				if (newValue == dateLocal.getYear()) {
					for (int mois = 1; mois <= dateLocal.getMonthValue(); mois++) {
						obsListeMois.add(mois);
					}
					if (oldValeurMois + 1 > dateLocal.getMonthValue()) {
						oldValeurMois = dateLocal.getMonthValue() - 1;
					}
					tabMoisMois.setItems(obsListeMois);
					tabMoisMois.getSelectionModel().select(oldValeurMois);
				} else {
					for (int mois = 1; mois <= 12; mois++) {
						obsListeMois.add(mois);
					}
					tabMoisMois.setItems(obsListeMois);
					tabMoisMois.getSelectionModel().select(oldValeurMois);
				}

			}
		});
	}

	/**
	 * cette methode notifie le checkBox tabMoisStation de sort que lorsque il
	 * change de valeur si tout les champs sont rempli la methode
	 * tacheDonneeMois sera appelé avec la nouvelle valeur du champ
	 */
	public void obsChoiceBoxStation() {
		tabMoisStation.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!isChoiceBoxEmpty()) {
					tacheDonneeMois(model.getidMapStation(newValue), tabMoisAnnee.getValue(), tabMoisMois.getValue(),
							tabMoisOperation.getSelectionModel().getSelectedIndex() + 1);
				}

			}
		});
	}

	/**
	 * cette methode notifie le checkBox tabMoisAnnee de sort que lorsque il
	 * change de valeur si tout les champs sont rempli la methode
	 * tacheDonneeMois sera appelé avec la nouvelle valeur du champ
	 */
	private void obsChoiceBoxAnnee() {
		tabMoisAnnee.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				if (!isChoiceBoxEmpty()) {
					tacheDonneeMois(model.getidMapStation(tabMoisStation.getValue()), newValue, tabMoisMois.getValue(),
							tabMoisOperation.getSelectionModel().getSelectedIndex() + 1);
				}

			}
		});
	}

	/**
	 * cette methode notifie le checkBox tabMoisMois de sort que lorsque il
	 * change de valeur si tout les champs sont rempli la methode
	 * tacheDonneeMois sera appelé avec la nouvelle valeur du champ
	 */
	private void obsChoiceBoxMois() {
		tabMoisMois.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				if (!isChoiceBoxEmpty()) {
					tacheDonneeMois(model.getidMapStation(tabMoisStation.getValue()), tabMoisAnnee.getValue(), newValue,
							tabMoisOperation.getSelectionModel().getSelectedIndex() + 1);
				}

			}
		});

	}

	/**
	 * cette methode notifie le checkBox tabMoisOperation de sort que lorsque il
	 * change de valeur si tout les champs sont rempli la methode
	 * tacheDonneeMois sera appelé avec la nouvelle valeur du champ
	 */
	private void obsChoiceBoxOperation() {
		tabMoisOperation.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!isChoiceBoxEmpty()) {
					tacheDonneeMois(model.getidMapStation(tabMoisStation.getValue()), tabMoisAnnee.getValue(),
							tabMoisMois.getValue(), initialiserVue.SelectedIndexOperation(newValue));
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
		initialiserVue.initTableView(colHoraireMois, colTemperatureMois, colHumiditeMois, colNebulositeMois);
		initialiserVue.initListeStation(tabMoisStation);
		initialiserVue.initListeAnnee(tabMoisAnnee);
		initialiserVue.initListeOperation(tabMoisOperation);
		initialiserVue.initListeMois(tabMoisMois);
		initialiserVue.checkBox(check);
		check.setText("Listener\nActiver");
		desableBntMois();
		choiceboxMois();
		obsChoiceBoxStation();
		obsChoiceBoxAnnee();
		obsChoiceBoxMois();
		obsChoiceBoxOperation();

	}

}
