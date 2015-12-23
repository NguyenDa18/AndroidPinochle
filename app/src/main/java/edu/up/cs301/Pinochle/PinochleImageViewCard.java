package edu.up.cs301.Pinochle;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import edu.up.cs301.card.Card;

/**
 * the imageview used for diaplaying the card
 *
 * Created by Matthew Yuen on 11/5/2015.
 *
 * @author Matthew Yuen
 * @version November 7, 2015
 * created class
 */
public class PinochleImageViewCard extends ImageView {
    //holds the card to display
    Card card;

    /**
     * constructor
     * @param context the activity
     * @param card the card to be displayed
     */
    public PinochleImageViewCard(Context context,Card card) {
        super(context);

        //save the card
        this.card = card;

        //set the image to the card's image
        setImageResource(card.getImage());
    }

    /**
     * getter method for the card
     * @return the card
     */
    public Card getCard(){
        return card;
    }
}
