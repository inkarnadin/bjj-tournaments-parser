package league.shaka

import groovy.util.logging.Slf4j
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

@Slf4j
class KotPParser {

    static void parse(KotPTournaments tournamentUrl, HashSet<Competitor> competitors) {
        Document doc = Jsoup.connect(tournamentUrl.url).get()

        def title = doc.select('main').select('h1[class="section-title"]').text()
        def brackets = doc.select('div[class="brackets"]')

        //def competitors = new HashSet<Competitor>()
        def fights = new HashSet<Fight>()

        def tournament = new Tournament()
        tournament.title = title

        def tourTitleElements = brackets.select('h6[class="tour-title"]')

        int counter = 0
        def tourElements = brackets.select('div[class="tour-item"]')
        for (def e : tourElements) {
            def category = new Category(tourTitleElements.get(counter).text())
            // если найден стиль - категория не меняется, так как это разделенная на части сетка
            counter = e.attribute('style') ? counter : counter + 1

            Elements matchElements = e.select('ul')
            for (def e1 : matchElements) {
                parseFight(e1, category).ifPresent { x -> fights.add(x) }

                Elements competitorElements = matchElements.select('div[class=td-body]')
                for (def e2 : competitorElements) {
                    parseCompetitor(e2, competitors, 0).ifPresent { x -> competitors.add(x) }
                    parseCompetitor(e2, competitors, 1).ifPresent { x -> competitors.add(x) }
                }
            }
        }

        competitors.removeAll { !it.name }
        for (def c : competitors) {
            def twice = new Tournament()
            twice.title = tournament.title

            fights.findAll { it.winner == c.name || it.loser == c.name }.each { twice.fights.add(it) }

            if (twice.fights) {
                c.tournaments.add(twice)
            }
        }
    }

    static Optional<Competitor> parseCompetitor(Element element, Set<Competitor> competitors, int index) {
        def names = element.select('strong')
        def teams = element.select('span')

        if (!names[index]) {
            Optional.empty()
        }

        def competitor = competitors.find { it.name == names[index]?.text() } as Competitor
        if (!competitor) {
            competitor = new Competitor()
            competitor.name = names[index]?.text()
            competitor.teams.add(teams[index]?.text())
        }
        return Optional.of(competitor)
    }

    static Optional<Fight> parseFight(Element element, Category category) {
        def winner = element.select('li[class="active"]').select('strong').text()
        def loser = element.select('li[class!="active"]').select('strong').text()
        log.trace("winner: $winner, loser: $loser")

        if (loser.length() < 3 || winner.length() < 3) {
            return Optional.empty()
        }

        def fight = new Fight()
        fight.winner = winner
        fight.loser = loser
        fight.format = category.format
        fight.belt = category.belts
        fight.weight = category.weight

        return Optional.of(fight)
    }

    static void showTourTitle(Elements elements) {
        Elements elements2 = elements.select('h6[class="tour-title"]')
        for (Element e : elements2) {
            println e.text()
        }
    }

}
