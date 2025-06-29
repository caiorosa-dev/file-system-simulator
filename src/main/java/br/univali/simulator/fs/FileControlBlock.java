package br.univali.simulator.fs;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Getter()
public abstract class FileControlBlock {

    private static final AtomicInteger ID_SEQ = new AtomicInteger(1);

    protected final int inode;
    protected String  name;
    protected final User owner;
    protected int mode;
    protected LocalDateTime createdAt;
    protected LocalDateTime editedAt;
    protected LocalDateTime readAt;

    protected FileControlBlock(String name, User owner, int mode) {
        this.inode     = ID_SEQ.getAndIncrement();
        this.name      = name;
        this.owner     = owner;
        this.mode      = mode;
        this.createdAt = LocalDateTime.now();
        this.editedAt  = this.createdAt;
        this.readAt    = this.createdAt;
    }

    public String  getModeString(){ return Permission.toString(mode); }

    /* edição simples de nome e modo */
    public void rename(String newName) {
        this.name    = newName;
        this.editedAt = LocalDateTime.now();
    }
    public void chmod(int newMode) { this.mode = newMode; }

    /* permissões genéricas */
    protected void checkRead (User u){ if(!Permission.canRead (this,u)) throw new SecurityException("read denied"); }
    protected void checkWrite(User u){ if(!Permission.canWrite(this,u)) throw new SecurityException("write denied"); }
    protected void checkExec (User u){ if(!Permission.canExec (this,u)) throw new SecurityException("exec denied"); }
}
