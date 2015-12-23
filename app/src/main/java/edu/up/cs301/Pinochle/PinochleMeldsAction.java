package edu.up.cs301.Pinochle;

import edu.up.cs301.game.GamePlayer;

/**
 * melds action
 *
 * Created by Matthew Yuen on 11/6/2015.
 * @author Matthew Yuen
 * @version November 7, 2015
 * created class
 */
public class PinochleMeldsAction extends PinochleGameAction {

    /**
     * constructor
     * @param p player who's action it is
     * @param name name of that player
     */
    public PinochleMeldsAction(GamePlayer p, String name) {
        super(p,name);
    }

    /**
     * toString method
     * @return string used by log
     */
    @Override
    public String toString(){
        return getName()+":\n  Ready for Trick Taking";
    }
}
