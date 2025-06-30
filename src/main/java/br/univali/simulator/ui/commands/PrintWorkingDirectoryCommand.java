package br.univali.simulator.ui.commands;

import br.univali.simulator.fs.FileControlBlock;
import br.univali.simulator.fs.Folder;
import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.TerminalColors;
import br.univali.simulator.ui.ShellContext;

import java.text.SimpleDateFormat;

public class PrintWorkingDirectoryCommand extends AbstractCommand {
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public PrintWorkingDirectoryCommand() {
        super("pwd", "pwd [-v]", "Mostrar diretório atual (use -v para informações detalhadas)");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        boolean verbose = args.length > 0 && args[0].equals("-v");
        String currentPath = context.getFileSystem().pwd();
        
        if (!verbose) {
            return TerminalColors.colorizePath(currentPath);
        }
        
        // Verbose mode - show detailed information about current directory
        StringBuilder sb = new StringBuilder();
        sb.append(TerminalColors.colorize("Diretório atual:", TerminalColors.WHITE_BOLD))
          .append(" ").append(TerminalColors.colorizePath(currentPath)).append("\n");
        
        try {
            Folder currentDir = getCurrentDirectory(context);
            
            // Directory information
            sb.append(TerminalColors.colorize("Proprietário:", TerminalColors.WHITE_BOLD))
              .append(" ").append(TerminalColors.colorizeUser(currentDir.getOwner().getName())).append("\n");
            
            sb.append(TerminalColors.colorize("Permissões:", TerminalColors.WHITE_BOLD))
              .append(" ").append(TerminalColors.colorizePermissions(currentDir.getModeString()))
              .append(" (").append(String.format("%04o", currentDir.getMode())).append(")\n");
            
            sb.append(TerminalColors.colorize("Criado em:", TerminalColors.WHITE_BOLD))
              .append(" ").append(DATE_FORMAT.format(currentDir.getCreatedAt())).append("\n");
            
            sb.append(TerminalColors.colorize("Modificado em:", TerminalColors.WHITE_BOLD))
              .append(" ").append(DATE_FORMAT.format(currentDir.getEditedAt())).append("\n");
            
            // Count items
            int totalItems = currentDir.list().size();
            long filesCount = currentDir.list().values().stream()
                .filter(fcb -> !(fcb instanceof Folder))
                .count();
            long dirsCount = totalItems - filesCount;
            
            sb.append(TerminalColors.colorize("Conteúdo:", TerminalColors.WHITE_BOLD))
              .append(" ").append(totalItems).append(" itens (")
              .append(filesCount).append(" arquivos, ")
              .append(dirsCount).append(" diretórios)");
            
        } catch (Exception e) {
            sb.append(TerminalColors.colorizeError("Erro ao obter informações detalhadas: " + e.getMessage()));
        }
        
        return sb.toString();
    }
    
    private Folder getCurrentDirectory(ShellContext context) {
        String currentPath = context.getFileSystem().pwd();
        if (currentPath.equals("/")) {
            return context.getFileSystem().getRoot();
        }
        
        String[] parts = currentPath.substring(1).split("/");
        Folder current = context.getFileSystem().getRoot();
        
        for (String part : parts) {
            if (!part.isEmpty()) {
                FileControlBlock child = current.child(part);
                if (child instanceof Folder) {
                    current = (Folder) child;
                }
            }
        }
        return current;
    }
} 