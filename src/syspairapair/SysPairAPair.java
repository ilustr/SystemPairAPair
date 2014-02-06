/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syspairapair;

import model.agent.Agent;
import model.environment.Environment;

/**
 *
 * @author ilustr
 */
public class SysPairAPair {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Environment env = Environment.getInstance();
       
        env.startGame();
        
        while (true){
            Thread.sleep(Agent.TIME_SLEEP_MS);
            env.displayMap();
        }

    }

}
