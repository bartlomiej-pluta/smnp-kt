package data.enumeration

enum class Pitch {
    C, C_S, D, D_S, E, F, F_S, G, G_S, A, A_S, H;

    override fun toString(): String {
        return when(this) {
            C -> "C"
            C_S -> "C#"
            D -> "D"
            D_S -> "D#"
            E -> "E"
            F -> "F"
            F_S -> "F#"
            G -> "G"
            G_S -> "G#"
            A -> "A"
            A_S -> "A#"
            H -> "H"
        }
    }

    companion object {
        fun parse(symbol: String): Pitch {
            return when(symbol.toLowerCase()) {
                "cb" -> H
                "c" -> C
                "c#" -> C_S
                "db" -> C_S
                "d" -> D
                "d#" -> D_S
                "eb" -> D_S
                "e" -> E
                "e#" -> F
                "fb" -> E
                "f" -> F
                "f#" -> F_S
                "gb" -> F_S
                "g" -> G
                "g#" -> G_S
                "ab" -> G_S
                "a" -> A
                "a#" -> A_S
                "b" -> A_S
                "h" -> H
                "h#" -> C
                else -> throw RuntimeException("Unknown pitch symbol")
            }
        }
    }
}