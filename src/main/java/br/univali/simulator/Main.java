package br.univali.simulator;

import br.univali.simulator.fs.Folder;
import br.univali.simulator.ui.UserSession;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
	public static void main(String[] args) {
		Folder rootFolder = new Folder("c:");
		UserSession session = new UserSession(rootFolder);

		while (true) {
			try {
				session.getNextInstruction();
			} catch (Exception error) {
				break;
			}
		}
	}
}