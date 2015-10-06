package todd;

import java.util.ArrayList;
import java.util.List;

/**
 * Dies ist das Spielfeld.
 *
 * @author christian
 *
 */
public class Spielfeld {

	private final int SPIELFELD_LAENGE = 7;
	private final int UNDEFINIERT = -1;
	private final int FREI = 0;
	private final int BELEGT = 1;

	// CHECKSTYLE:OFF
	private final int[][] gewinnStellung = new int[][] { //
			{ -1, -1, 0, 0, 0, -1, -1 }, //
			{ -1, -1, 0, 0, 0, -1, -1 }, //
			{ 0, 0, 0, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 1, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 0, 0, 0 }, //
			{ -1, -1, 0, 0, 0, -1, -1 }, //
			{ -1, -1, 0, 0, 0, -1, -1 }, //
	};
	// CHECKSTYLE:ON

	private int[][] feld;

	public Spielfeld() {
		// initialisiere das Spielfeld
		// CHECKSTYLE:OFF
		this.feld = new int[][] { //
				{ -1, -1, 1, 1, 1, -1, -1 }, //
				{ -1, -1, 1, 1, 1, -1, -1 }, //
				{ 1, 1, 1, 1, 1, 1, 1 }, //
				{ 1, 1, 1, 0, 1, 1, 1 }, //
				{ 1, 1, 1, 1, 1, 1, 1 }, //
				{ -1, -1, 1, 1, 1, -1, -1 }, //
				{ -1, -1, 1, 1, 1, -1, -1 }, //
		};

	}

	/**
	 * liefert den Inhalt eines gesuchten Felds.
	 *
	 * @param pX
	 *            x-Koordinate des Felds
	 * @param pY
	 *            y-Koordinate des Felds
	 * @return Inhalt des Felds
	 */
	public int getFeld(final int pX, final int pY) {
		int feldInhalt = this.UNDEFINIERT;
		if ((pX >= 0) && (pX < this.SPIELFELD_LAENGE)) {
			if ((pY >= 0) && (pY < this.SPIELFELD_LAENGE)) {
				feldInhalt = this.feld[pX][pY];
			}
		}
		return feldInhalt;
	}

	/**
	 * belegt ein bestimmtes Feld mit einem Wert.
	 *
	 * @param pX
	 *            x-Koordinate
	 * @param pY
	 *            y-Koordinate
	 * @param wert
	 *            Wert für das zu belegende Feld
	 */
	public void setFeld(final int pX, final int pY, final int wert) {
		if ((pX >= 0) && (pX < this.SPIELFELD_LAENGE)) {
			if ((pY >= 0) && (pY < this.SPIELFELD_LAENGE)) {
				this.feld[pX][pY] = wert;
			}
		}
	}

	/**
	 * sucht alle momentan möglichen Züge.
	 *
	 * @return Liste aller möglichen Züge
	 */
	public List<Zug> getAlleZuege() {
		List<Zug> zuege = new ArrayList<Zug>();
		for (int i = 0; i < this.SPIELFELD_LAENGE; i++) {
			for (int j = 0; j < this.SPIELFELD_LAENGE; j++) {
				int currentField = this.getFeld(i, j);
				if (currentField == this.BELEGT) {
					if ((this.getFeld(i + 1, j) == this.BELEGT) && ((this.getFeld(i + 2, j)) == this.FREI)) {
						Zug zug = new Zug(i, j, ERichtung.SUEDEN);
						zuege.add(zug);
					}
					if ((this.getFeld(i - 1, j) == this.BELEGT) && ((this.getFeld(i - 2, j)) == this.FREI)) {
						Zug zug = new Zug(i, j, ERichtung.NORDEN);
						zuege.add(zug);
					}
					if ((this.getFeld(i, j + 1) == this.BELEGT) && ((this.getFeld(i, j + 2)) == this.FREI)) {
						Zug zug = new Zug(i, j, ERichtung.OSTEN);
						zuege.add(zug);
					}
					if ((this.getFeld(i, j - 1) == this.BELEGT) && ((this.getFeld(i, j - 2)) == this.FREI)) {
						Zug zug = new Zug(i, j, ERichtung.WESTEN);
						zuege.add(zug);
					}
				}
			}
		}
		return zuege;
	}

	public Spielfeld copy() {
		Spielfeld copySpielfeld = new Spielfeld();
		for (int i = 0; i < this.SPIELFELD_LAENGE; i++) {
			for (int j = 0; j < this.SPIELFELD_LAENGE; j++) {
				copySpielfeld.setFeld(i, j, this.getFeld(i, j));
			}
		}
		return copySpielfeld;
	}

	public boolean gewonnen() {
		boolean gewonnen = true;
		for (int i = 0; i < this.SPIELFELD_LAENGE; i++) {
			for (int j = 0; j < this.SPIELFELD_LAENGE; j++) {
				int currentFeld = this.getFeld(i, j);
				if (currentFeld != this.gewinnStellung[i][j]) {
					gewonnen = false;
					break;
				}
			}
		}
		return gewonnen;
	}

	/**
	 * spielt das Spiel durch. Diese Methode wird rekursiv aufgerufen.
	 *
	 * @param nrZug
	 *            Nummer des aktuellen Zugs.
	 * @param pGewinnZuege
	 *            Liste mit allen bisher durchgespielten Zügen
	 * @return Ist das Spiel gewonnen?
	 */
	public boolean play(final int nrZug, final List<Zug> pGewinnZuege) {
		boolean gewonnen = false;
		List<Zug> zuege = this.getAlleZuege();
		for (Zug zug : zuege) {
			zug.doIt(this);
			if (this.gewonnen()) {
				gewonnen = true;
				pGewinnZuege.add(zug);
				break;
			}
			pGewinnZuege.add(zug);
			gewonnen = this.play(nrZug + 1, pGewinnZuege);
			if (gewonnen) {
				break;
			} else {
				zug.undoIt(this);
				pGewinnZuege.remove(pGewinnZuege.size() - 1);
			}
		}
		return gewonnen;
	}

	public void print() {
		for (int i = 0; i < this.SPIELFELD_LAENGE; i++) {
			System.out.print("Zeile " + i + ":    ");
			for (int j = 0; j < this.SPIELFELD_LAENGE; j++) {
				int currentField = this.getFeld(i, j);
				if (currentField == this.BELEGT) {
					System.out.print(currentField);
				} else if (currentField == this.UNDEFINIERT) {
					System.out.print(" ");
				} else {
					System.out.print("-");
				}
				System.out.print("   ");
			}
			System.out.println("");
		}
	}
}
