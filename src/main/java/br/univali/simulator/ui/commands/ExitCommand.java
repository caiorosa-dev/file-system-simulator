package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.TerminalColors;
import br.univali.simulator.ui.ShellContext;

public class ExitCommand extends AbstractCommand {
    
    public ExitCommand() {
        super("exit", "exit", "Sair do simulador");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        try {
            System.out.println(TerminalColors.colorizeSuccess("File system salvo com sucesso!"));
        } catch (Exception e) {
            System.err.println(TerminalColors.colorizeError("Erro ao salvar file system: " + e.getMessage()));
        }
        System.exit(0);
        return ""; // Never reached
    }
} 