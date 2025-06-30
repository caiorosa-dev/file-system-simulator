package br.univali.simulator.ui.commands;

import br.univali.simulator.fs.FileControlBlock;
import br.univali.simulator.fs.File;
import br.univali.simulator.fs.Folder;
import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.TerminalColors;
import br.univali.simulator.ui.ShellContext;

public class FileCommand extends AbstractCommand {
    
    public FileCommand() {
        super("file", "file <arquivo_ou_diretorio>", "Mostrar tipo do arquivo ou diretório");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        validateMinArgs(args, 1);
        
        String target = args[0];
        
        try {
            FileControlBlock fcb = findTarget(context, target);
            return formatFileType(fcb, target);
        } catch (Exception e) {
            return TerminalColors.colorizeError("file: não foi possível acessar '" + target + "': " + e.getMessage());
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
    
    private String formatFileType(FileControlBlock fcb, String originalPath) {
        StringBuilder sb = new StringBuilder();
        
        // Format: filename: file type description
        sb.append(TerminalColors.colorizeFileName(originalPath, fcb instanceof Folder))
          .append(": ");
        
        if (fcb instanceof Folder) {
            Folder folder = (Folder) fcb;
            int itemCount = folder.list().size();
            sb.append(TerminalColors.colorize("diretório", TerminalColors.BLUE_BOLD));
            
            if (itemCount == 0) {
                sb.append(" (vazio)");
            } else {
                sb.append(" (").append(itemCount).append(" ")
                  .append(itemCount == 1 ? "item" : "itens").append(")");
            }
        } else {
            // It's a file
            sb.append(TerminalColors.colorize("arquivo regular", TerminalColors.WHITE));
            
            // Try to determine file type by extension
            String name = fcb.getName();
            String extension = "";
            int lastDot = name.lastIndexOf('.');
            if (lastDot > 0 && lastDot < name.length() - 1) {
                extension = name.substring(lastDot + 1).toLowerCase();
            }
            
            String typeDescription = getFileTypeDescription(extension);
            if (!typeDescription.isEmpty()) {
                sb.append(", ").append(TerminalColors.colorize(typeDescription, TerminalColors.CYAN));
            }
        }
        
        // Add permissions info
        sb.append(" (")
          .append(TerminalColors.colorizePermissions(fcb.getModeString()))
          .append(")");
        
        return sb.toString();
    }
    
    private String getFileTypeDescription(String extension) {
        switch (extension) {
            case "txt":
                return "arquivo de texto";
            case "log":
                return "arquivo de log";
            case "conf":
            case "config":
                return "arquivo de configuração";
            case "sh":
                return "script shell";
            case "java":
                return "código fonte Java";
            case "py":
                return "script Python";
            case "js":
                return "código JavaScript";
            case "html":
            case "htm":
                return "documento HTML";
            case "css":
                return "folha de estilo CSS";
            case "json":
                return "dados JSON";
            case "xml":
                return "documento XML";
            case "md":
                return "documento Markdown";
            case "pdf":
                return "documento PDF";
            case "jpg":
            case "jpeg":
            case "png":
            case "gif":
                return "imagem";
            case "mp3":
            case "wav":
                return "arquivo de áudio";
            case "mp4":
            case "avi":
                return "arquivo de vídeo";
            case "zip":
            case "tar":
            case "gz":
                return "arquivo compactado";
            default:
                return "";
        }
    }
} 