package br.univali.simulator.fs;

import java.util.Arrays;

public final class File extends FileControlBlock {

    private int   size;           // bytes válidos
    private int[] blocks;         // blocos no "disco"

    File(String name, User owner, int mode) {
        super(name, owner, mode);
        this.size   = 0;
        this.blocks = new int[0];
    }
    /* leitura */
    public String read(User u, Disk disk){
        checkRead(u);
        this.readAt = java.time.LocalDateTime.now();
        return disk.read(blocks, size);
    }
    /* sobrescreve todo o arquivo */
    public void write(User u, Disk disk, String data){
        checkWrite(u);
        disk.free(blocks);                      // libera blocos antigos
        this.blocks = disk.allocate(data.length());
        disk.write(blocks, data);
        this.size   = data.length();
        this.editedAt = java.time.LocalDateTime.now();
    }
    /* cópia r-r */
    public File duplicate(String newName, Folder targetParent, Disk disk){
        File copy = new File(newName, this.owner, this.mode);
        copy.size   = this.size;
        copy.blocks = Arrays.copyOf(this.blocks, this.blocks.length);
        targetParent.addChild(copy);
        return copy;
    }
}
