package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.Command;
import br.univali.simulator.ui.ShellContext;

public class HelpCommand extends AbstractCommand {
    
    private final CommandRegistry registry;
    
    public HelpCommand(CommandRegistry registry) {
        super("help", "help [comando]", "Mostrar ajuda dos comandos");
        this.registry = registry;
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        if (args.length == 0) {
            // Show all commands
            StringBuilder sb = new StringBuilder();
            sb.append("Comandos disponíveis:\n");
            for (String commandName : registry.getAvailableCommands()) {
                Command cmd = registry.getCommand(commandName);
                sb.append(String.format("  %-15s - %s\n", 
                    cmd.getUsage(), cmd.getDescription()));
            }
            return sb.toString();
        } else {
            // Show specific command help
            String commandName = args[0];
            Command cmd = registry.getCommand(commandName);
            if (cmd != null) {
                return String.format("Uso: %s\n%s", cmd.getUsage(), cmd.getDescription());
            } else {
                return "Comando não encontrado: " + commandName;
            }
        }
    }
} 