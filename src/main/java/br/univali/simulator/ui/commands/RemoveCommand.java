package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.ShellContext;

public class RemoveCommand extends AbstractCommand {
    
    public RemoveCommand() {
        super("rm", "rm <arquivo>", "Remover arquivo");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        validateMinArgs(args, 1);
        
        String fileName = args[0];
        context.getFileSystem().rm(fileName);
        return "";
    }
} 