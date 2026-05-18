package ____TesteABB;

import java.util.Scanner;

// Autores: [DAVI BARROS - 10385766] | [Guilherme Miyamoto Bragatto - 10736124]
public class Main {

    // Campos static para que todos os métodos consigam acessar sem passar por parâmetro
    private static Scanner scanner = new Scanner(System.in);
    private static ABB<ProgramaNetFlix> arvore = new ABB<>();
    private static int opcao;

    public static void main(String[] args) {

        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Ler dados de arquivo");
            System.out.println("2. Análises de dados");
            System.out.println("3. Inserir programa");
            System.out.println("4. Buscar programa por ID");
            System.out.println("5. Remover programa por ID");
            System.out.println("6. Exibir altura da árvore");
            System.out.println("7. Salvar dados em arquivo");
            System.out.println("8. Encerrar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: lerArquivo();   break;
                case 2: analises();     break;
                case 3: inserir();      break;
                case 4: buscar();       break;
                case 5: remover();      break;
                case 6: altura();       break;
                case 7: salvar();       break;
                case 8: encerrar();     break;
                default:
                    System.out.println("Opção inválida. Digite um número de 1 a 8.");
            }

        } while (opcao != 8);

    }

    private static int lerInteiroSeguro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Por favor, digite um número inteiro.");
            }
        }
    }

    private static double lerDoubleSeguro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Por favor, digite um número (use ponto para decimais).");
            }
        }
    }

    // Opção 1 — ler arquivo e montar a árvore
    private static void lerArquivo() {
        System.out.print("Digite o caminho/nome do arquivo CSV: ");
        String caminho = scanner.nextLine();
        GerenciadorDados.carregarArquivo(arvore, caminho);
    }

    // Opção 2 — sub-menu de análises (implementar nos dias 6-8)
    private static void analises() { // <-- Parâmetro int removido aqui
        int opcaoAnalise; // <-- Variável declarada para corrigir os erros vermelhos
        
        do {
            System.out.println("\n=== ANÁLISES ESTATÍSTICAS ===");
            System.out.println("1. Achados (TV-14 e Crime) - [Percurso: Em Ordem]");
            System.out.println("2. Auge do Gênero (Menores tmdb_score) - [Percurso: Pré-Ordem]");
            System.out.println("3. Países Virais (Filmes por país com nota > X) - [Percurso: Pós-Ordem]");
            System.out.println("4. Clássicos (Top N Mais Antigos) - [Percurso: Em Nível]");
            System.out.println("5. Maratona (Séries com mais temporadas) - [Percurso: Em Ordem]");
            System.out.println("6. Voltar ao Menu Principal");
            opcaoAnalise = lerInteiroSeguro("Escolha uma opção de análise: ");

            switch (opcaoAnalise) {
                case 1: achados(); break;
                case 2: augeGenero(); break;
                case 3: paisesVirais(); break; // <-- Adicionados aqui para remover os avisos amarelos
                case 4: classicos(); break;
                case 5: maratona(); break;
                case 6: System.out.println("A regressar ao menu principal..."); break;
                default: System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcaoAnalise != 6);
    }

    // 1ª Estatística - Percurso: Em Ordem 
    private static void achados() {
        java.util.List<ProgramaNetFlix> lista = new java.util.ArrayList<>();
        
        arvore.emOrdem(arvore.getRaiz(), lista); 
        
        System.out.println("\n--- Top 10 títulos (TV-14 e Crime) ---");
        int count = 0;
        for (ProgramaNetFlix p : lista) {
            if ("TV-14".equals(p.getAge_certification()) && p.getGeneros().toLowerCase().contains("crime")) {
                System.out.println("- " + p.getTitulo() + " (ID: " + p.getId() + ")");
                count++;
                if (count == 10) break; 
            }
        }
        if (count == 0) {
            System.out.println("Nenhum programa encontrado com estes critérios.");
        }
    }

    // 2ª Estatística - Percurso: Pré-Ordem
    private static void augeGenero() {
        int n = lerInteiroSeguro("\nQuantos títulos com os menores tmdb_score deseja visualizar? (Deve ser > 5): ");
        
        if (n <= 5) {
            System.out.println("Atenção: O número deve ser estritamente maior que 5, conforme as diretrizes.");
            return;
        }
        
        java.util.List<ProgramaNetFlix> lista = new java.util.ArrayList<>();
        arvore.preOrdem(arvore.getRaiz(), lista); 
        
        lista.removeIf(p -> p.getTmdb_score() == 0.0);
        lista.sort(java.util.Comparator.comparingDouble(ProgramaNetFlix::getTmdb_score));
        
        System.out.println("\n--- Top " + n + " títulos com os piores scores no TMDB ---");
        for (int i = 0; i < Math.min(n, lista.size()); i++) {
            ProgramaNetFlix prog = lista.get(i);
            System.out.println((i+1) + ". " + prog.getTitulo() + " | Score TMDB: " + prog.getTmdb_score() + " | Tipo: " + prog.getShow_type());
        }
    }

    private static void paisesVirais() {
    System.out.print("\nDigite a sigla do país de produção (ex: US, BR): ");
    String pais = scanner.nextLine().trim().toUpperCase();
    double notaMinima = lerDoubleSeguro("Digite a nota mínima do IMDB desejada (ex: 7.5): ");

    java.util.List<ProgramaNetFlix> lista = new java.util.ArrayList<>();
    arvore.posOrdem(arvore.getRaiz(), lista); // Requisito: Percurso Pós-Ordem

    System.out.println("\n--- Filmes (" + pais + ") com IMDB > " + notaMinima + " ---");
    int count = 0;
    for (ProgramaNetFlix p : lista) {
        if ("MOVIE".equalsIgnoreCase(p.getShow_type()) && 
            p.getProduction_countries().contains(pais) && 
            p.getImdb_score() > notaMinima) {
            System.out.println("- " + p.getTitulo() + " | Nota IMDB: " + p.getImdb_score());
            count++;
        }
    }
    if (count == 0) System.out.println("Nenhum filme encontrado com estas características.");
}

private static void classicos() {
    int n = lerInteiroSeguro("\nQuantos títulos antigos deseja visualizar? (N > 5): ");
    if (n <= 5) {
        System.out.println("Pelas regras da análise, o número deve ser estritamente maior que 5.");
        return;
    }

    java.util.List<ProgramaNetFlix> lista = new java.util.ArrayList<>();
    arvore.emNivel(lista); // Requisito: Percurso em Largura / Nível

    // Ordena do mais antigo para o mais recente
    lista.sort(java.util.Comparator.comparingInt(ProgramaNetFlix::getRelease_year));

    System.out.println("\n--- Top " + n + " Títulos Mais Antigos (Clássicos) ---");
    for (int i = 0; i < Math.min(n, lista.size()); i++) {
        ProgramaNetFlix p = lista.get(i);
        System.out.println((i+1) + ". " + p.getTitulo() + " (" + p.getRelease_year() + ")");
    }
}

private static void maratona() {
    int minTemporadas = lerInteiroSeguro("\nNúmero mínimo de temporadas: ");

    java.util.List<ProgramaNetFlix> lista = new java.util.ArrayList<>();
    arvore.emOrdem(arvore.getRaiz(), lista);

    System.out.println("\n--- Séries com pelo menos " + minTemporadas + " temporadas ---");
    for (ProgramaNetFlix p : lista) {
        if ("SHOW".equalsIgnoreCase(p.getShow_type()) && p.getTemporadas() >= minTemporadas) {
            System.out.println("- " + p.getTitulo() + " (" + p.getTemporadas() + " temporadas)");
        }
    }
}

    // Opção 3 — inserir novo programa (implementar no Dia 4)
    private static void inserir() {
        System.out.println("\n--- Inserir Novo Programa ---");
    
    System.out.print("Título: ");
    String titulo = scanner.nextLine().trim();
    
    System.out.print("Tipo (SHOW ou MOVIE): ");
    String tipo = scanner.nextLine().trim().toUpperCase();
    while(!tipo.equals("SHOW") && !tipo.equals("MOVIE")) {
        System.out.print("Tipo inválido. Digite obrigatoriamente SHOW ou MOVIE: ");
        tipo = scanner.nextLine().trim().toUpperCase();
    }
    
    // Geração do ID exigido: ts + numero único ou tm + numero único
    long numUnico = System.currentTimeMillis() % 1000000; 
    String idGerado = (tipo.equals("SHOW") ? "ts" : "tm") + numUnico;
    
    System.out.print("Descrição: ");
    String descricao = scanner.nextLine().trim();
    
    System.out.print("Gêneros (ex: [\"comedy\", \"drama\"]): ");
    String generos = scanner.nextLine().trim();
    
    System.out.print("Países de Produção (ex: [\"US\"]): ");
    String paises = scanner.nextLine().trim();
    
    int ano = lerInteiroSeguro("Ano de Lançamento: ");
    
    System.out.print("Classificação Etária (ex: TV-14, R): ");
    String age = scanner.nextLine().trim();
    
    int runtime = lerInteiroSeguro("Duração (minutos): ");
    int temporadas = (tipo.equals("SHOW")) ? lerInteiroSeguro("Número de Temporadas: ") : 0;
    
    long imdb_id = (long) lerInteiroSeguro("ID numérico do IMDB (somente números): ");
    double imdb_score = lerDoubleSeguro("Nota do IMDB (utilize ponto ou vírgula conforme o seu sistema regional): ");
    int imdb_votes = lerInteiroSeguro("Quantidade total de votos do IMDB: ");
    double tmdb_score = lerDoubleSeguro("Nota do TMDB: ");
    double tmdb_pop = lerDoubleSeguro("Popularidade TMDB: ");

    ProgramaNetFlix novo = new ProgramaNetFlix(
        titulo, tipo, descricao, generos, paises, idGerado, imdb_id,
        ano, age, runtime, temporadas, imdb_votes, imdb_score, tmdb_score, tmdb_pop
    );

    arvore.inserir(novo);
    System.out.println("\nPrograma inserido com sucesso na árvore! O ID gerado para este registo foi: " + idGerado);
}
    // Opção 4 — buscar programa por ID com contagem de comparações e tempo
    private static void buscar() {
        System.out.print("Digite o ID do programa (ex: ts123 ou tm456): ");
        String idBusca = scanner.nextLine().trim();
 
        ProgramaNetFlix fantasma = new ProgramaNetFlix(
            "", "", "", "", "", idBusca, 0L, 0, "", 0, 0, 0, 0.0, 0.0, 0.0
        );
 
        arvore.getE_ZeraContador();
        long tempoInicio = System.nanoTime();
        Node<ProgramaNetFlix> resultado = arvore.search(fantasma);
        long tempoFim = System.nanoTime();
        int comparacoes = arvore.getE_ZeraContador();
 
        if (resultado != null) {
            ProgramaNetFlix p = resultado.getValue();
            System.out.println("\n--- Programa encontrado ---");
            System.out.println("ID:              " + p.getId());
            System.out.println("Título:          " + p.getTitulo());
            System.out.println("Tipo:            " + p.getShow_type());
            System.out.println("Ano:             " + p.getRelease_year());
            System.out.println("Certificação:    " + p.getAge_certification());
            System.out.println("Gêneros:         " + p.getGeneros());
            System.out.println("Países:          " + p.getProduction_countries());
            System.out.println("Runtime:         " + p.getRuntime() + " min");
            System.out.println("Temporadas:      " + p.getTemporadas());
            System.out.println("IMDB Score:      " + p.getImdb_score());
            System.out.println("IMDB Votes:      " + p.getImdb_votes());
            System.out.println("TMDB Score:      " + p.getTmdb_score());
            System.out.println("TMDB Popularity: " + p.getTmdb_popularity());
            System.out.println("Descrição:       " + p.getDescricao());
        } else {
            System.out.println("Programa com ID \"" + idBusca + "\" não encontrado.");
        }
        System.out.printf("Tempo de busca:         %d ns (%.4f ms)%n",
                (tempoFim - tempoInicio), (tempoFim - tempoInicio) / 1_000_000.0);
        System.out.println("Comparações realizadas: " + comparacoes);
    }

    // Opção 5 — remover programa por ID
    private static void remover() {
        System.out.print("Digite o ID do programa a remover: ");
        String idRemover = scanner.nextLine().trim();

        // Objeto fantasma com o id a remover
        ProgramaNetFlix fantasma = new ProgramaNetFlix(
            "", "", "", "", "",
            idRemover,
            0L,
            0, "", 0, 0, 0,
            0.0, 0.0, 0.0
        );

        boolean removido = arvore.eliminar(fantasma);

        if (removido) {
            System.out.println("Programa \"" + idRemover + "\" removido com sucesso.");
        } else {
            System.out.println("ID \"" + idRemover + "\" não encontrado na árvore.");
        }
    }

    // Opção 6 — exibir altura da árvore
    private static void altura() {
        System.out.println("Altura atual da árvore BST: " + arvore.getAltura());
    }

    // Opção 7 — salvar dados em arquivo (implementar no Dia 5)
    private static void salvar() {
        System.out.print("\nDigite o nome do ficheiro para a gravação (ex: netflix_atualizado.csv): ");
        String nomeFicheiro = scanner.nextLine().trim();
        
        if(nomeFicheiro.isEmpty()) {
            System.out.println("Nome de ficheiro não pode ser vazio.");
            return;
        }
    
    // Chama o método disponível no ficheiro GerenciadorDados
    GerenciadorDados.salvarArquivo(arvore, nomeFicheiro);
}

    // Opção 8 — liberar memória e encerrar
    private static void encerrar() {
        arvore.setRaiz(null); // libera toda a árvore
        System.out.println("Dados liberados. Encerrando...");
    }
}