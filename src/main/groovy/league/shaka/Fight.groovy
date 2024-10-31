package league.shaka

class Fight {

    String format
    String weight
    String winner
    String loser
    String[] belt

    String showResult(String competitorName) {
        competitorName == winner ? "$format ($belt, $weight) WIN $loser\n" : "$format ($belt, $weight) LOSE $winner\n"
    }

}
