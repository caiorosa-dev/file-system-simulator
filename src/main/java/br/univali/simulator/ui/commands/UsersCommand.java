package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.ShellContext;

public class UsersCommand extends AbstractCommand {
    
    public UsersCommand() {
        super("users", "users", "Listar todos os usuários");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        return String.join(" ", context.getAllUsernames());
    }
} 