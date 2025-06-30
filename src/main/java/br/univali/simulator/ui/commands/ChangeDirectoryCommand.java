package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.ShellContext;

public class ChangeDirectoryCommand extends AbstractCommand {
    
    public ChangeDirectoryCommand() {
        super("cd", "cd <diretorio>", "Mudar diretório atual");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        validateMinArgs(args, 1);
        
        String path = args[0];
        
        // Handle aliases
        if (path.equals("~")) {
            path = "/";
        }
        
        context.getFileSystem().cd(path);
        return "";
    }
} 