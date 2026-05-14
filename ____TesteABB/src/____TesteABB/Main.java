package ____TesteABB;

import java.util.Scanner;

public class Main{
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);
        //opão 4
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

        //opção 5
        System.out.print("Digite o ID do programa para remover: ");
        String idRemover = scanner.nextLine();
        
        // 1. Cria o objeto fantasma com o ID digitado
        ProgramaNetFlix fantasmaRemover = new ProgramaNetFlix(idRemover, "", "", "", 0, "", 0, "", "", 0.0, "", 0.0, 0.0, 0.0, 0.0);
        
        // 2. Chama o método de eliminar da árvore
        boolean foiRemovido = arvore.eliminar(fantasmaRemover);
        
        // 3. Verifica o resultado
        if (foiRemovido) {
            System.out.println("Programa com ID " + idRemover + " removido com sucesso!");
        } else {
            System.out.println("Falha na remoção: ID não encontrado.");
        }
    }
}