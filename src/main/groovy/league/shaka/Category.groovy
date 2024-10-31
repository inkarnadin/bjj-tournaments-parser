package league.shaka

class Category {

    String format
    String age
    List<String> belts = []
    String weight

    Category(String value) {
        def lowerValue = value.toLowerCase().replace(' ', '')

        updateFormat(lowerValue)
        updateBelt(lowerValue)
        updateWeight(lowerValue)
    }

    void updateFormat(String value) {
        //NO-GI/ВЗРОСЛЫЕ МУЖЧИНЫ MASTER (ОТ 1991 Г. ) /БЕЛЫЙ/ДО 91.5 КГ | КОВЕР 1
        //GI/ВЗРОСЛЫЕ МУЖЧИНЫ ADULT ABSOLUTE/СИНИЙ/0+ КГ | КОВЕР 4

        def gi = value.matches('(gi).*') ? 'GI' : null
        def noGi = value.matches('(no-gi).*') ? 'NO-GI' : null

        this.format = gi ?: noGi ?: '?'
    }

    void updateBelt(String value) {
        value =~ '.*(бел).*' ? this.belts.add('белый') : null
        value =~ '.*(сер).*' ? this.belts.add('серый') : null
        value =~ '.*(жел).*' ? this.belts.add('желтый') : null
        value =~ '.*(оранж).*' ? this.belts.add('оранжевый') : null
        value =~ '.*(зел).*' ? this.belts.add('зеленый') : null
        value =~ '.*(син).*' ? this.belts.add('синий') : null
        value =~ '.*(пурпур).*' ? this.belts.add('пурпурный') : null
        value =~ '.*(коричнев).*' ? this.belts.add('коричневый') : null
        value =~ '.*(черн).*' ? this.belts.add('черный') : null

        if (!this.belts) {
            this.belts.add('N/A')
        }
    }

    void updateWeight(String value) {
        def matches = value =~ '/ ?от [\\d.+]+ ?кг|/ ?до [\\d.+]+ ?кг|/ ?[\\d.+]+ ?кг'
        def result = matches.find() ? matches.group(0).replace('/', '') : '? кг'
        result = result =~ '0\\+' ? 'ABS' : result

        this.weight = result
    }

}
