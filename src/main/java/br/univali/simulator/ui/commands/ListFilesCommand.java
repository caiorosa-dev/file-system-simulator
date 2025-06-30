package br.univali.simulator.ui.commands;

import br.univali.simulator.fs.FileControlBlock;
import br.univali.simulator.fs.Folder;
import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.TerminalColors;
import br.univali.simulator.ui.ShellContext;

public class ListFilesCommand extends AbstractCommand {
    
    public ListFilesCommand() {
        super("ls", "ls [-l]", "Listar arquivos e diretórios");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        boolean verbose = args.length > 0 && args[0].equals("-l");
        
        try {
            if (verbose) {
                return getVerboseListing(context);
            } else {
                return getSimpleListing(context);
            }
        } catch (Exception e) {
            return TerminalColors.colorizeError("ls: " + e.getMessage());
        }
    }
    
    private String getSimpleListing(ShellContext context) {
        Folder currentDir = getCurrentDirectory(context);
        StringBuilder sb = new StringBuilder();
        
        for (FileControlBlock fcb : currentDir.list().values()) {
            boolean isDirectory = fcb instanceof Folder;
            sb.append(TerminalColors.colorizeFileName(fcb.getName(), isDirectory)).append(" ");
        }
        
        return sb.toString().trim();
    }
    
    private String getVerboseListing(ShellContext context) {
        Folder currentDir = getCurrentDirectory(context);
        StringBuilder sb = new StringBuilder();
        
        for (FileControlBlock fcb : currentDir.list().values()) {
            boolean isDirectory = fcb instanceof Folder;
            String type = isDirectory ? TerminalColors.colorize("<DIR>", TerminalColors.BLUE_BOLD) : "FILE";
            
            sb.append(String.format("%-10s %4d %s %s%n",
                type,
                fcb.getInode(),
                TerminalColors.colorizePermissions(fcb.getModeString()),
                TerminalColors.colorizeFileName(fcb.getName(), isDirectory)));
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