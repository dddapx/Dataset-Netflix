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

        scanner.close();
    }

    // Opção 1 — ler arquivo e montar a árvore
    private static void lerArquivo() {
        System.out.print("Digite o caminho/nome do arquivo CSV: ");
        String caminho = scanner.nextLine();
        GerenciadorDados.carregarArquivo(arvore, caminho);
    }

    // Opção 2 — sub-menu de análises (implementar nos dias 6-8)
    private static void analises() {
        // TODO: implementar sub-menu com as 5 análises (Dias 6-8)
        System.out.println("Análises ainda não implementadas.");
    }

    // Opção 3 — inserir novo programa (implementar no Dia 4)
    private static void inserir() {
        // TODO: coletar campos via Scanner e inserir na árvore (Dia 4)
        System.out.println("Inserção ainda não implementada.");
    }

    // Opção 4 — buscar programa por ID com contagem de comparações e tempo
    private static void buscar() {
        System.out.print("Digite o ID do programa (ex: ts123 ou tm456): ");
        String idBusca = scanner.nextLine().trim();

        // Objeto fantasma: só o id importa para o compareTo, os demais ficam vazios/zero
        ProgramaNetFlix fantasma = new ProgramaNetFlix(
            "", "", "", "", "",   // titulo, show_type, descricao, generos, production_countries
            idBusca,              // id (String, chave da BST)
            0L,                   // imdb_id
            0, "", 0, 0, 0,       // release_year, age_certification, runtime, temporadas, imdb_votes
            0.0, 0.0, 0.0         // imdb_score, tmdb_score, tmdb_popularity
        );

        // Zera o contador antes de buscar
        arvore.getE_ZeraContador();

        long tempoInicio = System.nanoTime();
        Node<ProgramaNetFlix> resultado = arvore.search(fantasma);
        long tempoFim = System.nanoTime();

        int comparacoes = arvore.getE_ZeraContador();

        if (resultado != null) {
            System.out.println("Programa encontrado:\n" + resultado.getValue().toString());
        } else {
            System.out.println("Programa com ID \"" + idBusca + "\" não encontrado.");
        }
        System.out.println("Tempo de busca: " + (tempoFim - tempoInicio) + " ns");
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
        // TODO: gravar em-ordem no CSV com nome fornecido pelo usuário (Dia 5)
        System.out.println("Salvamento ainda não implementado.");
    }

    // Opção 8 — liberar memória e encerrar
    private static void encerrar() {
        arvore.setRaiz(null); // libera toda a árvore
        System.out.println("Dados liberados. Encerrando...");
    }
}