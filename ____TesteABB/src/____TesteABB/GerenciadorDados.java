package ____TesteABB;

// Autores: [DAVI BARROS - 10385766] | [Guilherme Miyamoto Bragatto - 10736124]

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorDados {

    public static void carregarArquivo(ABB<ProgramaNetFlix> arvore, String caminhoArquivo) {

        // Regex que ignora vírgulas dentro de aspas duplas
        String regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

        int qtdInseridos  = 0;
        int qtdDescartados = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {

            String linha;
            br.readLine(); 

            while ((linha = br.readLine()) != null) {

                String[] colunas = linha.split(regex, -1);

                // Linha válida: exatamente 15 campos, todos preenchidos
                if (colunas.length == 15 && linhaEhValida(colunas)) {
                    try {
                        String id                  = colunas[0].replace("\"", "").trim();
                        String titulo              = colunas[1].replace("\"", "").trim();
                        String show_type           = colunas[2].replace("\"", "").trim();
                        String descricao           = colunas[3].replace("\"", "").trim();
                        int    release_year        = Integer.parseInt(colunas[4].trim());
                        String age_certification   = colunas[5].replace("\"", "").trim();
                        int    runtime             = Integer.parseInt(colunas[6].trim());
                        String generos             = colunas[7].replace("\"", "").trim();
                        String production_countries = colunas[8].replace("\"", "").trim();
                        int    temporadas          = (int) Double.parseDouble(colunas[9].trim());

                        // imdb_id pode conter letras (ex: "tt1234567") — extrai só dígitos
                        String imdbRaw = colunas[10].replace("\"", "").trim();
                        Long imdb_id   = imdbRaw.isEmpty() ? 0L
                                : imdbRaw.replaceAll("[^0-9]", "").isEmpty() ? 0L
                                : Long.parseLong(imdbRaw.replaceAll("[^0-9]", ""));

                        double imdb_score      = Double.parseDouble(colunas[11].trim());
                        int    imdb_votes      = (int) Double.parseDouble(colunas[12].trim());
                        double tmdb_popularity = Double.parseDouble(colunas[13].trim());
                        double tmdb_score      = Double.parseDouble(colunas[14].trim());

                        ProgramaNetFlix programa = new ProgramaNetFlix(
                                titulo, show_type, descricao, generos,
                                production_countries, id, imdb_id,
                                release_year, age_certification, runtime,
                                temporadas, imdb_votes, imdb_score,
                                tmdb_score, tmdb_popularity
                        );

                        arvore.inserir(programa);
                        qtdInseridos++;

                    } catch (NumberFormatException e) {
                        qtdDescartados++;
                    }
                } else {
                    qtdDescartados++;
                }
            }

            System.out.println("\nLeitura finalizada!");
            System.out.println("Programas inseridos na árvore: " + qtdInseridos);
            System.out.println("Linhas descartadas (incompletas/inválidas): " + qtdDescartados);

        } catch (IOException e) {
            System.out.println("Erro ao encontrar ou ler o arquivo: " + e.getMessage());
        }
    }

    public static void salvarArquivo(ABB<ProgramaNetFlix> arvore, String nomeArquivo) {

        // Coleta todos os nós em em-ordem (ordem crescente de ID)
        List<ProgramaNetFlix> lista = new ArrayList<>();
        arvore.emOrdem(arvore.getRaiz(), lista);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo))) {

            // Cabeçalho idêntico ao arquivo original
            bw.write("id,title,type,description,release_year,age_certification," +
                     "runtime,genres,production_countries,seasons," +
                     "imdb_id,imdb_score,imdb_votes,tmdb_popularity,tmdb_score");
            bw.newLine();

            for (ProgramaNetFlix p : lista) {
                // Campos de texto entre aspas para tratar vírgulas internas
                StringBuilder sb = new StringBuilder();
                sb.append(csvCampo(p.getId())).append(",");
                sb.append(csvCampo(p.getTitulo())).append(",");
                sb.append(csvCampo(p.getShow_type())).append(",");
                sb.append(csvCampo(p.getDescricao())).append(",");
                sb.append(p.getRelease_year()).append(",");
                sb.append(csvCampo(p.getAge_certification())).append(",");
                sb.append(p.getRuntime()).append(",");
                sb.append(csvCampo(p.getGeneros())).append(",");
                sb.append(csvCampo(p.getProduction_countries())).append(",");
                sb.append(p.getTemporadas()).append(",");
                sb.append(p.getImdb_id() == 0 ? "" : "tt" + p.getImdb_id()).append(",");
                sb.append(p.getImdb_score()).append(",");
                sb.append(p.getImdb_votes()).append(",");
                sb.append(p.getTmdb_popularity()).append(",");
                sb.append(p.getTmdb_score());
                bw.write(sb.toString());
                bw.newLine();
            }

            System.out.println("\nArquivo salvo com sucesso: " + nomeArquivo);
            System.out.println("Total de registros gravados: " + lista.size());

        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
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

    private static String csvCampo(String valor) {
        if (valor == null) return "";
        if (valor.contains(",") || valor.contains("\"") || valor.contains("\n")) {
            return "\"" + valor.replace("\"", "\"\"") + "\"";
        }
        return valor;
    }
}