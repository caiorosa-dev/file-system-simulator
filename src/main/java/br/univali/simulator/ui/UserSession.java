package br.univali.simulator.ui;

import java.util.Scanner;

public final class UserSession {

    private final Scanner in = new Scanner(System.in);
    private final CommandHandler commandHandler;
    private final ShellContext context;

    public UserSession(ShellContext context){
        this.context = context;
        this.commandHandler = new CommandHandler(context);
    }
    
    public void start(){
        System.out.println("=== Simple File System Simulator ===");
        System.out.println("Digite 'help' para ver os comandos disponíveis.");
        System.out.println();
        
        while(true){
            System.out.print(context.getPrompt());
            String line = in.nextLine();
            String output = commandHandler.handleCommand(line);
            if(!output.isBlank()) {
                System.out.println(output);
            }
        }
    }
}
