package br.univali.simulator.fs;

import java.util.Date;

public class File extends FileControlBlock {
    private String data;

    public void setDate(String newData){
        this.edited_at = new Date();
        this.data = newData;
    }

    public String getData(){
        this.readed_at = new Date();
        return this.data;
    }

    public File(String name){
        super(new Date(), new Date(), new Date(), name);
    }
}
