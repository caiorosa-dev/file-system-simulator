package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.ShellContext;

public class CatCommand extends AbstractCommand {
    
    public CatCommand() {
        super("cat", "cat <arquivo>", "Mostrar conteúdo do arquivo");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        validateMinArgs(args, 1);
        
        String fileName = args[0];
        return context.getFileSystem().cat(fileName);
    }
} 