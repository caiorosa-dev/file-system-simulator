package br.univali.simulator.ui.commands;

import br.univali.simulator.fs.Disk;
import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.TerminalColors;
import br.univali.simulator.ui.ShellContext;

public class DiskInfoCommand extends AbstractCommand {
    
    public DiskInfoCommand() {
        super("diskinfo", "diskinfo", "Mostrar informações detalhadas do disco");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        Disk disk = context.getFileSystem().getDisk();
        StringBuilder sb = new StringBuilder();
        
        // Header
        sb.append(TerminalColors.colorize("=== Informações do Disco ===", TerminalColors.CYAN_BOLD)).append("\n\n");
        
        // Basic disk specifications
        sb.append(TerminalColors.colorize("Especificações:", TerminalColors.WHITE_BOLD)).append("\n");
        sb.append("  Tamanho do bloco:    ").append(formatBytes(disk.getBlockSize())).append("\n");
        sb.append("  Total de blocos:     ").append(disk.getTotalBlocks()).append("\n");
        sb.append("  Capacidade total:    ").append(formatBytes(disk.getTotalCapacity())).append("\n\n");
        
        // Usage statistics
        sb.append(TerminalColors.colorize("Estatísticas de Uso:", TerminalColors.WHITE_BOLD)).append("\n");
        sb.append("  Blocos usados:       ").append(disk.getUsedBlocks()).append("\n");
        sb.append("  Blocos livres:       ").append(disk.getFreeBlocks()).append("\n");
        sb.append("  Capacidade usada:    ").append(formatBytes(disk.getUsedCapacity())).append("\n");
        sb.append("  Capacidade livre:    ").append(formatBytes(disk.getFreeCapacity())).append("\n");
        sb.append("  Uso do disco:        ").append(String.format("%.2f%%", disk.getUsagePercentage())).append("\n\n");
        
        // Visual usage bar
        sb.append(TerminalColors.colorize("Utilização:", TerminalColors.WHITE_BOLD)).append("\n");
        sb.append("  ").append(createUsageBar(disk.getUsagePercentage())).append("\n\n");
        
        // Bitmap preview (first 64 characters)
        sb.append(TerminalColors.colorize("Mapa de Bits (primeiros 64 blocos):", TerminalColors.WHITE_BOLD)).append("\n");
        String bitmap = disk.bitmap();
        String preview = bitmap.length() > 64 ? bitmap.substring(0, 64) : bitmap;
        sb.append("  ").append(colorizeBitmap(preview)).append("\n");
        if (bitmap.length() > 64) {
            sb.append("  ").append(TerminalColors.colorize("(... e mais " + (bitmap.length() - 64) + " blocos)", TerminalColors.WHITE)).append("\n");
        }
        sb.append("  Legenda: ").append(TerminalColors.colorize("#", TerminalColors.RED_BOLD)).append(" = usado, ")
          .append(TerminalColors.colorize(".", TerminalColors.GREEN)).append(" = livre");
        
        return sb.toString();
    }
    
    private String formatBytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.1f KiB", bytes / 1024.0);
        } else {
            return String.format("%.1f MiB", bytes / (1024.0 * 1024.0));
        }
    }
    
    private String createUsageBar(double percentage) {
        int barLength = 50;
        int usedLength = (int) (percentage * barLength / 100);
        int freeLength = barLength - usedLength;
        
        StringBuilder bar = new StringBuilder();
        bar.append("[");
        bar.append(TerminalColors.colorize("=".repeat(usedLength), TerminalColors.RED_BOLD));
        bar.append(TerminalColors.colorize("-".repeat(freeLength), TerminalColors.GREEN));
        bar.append("] ");
        bar.append(String.format("%.2f%%", percentage));
        
        return bar.toString();
    }
    
    private String colorizeBitmap(String bitmap) {
        StringBuilder colored = new StringBuilder();
        for (char c : bitmap.toCharArray()) {
            if (c == '#') {
                colored.append(TerminalColors.colorize(String.valueOf(c), TerminalColors.RED_BOLD));
            } else {
                colored.append(TerminalColors.colorize(String.valueOf(c), TerminalColors.GREEN));
            }
        }
        return colored.toString();
    }
} 