package br.univali.simulator.fs;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Folder extends FileControlBlock {
    private final Map<String, FileControlBlock> children = new LinkedHashMap<>();
    private Folder parent;

    Folder(String name, User owner, int mode){
        super(name, owner, mode);
        this.parent = null; // Root folder has no parent
    }
    
    /* ── Parent management ─────────────────────────────── */
    public void setParent(Folder parent) {
        this.parent = parent;
    }
    
    public Folder getParent() {
        return parent;
    }
    
    public boolean isRoot() {
        return parent == null;
    }
    
    /* ── CRUD de filhos ─────────────────────────────── */
    public void addChild(FileControlBlock f){
        if(children.containsKey(f.getName()))
            throw new IllegalArgumentException("Já existe: "+f.getName());
        children.put(f.getName(), f);
        
        // Set parent reference for folders
        if(f instanceof Folder) {
            ((Folder) f).setParent(this);
        }
    }
    public FileControlBlock child(String n){ return children.get(n); }
    public void removeChild(String n){ 
        FileControlBlock removed = children.remove(n);
        // Clear parent reference for folders
        if(removed instanceof Folder) {
            ((Folder) removed).setParent(null);
        }
    }
    public Map<String, FileControlBlock> list(){ return children; }
}
