package core

import league.shaka.Competitor
import league.shaka.KotPParser
import league.shaka.KotPTournaments

class Parser {

    static void main(String[] args) {
        def competitors = new HashSet<Competitor>()
        for (KotPTournaments tournamentUrl : KotPTournaments.enumConstants) {
            KotPParser.parse(tournamentUrl, competitors)
        }

        for (def c : competitors) {
            def file = new File('test.txt')
            file << "$c.name ($c.teams) \n${c.showResult()}\n"
            //println "$c.name ($c.teams) \n${c.showResult()}"
        }
    }

}
