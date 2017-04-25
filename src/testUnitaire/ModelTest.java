package testUnitaire;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import model.Model;
import model.Releve;

public class ModelTest {
	Model model = new Model();
	Releve releve = new Releve();

	@Test
	public void getNomMapStation() {
		String nomStationObtenue = model.getNomMapStation(7005);
		String nomStationAttendue = "ABBEVILLE";
		assertEquals(nomStationAttendue, nomStationObtenue);
	}

	@Test
	public void getgetidMapStation() {
		int idObtenue = model.getidMapStation("ABBEVILLE");
		int idAttendue = 7005;
		assertEquals(idAttendue, idObtenue);
	}

	@Test
	public void testgetMapStation() {
		Map<Integer, String> testMap = model.getMapStation();
		assertTrue(!testMap.isEmpty());
	}

	@Test
	public void testDonneeHoraire() {
		try {
			Releve releveObtenue = model.donneeHoraire(7005, 2014, 02, 01, 00);
			Releve releveAttendue = new Releve(00, (float) 279.75, 89, 0);
			assertTrue(releveObtenue != null);
			assertTrue(releveAttendue.Equals(releveObtenue));
		} catch (Exception e) {
			fail("probleme de connexion internet ");
		}
	}

	@Test
	public void testDonneeJour() {
		try {
			List<Releve> listeObtenue = model.donneeJour(7005, 2014, 02, 01);

			List<Releve> listeAttendue = new ArrayList<>();
			listeAttendue.add(new Releve(0, (float) 279.75, (float) 89.0, (float) 0.0));
			listeAttendue.add(new Releve(3, (float) 280.25, (float) 91.0, (float) 0.0));
			listeAttendue.add(new Releve(6, (float) 280.25, (float) 89.0, (float) 100.0));
			listeAttendue.add(new Releve(9, (float) 280.25, (float) 81.0, (float) 90.0));
			listeAttendue.add(new Releve(12, (float) 281.35, (float) 66.0, (float) 50.0));
			listeAttendue.add(new Releve(15, (float) 281.55, (float) 55.0, (float) 40.0));
			listeAttendue.add(new Releve(18, (float) 279.25, (float) 61.0, (float) 0.0));
			listeAttendue.add(new Releve(21, (float) 278.45, (float) 68.0, (float) 0.0));

			assertTrue(!listeObtenue.isEmpty());
			assertTrue(releve.Equals(listeObtenue, listeAttendue));
		} catch (Exception e) {
			fail("probleme de connexion internet ");
		}

	}

