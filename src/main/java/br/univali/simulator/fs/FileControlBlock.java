package br.univali.simulator.fs;

import lombok.Getter;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Getter()
public abstract class FileControlBlock {
    private static final AtomicInteger ID_SEQ = new AtomicInteger(1);

    protected final int inode;
    protected String  name;
    protected final User owner;
    protected int mode;
    protected Date createdAt;
    protected Date editedAt;
    protected Date readAt;

    protected FileControlBlock(String name, User owner, int mode) {
        this.inode     = ID_SEQ.getAndIncrement();
        this.name      = name;
        this.owner     = owner;
        this.mode      = mode;
        this.createdAt = new Date();
        this.editedAt  = this.createdAt;
        this.readAt    = this.createdAt;
    }

    public String  getModeString(){ return Permission.toString(mode); }

    /* edição simples de nome e modo */
    public void rename(String newName) {
        this.name    = newName;
        this.editedAt = new Date();
    }
    public void chmod(int newMode) { this.mode = newMode; }

    /* permissões genéricas */
    protected void checkRead (User u){ if(!Permission.canRead (this,u)) throw new SecurityException("read denied"); }
    protected void checkWrite(User u){ if(!Permission.canWrite(this,u)) throw new SecurityException("write denied"); }
    protected void checkExec (User u){ if(!Permission.canExec (this,u)) throw new SecurityException("exec denied"); }
}
