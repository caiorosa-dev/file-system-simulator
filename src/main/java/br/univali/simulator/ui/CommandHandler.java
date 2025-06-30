package br.univali.simulator.ui;

import br.univali.simulator.ui.commands.CommandRegistry;

public class CommandHandler {
    
    private final ShellContext context;
    private final CommandRegistry registry;
    
    public CommandHandler(ShellContext context) {
        this.context = context;
        this.registry = new CommandRegistry();
    }
    
    public String handleCommand(String line) {
        line = line.strip();
        if (line.isBlank()) {
            return "";
        }
        
        try {
            return executeCommand(line);
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }
    
    private String executeCommand(String line) {
        String[] parts = line.split("\\s+");
        String commandName = parts[0];
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);
        
        // Special handling for echo command with redirection
        if (commandName.equals("echo") && line.contains(">")) {
            // Pass the full line after "echo " to handle redirection properly
            String echoArgs = line.substring(4).strip();
            args = new String[]{echoArgs};
        }
        
        Command command = registry.getCommand(commandName);
        if (command != null) {
            return command.execute(context, args);
        }
        
        return "Comando não reconhecido: " + commandName + ". Digite 'help' para ver comandos disponíveis.";
    }
} 