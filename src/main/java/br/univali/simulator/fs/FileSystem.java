package br.univali.simulator.fs;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class FileSystem {

	private final Disk   disk     = new Disk();
	@Getter
	private final Folder root     = new Folder("/", User.ROOT, 0777);
	private Folder       cwd      = root;
	private User         current  = User.ROOT;

	public User   user(){ return current; }
	public String pwd (){
		return pathOf(cwd);
	}
	
	public Disk getDisk() {
		return disk;
	}

	public void setCurrentUser(User user) {
		this.current = user;
	}

	public void cd(String path){
		Folder dest = resolveFolder(path);
		cwd = dest;
	}

	public void mkdir(String name){
		cwd.checkWrite(current);
		cwd.addChild(new Folder(name, current, 0777));
	}

	public void touch(String name){
		cwd.checkWrite(current);
		cwd.addChild(new File(name, current, 0666));
	}

	public void echo(String content, String file){
		File f = findFile(file);
		if(f==null){         // criar se nao existir
			f = new File(file, current, 0666);
			cwd.addChild(f);
		}
		f.write(current,disk,content);
	}

	public String cat(String file){
		return findFile(file).read(current,disk);
	}

	public String ls(boolean verbose){
		StringBuilder sb=new StringBuilder();
		for(var fc : cwd.list().values()){
			if(verbose){
				sb.append(String.format("%-10s %4d %s %s%n",
						fc instanceof Folder?"<DIR>":"FILE",
						fc.getInode(),
						fc.getModeString(),
						fc.getName()));
			}else sb.append(fc.getName()).append(' ');
		}
		return sb.toString();
	}

	public void chmod(String arg, String name){
		int mode = Integer.parseInt(arg,8);
		FileControlBlock f = lookup(name);
		f.chmod(mode);
	}

	public void cp(String src,String dst){
		File srcF = resolveAndGetFile(src);
		if(dst.contains("/")){
			Folder target = resolveFolder(dst.substring(0,dst.lastIndexOf('/')));
			String newName = dst.substring(dst.lastIndexOf('/')+1);
			srcF.duplicate(newName, target, disk);
		}else{
			srcF.duplicate(dst,cwd,disk);
		}
	}

	public void mv(String old,String neu){
		if(neu.contains("/")){
			cp(old,neu);
			rm(old);
		}else{
			FileControlBlock f = lookup(old);
			cwd.removeChild(f.getName());
			f.rename(neu);
			cwd.addChild(f);
		}
	}

	public void rm(String name){
		cwd.checkWrite(current);
		cwd.removeChild(name);
	}

	public void su(String who){
		current = who.equals("root") ? User.ROOT : new User(who);
	}

	public String bitmap(){ return disk.bitmap(); }

	private Folder resolveFolder(String path){
		if(path.equals("/")||path.isBlank()) return root;
		
		// Handle special cases for navigation
		if(path.equals("./")) return cwd;
		if(path.equals("..")) return cwd.isRoot() ? root : cwd.getParent();
		
		List<String> parts = Arrays.stream(path.split("/"))
				.filter(p->!p.isBlank()).collect(Collectors.toList());
		Folder cur = path.startsWith("/") ? root : cwd;
		for(String p: parts){
			if(p.equals("."))        continue;
			if(p.equals("..")){ 
				cur = cur.isRoot() ? root : cur.getParent();
				continue;
			}
			var next = cur.child(p);
			if(next == null)
				throw new IllegalArgumentException("Diretório não encontrado: "+p);
			if(!(next instanceof Folder))
				throw new IllegalArgumentException("Não é diretório: "+p);
			cur=(Folder)next;
		}
		return cur;
	}

	private File findFile(String name){
		var f = lookup(name);
		if(!(f instanceof File))
			throw new IllegalArgumentException("Não é arquivo: "+name);
		return (File)f;
	}

	private File resolveAndGetFile(String path){
		int cut = path.lastIndexOf('/');
		Folder parent = cut==-1? cwd : resolveFolder(path.substring(0,cut));
		String fname  = cut==-1? path : path.substring(cut+1);
		var child = parent.child(fname);
		if(!(child instanceof File))
			throw new IllegalArgumentException("Não é arquivo: "+fname);
		return (File)child;
	}

	private FileControlBlock lookup(String name){
		FileControlBlock f = cwd.child(name);
		if(f==null) throw new IllegalArgumentException("Não encontrado: "+name);
		return f;
	}

	private String pathOf(Folder folder){
		if(folder == root) return "/";
		
		StringBuilder sb = new StringBuilder();
		Folder cur = folder;
		
		// Build path by traversing up to root
		while(cur != root && cur != null){
			sb.insert(0, "/" + cur.getName());
			cur = cur.getParent();
		}
		
		return sb.toString();
	}
}