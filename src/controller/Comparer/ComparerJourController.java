package controller.Comparer;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import boiteModale.BoiteModale;
import interfaceModel.Imodel;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import model.Model;
import model.Releve;
import utilitaireController.Graph;
import utilitaireController.UtilitaireController;
import utilitaireController.SubtractListe;

/**
 * cette class permet de comparer l'évolution des releve de deux journées et les
 * afficher dans un graphe
 * 
 * @author REMILA Yanis
 * @version 1.3
 *
 */
public class ComparerJourController implements Initializable {
	private final CategoryAxis xAxis = new CategoryAxis();
	private final NumberAxis yAxis = new NumberAxis();
	private ObservableList<XYChart.Series<String, Number>> obsList = FXCollections.observableArrayList();
	@FXML
	private LineChart<String, Number> chartJour = new LineChart<>(xAxis, yAxis, obsList);

	@FXML
	private ChoiceBox<String> Station, parametres, comparaison;
	@FXML
	private Label messageProgression;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private DatePicker date1, date2;
	@FXML
	private Button btnJour;
	@FXML
	private CheckBox check;

	List<List<Releve>> listJour = new ArrayList<List<Releve>>();
	List<Integer> listeParametre = new ArrayList<Integer>();
	UtilitaireController initialiserVue = new UtilitaireController();
	BoiteModale boiteModale = new BoiteModale();
	LocalDate dateLocal = LocalDate.now();
	Imodel model = new Model();
	Thread monThread;

