package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.ShellContext;

public class PrintWorkingDirectoryCommand extends AbstractCommand {
    
    public PrintWorkingDirectoryCommand() {
        super("pwd", "pwd", "Mostrar diretório atual");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        return context.getFileSystem().pwd();
    }
} 