package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.ShellContext;

public class BitmapCommand extends AbstractCommand {
    
    public BitmapCommand() {
        super("bitmap", "bitmap", "Mostrar bitmap do disco");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        return context.getFileSystem().bitmap();
    }
} 