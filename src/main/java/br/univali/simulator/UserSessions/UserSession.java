package br.univali.simulator.UserSessions;

import java.util.Scanner;

public class UserSession {

    private Scanner sc = new Scanner(System.in);

    public void getNextInstruction() throws Exception{
        String line = sc.nextLine();
        System.out.println(line);
        if(line.equals("exit")){
            throw new Exception("exiting");
        }   
        
    }

}
