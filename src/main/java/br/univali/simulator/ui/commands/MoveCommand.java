package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.ShellContext;

public class MoveCommand extends AbstractCommand {
    
    public MoveCommand() {
        super("mv", "mv <origem> <destino>", "Mover arquivo");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        validateMinArgs(args, 2);
        
        String source = args[0];
        String destination = args[1];
        context.getFileSystem().mv(source, destination);
        return "";
    }
} 