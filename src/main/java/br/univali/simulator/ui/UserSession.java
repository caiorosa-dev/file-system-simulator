package br.univali.simulator.ui;

import java.util.Scanner;

import br.univali.simulator.fs.FileSystem;

public final class UserSession {

    private final Scanner in = new Scanner(System.in);
    private final InstructionParser parser;
    private final FileSystem fs;

    public UserSession(FileSystem fs){
        this.fs = fs;
        this.parser = new InstructionParser(fs);
    }
    public void start(){
        while(true){
            System.out.printf("%s@sim:%s$ ",
                    fs.user().getName(), fs.pwd());
            String line = in.nextLine();
            String out  = parser.handle(line);
            if(!out.isBlank()) System.out.println(out);
        }
    }
}
