package edu.up.cs301.Pinochle;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.actionMsg.MyNameIsAction;

/**
 * Pass with help action
 *
 * Created by Matthew Yuen on 10/26/2015.
 *
 * @author Matthew Yuen
 * @version October 26, 2015
 * created class
 *
 * @author Matthew Yuen
 * @version November 7, 2015
 * extends PinochleGameAction
 */
public class PinochlePassWithHelpAction extends PinochleGameAction{

    /**
     * constructor
     * @param player player of sends the action
     * @param name name of the player
     */
    public PinochlePassWithHelpAction(GamePlayer player,String name) {
        super(player,name);
    }

    /**
     * toString
     * @return string used by log
     */
    @Override
    public String toString(){
        return getName()+":\n  Pass w/ help";
    }
}
