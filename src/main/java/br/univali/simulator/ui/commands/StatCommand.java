package br.univali.simulator.ui.commands;

import br.univali.simulator.fs.FileControlBlock;
import br.univali.simulator.fs.File;
import br.univali.simulator.fs.Folder;
import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.TerminalColors;
import br.univali.simulator.ui.ShellContext;

import java.text.SimpleDateFormat;

public class StatCommand extends AbstractCommand {
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public StatCommand() {
        super("stat", "stat <arquivo_ou_diretorio>", "Mostrar informações detalhadas do arquivo ou diretório");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        validateMinArgs(args, 1);
        
        String target = args[0];
        
        try {
            FileControlBlock fcb = findTarget(context, target);
            return formatFileInfo(fcb);
        } catch (Exception e) {
            return TerminalColors.colorizeError("stat: não foi possível acessar '" + target + "': " + e.getMessage());
        }
    }
    
    private FileControlBlock findTarget(ShellContext context, String target) {
        // Handle special cases
        if (target.equals(".")) {
            return getCurrentDirectory(context);
        }
        if (target.equals("..")) {
            return getParentDirectory(context);
        }
        
        // If no path separators, look in current directory
        if (!target.contains("/")) {
            FileControlBlock fcb = getCurrentDirectory(context).child(target);
            if (fcb == null) {
                throw new IllegalArgumentException("Arquivo ou diretório não encontrado");
            }
            return fcb;
        }
        
        // Handle absolute and relative paths
        String currentDir = context.getFileSystem().pwd();
        try {
            // Try as directory first
            context.getFileSystem().cd(target);
            FileControlBlock result = getCurrentDirectory(context);
            context.getFileSystem().cd(currentDir); // Restore original directory
            return result;
        } catch (Exception e) {
            // Restore directory and try as file
            try {
                context.getFileSystem().cd(currentDir);
            } catch (Exception ignored) {}
            
            // Parse as file path
            int lastSlash = target.lastIndexOf('/');
            String parentPath = target.substring(0, lastSlash);
            String fileName = target.substring(lastSlash + 1);
            
            try {
                context.getFileSystem().cd(parentPath);
                FileControlBlock fcb = getCurrentDirectory(context).child(fileName);
                context.getFileSystem().cd(currentDir); // Restore
                
                if (fcb == null) {
                    throw new IllegalArgumentException("Arquivo não encontrado");
                }
                return fcb;
            } catch (Exception e2) {
                try {
                    context.getFileSystem().cd(currentDir);
                } catch (Exception ignored) {}
                throw new IllegalArgumentException("Caminho não encontrado");
            }
        }
    }
    
    private Folder getCurrentDirectory(ShellContext context) {
        // Use reflection or access the current working directory through FileSystem
        String currentPath = context.getFileSystem().pwd();
        if (currentPath.equals("/")) {
            return context.getFileSystem().getRoot();
        }
        
        // Navigate to get the current directory object
        String originalPath = currentPath;
        try {
            context.getFileSystem().cd("/");
            context.getFileSystem().cd(originalPath);
            
            // We need to access the cwd field - let's use a workaround
            // Since we can't access private fields, we'll traverse the path
            String[] parts = originalPath.substring(1).split("/");
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
        } catch (Exception e) {
            return context.getFileSystem().getRoot();
        }
    }
    
    private Folder getParentDirectory(ShellContext context) {
        String pwd = context.getFileSystem().pwd();
        if (pwd.equals("/")) {
            return context.getFileSystem().getRoot();
        }
        
        int lastSlash = pwd.lastIndexOf('/');
        if (lastSlash == 0) {
            return context.getFileSystem().getRoot();
        }
        
        String parentPath = pwd.substring(0, lastSlash);
        String currentDir = context.getFileSystem().pwd();
        try {
            context.getFileSystem().cd(parentPath);
            Folder parent = getCurrentDirectory(context);
            context.getFileSystem().cd(currentDir);
            return parent;
        } catch (Exception e) {
            return context.getFileSystem().getRoot();
        }
    }
    
    private String formatFileInfo(FileControlBlock fcb) {
        StringBuilder sb = new StringBuilder();
        
        // File name and type
        boolean isDirectory = fcb instanceof Folder;
        String type = isDirectory ? "diretório" : "arquivo regular";
        
        sb.append("  ").append(TerminalColors.colorize("Arquivo:", TerminalColors.WHITE_BOLD))
            .append(" ").append(TerminalColors.colorizeFileName(fcb.getName(), isDirectory)).append("\n");
        
        sb.append("  ").append(TerminalColors.colorize("Tipo:", TerminalColors.WHITE_BOLD))
            .append(" ").append(type).append("\n");
        
        // Size (for files)
        if (fcb instanceof File) {
            // We can't directly access size from File class, so we'll show inode info instead
            sb.append("  ").append(TerminalColors.colorize("Inode:", TerminalColors.WHITE_BOLD))
                .append(" ").append(fcb.getInode()).append("\n");
        } else {
            Folder folder = (Folder) fcb;
            int itemCount = folder.list().size();
            sb.append("  ").append(TerminalColors.colorize("Itens:", TerminalColors.WHITE_BOLD))
                .append(" ").append(itemCount).append("\n");
        }
        
        // Permissions
        sb.append("  ").append(TerminalColors.colorize("Permissões:", TerminalColors.WHITE_BOLD))
            .append(" ").append(TerminalColors.colorizePermissions(fcb.getModeString()))
            .append(" (").append(String.format("%04o", fcb.getMode())).append(")\n");
        
        // Owner
        sb.append("  ").append(TerminalColors.colorize("Proprietário:", TerminalColors.WHITE_BOLD))
            .append(" ").append(TerminalColors.colorizeUser(fcb.getOwner().getName())).append("\n");
        
        // Dates
        sb.append("  ").append(TerminalColors.colorize("Criado em:", TerminalColors.WHITE_BOLD))
            .append(" ").append(DATE_FORMAT.format(fcb.getCreatedAt())).append("\n");
        
        sb.append("  ").append(TerminalColors.colorize("Modificado em:", TerminalColors.WHITE_BOLD))
            .append(" ").append(DATE_FORMAT.format(fcb.getEditedAt())).append("\n");
        
        sb.append("  ").append(TerminalColors.colorize("Acessado em:", TerminalColors.WHITE_BOLD))
            .append(" ").append(DATE_FORMAT.format(fcb.getReadAt())).append("\n");
        
        return sb.toString();
    }
} 