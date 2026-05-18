package ____TesteABB;

//Autores: [DAVI BARROS - 10385766] | [Guilherme Miyamoto Bragatto - 10736124]
public class ProgramaNetFlix implements Comparable<ProgramaNetFlix>{
    private String id, titulo, show_type, descricao, generos, Production_countries, age_certification;
    private Long imdb_id;
    private int release_year, runtime, temporadas, imdb_votes;
    private double imdb_score, tmdb_score, tmdb_popularity;

    public ProgramaNetFlix(String titulo, String show_type, String descricao, String generos,
        String production_countries, String id, Long imdb_id, int release_year, String age_certification, int runtime,
        int temporadas, int imdb_votes, double imdb_score, double tmdb_score, double tmdb_popularity) {
        this.titulo = titulo;
        this.show_type = show_type;
        this.descricao = descricao;
        this.generos = generos;
        Production_countries = production_countries;
        this.id = id;
        this.imdb_id = imdb_id;
        this.release_year = release_year;
        this.age_certification = age_certification;
        this.runtime = runtime;
        this.temporadas = temporadas;
        this.imdb_votes = imdb_votes;
        this.imdb_score = imdb_score;
        this.tmdb_score = tmdb_score;
        this.tmdb_popularity = tmdb_popularity;
    }

    public String toString() {
        return titulo + " - " + release_year + ", imdb: " + imdb_score + ", descricao: " + descricao;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getShow_type() {
        return show_type;
    }
    public void setShow_type(String show_type) {
        this.show_type = show_type;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getGeneros() {
        return generos;
    }
    public void setGeneros(String generos) {
        this.generos = generos;
    }
    public String getProduction_countries() {
        return Production_countries;
    }
    public void setProduction_countries(String production_countries) {
        Production_countries = production_countries;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Long getImdb_id() {
        return imdb_id;
    }
    public void setImdb_id(Long imdb_id) {
        this.imdb_id = imdb_id;
    }
    public int getRelease_year() {
        return release_year;
    }
    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }
    public String getAge_certification() {
        return age_certification;
    }
    public void setAge_certification(String age_certification) {
        this.age_certification = age_certification;
    }
    public int getRuntime() {
        return runtime;
    }
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }
    public int getTemporadas() {
        return temporadas;
    }
    public void setTemporadas(int temporadas) {
        this.temporadas = temporadas;
    }
    public int getImdb_votes() {
        return imdb_votes;
    }
    public void setImdb_votes(int imdb_votes) {
        this.imdb_votes = imdb_votes;
    }
    public double getImdb_score() {
        return imdb_score;
    }
    public void setImdb_score(double imdb_score) {
        this.imdb_score = imdb_score;
    }
    public double getTmdb_score() {
        return tmdb_score;
    }
    public void setTmdb_score(double tmdb_score) {
        this.tmdb_score = tmdb_score;
    }
    public double getTmdb_popularity() {
        return tmdb_popularity;
    }
    public void setTmdb_popularity(double tmdb_popularity) {
        this.tmdb_popularity = tmdb_popularity;
    }

    @Override
    public int compareTo(ProgramaNetFlix outro) {  // comparamos os ids
        if(id.compareTo(outro.getId()) < 0) return -1;
        else if(id.compareTo(outro.getId()) == 0) return 0;
        else return  1;
    }
}