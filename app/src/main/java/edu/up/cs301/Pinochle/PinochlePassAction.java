package edu.up.cs301.Pinochle;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.actionMsg.MyNameIsAction;

/**
 * Pass action
 *
 * Created by Matthew Yuen on 10/24/2015.
 *
 * @author Matthew Yuen
 * @version October 26, 2015
 * created class
 *
 * @author Matthew Yuen
 * @version November 7, 2015
 * extends pinochle game action
 */
public class PinochlePassAction extends PinochleGameAction {

    /**
     * constructor
     * @param p player who's action it is
     * @param name name of that player
     */
    public PinochlePassAction(GamePlayer p, String name) {
        super(p,name);
    }


    /**
     * toString method
     * @return string used for the log
     */
    @Override
    public String toString(){
        return getName()+":\n  Pass";
    }
}