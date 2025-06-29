package br.univali.simulator.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.univali.simulator.fs.FileSystem;

public final class InstructionParser {

    private static final Pattern ECHO = Pattern.compile("echo\\s+\"(.*)\"\\s*>\\s*(\\S+)");

    private final FileSystem fs;

    InstructionParser(FileSystem fs){ this.fs = fs; }

    public String handle(String line){
        line = line.strip();
        if(line.isBlank()) return "";
        try{
            if(line.equals("exit")) System.exit(0);

            if(line.startsWith("cd ")){ fs.cd(arg(line)); return ""; }
            if(line.equals("pwd"))    return fs.pwd();
            if(line.startsWith("mkdir ")) { fs.mkdir(arg(line)); return ""; }
            if(line.startsWith("touch ")) { fs.touch(arg(line)); return ""; }
            if(line.startsWith("cat "))   { return fs.cat(arg(line)); }
            if(line.equals("ls"))         { return fs.ls(false); }
            if(line.equals("ls -l"))      { return fs.ls(true); }
            if(line.startsWith("chmod ")){
                String[] p=line.split("\\s+");
                fs.chmod(p[1],p[2]); return "";
            }
            if(line.startsWith("cp ")){
                String[] p=line.split("\\s+");
                fs.cp(p[1],p[2]); return "";
            }
            if(line.startsWith("mv ")){ String[]p=line.split("\\s+"); fs.mv(p[1],p[2]); return ""; }
            if(line.startsWith("rm ")){ fs.rm(arg(line)); return ""; }
            if(line.startsWith("su ")){ fs.su(arg(line)); return ""; }
            if(line.equals("bitmap")) { return fs.bitmap(); }

            Matcher m = ECHO.matcher(line);
            if(m.matches()){ fs.echo(m.group(1), m.group(2)); return ""; }

            return "Comando não reconhecido.";
        }catch(Exception e){
            return "Erro: "+e.getMessage();
        }
    }
    private static String arg(String l){ return l.substring(l.indexOf(' ')+1).strip(); }
}
