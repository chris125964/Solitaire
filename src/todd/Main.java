package todd;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(final String[] args) {
		System.out.println("Hier entsteht Solitaire!");
		Spielfeld feld = new Spielfeld();
		feld.print();
		List<Zug> gewinnZuege = new ArrayList<Zug>();
		boolean gewonnen = feld.play(1, gewinnZuege);
		System.out.println("fertig. gewonnen: " + gewonnen);
		if (gewonnen) {
			for (Zug zug : gewinnZuege) {
				System.out.println(zug.toString());
			}
		}
	}

}
