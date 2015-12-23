package edu.up.cs301.Pinochle;

import edu.up.cs301.card.Suit;
import edu.up.cs301.game.GamePlayer;

/**
 * action for a player choosing a trump suit
 *
 * Created by Matthew Yuen on 11/5/2015.
 *
 * @author Matthew Yuen
 * @version November 7, 2015
 * created class
 */
public class PinochleChooseTrumpSuitAction extends PinochleGameAction {
    //the trump suit
    private Suit suit;

    /**
     * constructor
     * @param p player who made the action
     * @param name name of the player
     * @param suit the trump suit
     */
    public PinochleChooseTrumpSuitAction(GamePlayer p, String name, Suit suit) {
        super(p,name);
        this.suit=suit;
    }

    /**
     * getter method for the trump suit
     * @return the trump suit
     */
    public Suit getSuit() {return suit;}

    /**
     * toString method
     * @return the log entry
     */
    @Override
    public String toString(){
        return getName()+":\n  Trump Suit is "+suit;
    }
}
