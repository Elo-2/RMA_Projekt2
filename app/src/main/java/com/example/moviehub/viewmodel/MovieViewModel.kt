package com.example.moviehub.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviehub.data.local.MovieDatabase
import com.example.moviehub.data.repository.MovieRepository
import com.example.moviehub.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovieUiState(
    val movies: List<Movie> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

class MovieViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database = MovieDatabase.getDatabase(application)

    private val repository = MovieRepository(
        database.movieDao()
    )

    private val _uiState = MutableStateFlow(
        MovieUiState(
            isLoading = true
        )
    )

    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    private val initialMovies = listOf(

        Movie(
            id = 1,
            title = "Inception",
            description = "Dom Cobb je stručnjak za krađu tajni iz ljudskih snova. Dobija neobičan zadatak da uradi suprotno i ubaci novu ideju u nečiju podsvijest.",
            imageUrl = "inception",
            rating = 8.8,
            releaseYear = 2010,
            genre = "Naučna fantastika"
        ),

        Movie(
            id = 2,
            title = "The Dark Knight",
            description = "Batman se suočava s Jokerom, opasnim kriminalcem koji želi izazvati haos u Gothamu i natjerati njegove stanovnike da pokažu svoju najmračniju stranu.",
            imageUrl = "the_dark_knight",
            rating = 9.0,
            releaseYear = 2008,
            genre = "Akcija"
        ),

        Movie(
            id = 3,
            title = "Interstellar",
            description = "Kada Zemlji prijeti velika kriza, grupa astronauta kreće kroz svemir i crvotočinu u potrazi za novim mjestom pogodnim za život čovječanstva.",
            imageUrl = "interstellar",
            rating = 8.7,
            releaseYear = 2014,
            genre = "Naučna fantastika"
        ),

        Movie(
            id = 4,
            title = "The Matrix",
            description = "Neo otkriva da svijet koji poznaje nije ono što izgleda. Pridružuje se grupi pobunjenika koja se bori protiv sistema koji kontroliše čovječanstvo.",
            imageUrl = "the_matrix",
            rating = 8.7,
            releaseYear = 1999,
            genre = "Akcija"
        ),

        Movie(
            id = 5,
            title = "The Shawshank Redemption",
            description = "Andy Dufresne provodi godine u zatvoru gdje razvija snažno prijateljstvo s Redom i pokušava sačuvati nadu uprkos teškim okolnostima.",
            imageUrl = "shawshank_redemption",
            rating = 9.3,
            releaseYear = 1994,
            genre = "Drama"
        ),

        Movie(
            id = 6,
            title = "The Godfather",
            description = "Don Vito Corleone vodi moćnu kriminalnu porodicu, dok se njegov sin Michael postepeno uvlači u porodične poslove i sukobe.",
            imageUrl = "the_godfather",
            rating = 9.2,
            releaseYear = 1972,
            genre = "Krimi"
        ),

        Movie(
            id = 7,
            title = "Pulp Fiction",
            description = "Nekoliko neobičnih i međusobno povezanih priča o kriminalcima, plaćenicima i ljudima iz podzemlja odvija se kroz jedan haotičan dan.",
            imageUrl = "pulp_fiction",
            rating = 8.9,
            releaseYear = 1994,
            genre = "Krimi"
        ),

        Movie(
            id = 8,
            title = "Fight Club",
            description = "Nezadovoljan svakodnevnim životom, pripovjedač upoznaje Tylera Durdena. Njihovo prijateljstvo prerasta u stvaranje tajnog kluba koji brzo izmiče kontroli.",
            imageUrl = "fight_club",
            rating = 8.8,
            releaseYear = 1999,
            genre = "Drama"
        ),

        Movie(
            id = 9,
            title = "Forrest Gump",
            description = "Forrest Gump svojim jednostavnim pogledom na život prolazi kroz niz nevjerovatnih događaja i slučajno postaje dio važnih trenutaka američke historije.",
            imageUrl = "forrest_gump",
            rating = 8.8,
            releaseYear = 1994,
            genre = "Drama"
        ),

        Movie(
            id = 10,
            title = "The Lord of the Rings: The Fellowship of the Ring",
            description = "Frodo Baggins mora odnijeti moćni Jedinstveni prsten do Mordora kako bi ga uništio prije nego što ga pronađe mračni gospodar Sauron.",
            imageUrl = "lotr_fellowship",
            rating = 8.9,
            releaseYear = 2001,
            genre = "Fantazija"
        ),

        Movie(
            id = 11,
            title = "The Lord of the Rings: The Two Towers",
            description = "Družina se razdvaja dok se rat protiv Sauronovih snaga približava. Frodo i Sam nastavljaju put prema Mordoru, dok se ostali bore za opstanak Međuzemlja.",
            imageUrl = "lotr_two_towers",
            rating = 8.8,
            releaseYear = 2002,
            genre = "Fantazija"
        ),

        Movie(
            id = 12,
            title = "The Lord of the Rings: The Return of the King",
            description = "Velika završna bitka za Međuzemlje počinje dok Frodo i Sam pokušavaju doći do Mount Dooma i uništiti Jedinstveni prsten.",
            imageUrl = "lotr_return_king",
            rating = 9.0,
            releaseYear = 2003,
            genre = "Fantazija"
        ),

        Movie(
            id = 13,
            title = "Gladiator",
            description = "Rimski general Maximus nakon izdaje gubi sve što mu je bilo važno. Kao gladijator pokušava preživjeti i izboriti se za osvetu i pravdu.",
            imageUrl = "gladiator",
            rating = 8.5,
            releaseYear = 2000,
            genre = "Akcija"
        ),

        Movie(
            id = 14,
            title = "Titanic",
            description = "Jack i Rose upoznaju se tokom putovanja brodom Titanic. Njihova ljubavna priča odvija se dok se putovanje pretvara u jednu od najpoznatijih pomorskih tragedija.",
            imageUrl = "titanic",
            rating = 7.9,
            releaseYear = 1997,
            genre = "Romansa"
        ),

        Movie(
            id = 15,
            title = "Avatar",
            description = "Bivši marinac Jake Sully odlazi na Pandoru i kroz svoj novi život među Na'vijima počinje preispitivati svoju misiju i odnos prema njihovom svijetu.",
            imageUrl = "avatar",
            rating = 7.9,
            releaseYear = 2009,
            genre = "Naučna fantastika"
        ),

        Movie(
            id = 16,
            title = "Avengers: Endgame",
            description = "Nakon katastrofalnog sukoba s Thanosom, preživjeli Osvetnici okupljaju se kako bi pokušali vratiti izgubljene članove i spasiti svijet.",
            imageUrl = "avengers_endgame",
            rating = 8.4,
            releaseYear = 2019,
            genre = "Akcija"
        ),

        Movie(
            id = 17,
            title = "Spider-Man: No Way Home",
            description = "Peter Parker pokušava vratiti svoj život u normalu nakon što njegov identitet postane poznat javnosti, ali njegov pokušaj dovodi do neočekivanih posljedica.",
            imageUrl = "spiderman_no_way_home",
            rating = 8.2,
            releaseYear = 2021,
            genre = "Akcija"
        ),

        Movie(
            id = 18,
            title = "Joker",
            description = "Arthur Fleck sanja o tome da postane komičar, ali ga odbacivanje društva i težak život postepeno guraju prema sve mračnijem putu.",
            imageUrl = "joker",
            rating = 8.3,
            releaseYear = 2019,
            genre = "Drama"
        ),

        Movie(
            id = 19,
            title = "Parasite",
            description = "Članovi siromašne porodice postepeno pronalaze način da uđu u život bogate porodice. Njihovi odnosi ubrzo otkrivaju duboke društvene razlike.",
            imageUrl = "parasite",
            rating = 8.5,
            releaseYear = 2019,
            genre = "Triler"
        ),

        Movie(
            id = 20,
            title = "Whiplash",
            description = "Mladi bubnjar Andrew želi postati vrhunski muzičar, ali njegov odnos sa izuzetno zahtjevnim profesorom pretvara ambiciju u stalnu borbu za savršenstvo.",
            imageUrl = "whiplash",
            rating = 8.5,
            releaseYear = 2014,
            genre = "Drama"
        ),

        Movie(
            id = 21,
            title = "The Green Mile",
            description = "Čuvar zatvora Paul Edgecomb upoznaje neobičnog zatvorenika čije prisustvo i sposobnosti duboko mijenjaju živote ljudi oko njega.",
            imageUrl = "green_mile",
            rating = 8.6,
            releaseYear = 1999,
            genre = "Drama"
        ),

        Movie(
            id = 22,
            title = "The Prestige",
            description = "Dvojica mađioničara postaju veliki rivali i pokušavaju otkriti tajne jedan drugog, dok njihova opsesija savršenim trikom postaje sve opasnija.",
            imageUrl = "prestige",
            rating = 8.5,
            releaseYear = 2006,
            genre = "Triler"
        ),

        Movie(
            id = 23,
            title = "Django Unchained",
            description = "Django, oslobođeni rob, udružuje se s lovcem na ucjene Dr. Kingom Schultzom kako bi pronašao svoju suprugu i oslobodio je iz ropstva.",
            imageUrl = "django_unchained",
            rating = 8.5,
            releaseYear = 2012,
            genre = "Vestern"
        ),

        Movie(
            id = 24,
            title = "The Wolf of Wall Street",
            description = "Jordan Belfort gradi ogromno bogatstvo na Wall Streetu, ali njegov raskošan način života, pohlepa i ilegalne poslovne aktivnosti počinju uništavati sve oko njega.",
            imageUrl = "wolf_of_wall_street",
            rating = 8.2,
            releaseYear = 2013,
            genre = "Krimi"
        ),

        Movie(
            id = 25,
            title = "Oppenheimer",
            description = "J. Robert Oppenheimer vodi tim naučnika u razvoju prvog atomskog oružja tokom Drugog svjetskog rata, a kasnije se suočava s posljedicama svog rada.",
            imageUrl = "oppenheimer",
            rating = 8.6,
            releaseYear = 2023,
            genre = "Drama"
        )
    )

    init {
        observeMovies()
        insertInitialMovies()
    }

    private fun observeMovies() {
        viewModelScope.launch {
            repository.getMovies()
                .catch {
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false
                        )
                    }
                }
                .collect { movies ->
                    _uiState.update { state ->
                        state.copy(
                            movies = movies,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun insertInitialMovies() {
        viewModelScope.launch {
            repository.insertMovies(initialMovies)
        }
    }

    fun searchMovies(query: String) {
        _uiState.update { state ->
            state.copy(
                searchQuery = query,
                isLoading = true
            )
        }

        viewModelScope.launch {
            repository.searchMovies(query)
                .catch {
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false
                        )
                    }
                }
                .collect { movies ->
                    _uiState.update { state ->
                        state.copy(
                            movies = movies,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun getMovieById(id: Int): Movie? {
        return uiState.value.movies.find { movie ->
            movie.id == id
        }
    }
}