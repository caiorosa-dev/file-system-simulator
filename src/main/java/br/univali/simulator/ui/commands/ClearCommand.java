package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.TerminalColors;
import br.univali.simulator.ui.ShellContext;

public class ClearCommand extends AbstractCommand {
    
    public ClearCommand() {
        super("clear", "clear", "Limpar a tela do terminal");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        // Print ANSI escape sequence to clear screen and move cursor to top-left
        System.out.print(TerminalColors.CLEAR_SCREEN);
        System.out.flush();
        return "";
    }
} 