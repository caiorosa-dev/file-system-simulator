package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.ShellContext;

public class TouchCommand extends AbstractCommand {
    
    public TouchCommand() {
        super("touch", "touch <nome_arquivo>", "Criar arquivo vazio");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        validateMinArgs(args, 1);
        
        String fileName = args[0];
        context.getFileSystem().touch(fileName);
        return "";
    }
} 