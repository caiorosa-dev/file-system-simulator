package br.univali.simulator;

import br.univali.simulator.fs.FileSystem;
import br.univali.simulator.ui.UserSession;

public class Main {
	public static void main(String[] args) {
		FileSystem fs = new FileSystem();
		UserSession session = new UserSession(fs);

		session.start();
	}
}