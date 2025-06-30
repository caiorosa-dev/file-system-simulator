package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.ShellContext;

public class ChmodCommand extends AbstractCommand {
    
    public ChmodCommand() {
        super("chmod", "chmod <modo> <arquivo>", "Alterar permissões do arquivo");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        validateMinArgs(args, 2);
        
        String mode = args[0];
        String fileName = args[1];
        context.getFileSystem().chmod(mode, fileName);
        return "";
    }
} 