	/**
	 * cette methode permet lancé une tache dans un thread et cette tache a pour
	 * role de récupérer les listes de releve deux années et d'afficher leur
	 * series(courbe) on plus de celle de la difference et de l'ecart type dans
	 * un graph et suivre l'évolution du chargement des données sur un progresse
	 * bar
	 * 
	 * @param listeParametre
	 *            : liste des parametres
	 */
	private void tacheDonneeJour(List<Integer> listeParametre) {
		chartJour.getData().clear();
		listJour.clear();
		Task<ObservableList<Releve>> tache = new Task<ObservableList<Releve>>() {
			@Override
			protected ObservableList<Releve> call() throws Exception {
				try {
					List<Releve> listDonneeJour1 = model.donneeJour(listeParametre.get(0), listeParametre.get(1),
							listeParametre.get(2), listeParametre.get(3));
					List<Releve> listDonneeJour2 = model.donneeJour(listeParametre.get(0), listeParametre.get(4),
							listeParametre.get(5), listeParametre.get(6));
					listJour.add(listDonneeJour1);
					listJour.add(listDonneeJour2);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							List<Releve> subList = SubtractListe.subtractList(listDonneeJour1, listDonneeJour2);
							listJour.add(subList);

							List<XYChart.Series<String, Number>> listeSeries = Graph.createListSerie(listJour,
									listeParametre.get(7), 2, 2, "jour");
							for (XYChart.Series<String, Number> serie : listeSeries) {
								chartJour.getData().add(serie);
							}
							updateProgress(1, 1);
							updateMessage("chergement terminer");
						}
					});
				} catch (Exception e) {
					updateMessage(e.getMessage());
					updateProgress(0, 0);
					boiteModale.information("erreur", e.getMessage());
				}
				return null;
			}
		};
		initialiserVue.lancerThread(monThread, tache, messageProgression, progressBar);
	}

	/**
	 * permet de récuperer les valeurs des champs sélectionner dans l'IHM et les
	 * passer en parametres a la methode tacheDonneeJour lors du clic sur le
	 * btnComparer
	 * 
	 * @param event
	 *            : event
	 */
	@FXML
	void comparerJour(ActionEvent event) {
		listeParametre.clear();
		LocalDate date1Obtenue = date1.getValue();
		LocalDate date2Obtenue = date2.getValue();
		listeParametre.add(model.getidMapStation(Station.getValue()));
		listeParametre.add(date1Obtenue.getYear());
		listeParametre.add(date1Obtenue.getMonthValue());
		listeParametre.add(date1Obtenue.getDayOfMonth());
		listeParametre.add(date2Obtenue.getYear());
		listeParametre.add(date2Obtenue.getMonthValue());
		listeParametre.add(date2Obtenue.getDayOfMonth());
		listeParametre.add(parametres.getSelectionModel().getSelectedIndex() + 1);
		tacheDonneeJour(listeParametre);
	}

	/**
	 * permet ce crée une relation maitre esclave entre le btnJour et les autres
	 * champs de l'IHM tel que le bntJour reste désactiver tant que tout les
	 * champs ne soit pas remplit et que le checkBox ne soit pas selectionner
	 * pour dire que les listener sont désactiver
	 */
	public void disableBntVisualiser() {
		BooleanBinding bindAfficheMois = new BooleanBinding() {
			{
				super.bind(Station.valueProperty(), date1.valueProperty(), date2.valueProperty(),
						parametres.valueProperty(), check.selectedProperty());
			}

			@Override
			protected boolean computeValue() {
				if (Station.getValue() != null && date1.getValue() != null && date1.getValue() != null
						&& parametres.getValue() != null && check.isSelected()) {
					return false;
				}
				return true;
			}
		};
		btnJour.disableProperty().bind(bindAfficheMois);
	}

	/**
	 * vérifie si tout les champs que l'IHM son non vide
	 * 
	 * @return false si tout les champs non vide sinon true
	 */
	private Boolean isChoiceBoxEmpty() {
		if (Station.getValue() != null && date1.getValue() != null && date2.getValue() != null
				&& parametres.getValue() != null && !check.isSelected()) {
			return false;
		}
		return true;
	}

	/**
	 * cette methode notifie le datePiker date1 de sort que lorsque il change de
	 * valeur si tout les champs sont rempli la methode tacheDonneeJour sera
	 * appelé avec la nouvelle valeur du champ
	 */
	private void obsDatePiker1() {
		date1.valueProperty().addListener(new ChangeListener<LocalDate>() {

			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {
				if (!isChoiceBoxEmpty()) {
					listeParametre.clear();
					LocalDate date2Obtenue = date2.getValue();
					listeParametre.add(model.getidMapStation(Station.getValue()));
					listeParametre.add(newValue.getYear());
					listeParametre.add(newValue.getMonthValue());
					listeParametre.add(newValue.getDayOfMonth());
					listeParametre.add(date2Obtenue.getYear());
					listeParametre.add(date2Obtenue.getMonthValue());
					listeParametre.add(date2Obtenue.getDayOfMonth());
					listeParametre.add(parametres.getSelectionModel().getSelectedIndex() + 1);
					tacheDonneeJour(listeParametre);
				}

			}
		});
	}

	/**
	 * cette methode notifie le datePiker date2 de sort que lorsque il change de
	 * valeur si tout les champs sont rempli la methode tacheDonneeJour sera
	 * appelé avec la nouvelle valeur du champ
	 */
	private void obsDatePiker2() {
		date2.valueProperty().addListener(new ChangeListener<LocalDate>() {

			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {
				if (!isChoiceBoxEmpty()) {
					listeParametre.clear();
					LocalDate date1Obtenue = date1.getValue();
					listeParametre.add(model.getidMapStation(Station.getValue()));
					listeParametre.add(date1Obtenue.getYear());
					listeParametre.add(date1Obtenue.getMonthValue());
					listeParametre.add(date1Obtenue.getDayOfMonth());
					listeParametre.add(newValue.getYear());
					listeParametre.add(newValue.getMonthValue());
					listeParametre.add(newValue.getDayOfMonth());
					listeParametre.add(parametres.getSelectionModel().getSelectedIndex() + 1);
					tacheDonneeJour(listeParametre);
				}

			}
		});
	}

	/**
	 * cette methode notifie le ChoiceBox satation de sort que lorsque il change
	 * de valeur si tout les champs sont rempli la methode tacheDonneeJour sera
	 * appelé avec la nouvelle valeur du champ
	 */
	private void obsChoiceBoxStation() {
		Station.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!isChoiceBoxEmpty()) {
					listeParametre.clear();
					LocalDate date1Obtenue = date1.getValue();
					LocalDate date2Obtenue = date2.getValue();
					listeParametre.add(model.getidMapStation(newValue));
					listeParametre.add(date1Obtenue.getYear());
					listeParametre.add(date1Obtenue.getMonthValue());
					listeParametre.add(date1Obtenue.getDayOfMonth());
					listeParametre.add(date2Obtenue.getYear());
					listeParametre.add(date2Obtenue.getMonthValue());
					listeParametre.add(date2Obtenue.getDayOfMonth());
					listeParametre.add(parametres.getSelectionModel().getSelectedIndex() + 1);
					tacheDonneeJour(listeParametre);
				}

			}
		});
	}

	/**
	 * cette methode notifie le ChoiceBox parametres de sort que lorsque il
	 * change de valeur si tout les champs sont rempli la methode
	 * tacheDonneeJour sera appelé avec la nouvelle valeur du champ
	 */
	private void obsChoiceBoxParametre() {
		parametres.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				listeParametre.clear();
				LocalDate date1Obtenue = date1.getValue();
				LocalDate date2Obtenue = date2.getValue();
				if (!isChoiceBoxEmpty()) {
					listeParametre.add(model.getidMapStation(Station.getValue()));
					listeParametre.add(date1Obtenue.getYear());
					listeParametre.add(date1Obtenue.getMonthValue());
					listeParametre.add(date1Obtenue.getDayOfMonth());
					listeParametre.add(date2Obtenue.getYear());
					listeParametre.add(date2Obtenue.getMonthValue());
					listeParametre.add(date2Obtenue.getDayOfMonth());
					listeParametre.add(initialiserVue.SelectedIndexParametre(newValue));
					tacheDonneeJour(listeParametre);
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
		initialiserVue.initListeStation(Station);
		initialiserVue.initListeParametre(parametres);
		initialiserVue.initListeComparaison(comparaison, true);
		initialiserVue.controlleDate(date1);
		initialiserVue.controlleDate(date2);
		initialiserVue.checkBox(check);
		disableBntVisualiser();
		check.setText("Listener\nActiver");
		obsDatePiker1();
		obsDatePiker2();
		obsChoiceBoxStation();
		obsChoiceBoxParametre();
	}

}