	@Test
	public void testDonneeMoisMoyenneParJour() {
		try {
			List<Releve> listeObtenue = model.donneeMois(7005, 2014, 02, 1);

			List<Releve> listeAttendue = new ArrayList<>();
			listeAttendue.add(new Releve(1, (float) 280.13748, (float) 75.0, (float) 35.0));
			listeAttendue.add(new Releve(2, (float) 278.62143, (float) 75.42857, (float) 10.714286));
			listeAttendue.add(new Releve(3, (float) 276.9357, (float) 85.42857, (float) 0.0));
			listeAttendue.add(new Releve(4, (float) 279.325, (float) 77.125, (float) 26.875));
			listeAttendue.add(new Releve(5, (float) 280.5625, (float) 76.375, (float) 34.375));
			listeAttendue.add(new Releve(6, (float) 280.27496, (float) 81.125, (float) 42.5));
			listeAttendue.add(new Releve(7, (float) 281.55002, (float) 79.125, (float) 41.25));
			listeAttendue.add(new Releve(8, (float) 281.4, (float) 72.5, (float) 40.0));
			listeAttendue.add(new Releve(9, (float) 279.2875, (float) 68.5, (float) 35.0));
			listeAttendue.add(new Releve(10, (float) 279.03754, (float) 83.5, (float) 47.5));
			listeAttendue.add(new Releve(11, (float) 278.1357, (float) 81.14286, (float) 35.714287));
			listeAttendue.add(new Releve(12, (float) 278.5875, (float) 81.125, (float) 31.25));
			listeAttendue.add(new Releve(13, (float) 278.5875, (float) 80.125, (float) 43.75));
			listeAttendue.add(new Releve(14, (float) 279.15, (float) 86.125, (float) 50.0));
			listeAttendue.add(new Releve(15, (float) 282.38748, (float) 66.625, (float) 35.625));
			listeAttendue.add(new Releve(16, (float) 280.18747, (float) 83.0, (float) 27.5));
			listeAttendue.add(new Releve(17, (float) 280.075, (float) 84.0, (float) 36.875));
			listeAttendue.add(new Releve(18, (float) 280.43573, (float) 79.85714, (float) 55.714287));
			listeAttendue.add(new Releve(19, (float) 280.1875, (float) 90.875, (float) 40.0));
			listeAttendue.add(new Releve(20, (float) 282.45, (float) 88.25, (float) 50.0));
			listeAttendue.add(new Releve(21, (float) 279.625, (float) 78.0, (float) 36.875));
			listeAttendue.add(new Releve(22, (float) 279.31253, (float) 85.125, (float) 21.875));
			listeAttendue.add(new Releve(23, (float) 281.2125, (float) 78.75, (float) 43.125));
			listeAttendue.add(new Releve(24, (float) 282.0, (float) 75.375, (float) 40.0));
			listeAttendue.add(new Releve(25, (float) 281.9875, (float) 75.25, (float) 46.25));
			listeAttendue.add(new Releve(26, (float) 279.32498, (float) 82.375, (float) 23.75));
			listeAttendue.add(new Releve(27, (float) 279.07498, (float) 84.0, (float) 42.5));
			listeAttendue.add(new Releve(28, (float) 278.925, (float) 86.375, (float) 46.25));

			assertTrue(!listeObtenue.isEmpty());
			assertTrue(releve.Equals(listeObtenue, listeAttendue));
		} catch (Exception e) {
			fail("probleme de connexion internet ");
		}
	}

	@Test
	public void testDonneeMoisMaxParJour() {
		try {
			List<Releve> listeObtenue = model.donneeMois(7005, 2014, 02, 2);

			List<Releve> listeAttendue = new ArrayList<>();
			listeAttendue.add(new Releve(1, (float) 281.55, (float) 91.0, (float) 100.0));
			listeAttendue.add(new Releve(2, (float) 282.65, (float) 87.0, (float) 40.0));
			listeAttendue.add(new Releve(3, (float) 280.05, (float) 95.0, (float) 0.0));
			listeAttendue.add(new Releve(4, (float) 280.65, (float) 85.0, (float) 90.0));
			listeAttendue.add(new Releve(5, (float) 282.35, (float) 87.0, (float) 100.0));
			listeAttendue.add(new Releve(6, (float) 283.25, (float) 92.0, (float) 100.0));
			listeAttendue.add(new Releve(7, (float) 284.85, (float) 92.0, (float) 100.0));
			listeAttendue.add(new Releve(8, (float) 283.85, (float) 86.0, (float) 100.0));
			listeAttendue.add(new Releve(9, (float) 281.05, (float) 75.0, (float) 100.0));
			listeAttendue.add(new Releve(10, (float) 281.05, (float) 92.0, (float) 100.0));
			listeAttendue.add(new Releve(11, (float) 279.85, (float) 88.0, (float) 100.0));
			listeAttendue.add(new Releve(12, (float) 280.85, (float) 89.0, (float) 100.0));
			listeAttendue.add(new Releve(13, (float) 280.45, (float) 89.0, (float) 100.0));
			listeAttendue.add(new Releve(14, (float) 286.15, (float) 95.0, (float) 100.0));
			listeAttendue.add(new Releve(15, (float) 284.15, (float) 86.0, (float) 100.0));
			listeAttendue.add(new Releve(16, (float) 282.85, (float) 91.0, (float) 100.0));
			listeAttendue.add(new Releve(17, (float) 281.95, (float) 91.0, (float) 90.0));
			listeAttendue.add(new Releve(18, (float) 283.45, (float) 93.0, (float) 100.0));
			listeAttendue.add(new Releve(19, (float) 282.15, (float) 95.0, (float) 100.0));
			listeAttendue.add(new Releve(20, (float) 285.05, (float) 95.0, (float) 100.0));
			listeAttendue.add(new Releve(21, (float) 281.95, (float) 85.0, (float) 100.0));
			listeAttendue.add(new Releve(22, (float) 282.15, (float) 92.0, (float) 75.0));
			listeAttendue.add(new Releve(23, (float) 285.15, (float) 90.0, (float) 90.0));
			listeAttendue.add(new Releve(24, (float) 286.75, (float) 84.0, (float) 100.0));
			listeAttendue.add(new Releve(25, (float) 284.15, (float) 90.0, (float) 100.0));
			listeAttendue.add(new Releve(26, (float) 283.05, (float) 91.0, (float) 90.0));
			listeAttendue.add(new Releve(27, (float) 282.75, (float) 93.0, (float) 100.0));
			listeAttendue.add(new Releve(28, (float) 281.25, (float) 94.0, (float) 100.0));

			assertTrue(!listeObtenue.isEmpty());
			assertTrue(releve.Equals(listeObtenue, listeAttendue));
		} catch (Exception e) {
			fail("probleme de connexion internet ");
		}
	}

