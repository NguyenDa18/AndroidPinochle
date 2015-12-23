package edu.up.cs301.card;

import java.io.Serializable;

import edu.up.cs301.game.R;

/**
 * A playing card in the standard 52-card deck. The images, which have been
 * placed in the res/drawable-hdpi folder in the project, are from
 * http://www.pdclipart.org.
 * 
 * In order to display the card-images on the android you need to call the
 *   Card.initImages(currentActivity)
 * method during initialization; the 52 image files need to be placed in the
 * res/drawable-hdpi project area.
 * 
 * @author Steven R. Vegdahl
 * @version July 2013
 *
 * @author Matthew Yuen
 * @version October 13, 2015
 * Changes:
 * added a trump suit with getter and setter methods
 * added a compareTo method
 * changed images to reflect changes in Rank
 * added a getImage method
 * removed unneeded methods
 *
 * @version October 26, 2015
 * changed images to reflect changes in suit
 * fixed error for nine of hearts
 *
 */
public class Card implements Serializable {

	// to satisfiy the Serializable interface
	private static final long serialVersionUID = 893542931190030342L;
	
	// instance variables: the card's rank and the suit
    private Rank rank;
    private Suit suit;

    /**
     * Constructor for class card
     *
     * @param r the Rank of the card
     * @param s the Suit of the card
     */
    public Card(Rank r, Suit s) {
        rank = r;
        suit = s;
    }


	private static Suit trumpSuit;

	public static Card compareTo(Card firstCard,Card secondCard){
		//whoever played the larger card first wins
		if (firstCard.equals(secondCard)) return firstCard;

		//if same suit
		if (firstCard.getSuit()==secondCard.getSuit()) {
			if (firstCard.getRank().value() > secondCard.getRank().value()) {
				return firstCard;
			} else {
				return secondCard;
			}
		}
		else if (secondCard.getSuit()==trumpSuit) {
			return secondCard;
		}
		return firstCard;
	}

	public static void setTrumpSuit(Suit suit){
		trumpSuit=suit;
	}

	public static Suit getTrumpSuit(){
		return trumpSuit;
	}

    /**
     * Creates a Card from a String.  (Can be used instead of the
     * constructor.)
     *
     * @param str
     * 		a two-character string representing the card, which is
     *		of the form "4C", with the first character representing the rank,
     *		and the second character representing the suit.  Each suit is
     *		denoted by its first letter.  Each single-digit rank is represented
     *		by its digit.  The letters 'T', 'J', 'Q', 'K' and 'A', represent
     *		the ranks Ten, Jack, Queen, King and Ace, respectively.
     * @return
     * 		A Card object that corresponds to the 'str' string. Returns
     *		null if 'str' has improper format.
     */
    public static Card fromString(String str) {
    	// check the string for being null
        if (str == null) return null;
        
        // trim the string; return null if length is not 2
        str = str.trim();
        if (str.length() !=2) return null;
        
        // get the rank and suit corresponding to the two characters
        // in the string
        Rank r = Rank.fromChar(str.charAt(0));
        Suit s = Suit.fromChar(str.charAt(1));
        
        // if both rank and suit are non-null, create the corresponding
        // card; if either is null, return null
        return r==null || s == null ? null : new Card(r, s);
    }

    /**
     * Produces a textual description of a Card.
     *
     * @return
	 *		A string such as "Jack of Spades", which describes the card.
     */
    public String toString() {
        return rank.longName()+" of "+suit.longName()+"s";
    }

    /**
     * Tells whether two Card objects represent the same card.
     *
     * @return
	 *		true if the two card objects represent the same card, false
     *		otherwise.
     */
    public boolean equals(Card other) {
        return this.rank == other.rank && this.suit == other.suit;
    }

    /**
     * Gives a two-character version of the card (e.g., "TS" for ten of
     * spades).
     */
    public String shortName() {
        return "" + getRank().shortName() + getSuit().shortName();
    }

    /**
     * Tells the card's rank.
     *
     * @return
	 *		a Rank object (actually of a subclass) that tells the card's
     *		rank (e.g., Jack, three).
     */
    public Rank getRank() {
    	return rank;
    }

    /**
     * Tells the card's suit.
     *
     * @return
	 *		a Suit object (actually of a subclass) that tells the card's
     *		rank (e.g., heart, club).
     */
    public Suit getSuit() {
    	return suit;
    }
 
    // array that contains the android resource indices for the 52 card
    // images
    private static int[][] resIdx = {
            {
                    R.drawable.card_9c, R.drawable.card_jc, R.drawable.card_qc,
                    R.drawable.card_kc, R.drawable.card_tc, R.drawable.card_ac
            },
            {
                    R.drawable.card_9d, R.drawable.card_jd, R.drawable.card_qd,
                    R.drawable.card_kd, R.drawable.card_td, R.drawable.card_ad,
            },
            {
                    R.drawable.card_9s, R.drawable.card_js, R.drawable.card_qs,
                    R.drawable.card_ks, R.drawable.card_ts, R.drawable.card_as
            },
            {
                    R.drawable.card_9h, R.drawable.card_jh, R.drawable.card_qh,
                    R.drawable.card_kh, R.drawable.card_th, R.drawable.card_ah
            },
    };
    public int getImage(){
        return resIdx[suit.ordinal()][rank.value()];
    }
    public boolean isCounter(){
        return getRank().ordinal()>=Rank.KING.ordinal();
    }
    public static Card getCardFromResourceId(int resourceId){
        Suit[] suits={Suit.Club,Suit.Heart,Suit.Spade,Suit.Diamond};
        Rank[] ranks={Rank.NINE,Rank.JACK,Rank.QUEEN,Rank.KING,Rank.TEN,Rank.ACE};

        for (int i=0;i<resIdx.length;i++){
            for (int j=0;j<resIdx[i].length;j++){
                if (resourceId==resIdx[i][j]){
                    return new Card(ranks[j],suits[i]);
                }
            }
        }
        return null;
    }
}
