package manipuladorArquivos;

class SizeCalculator {
	static void totalSize(long tamanho) {
		System.out.println("Tamanho total do diretório: " + Convert.convertBytes(tamanho, true)); 
	}
	
	static void totalDeletedSize(long tamanhoApagado) {
		System.out.println("Tamanho total apagado: " + Convert.convertBytes(tamanhoApagado, true));
	}
	
	static void actualSize(long tamanho, long tamanhoApagado) {
		System.out.println("Tamanho total apagado: " + Convert.convertBytes((tamanho - tamanhoApagado), true));
	}
}
