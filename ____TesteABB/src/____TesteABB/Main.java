package ____TesteABB;

import java.util.List;
import java.util.Scanner;

public class Main{
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);
        ABB<ProgramaNetFlix> arvore = new ABB<>();
        int opcao;

        do{
            System.out.println("Menu:");
            System.out.println("1. Inserir programa");
            System.out.println("2. Exibir programas (pre-ordem)");
            System.out.println("3. Buscar programa por ID");
            System.out.println("4. Remover programa por ID");
            System.out.println("5. Buscar programas por gênero");
            System.out.println("6. Sla");
            System.out.println("7. Sla");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    // Lógica para inserir um programa
                    break;
                case 2:
                    // Lógica para exibir programas em pré-ordem
                    break;
                case 3:
                    // Lógica para buscar programa por ID
                    break;
                case 4:
                    // Lógica para remover programa por ID
                    break;
                case 5:
                    // Lógica para buscar programas por gênero
                    break;
                case 6:
                    break;
                case 7:
                    //
                    break;
                case 8:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Adicione um número de 1 a 8.");
            }
        } while (opcao != 8);
        
        //opção 1
        public void um(){

        }

        //opção 2
        public void dois(){
            System.out.println();
            int  = scanner.nextInt();

            switch(){
                case 1:
                    //
                    break;
                default:
                    System.out.println("Opção inválida. Adicione um número de 1 a 5.");
            }
        }

        //opção 3
        public void tres(){
            arvore.inserir();
        }

        //opção 4
        public void idBusca() {
            System.out.print("Digite o ID do programa para buscar: ");
            String idBusca = scanner.nextLine();
            
            //objeto 'fantasma'
            ProgramaNetFlix fantasmaBusca = new ProgramaNetFlix(idBusca, "", "", "", 0, "", 0, "", "", 0.0, "", 0.0, 0.0, 0.0, 0.0);
            
            //zera o contador de comparações
            arvore.getE_ZeraContador(); 
            
            //monitora o tempo (em nanossegundos) e faz a busca
            long tempoInicio = System.nanoTime();
            Node<ProgramaNetFlix> resultadoBusca = arvore.search(fantasmaBusca);
            long tempoFim = System.nanoTime();
            
            int totalComparacoes = arvore.getE_ZeraContador(); 
            
            if (resultadoBusca != null) {
                System.out.println("Programa Encontrado: " + resultadoBusca.getValue().toString());
            } else {
                System.out.println("Programa não encontrado na árvore.");
            }
            System.out.println("Tempo de execução da busca: " + (tempoFim - tempoInicio) + " ns");
            System.out.println("Comparações realizadas: " + totalComparacoes);
        }

        //opção 5
        public void idRemover() {
            System.out.print("Digite o ID do programa para remover: ");
            String idRemover = scanner.nextLine();
            
            //cria o objeto fantasma com o ID
            ProgramaNetFlix fantasmaRemover = new ProgramaNetFlix(idRemover, "", "", "", 0, "", 0, "", "", 0.0, "", 0.0, 0.0, 0.0, 0.0);
            
            //chama eliminar da árvore
            boolean foiRemovido = arvore.eliminar(fantasmaRemover);
            
            if (foiRemovido) {
                System.out.println("Programa com ID " + idRemover + " removido com sucesso!");
            } else {
                System.out.println("Falha na remoção: ID não encontrado.");
            }
        }

    //opção 6
    public void seis() {
        double[] somaPopularidade = new double[12];
        double[] somaScores = new double[15];
        int[] contadorFilmes = new int[15];

        System.out.print("Digite o gênero escolhido: ");
        String genero = scanner.nextLine();

        for (ProgramaNetFlix programa : arvore) {
            if (genero.equals(programa.getGenre())) {
                int releaseYear = programa.getRelease_year();
                int decadeIndex = releaseYear - 1900;
                somaPopularidade[decadeIndex] += programa.getTmdb_popularity();
                somaScores[decadeIndex] += programa.getImdb_score();
                contadorFilmes[decadeIndex]++
                
                for (int i = 0; i < somaScores.length; i++) {
                    somaScores[i] += programa.getScore(i);
                }
                //media de popularidade e scores por década
                for(int i = 0; i < somaScores.length; i++){
                    somaPopularidade[i] /= contadorFilmes[i];
                    somaScores[i] /= contadorFilmes[i];
                }
            }
        }

        List<ProgramaNetFlix> programasPorGenero = arvore.buscarPorGenero(genero);
        preOrdem(raiz, programasPorGenero);

        for (ProgramaNetFlix programa : programasPorGenero) {
            if(genres.contains(programa.getGenre())) {
                programa.getRelease_year();
                System.out.println(programa.getRelease_year() / 10 * 10 + programa.getTitle());
            }
        }
    }

    //opção 7
    public void sete() {
        // Lógica para opção 7
    }

    //opção 8
    public void oito() {
        System.out.println("Okay, Saindo");
        opcao = 0;
    }
}