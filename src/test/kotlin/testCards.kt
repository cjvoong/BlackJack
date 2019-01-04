package blackjack;
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class testCards{
    @Test
    fun testTwoAcesBothOne(){
        val hand = Hand()
        hand.addCard(Card(CardNum.ACE,Suit.SPADE))
        hand.addCard(Card(CardNum.ACE,Suit.DIAMOND))
        hand.addCard(Card(CardNum.TEN,Suit.SPADE))
        hand.addCard(Card(CardNum.NINE,Suit.SPADE))
        assertEquals(21,hand.getScore(),"Score")
    }

    @Test
    fun testTwoAcesOneElevenOneOne(){
        val hand = Hand()
        hand.addCard(Card(CardNum.ACE,Suit.SPADE))
        hand.addCard(Card(CardNum.ACE,Suit.DIAMOND))
        hand.addCard(Card(CardNum.NINE,Suit.SPADE))
        assertEquals(21,hand.getScore(),"Score")
    }

    @Test
    fun testThreeAcesOneElevenOneOne(){
        val hand = Hand()
        hand.addCard(Card(CardNum.ACE,Suit.SPADE))
        hand.addCard(Card(CardNum.ACE,Suit.DIAMOND))
        hand.addCard(Card(CardNum.ACE,Suit.CLUB))
        hand.addCard(Card(CardNum.EIGHT,Suit.SPADE))
        assertEquals(21,hand.getScore(),"Score")
    }

    @Test
    fun testFourAcesOneElevenOneOne(){
        val hand = Hand()
        hand.addCard(Card(CardNum.ACE,Suit.SPADE))
        hand.addCard(Card(CardNum.ACE,Suit.DIAMOND))
        hand.addCard(Card(CardNum.ACE,Suit.CLUB))
        hand.addCard(Card(CardNum.ACE,Suit.DIAMOND))
        hand.addCard(Card(CardNum.SEVEN,Suit.SPADE))
        assertEquals(21,hand.getScore(),"Score")
    }

    @Test
    fun testScoreNoAces(){
        val hand = Hand()
        hand.addCard(Card(CardNum.KING,Suit.SPADE))
        hand.addCard(Card(CardNum.QUEEN,Suit.DIAMOND))
        assertEquals(20,hand.getScore(),"Score")
    }

    @Test
    fun testScoreBlackJack(){
        val hand = Hand()
        hand.addCard(Card(CardNum.JACK,Suit.SPADE))
        hand.addCard(Card(CardNum.ACE,Suit.DIAMOND))
        assertEquals(21,hand.getScore(),"Score")
    }

    @Test
    fun testHandWinner(){
        val winner = Hand()
        winner.addCard(Card(CardNum.QUEEN,Suit.DIAMOND))
        winner.addCard(Card(CardNum.KING,Suit.DIAMOND))
        val loser = Hand()
        loser.addCard(Card(CardNum.KING,Suit.SPADE))
        loser.addCard(Card(CardNum.NINE,Suit.SPADE))

        assert(winner < loser)

    }
}