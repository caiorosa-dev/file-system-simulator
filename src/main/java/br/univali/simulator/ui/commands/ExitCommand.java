package br.univali.simulator.ui.commands;

import br.univali.simulator.persistence.FileSystemPersistence;
import br.univali.simulator.ui.ShellContext;

public class ExitCommand extends AbstractCommand {
    
    private final FileSystemPersistence persistence;
    
    public ExitCommand() {
        super("exit", "exit", "Sair do simulador");
        this.persistence = new FileSystemPersistence();
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        try {
            persistence.saveFileSystem(context.getFileSystem());
            System.out.println("File system salvo com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao salvar file system: " + e.getMessage());
        }
        System.exit(0);
        return ""; // Never reached
    }
} 