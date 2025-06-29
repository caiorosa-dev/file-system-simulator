package br.univali.simulator.fs;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.univali.simulator.exceptions.ExistingFileException;

public class Folder extends FileControlBlock {
    public Map<String, FileControlBlock> files = new HashMap<>();

    public void addFile(FileControlBlock file) throws ExistingFileException{
        var existingFile = files.get(file.getName());
        if(existingFile != null){
            throw new ExistingFileException();
        }else{
            this.files.put(file.getName(), file);
        }

    }

    public void createFolder(String name) throws ExistingFileException{
        Folder folder = new Folder(name);
        this.addFile(folder);
    }

    public Folder(String name) {
        super(new Date(), new Date(), new Date(), name);
    }
}
