package br.univali.simulator.ui.commands;

import br.univali.simulator.ui.AbstractCommand;
import br.univali.simulator.ui.Command;

import java.util.*;

public class CommandRegistry {
    
    private final Map<String, Command> commands;
    private final Map<String, String> aliases;
    
    public CommandRegistry() {
        this.commands = new HashMap<>();
        this.aliases = new HashMap<>();
        initializeCommands();
    }
    
    private void initializeCommands() {
        // System commands
        registerCommand(new ExitCommand());
        
        // User management commands
        registerCommand(new AddUserCommand());
        registerCommand(new SwitchUserCommand());
        registerCommand(new WhoAmICommand());
        registerCommand(new UsersCommand());
        
        // File system navigation commands
        registerCommand(new ChangeDirectoryCommand());
        registerCommand(new PrintWorkingDirectoryCommand());
        registerCommand(new ListFilesCommand());
        
        // File and directory management commands
        registerCommand(new MakeDirectoryCommand());
        registerCommand(new TouchCommand());
        registerCommand(new CatCommand());
        registerCommand(new CopyCommand());
        registerCommand(new MoveCommand());
        registerCommand(new RemoveCommand());
        registerCommand(new ChmodCommand());
        registerCommand(new EchoCommand());
        
        // System information commands
        registerCommand(new BitmapCommand());
        
        // Help command (needs registry reference)
        registerCommand(new HelpCommand(this));
        
        // Setup aliases
        setupAliases();
    }
    
    private void setupAliases() {
        // Common aliases
        aliases.put("?", "help");
        aliases.put("h", "help");
        aliases.put("dir", "ls");
        aliases.put("type", "cat");
        aliases.put("del", "rm");
        aliases.put("md", "mkdir");
        aliases.put("rd", "rm");
    }
    
    public void registerCommand(Command command) {
        if (command instanceof AbstractCommand) {
            AbstractCommand abstractCommand = (AbstractCommand) command;
            commands.put(abstractCommand.name, command);
        }
    }
    
    public Command getCommand(String name) {
        // Check aliases first
        String actualName = aliases.getOrDefault(name, name);
        return commands.get(actualName);
    }
    
    public boolean hasCommand(String name) {
        String actualName = aliases.getOrDefault(name, name);
        return commands.containsKey(actualName);
    }
    
    public Set<String> getAvailableCommands() {
        return new TreeSet<>(commands.keySet());
    }
    
    public Map<String, String> getAliases() {
        return new HashMap<>(aliases);
    }
} 