package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.ShellContext;

public abstract class AbstractCommand implements Command {
    
    protected final String name;
    protected final String usage;
    protected final String description;
    
    protected AbstractCommand(String name, String usage, String description) {
        this.name = name;
        this.usage = usage;
        this.description = description;
    }
    
    @Override
    public String getUsage() {
        return usage;
    }
    
    @Override
    public String getDescription() {
        return description;
    }
    
    protected String joinArgs(String[] args, int startIndex) {
        if (args.length <= startIndex) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = startIndex; i < args.length; i++) {
            if (i > startIndex) sb.append(" ");
            sb.append(args[i]);
        }
        return sb.toString();
    }
    
    protected void validateMinArgs(String[] args, int minArgs) {
        if (args.length < minArgs) {
            throw new IllegalArgumentException("Uso: " + getUsage());
        }
    }
} 