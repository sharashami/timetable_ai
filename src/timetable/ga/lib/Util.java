package timetable.ga.lib;


import java.util.ArrayList;

import org.jgap.Gene;
import org.jgap.IChromosome;

public class Util {


	public static StringBuffer replaceCaracteres(int tBuffer, String str, StringBuffer buffer) {
	      if(str == null)str = "";
	      int indexChar = 0;
	      int tamanho = str.length();
	      int tamBuffer = tBuffer;
	      

	      for (int i = 0; i < tBuffer; i++) {
	         buffer.append(' ');
	      }
	      
	      char vetorChar[] = str.toCharArray();
	      for (int i = 0; i < (0 + tamanho) && i < tamBuffer ; i++) {
	    	  
	         buffer.setCharAt(i, vetorChar[indexChar]);
	         indexChar++;
	      }
	      return buffer;
	 }

	
	public static int valueGene(IChromosome c , int locus){
		return (Integer)(c.getGene(locus).getAllele());
	}

	public static int intAllele(Gene g){
		return (Integer)g.getAllele();
	}

	public static boolean possueRepeticao(IChromosome c1) {
		
		java.util.List<Integer> repeticoes = new ArrayList<Integer>();
		Gene[] c1Genes = c1.getGenes();
		for (int i = 0; i < c1Genes.length; i++) {
			if(repeticoes.contains(Util.intAllele(c1Genes[i]))){
				System.out.println("REPETIDO");
				return true;
			}else repeticoes.add(Util.intAllele(c1Genes[i]));
		}
		return false;
	}

	public static void verificarRepeticao(String antesDepois,IChromosome c1) {
		java.util.List<Integer> repeticoes = new ArrayList<Integer>();
		Gene[] c1Genes = c1.getGenes();
		System.out.println("teste repeticao");
		for (int i = 0; i < c1Genes.length; i++) {
			if(repeticoes.contains(Util.intAllele(c1Genes[i]))){
				System.out.println(antesDepois+ " Repetido");break;
			}else repeticoes.add(Util.intAllele(c1Genes[i]));
		}
	}
}
