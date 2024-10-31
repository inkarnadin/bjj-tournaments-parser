package league.shaka

class Competitor {

    String name
    Set<String> teams = []
    Set<Tournament> tournaments = []

    String showResult() {
        def result = ''
        for (def t : tournaments) {
            result = "==$t.title==\n"
            for (def f : t.fights) {
                result += f.showResult(name)
            }
        }
        return result
    }

}