	@Test
	public void testDonneeMoisMinParJour() {
		try {
			List<Releve> listeObtenue = model.donneeMois(7005, 2014, 02, 3);

			List<Releve> listeAttendue = new ArrayList<>();

			listeAttendue.add(new Releve(1, (float) 278.45, (float) 55.0, (float) 0.0));
			listeAttendue.add(new Releve(2, (float) 275.55, (float) 62.0, (float) 0.0));
			listeAttendue.add(new Releve(3, (float) 275.15, (float) 73.0, (float) 0.0));
			listeAttendue.add(new Releve(4, (float) 277.95, (float) 72.0, (float) 0.0));
			listeAttendue.add(new Releve(5, (float) 278.05, (float) 66.0, (float) 0.0));
			listeAttendue.add(new Releve(6, (float) 278.55, (float) 76.0, (float) 0.0));
			listeAttendue.add(new Releve(7, (float) 279.35, (float) 71.0, (float) 0.0));
			listeAttendue.add(new Releve(8, (float) 279.45, (float) 52.0, (float) 0.0));
			listeAttendue.add(new Releve(9, (float) 278.25, (float) 61.0, (float) 0.0));
			listeAttendue.add(new Releve(10, (float) 277.25, (float) 75.0, (float) 0.0));
			listeAttendue.add(new Releve(11, (float) 276.85, (float) 73.0, (float) 0.0));
			listeAttendue.add(new Releve(12, (float) 275.85, (float) 69.0, (float) 0.0));
			listeAttendue.add(new Releve(13, (float) 276.95, (float) 71.0, (float) 0.0));
			listeAttendue.add(new Releve(14, (float) 275.55, (float) 68.0, (float) 0.0));
			listeAttendue.add(new Releve(15, (float) 279.75, (float) 57.0, (float) 0.0));
			listeAttendue.add(new Releve(16, (float) 279.15, (float) 72.0, (float) 0.0));
			listeAttendue.add(new Releve(17, (float) 278.05, (float) 76.0, (float) 0.0));
			listeAttendue.add(new Releve(18, (float) 279.45, (float) 69.0, (float) 0.0));
			listeAttendue.add(new Releve(19, (float) 278.15, (float) 83.0, (float) 0.0));
			listeAttendue.add(new Releve(20, (float) 280.75, (float) 83.0, (float) 0.0));
			listeAttendue.add(new Releve(21, (float) 278.25, (float) 64.0, (float) 0.0));
			listeAttendue.add(new Releve(22, (float) 277.65, (float) 77.0, (float) 0.0));
			listeAttendue.add(new Releve(23, (float) 277.15, (float) 65.0, (float) 0.0));
			listeAttendue.add(new Releve(24, (float) 278.75, (float) 61.0, (float) 0.0));
			listeAttendue.add(new Releve(25, (float) 278.75, (float) 59.0, (float) 0.0));
			listeAttendue.add(new Releve(26, (float) 276.65, (float) 70.0, (float) 0.0));
			listeAttendue.add(new Releve(27, (float) 276.45, (float) 73.0, (float) 0.0));
			listeAttendue.add(new Releve(28, (float) 277.05, (float) 73.0, (float) 0.0));

			assertTrue(!listeObtenue.isEmpty());
			assertTrue(releve.Equals(listeObtenue, listeAttendue));
		} catch (Exception e) {
			fail("probleme de connexion internet ");
		}
	}

