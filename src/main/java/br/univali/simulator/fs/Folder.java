package br.univali.simulator.fs;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Folder extends FileControlBlock {

    private final Map<String, FileControlBlock> children = new LinkedHashMap<>();

    Folder(String name, User owner, int mode){
        super(name, owner, mode);
    }
    /* ── CRUD de filhos ─────────────────────────────── */
    public void addChild(FileControlBlock f){
        if(children.containsKey(f.getName()))
            throw new IllegalArgumentException("Já existe: "+f.getName());
        children.put(f.getName(), f);
    }
    public FileControlBlock child(String n){ return children.get(n); }
    public void removeChild(String n){ children.remove(n); }
    public Map<String, FileControlBlock> list(){ return children; }
}
