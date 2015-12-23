package edu.up.cs301.Pinochle;

import java.util.ArrayList;

import edu.up.cs301.card.Card;
import edu.up.cs301.card.Rank;
import edu.up.cs301.card.Suit;

/**
 * Created by Alex on 10/21/2015.
 */
public class MeldFinder {

    /**
     * finds marriages in trump in a hand
     *
     * @param Deck      the hand to check
     * @param Trump     the trump suit
     *
     * @return          0 means no kings or queens found
     *                  1 means a single king or queen found
     *                  2 means two kings or two queens found
     *                  3 means a single marriage found
     *                  4 means a marriage and an extra king or queen
     *                  5 means a double marriage
     */
    public static int trumpMarriageFind(ArrayList<Card> Deck, Suit Trump){

        int king = 0;
        int queen = 0;

        for(Card card : Deck)
        {
            if(card.getSuit() == Trump)
            {
                if(card.getRank() == Rank.KING)
                {
                    king++;
                }
                else if(card.getRank() == Rank.KING)
                {
                    queen++;
                }
            }

        }//for

        if(king == 2 && queen == 2)
            return 5;
        if((king == 2 && queen == 1) || (king == 1 && queen == 2))
            return 4;
        if(king == 1 && queen == 1)
            return 3;
        if(king == 2 || queen == 2)
            return 2;

        return king + queen;
    }//trumpMarriageFind


    /**
     * Finds either a run in trump or a double run in a given hand
     *
     * @param Deck      the hand to check
     * @param Trump     the trump suit
     *
     * @return          0 no run found
     *                  1 - 14 one partial run
     *                  15 two partial runs
     *                  16 single run found
     *                  17 one run and one partial of three
     *                  18 one run and a partial of four
     *                  19 two runs
     */
    public  static int runFind(ArrayList<Card> Deck, Suit Trump)
   {
        int numFound = 0;
        int numFound2 = 0;
        int ace = 0;
        int king = 0;
        int queen = 0;
        int jack = 0;
        int ten = 0;

        for(Card card : Deck)
        {
            if(card.getSuit() == Trump)
            {
                if(card.getRank() == Rank.ACE)
                {
                    if(ace == 0)
                    {
                        numFound++;
                    }
                    else
                    {
                        numFound2++;
                    }
                    ace++;
                }
                if(card.getRank() == Rank.KING)
                {
                    if(king == 0)
                    {
                        numFound++;
                    }
                    else
                    {
                        numFound2++;
                    }
                    king++;
                }
                if(card.getRank() == Rank.QUEEN)
                {
                    if(queen == 0)
                    {
                        numFound++;
                    }
                    else
                    {
                        numFound2++;
                    }
                    queen++;
                }
                if(card.getRank() == Rank.JACK)
                {
                    if(jack == 0)
                    {
                        numFound++;
                    }
                    else
                    {
                        numFound2++;
                    }
                    jack++;
                }
                if(card.getRank() == Rank.TEN)
                {
                    if(ten == 0)
                    {
                        numFound++;
                    }
                    else
                    {
                        numFound2++;
                    }
                    ten++;
                }
            }
        }

        if(numFound < 3)
            return 0;
        if(numFound >= 3 && numFound2 < 3)
        {//a partial run in one
            //one run
            if(numFound == 5)
                return 16;

            //a four card partial run
            if(numFound == 4)
            {
                if(jack == 0)
                    return 14;
                if(queen == 0)
                    return 13;
                if(king == 0)
                    return 12;
                if(ten == 0)
                    return 11;
                if(ace == 0)
                    return 10;
            }

            //a three card partial run
            if(ace != 0 && king != 0 && queen != 0 && jack == 0 && ten == 0)
                return 6;
            if(ace != 0 && king != 0 && queen == 0 && jack != 0 && ten == 0)
                return 4;
            if(ace != 0 && king != 0 && queen == 0 && jack == 0 && ten != 0)
                return 9;
            if(ace != 0 && king == 0 && queen != 0 && jack != 0 && ten == 0)
                return 3;
            if(ace != 0 && king == 0 && queen != 0 && jack == 0 && ten != 0)
                return 8;
            if(ace != 0 && king == 0 && queen == 0 && jack != 0 && ten != 0)
                return 7;
            if(ace == 0 && king != 0 && queen != 0 && jack != 0 && ten == 0)
                return 1;
            if(ace == 0 && king != 0 && queen != 0 && jack == 0 && ten != 0)
                return 5;
            if(ace == 0 && king == 0 && queen != 0 && jack != 0 && ten != 0)
                return 2;

        }//a partial run in one
        else if(numFound < 5 && numFound2 < 5)
        {
            return 15;
        }
        else
            return 14 + numFound2;

       return -1;
    }