	@Test
	public void testDonneeMoisEcartTypeParJour() {
		try {
			List<Releve> listeObtenue = model.donneeMois(7005, 2014, 02, 4);

			List<Releve> listeAttendue = new ArrayList<>();

			listeAttendue.add(new Releve(1, (float) 0.9545083, (float) 13.275918, (float) 39.37004));
			listeAttendue.add(new Releve(2, (float) 2.3407688, (float) 7.384485, (float) 14.74269));
			listeAttendue.add(new Releve(3, (float) 1.6470112, (float) 7.5186844, (float) 0.0));
			listeAttendue.add(new Releve(4, (float) 0.9934153, (float) 4.5121365, (float) 34.725487));
			listeAttendue.add(new Releve(5, (float) 1.3138149, (float) 6.575285, (float) 40.11215));
			listeAttendue.add(new Releve(6, (float) 1.4175291, (float) 4.9607835, (float) 46.300648));
			listeAttendue.add(new Releve(7, (float) 1.9608692, (float) 7.507288, (float) 44.84348));
			listeAttendue.add(new Releve(8, (float) 1.4645768, (float) 10.523783, (float) 43.30127));
			listeAttendue.add(new Releve(9, (float) 0.8731195, (float) 4.3011627, (float) 39.37004));
			listeAttendue.add(new Releve(10, (float) 1.4251616, (float) 6.0827627, (float) 47.631397));
			listeAttendue.add(new Releve(11, (float) 0.9847849, (float) 5.591539, (float) 44.03153));
			listeAttendue.add(new Releve(12, (float) 1.7072934, (float) 6.3725486, (float) 40.136486));
			listeAttendue.add(new Releve(13, (float) 1.061764, (float) 5.0605707, (float) 45.259666));
			listeAttendue.add(new Releve(14, (float) 3.3970575, (float) 7.7852025, (float) 50.0));
			listeAttendue.add(new Releve(15, (float) 1.4840331, (float) 8.061289, (float) 38.032677));
			listeAttendue.add(new Releve(16, (float) 1.1123625, (float) 6.670832, (float) 35.61952));
			listeAttendue.add(new Releve(17, (float) 1.2173265, (float) 5.477226, (float) 41.902977));
			listeAttendue.add(new Releve(18, (float) 1.2977252, (float) 6.577637, (float) 48.36089));
			listeAttendue.add(new Releve(19, (float) 1.3133298, (float) 3.8222213, (float) 43.30127));
			listeAttendue.add(new Releve(20, (float) 1.524793, (float) 4.2352686, (float) 50.0));
			listeAttendue.add(new Releve(21, (float) 1.2356708, (float) 7.053368, (float) 38.64401));
			listeAttendue.add(new Releve(22, (float) 1.582671, (float) 5.230619, (float) 29.14806));
			listeAttendue.add(new Releve(23, (float) 2.8257456, (float) 8.771402, (float) 43.368877));
			listeAttendue.add(new Releve(24, (float) 2.6598861, (float) 8.26041, (float) 43.30127));
			listeAttendue.add(new Releve(25, (float) 1.7006909, (float) 9.921567, (float) 46.351242));
			listeAttendue.add(new Releve(26, (float) 2.239279, (float) 7.3303733, (float) 31.19996));
			listeAttendue.add(new Releve(27, (float) 2.3541174, (float) 6.3245554, (float) 44.9305));
			listeAttendue.add(new Releve(28, (float) 1.5650499, (float) 8.230089, (float) 46.351242));

			assertTrue(!listeObtenue.isEmpty());
			assertTrue(releve.Equals(listeObtenue, listeAttendue));
		} catch (Exception e) {
			fail("probleme de connexion internet ");
		}
	}

