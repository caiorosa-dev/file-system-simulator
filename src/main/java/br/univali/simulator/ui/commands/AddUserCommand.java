package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.TerminalColors;
import br.univali.simulator.ui.ShellContext;

public class AddUserCommand extends AbstractCommand {
    
    public AddUserCommand() {
        super("adduser", "adduser <nome_usuario>", "Criar novo usuário");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        validateMinArgs(args, 1);
        
        String username = args[0];
        context.createUser(username);
        return TerminalColors.colorizeSuccess("Usuário '" + username + "' criado com sucesso");
    }
} 