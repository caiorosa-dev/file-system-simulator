package br.univali.simulator;

import br.univali.simulator.fs.FileSystem;
import br.univali.simulator.ui.ShellContext;
import br.univali.simulator.ui.UserSession;

public class Main {
	public static void main(String[] args) {
		FileSystem fs = new FileSystem();
		ShellContext context = new ShellContext(fs);
		UserSession session = new UserSession(context);

		session.start();
	}
}