package br.univali.simulator.fs;

public final class Permission {
	private Permission(){}

	public static String toString(int mode){
		StringBuilder sb = new StringBuilder(9);
		for(int i=8;i>=0;i--){
			int bit = 1<<i;
			char c;
			switch(i%3){
				case 2:  c = (mode & bit)!=0 ? 'r' : '-'; break;
				case 1:  c = (mode & bit)!=0 ? 'w' : '-'; break;
				default: c = (mode & bit)!=0 ? 'x' : '-';
			}
			sb.append(c);
		}
		return sb.toString();
	}

	public static boolean canRead(FileControlBlock f, User who){
		if(who.isRoot()) return true;
		int mask = who.equals(f.owner) ? 1<<8 : 1<<5;
		int other = 1<<2;
		return (f.mode & (mask|other))!=0;
	}

	public static boolean canWrite(FileControlBlock f, User who){
		if(who.isRoot()) return true;
		int mask = who.equals(f.owner) ? 1<<7 : 1<<4;
		int other = 1<<1;
		return (f.mode & (mask|other))!=0;
	}

	public static boolean canExec(FileControlBlock f, User who){
		if(who.isRoot()) return true;
		int mask = who.equals(f.owner) ? 1<<6 : 1<<3;
		int other = 1;
		return (f.mode & (mask|other))!=0;
	}
}