	@Test
	public void testDonneeAnneeMoyenneParMois() {
		try {
			List<Releve> listeObtenue = model.donneeAnnee(7005, 2014, 1);

			List<Releve> listeAttendue = new ArrayList<>();
			listeAttendue.add(new Releve(1, (float) 279.62576, (float) 85.77074, (float) 40.637096));
			listeAttendue.add(new Releve(2, (float) 279.95636, (float) 80.01722, (float) 36.438137));
			listeAttendue.add(new Releve(3, (float) 281.53873, (float) 77.67742, (float) 30.10484));
			listeAttendue.add(new Releve(4, (float) 284.15652, (float) 77.21726, (float) 33.44524));
			listeAttendue.add(new Releve(5, (float) 285.83185, (float) 78.3629, (float) 34.798386));
			listeAttendue.add(new Releve(6, (float) 288.83414, (float) 75.87679, (float) 34.228172));
			listeAttendue.add(new Releve(7, (float) 291.30728, (float) 80.22581, (float) 35.967743));
			listeAttendue.add(new Releve(8, (float) 289.1808, (float) 81.05472, (float) 39.28091));
			listeAttendue.add(new Releve(9, (float) 289.51376, (float) 81.833336, (float) 31.895834));
			listeAttendue.add(new Releve(10, (float) 286.84393, (float) 83.52419, (float) 36.653225));
			listeAttendue.add(new Releve(11, (float) 282.3537, (float) 86.77321, (float) 39.44048));
			listeAttendue.add(new Releve(12, (float) 278.60764, (float) 85.173386, (float) 35.04032));

			assertTrue(!listeObtenue.isEmpty());
			assertTrue(releve.Equals(listeObtenue, listeAttendue));
		} catch (Exception e) {
			fail("probleme de connexion internet ");
		}
	}

	@Test
	public void testDonneeAnneeMaxParMois() {
		try {
			List<Releve> listeObtenue = model.donneeAnnee(7005, 2014, 2);

			List<Releve> listeAttendue = new ArrayList<>();
			listeAttendue.add(new Releve(1, (float) 287.05, (float) 100.0, (float) 101.0));
			listeAttendue.add(new Releve(2, (float) 286.75, (float) 95.0, (float) 100.0));
			listeAttendue.add(new Releve(3, (float) 292.95, (float) 99.0, (float) 101.0));
			listeAttendue.add(new Releve(4, (float) 293.25, (float) 99.0, (float) 101.0));
			listeAttendue.add(new Releve(5, (float) 297.75, (float) 99.0, (float) 100.0));
			listeAttendue.add(new Releve(6, (float) 297.55, (float) 97.0, (float) 100.0));
			listeAttendue.add(new Releve(7, (float) 302.95, (float) 98.0, (float) 100.0));
			listeAttendue.add(new Releve(8, (float) 298.25, (float) 98.0, (float) 100.0));
			listeAttendue.add(new Releve(9, (float) 299.85, (float) 98.0, (float) 100.0));
			listeAttendue.add(new Releve(10, (float) 295.85, (float) 99.0, (float) 100.0));
			listeAttendue.add(new Releve(11, (float) 292.65, (float) 98.0, (float) 101.0));
			listeAttendue.add(new Releve(12, (float) 285.15, (float) 97.0, (float) 100.0));

			assertTrue(!listeObtenue.isEmpty());
			assertTrue(releve.Equals(listeObtenue, listeAttendue));
		} catch (Exception e) {
			fail("probleme de connexion internet ");
		}
	}

