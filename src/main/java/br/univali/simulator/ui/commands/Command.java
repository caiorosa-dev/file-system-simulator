package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.ShellContext;

public interface Command {
    /**
     * Execute the command with given arguments
     * @param context The shell context
     * @param args Command arguments (excluding the command name itself)
     * @return Command output or empty string if no output
     */
    String execute(ShellContext context, String[] args);
    
    /**
     * Get command usage information
     * @return Usage string
     */
    String getUsage();
    
    /**
     * Get command description
     * @return Description string
     */
    String getDescription();
} 