    /**   OBSOLETE
     * Finds either a run in trump or a double run in a given hand
     *
     * @param Deck      the hand to check
     * @param Trump     the trump suit
     *
     * @return          0 no run found
     *                  1 - 8 three cards found
     *                  9 - 13 four cards found
     *                  14 single run found
     *                  15 - 22 one run and three extra cards found
     *                  23 - 27 one run and four extra cards found
     *                  28 double run found
     */
    public  static int runFindObsolete(ArrayList<Card> Deck, Suit Trump)
    {
        int numFound = 0;
        int i = 0;
        boolean isFound[] = new boolean[Deck.size()];
        boolean ace = false;
        boolean king = false;
        boolean queen = false;
        boolean jack = false;
        boolean ten = false;

        for(Card card : Deck)
        {
            if(card.getSuit() == Trump)
            {
                if(card.getRank() == Rank.ACE && !ace)
                {
                    numFound++;
                    isFound[i] = true;
                    ace = true;
                }
                if(card.getRank() == Rank.KING && !king)
                {
                    numFound++;
                    isFound[i] = true;
                    king = true;
                }
                if(card.getRank() == Rank.QUEEN && !queen)
                {
                    numFound++;
                    isFound[i] = true;
                    queen = true;
                }
                if(card.getRank() == Rank.JACK && !jack)
                {
                    numFound++;
                    isFound[i] = true;
                    jack = true;
                }
                if(card.getRank() == Rank.TEN && !ten)
                {
                    numFound++;
                    isFound[i] = true;
                    ten = true;
                }
            }
        }

        if(numFound < 3)
        {
            return 0;
        }

        if(numFound < 5)
        {

            if(numFound ==3) {
                if (ace && king && queen) {
                    return 1;
                }
                if (ace && king && jack) {
                    return 2;
                }
                if (ace && king && ten) {
                    return 3;
                }
                if (ace && queen && jack) {
                    return 4;
                }
                if (ace && queen && ten) {
                    return 5;
                }

                if (king && queen && jack) {
                    return 6;
                }
                if (king && queen && ten) {
                    return 7;
                }
                if (queen && jack && ten) {
                    return 8;
                }
            }

            if(numFound == 4)
            {
                if(ace && king && queen && jack)
                {
                    return 9;
                }
                if(ace && king && queen && ten)
                {
                    return 10;
                }
                if(ace && king && jack && ten)
                {
                    return 11;
                }
                if(ace && queen && jack && ten)
                {
                    return 12;
                }
                if(king && queen && jack && ten)
                {
                    return 13;
                }
            }
        }//num<5

        if(numFound == 5)
        {
            i = 0;
            ace = false;
            king = false;
            queen = false;
            jack = false;
            ten = false;

            for(Card card : Deck)
            {
                if(card.getSuit() == Trump && !isFound[i])
                {
                    if(card.getRank() == Rank.ACE && !ace)
                    {
                        numFound++;
                        isFound[i] = true;
                        ace = true;
                    }
                    if(card.getRank() == Rank.KING && !king)
                    {
                        numFound++;
                        isFound[i] = true;
                        king = true;
                    }
                    if(card.getRank() == Rank.QUEEN && !queen)
                    {
                        numFound++;
                        isFound[i] = true;
                        queen = true;
                    }
                    if(card.getRank() == Rank.JACK && !jack)
                    {
                        numFound++;
                        isFound[i] = true;
                        jack = true;
                    }
                    if(card.getRank() == Rank.TEN && !ten)
                    {
                        numFound++;
                        isFound[i] = true;
                        ten = true;
                    }
                }
            }//for card

            if(numFound < 8)
            {
                return 14;
            }
            if(numFound ==8) {
                if (ace && king && queen) {
                    return 15;
                }
                if (ace && king && jack) {
                    return 16;
                }
                if (ace && king && ten) {
                    return 17;
                }
                if (ace && queen && jack) {
                    return 18;
                }
                if (ace && queen && ten) {
                    return 19;
                }

                if (king && queen && jack) {
                    return 20;
                }
                if (king && queen && ten) {
                    return 21;
                }
                if (queen && jack && ten) {
                    return 22;
                }
            }

            if(numFound == 9)
            {
                if (ace && king && queen && jack) {
                    return 23;
                }
                if (ace && king && queen && ten) {
                    return 24;
                }
                if (ace && king && jack && ten) {
                    return 25;
                }
                if (ace && queen && jack && ten) {
                    return 26;
                }
                if (king && queen && jack && ten) {
                    return 27;
                }
            }

            if(numFound == 10)
            {
                return 28;
            }
        }//num == 5

        return numFound;
    }

    /**
     * finds an around of a particular rank
     *
     * @param Deck          the hand to check
     * @param rankToUse     the trump suit
     *
     * @return      0 no arounds found
     *              5 is a partial of at least 3
     *              6 is one full around and a partial of less than three
     *              7 is on partial of three and one full
     *              8 is two full arounds
     */
    public static int aroundFind(ArrayList<Card> Deck, Rank rankToUse)
    {
        int dimond = 0;
        int heart = 0;
        int spade = 0;
        int club = 0;

        for(Card card : Deck)
        {
            if(card.getRank() == rankToUse)
            {
                if(card.getSuit() == Suit.Club)
                {
                    club++;
                }
                if(card.getSuit() == Suit.Diamond)
                {
                    dimond++;
                }
                if(card.getSuit() == Suit.Spade)
                {
                    spade++;
                }
                if(card.getSuit() == Suit.Heart)
                {
                    heart++;
                }
            }
        }

        int numFound = spade + dimond + heart + club;

        if(numFound < 3)
            return 0;
        if (numFound == 8)
            return 8;
        if(numFound == 7)
            return 7;

        int suitFound = 0;

        if(heart > 0)
            suitFound++;
        if(spade > 0)
            suitFound++;
        if(dimond > 0)
            suitFound++;
        if(club > 0)
        {
            suitFound++;
        }

        if(suitFound >= 4)
            return 6;

        if(suitFound > 2)
            return 5;

        return 1;
    }

    /**
     * finds marriages in non trump suits
     *
     * @param Deck
     * @param Trump
     * @return
     */
    public static int nonTrumpMarriageFind(ArrayList<Card> Deck, Suit Trump)
    {
        Suit nonTrump[] = new Suit[3];
        char suits[] = {'s', 'c', 'h', 'd'};
        int found = 0;

        for (char suit : suits)
        {
            Suit temp = Suit.fromChar(suit);

            if(temp != Trump)
            {
                switch (trumpMarriageFind(Deck, temp))
                {
                    case 5://double marriage
                        found += 2;
                        break;
                    case 4:
                    case 3://single marriage
                        found += 1;
                        break;
                    default:
                        break;
                }
            }
        }

        return found;
    }
}
