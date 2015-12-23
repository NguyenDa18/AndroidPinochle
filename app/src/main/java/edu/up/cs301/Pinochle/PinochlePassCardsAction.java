package edu.up.cs301.Pinochle;

import java.util.ArrayList;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * pass cards action
 *
 * Created by Matthew Yuen on 11/6/2015.
 *
 * @author Matthew Yuen
 * @version November 7, 2015
 * created class
 */
public class PinochlePassCardsAction extends PinochleGameAction {
    //arraylist of cards that player is passing
    private ArrayList<Card> passCards;

    /**
     * constructor
     * @param p player who's action it is
     * @param name the player name
     * @param passCards the arraylist of cards that the player is passing
     */
    public PinochlePassCardsAction(GamePlayer p, String name,ArrayList<Card> passCards) {
        super(p,name);
        this.passCards=passCards;
    }

    /**
     * getter method for the pass cards
     * @return the pass cards
     */
    public ArrayList<Card> getPassCards(){
        return passCards;
    }

    /**
     * toString method
     * @return string that is used by log
     */
    @Override
    public String toString(){
        return getName()+":\n  Passed cards";
    }
}
