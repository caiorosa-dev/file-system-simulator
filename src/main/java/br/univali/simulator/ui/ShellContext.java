package br.univali.simulator.ui;

import br.univali.simulator.fs.FileSystem;
import br.univali.simulator.fs.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ShellContext {
    private final Map<String, User> users;
    private final FileSystem fileSystem;
    private User currentUser;
    
    public ShellContext(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        this.users = new HashMap<>();
        
        // Initialize with root user
        this.users.put("root", User.ROOT);
        this.currentUser = User.ROOT;
    }
    
    public FileSystem getFileSystem() {
        return fileSystem;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public void switchUser(String username) {
        if (!users.containsKey(username)) {
            throw new IllegalArgumentException("Usuário não existe: " + username);
        }
        this.currentUser = users.get(username);
        fileSystem.setCurrentUser(currentUser);
    }
    
    public void createUser(String username) {
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("Usuário já existe: " + username);
        }
        User newUser = new User(username);
        users.put(username, newUser);
    }
    
    public boolean userExists(String username) {
        return users.containsKey(username);
    }
    
    public Set<String> getAllUsernames() {
        return users.keySet();
    }
    
    public String getPrompt() {
        return String.format("%s@%ssimple-fs%s:%s%s$ ", 
            TerminalColors.colorizeUser(currentUser.getName()),
            TerminalColors.GREEN_BOLD,
            TerminalColors.RESET,
            TerminalColors.colorizePath(fileSystem.pwd()),
            TerminalColors.RESET);
    }
} 