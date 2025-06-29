package br.univali.simulator.UserSessions;

import java.util.Scanner;

import br.univali.simulator.FileSystem.Folder;

public class UserSession {

    private Scanner sc = new Scanner(System.in);
    private Folder workingFolder;
    private InstructionParser parser;

    public UserSession(Folder rootFolder) {
        this.workingFolder = rootFolder;
    }

    public void getNextInstruction() throws Exception {
        String line = sc.nextLine();
        System.out.println(line);
        if (line.equals("exit")) {
            throw new Exception("exiting");
        }

    }

}
