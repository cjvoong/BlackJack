package blackjack;
import java.util.*

class Card (val number: CardNum,val suit: Suit){
    override fun toString():String = ("[${number.pip}${suit.unicode}]")
}

enum class CardNum(val pip:String, val points:Int) {
    TWO("2",2),THREE("3",3),FOUR("4",4),FIVE("5",5),SIX("6",6),SEVEN("7",7),EIGHT("8",8),
    NINE("9",9),TEN("10",10),JACK("J",10),QUEEN("Q",10),KING("K",10),ACE("A",11)
}

enum class Suit(val unicode:String) {
    DIAMOND("\u2666"), CLUB("\u2663"), HEART("\u2665"), SPADE("\u2660")
}

class Deck {
    val cards= CardNum.values().flatMap{x -> Suit.values().map{y -> Card(x,y)}}.toMutableList() //initialise deck
    //deal first card or last card?  or does it matter?  If no cards left it returns null
    fun deal(): Card? =
        when {
            cards.isEmpty() -> null
            else -> {
                val card = cards.last()
                cards.removeAt(cards.lastIndex)
                println("Dealt card $card, deck now has ${cards.count()} left")
                card
            }
        }

    fun shuffle(){
        //fischer yates shuffle
        for (i in 51 downTo 1) {
            val j = Random().nextInt(i)
//            val temp = cards[i]
//            cards[i] = cards[j]
//            cards[j] = temp
            cards[i] = cards[j].also{cards[j]=cards[i]}
            println("Swapped card[$i] and card[$j]")
        }
    }
}

class Hand {
    val cards: MutableList<Card> = mutableListOf()

    fun getScore():Int = (cards.sumBy { it.number.points } - ((cards.count{it.number==CardNum.ACE} - (21 - (cards.sumBy { it.number.points } - (10*cards.count{it.number==CardNum.ACE})))/10)*10))

    fun getDiffFromTwentyOne():Int = (if (getScore()>21) 99 else 21-getScore())

    fun addCard(card:Card?) = card?.let {cards.add(card)} //add card if not null

    operator fun compareTo(otherHand:Hand):Int = this.getDiffFromTwentyOne() - otherHand.getDiffFromTwentyOne()

    override fun toString():String = cards.joinToString(prefix="Hand [Points=${getScore()} ",postfix="]",separator=",")
}

fun main(args: Array<String>) {
    val deck = Deck()
    var playerWinCount=0
    var computerWinCount=0
    var drawCount=0
    //shuffle deck
    deck.shuffle()

    //play game
    var playAgain:String?="1"
    //minimum for a game
    while (playAgain=="1") {
        //deal 2 cards for player and computer
        val player = Hand()
        val computer = Hand()

        player.addCard(deck.deal())
        computer.addCard(deck.deal())
        player.addCard(deck.deal())
        computer.addCard(deck.deal())

        println("-------------------------")
        println("P: $player")
        println("1. Stick | 2. Twist?")
        var input = readLine()
        //ask player if they wish to stick or twist
        while (input != "1") {
            if (input == "2") {
                player.addCard(deck.deal())
                println("P: $player")
                if (player.getScore() > 21) {
                    println("P: BUSTED")
                    break
                } else {
                    println("1. Stick | 2. Twist?")
                }
            } else if (input=="1"){
                println("P: STUCK")
            } else {
                println("Invalid option")
            }
            input = readLine()
            while (input!="1" && input!="2") {
                println("Invalid input")
                println("1. Stick | 2. Twist")
                input = readLine()
            }
        }

        println("-------------------------")
        println("C: $computer")
        //play cpu game, twist unless we reach 17
        while (computer.getScore() < 17) {
            computer.addCard(deck.deal())
            println("C: $computer")
            if (computer.getScore() > 21)
                println("C: BUSTED")
        }

        when {
            player < computer -> {println("Player WINS"); playerWinCount++}
            player > computer -> {println("Computer WINS"); computerWinCount++}
            else -> {println("DRAW"); drawCount++}
        }
        println("==========================")
        println("Play again? 1. Yes | 2. No")
        playAgain = readLine()
        while (playAgain!="1" && playAgain!="2") {
            println("Invalid input")
            println("Play again? 1. Yes | 2. No")
            playAgain = readLine()
        }

        if (deck.cards.count() < 4 && playAgain=="1"){
            println("Sorry too few cards left, exiting...")
            break
        }
    }

    println("Player won: $playerWinCount | Computer won: $computerWinCount | Draws: $drawCount")
    println("Bye!")
}