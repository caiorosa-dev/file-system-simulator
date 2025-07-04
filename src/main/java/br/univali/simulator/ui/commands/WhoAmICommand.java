package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.TerminalColors;
import br.univali.simulator.ui.ShellContext;

public class WhoAmICommand extends AbstractCommand {
    
    public WhoAmICommand() {
        super("whoami", "whoami", "Mostrar usuário atual");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        return TerminalColors.colorizeUser(context.getCurrentUser().getName());
    }
} 