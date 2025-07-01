package br.univali.simulator.fs;


import java.util.Arrays;

public final class Disk {

	public static final int  BLOCK_SIZE  = 64;     // bytes
	public static final int  TOTAL_BLOCKS = 1024;  // 64 KiB
	private final char[]     data = new char[BLOCK_SIZE*TOTAL_BLOCKS];
	private final boolean[]  used = new boolean[TOTAL_BLOCKS];

	/* alocação contínua simplificada */
	public int[] allocate(int bytes){
		int need = (bytes + BLOCK_SIZE -1)/BLOCK_SIZE;
		int start=-1,count=0;

		for(int i=0;i<TOTAL_BLOCKS;i++){
			if(!used[i]){
				if(start==-1) start=i;
				if(++count==need){
					int[] idx = new int[need];
					for(int b=0;b<need;b++){
						used[start+b]=true;
						idx[b]=start+b;
					}
					return idx;
				}
			}else { start=-1; count=0; }
		}

		throw new RuntimeException("Disco cheio");
	}

	public void free(int[] blocks){
		if(blocks==null) return;
		for(int b:blocks) used[b]=false;
	}

	public void write(int[] blocks, String s){
		char[] c = s.toCharArray();
		for(int i=0;i<c.length;i++){
			int blk=blocks[i/BLOCK_SIZE];
			data[blk*BLOCK_SIZE + (i%BLOCK_SIZE)] = c[i];
		}
	}

	public String read(int[] blocks, int size){
		char[] out = new char[size];
		for(int i = 0; i < size; i++){
			int blk=blocks[i/BLOCK_SIZE];
			out[i]=data[blk*BLOCK_SIZE + (i%BLOCK_SIZE)];
		}
		return new String(out);
	}

	public String bitmap(){
		StringBuilder sb =new StringBuilder(TOTAL_BLOCKS);
		for(boolean b: used) sb.append(b?'#':'.');
		return sb.toString();
	}
	
	// Disk statistics methods
	public int getUsedBlocks() {
		int count = 0;
		for (boolean b : used) {
			if (b) count++;
		}
		return count;
	}
	
	public int getFreeBlocks() {
		return TOTAL_BLOCKS - getUsedBlocks();
	}
	
	public int getTotalBlocks() {
		return TOTAL_BLOCKS;
	}
	
	public int getBlockSize() {
		return BLOCK_SIZE;
	}
	
	public long getTotalCapacity() {
		return (long) TOTAL_BLOCKS * BLOCK_SIZE;
	}
	
	public long getUsedCapacity() {
		return (long) getUsedBlocks() * BLOCK_SIZE;
	}
	
	public long getFreeCapacity() {
		return (long) getFreeBlocks() * BLOCK_SIZE;
	}
	
	public double getUsagePercentage() {
		return (double) getUsedBlocks() / TOTAL_BLOCKS * 100.0;
	}
}
