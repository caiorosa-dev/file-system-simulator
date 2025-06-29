package br.univali.simulator.fs;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class FileControlBlock {
    protected Date createdAt;
    protected Date editedAt;
    protected Date readAt;
    protected String name;

    public void rename(String newName) {
        this.name = newName;
        this.editedAt = new Date();
    }
}
