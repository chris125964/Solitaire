package todd;

/**
 * Definiert einen möglichen gültigen Zug.
 *
 * @author christian
 *
 */
public class Zug {

	private final int FREI = 0;
	private final int BELEGT = 1;

	private int x;
	private int y;
	private ERichtung richtung;

	public Zug(final int pX, final int pY, final ERichtung pRichtung) {
		this.x = pX;
		this.y = pY;
		this.richtung = pRichtung;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public ERichtung getRichtung() {
		return this.richtung;
	}

	public void undoIt(final Spielfeld pFeld) {
		this.perform(pFeld, this.BELEGT, this.FREI);
	}

	public void doIt(final Spielfeld pFeld) {
		this.perform(pFeld, this.FREI, this.BELEGT);
	}

	public void perform(final Spielfeld pFeld, final int pValue1, final int pValue2) {
		int x = this.getX();
		int y = this.getY();
		pFeld.setFeld(x, y, pValue1);
		switch (this.getRichtung()) {
		case NORDEN:
			pFeld.setFeld(x - 1, y, pValue1);
			pFeld.setFeld(x - 2, y, pValue2);
			break;
		case OSTEN:
			pFeld.setFeld(x, y + 1, pValue1);
			pFeld.setFeld(x, y + 2, pValue2);
			break;
		case SUEDEN:
			pFeld.setFeld(x + 1, y, pValue1);
			pFeld.setFeld(x + 2, y, pValue2);
			break;
		case WESTEN:
			pFeld.setFeld(x, y - 1, pValue1);
			pFeld.setFeld(x, y - 2, pValue2);
			break;
		default:
			throw new IllegalStateException("falsche Richtung!");
		}
	}

	@Override
	public String toString() {
		String toString = "(" + this.x + "," + this.y + ") -> ";
		switch (this.richtung) {
		case NORDEN:
			toString += "Norden";
			break;
		case OSTEN:
			toString += "Osten";
			break;
		case SUEDEN:
			toString += "Sueden";
			break;
		case WESTEN:
			toString += "Westen";
			break;
		default:
			throw new IllegalStateException("falsche Richtung!");
		}
		return toString;
	}
}
