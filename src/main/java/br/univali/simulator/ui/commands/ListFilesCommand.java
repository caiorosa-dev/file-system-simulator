package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.ShellContext;

public class ListFilesCommand extends AbstractCommand {
    
    public ListFilesCommand() {
        super("ls", "ls [-l]", "Listar arquivos e diretórios");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        boolean verbose = args.length > 0 && args[0].equals("-l");
        return context.getFileSystem().ls(verbose);
    }
} 