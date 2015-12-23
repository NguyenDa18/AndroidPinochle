package edu.up.cs301.Pinochle;

import edu.up.cs301.game.GamePlayer;

/**
 * a bidding action
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
 *
 */
public class PinochleBidAction extends PinochleGameAction {
    //holds the bid amount
    private int bid;

    /**
     * contructor
     * @param p player who is making the action
     * @param name name of the player
     * @param bid  the amount the player bid
     */
    public PinochleBidAction(GamePlayer p, String name,int bid) {
        super(p,name);
        this.bid=bid;
    }

    /**
     * getter method for bid
     * @return the amount bid
     */
    public int getBid(){
        return bid;
    }

    /**
     * toString method
     * @return what is to be displayed in the log
     */
    @Override
    public String toString(){
        return getName()+":\n  Bid "+bid;
    }
}