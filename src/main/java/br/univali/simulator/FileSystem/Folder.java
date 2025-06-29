package br.univali.simulator.FileSystem;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Folder extends FileControlBlock {
    public List<FileControlBlock> files = new LinkedList<>(); 

    public Folder(String name){
        super(new Date(), new Date(), new Date(), name);
    }
}
