package edu.up.cs301.Pinochle;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * a basic action for the pinochle game
 *
 * Created by Matthew Yuen on 11/7/2015.
 *
 *
 * @author Matthew Yuen
 * @version November 7, 2015
 */
public abstract class PinochleGameAction extends GameAction {

    //holds the name of the player who made the action
    private String name;

    /**
     * constructor
     * @param player the player whose action it is
     * @param name name of the player
     */
    public PinochleGameAction(GamePlayer player,String name) {
        super(player);
        this.name=name;
    }

    /**
     * getter method for player name
     * @return name of the player
     */
    public String getName(){
        return name;
    }
}
