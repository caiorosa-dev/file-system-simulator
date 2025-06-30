package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.ShellContext;

public class SwitchUserCommand extends AbstractCommand {
    
    public SwitchUserCommand() {
        super("su", "su <nome_usuario>", "Trocar para outro usuário");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        validateMinArgs(args, 1);
        
        String username = args[0];
        context.switchUser(username);
        return "";
    }
} 