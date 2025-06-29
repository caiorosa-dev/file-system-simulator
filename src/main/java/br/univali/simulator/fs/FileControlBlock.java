package br.univali.simulator.fs;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class FileControlBlock {
    protected Date created_at;
    protected Date edited_at;
    protected Date readed_at;
    protected String name;

    public void rename(String newName){
        this.name = newName;
        this.edited_at = new Date();
    }
    
}
