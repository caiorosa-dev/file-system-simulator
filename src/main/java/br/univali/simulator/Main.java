package br.univali.simulator;

import br.univali.simulator.fs.FileSystem;
import br.univali.simulator.persistence.FileSystemPersistence;
import br.univali.simulator.ui.ShellContext;
import br.univali.simulator.ui.UserSession;

public class Main {
	public static void main(String[] args) {
		FileSystem fs = loadOrCreateFileSystem();
		ShellContext context = new ShellContext(fs);
		UserSession session = new UserSession(context);

		session.start();
	}
	
	private static FileSystem loadOrCreateFileSystem() {
		FileSystemPersistence persistence = new FileSystemPersistence();
		try {
			FileSystem fs = persistence.loadFileSystem();
			System.out.println("File system carregado do arquivo filesystem.json");
			return fs;
		} catch (Exception e) {
			System.out.println("Criando novo file system...");
			return new FileSystem();
		}
	}
}