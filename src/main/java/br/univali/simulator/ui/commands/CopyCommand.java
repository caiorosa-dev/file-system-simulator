package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.ShellContext;

public class CopyCommand extends AbstractCommand {
    
    public CopyCommand() {
        super("cp", "cp <origem> <destino>", "Copiar arquivo");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        validateMinArgs(args, 2);
        
        String source = args[0];
        String destination = args[1];
        context.getFileSystem().cp(source, destination);
        return "";
    }
} 