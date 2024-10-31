package league.shaka

enum KotPTournaments {

    KOTP13("https://shakasports.com/brackets/176"),
    KOTP14("https://shakasports.com/brackets/224"),
    KOTP15("https://shakasports.com/brackets/228"),
    KOTP16("https://shakasports.com/brackets/266"),
    KOTP17("https://shakasports.com/brackets/275"),
    KOTP18("https://shakasports.com/brackets/367"),
    KOTP19("https://shakasports.com/brackets/467"),
    //KOTP20("https://shakasports.com/brackets/231"),
    KOTP21("https://shakasports.com/brackets/640"),
    KOTP22("https://shakasports.com/brackets/656"),
    KOTP23("https://shakasports.com/brackets/768"),
    KOTP24("https://shakasports.com/brackets/819"),
    KOTP25("https://shakasports.com/brackets/918")

    String url

    KotPTournaments(String url) {
        this.url = url
    }

}