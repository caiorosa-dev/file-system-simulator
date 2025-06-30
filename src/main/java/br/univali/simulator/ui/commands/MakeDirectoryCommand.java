package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.ShellContext;

public class MakeDirectoryCommand extends AbstractCommand {
    
    public MakeDirectoryCommand() {
        super("mkdir", "mkdir <nome_diretorio>", "Criar diretório");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        validateMinArgs(args, 1);
        
        String dirName = args[0];
        context.getFileSystem().mkdir(dirName);
        return "";
    }
} 