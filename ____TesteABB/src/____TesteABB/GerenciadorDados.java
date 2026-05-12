package ____TesteABB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GerenciadorDados {

    public static void carregarArquivo(ABB<ProgramaNetFlix> arvore, String caminhoArquivo) {
        
        //ignora vírgulas dentro de aspas
        String regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"; 
        
        int qtdInseridos = 0;
        int qtdDescartados = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            
            String linha;
            br.readLine();

            while ((linha = br.readLine()) != null) {
                
                String[] colunas = linha.split(regex, -1); 

                if (colunas.length == 15 && linhaEhValida(colunas)) {
                    
                    Long id = Long.parseLong(colunas[0].replace("\"", ""));
                    String titulo = colunas[1].replace("\"", "");
                    String show_type = colunas[2].replace("\"", "");
                    String descricao = colunas[3].replace("\"", "");
                    int release_year = Integer.parseInt(colunas[4]);
                    int age_certification = Integer.parseInt(colunas[5].replace("\"", ""));
                    int runtime = Integer.parseInt(colunas[6]);
                    String generos = colunas[7].replace("\"", "");
                    String production_countries = colunas[8].replace("\"", "");

                    int temporadas = (int) Double.parseDouble(colunas[9]);

                    Long imdb_id = null;
                    String imdbRaw = colunas[10].replace("\"", "");
                    if (!imdbRaw.isEmpty()) {
                        try {
                            imdb_id = Long.parseLong(imdbRaw);
                        } catch (NumberFormatException ex) {
                            imdb_id = 0L;
                        }
                    } else {
                        imdb_id = 0L;
                    }

                    double imdb_score = Double.parseDouble(colunas[11]);
                    int imdb_votes = (int) Double.parseDouble(colunas[12]);
                    double tmdb_popularity = Double.parseDouble(colunas[13]);
                    double tmdb_score = Double.parseDouble(colunas[14]);

                    ProgramaNetFlix programa = new ProgramaNetFlix(titulo, show_type, descricao, generos,
                                                                   production_countries, id, imdb_id,
                                                                   release_year, age_certification, runtime,
                                                                   temporadas, imdb_votes, imdb_score,
                                                                   tmdb_score, tmdb_popularity);

                    arvore.inserir(programa);
                    qtdInseridos++;

                } else {
                    qtdDescartados++;
                }
            }

            System.out.println("Leitura finalizada!");
            System.out.println("Programas inseridos na árvore: " + qtdInseridos);
            System.out.println("Linhas descartadas (incompletas): " + qtdDescartados);

        } catch (IOException e) {
            System.out.println("Erro ao encontrar ou ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter um número do CSV: " + e.getMessage());
        }
    }

    private static boolean linhaEhValida(String[] colunas) {
        for (String dado : colunas) {
            if (dado == null || dado.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}