package br.univali.simulator.persistence;

import br.univali.simulator.fs.FileSystem;
import br.univali.simulator.fs.Folder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemPersistence {
    private static final String FILESYSTEM_FILE = "filesystem.json";
    private final Gson gson;
    
    public FileSystemPersistence() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }
    
    public void saveFileSystem(FileSystem fileSystem) throws IOException {
        FileSystemSnapshot snapshot = createSnapshot(fileSystem);
        
        try (FileWriter writer = new FileWriter(FILESYSTEM_FILE)) {
            gson.toJson(snapshot, writer);
        }
    }
    
    public FileSystem loadFileSystem() throws IOException {
        Path filePath = Paths.get(FILESYSTEM_FILE);
        
        if (!Files.exists(filePath)) {
            // Create new filesystem with proper root
            return createDefaultFileSystem();
        }
        
        try (FileReader reader = new FileReader(FILESYSTEM_FILE)) {
            FileSystemSnapshot snapshot = gson.fromJson(reader, FileSystemSnapshot.class);
            return restoreFromSnapshot(snapshot);
        }
    }
    
    private FileSystem createDefaultFileSystem() {
        FileSystem fs = new FileSystem();
        // Create the "simple-fs" root directory structure
        return fs;
    }
    
    private FileSystemSnapshot createSnapshot(FileSystem fileSystem) {
        FileSystemSnapshot snapshot = new FileSystemSnapshot();
        
        // Save current directory path
        snapshot.currentDirectory = fileSystem.pwd();
        
        // Save current user
        snapshot.currentUser = fileSystem.user().getName();
        
        // Save folder structure (simplified - we'll implement this later)
        // For now, just save basic info
        snapshot.rootFolderData = serializeFolder(fileSystem.getRoot());
        
        return snapshot;
    }
    
    private String serializeFolder(Folder folder) {
        // Simplified serialization - just return JSON representation
        // This is a placeholder for a more complex serialization
        return gson.toJson(folder);
    }
    
    private FileSystem restoreFromSnapshot(FileSystemSnapshot snapshot) {
        FileSystem fs = new FileSystem();
        
        // Restore current user if exists
        if (snapshot.currentUser != null && !snapshot.currentUser.equals("root")) {
            try {
                fs.su(snapshot.currentUser);
            } catch (Exception e) {
                // If user doesn't exist, stay as root
            }
        }
        
        // Restore current directory
        if (snapshot.currentDirectory != null && !snapshot.currentDirectory.equals("/")) {
            try {
                fs.cd(snapshot.currentDirectory);
            } catch (Exception e) {
                // If directory doesn't exist, stay at root
            }
        }
        
        return fs;
    }
    
    // Inner class for JSON serialization
    private static class FileSystemSnapshot {
        String currentDirectory;
        String currentUser;
        String rootFolderData;
    }
} 