	@Test
	public void testDonneeAnneeMinParMois() {
		try {
			List<Releve> listeObtenue = model.donneeAnnee(7005, 2014, 3);
			List<Releve> listeAttendue = new ArrayList<>();
			listeAttendue.add(new Releve(1, (float) 272.65, (float) 61.0, (float) 0.0));
			listeAttendue.add(new Releve(2, (float) 275.15, (float) 52.0, (float) 0.0));
			listeAttendue.add(new Releve(3, (float) 273.15, (float) 35.0, (float) 0.0));
			listeAttendue.add(new Releve(4, (float) 274.95, (float) 39.0, (float) 0.0));
			listeAttendue.add(new Releve(5, (float) 275.15, (float) 37.0, (float) 0.0));
			listeAttendue.add(new Releve(6, (float) 280.05, (float) 40.0, (float) 0.0));
			listeAttendue.add(new Releve(7, (float) 281.25, (float) 42.0, (float) 0.0));
			listeAttendue.add(new Releve(8, (float) 282.95, (float) 47.0, (float) 0.0));
			listeAttendue.add(new Releve(9, (float) 279.75, (float) 45.0, (float) 0.0));
			listeAttendue.add(new Releve(10, (float) 279.85, (float) 52.0, (float) 0.0));
			listeAttendue.add(new Releve(11, (float) 276.15, (float) 62.0, (float) 0.0));
			listeAttendue.add(new Releve(12, (float) 269.65, (float) 54.0, (float) 0.0));

			assertTrue(!listeObtenue.isEmpty());
			assertTrue(releve.Equals(listeObtenue, listeAttendue));
		} catch (Exception e) {
			fail("probleme de connexion internet ");
		}
	}

	@Test
	public void testDonneeAnneeEcartTypeParMois() {
		try {
			List<Releve> listeObtenue = model.donneeAnnee(7005, 2014, 4);
			List<Releve> listeAttendue = new ArrayList<>();
			listeAttendue.add(new Releve(1, (float) 0.3615894, (float) 0.64734477, (float) 1.5020219));
			listeAttendue.add(new Releve(2, (float) 0.194911, (float) 1.2015076, (float) 1.8542677));
			listeAttendue.add(new Releve(3, (float) 0.7833067, (float) 1.2891029, (float) 2.8997564));
			listeAttendue.add(new Releve(4, (float) 0.17691888, (float) 2.9956295, (float) 2.520393));
			listeAttendue.add(new Releve(5, (float) 0.115514584, (float) 0.5590933, (float) 2.3211086));
			listeAttendue.add(new Releve(6, (float) 0.11577481, (float) 0.6386823, (float) 1.2820045));
			listeAttendue.add(new Releve(7, (float) 0.1472393, (float) 0.17526014, (float) 1.5208515));
			listeAttendue.add(new Releve(8, (float) 0.012431177, (float) 0.12208096, (float) 0.88112754));
			listeAttendue.add(new Releve(9, (float) 0.05956728, (float) 1.2399826, (float) 3.4270697));
			listeAttendue.add(new Releve(10, (float) 0.22783914, (float) 1.3513849, (float) 2.6542475));
			listeAttendue.add(new Releve(11, (float) 0.75151384, (float) 1.8215046, (float) 2.019183));
			listeAttendue.add(new Releve(12, (float) 0.34261927, (float) 1.1312234, (float) 2.0278018));

			assertTrue(!listeObtenue.isEmpty());
			assertTrue(releve.Equals(listeObtenue, listeAttendue));
		} catch (Exception e) {
			fail("probleme de connexion internet ");
		}
	}

}
