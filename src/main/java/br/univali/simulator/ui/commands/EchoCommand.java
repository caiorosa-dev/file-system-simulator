package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.ShellContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EchoCommand extends AbstractCommand {
    
    private static final Pattern REDIRECT_PATTERN = Pattern.compile("\"(.*)\"\\s*>\\s*(\\S+)");
    
    public EchoCommand() {
        super("echo", "echo \"<texto>\" > <arquivo>", "Escrever texto em arquivo");
    }
    
    @Override
    public String execute(ShellContext context, String[] args) {
        // Reconstruct the original line to handle redirection properly
        String fullLine = joinArgs(args, 0);
        
        Matcher matcher = REDIRECT_PATTERN.matcher(fullLine);
        if (matcher.matches()) {
            String content = matcher.group(1);
            String fileName = matcher.group(2);
            context.getFileSystem().echo(content, fileName);
            return "";
        }
        
        // If no redirection, just echo to console
        return fullLine.replaceAll("\"", "");
    }
} 