class SudokuAux {
	public static final Color preto = new Color(0, 0, 0);
	public static final Color branco = new Color(255, 255, 255);
	public static final Color vermelho = new Color(255, 0, 0);

	public static boolean sudokuValido(int[][] tabuleiro) {
		for (int i = 0; i < 9; i++) {
			if (!linhaValida(tabuleiro, i) || !colunaValida(tabuleiro, i)) {
				return false;
			}
		}
		for (int i = 0; i < 9; i = i + 3) {
			for (int j = 0; j < 9; j = j + 3) {
				if (segmentoValido(tabuleiro, i, j)) {
					return true;

				}

			}
		}
		return true;
	}

	// Coluna e linha válida (Validam a base de ver se todos os digitos entre 1 e 9 aparecem no máximo uma vez sendo que o 0 não conta.
	public static boolean linhaValida(int[][] tabuleiro, int linhas) {
		boolean[] linha = new boolean[9];
		for (int i = 0; i < 9; i++) {
			//Itera sobre a linha do tabuleiro
			int number = tabuleiro[linhas][i];
			if (number != 0) {
				//Se o valor for 0 esse indice não interessa (Repetição de 0 é válida)
				if (linha[number - 1]) {
					return false; 
				//Vê se o número já apareceu no array linha, se aparecer o método devolve falso o que implica repetição.
				}
				linha[number - 1] = true;
			}
		}

		return true;
	}

	public static boolean colunaValida(int[][] tabuleiro, int colunas) {
		boolean[] coluna = new boolean[9];
		for (int i = 0; i < 9; i++) {
			int number = tabuleiro[i][colunas];
			if (number != 0) {
				if (coluna[number - 1]) {
					return false; // repetido
				}
				coluna[number - 1] = true;
			}
		}
		return true;
	}

	// segmentoValido (Faz o mesmo mas apenas para 3x3 dando a linha inicial e a coluna inicial)

	public static boolean segmentoValido(int[][] tabuleiro, int linhaIni, int colunaIni) {
		boolean[] tresxtres = new boolean[9];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int number = tabuleiro[linhaIni + i][colunaIni + j];
				if (number != 0) {
					if (tresxtres[number - 1]) {
						return false; // repetido bloco
					}
					tresxtres[number - 1] = true;
				}

			}

		}
		return true;
	}
         //Função que leva a matriz resolvida e a dificuldade.
	public static int[][] gerarMatriz(int[][] solucaoMatriz, double percentagemdeZeros) {
		int[][] tabuleiro = new int[9][9];
		if (percentagemdeZeros < 0 || percentagemdeZeros > 1) {
			throw new IllegalArgumentException("A percentagem deverá estar entre 0 e 1");
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				double rngZeros = Math.random(); // Número random entre 0 e 1;
				//Quanto menor a percentagemdeZeros maior a dificuldade.
				if (percentagemdeZeros > rngZeros) {
					tabuleiro[i][j] = 0; // Aqui substitui os zeros.
				} else {
					//Senão mante mos numeros iniciais
					tabuleiro[i][j] = solucaoMatriz[i][j];
				}
			}
		}
		return tabuleiro;

	}

	// String para matriz, cria matriz vazia e designa os numeros da posição da matriz para esses espaços.
	public static String matriztoString(int[][] matriz) {
		String mtoString = "";
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				mtoString += matriz[i][j] + " "; // Espaço entre as aspas para espaçar o txt
			}
			mtoString += "\n";
		}
		return mtoString;
	}
	// produzir imagem

	public static void produzirImagem(ColorImage imagem, int[][] tabuleiro) {
		desenharTabuleiro(imagem);
		desenharNumeros(imagem, tabuleiro);
	}

	public static void desenharNumeros(ColorImage imagem, int[][] tabuleiro) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int numero = tabuleiro[i][j];
				alterarNumeros(imagem, j, i, numero);
			}
		}
	}

	private static void desenharTabuleiro(ColorImage imagem) {
		for (int i = 0; i <= 9; i++) {
			desenharLinhaHorizontal(imagem, i * (imagem.getWidth() - 1) / 9, preto);
		}
		for (int i = 0; i <= 9; i++) {
			desenharLinhaVertical(imagem, i * (imagem.getWidth() - 1) / 9, preto);
		}
		for (int i = 0; i <= 3; i++) {
			desenharLinhaVerticalSegmento(imagem, i * (imagem.getWidth() - 1) / 3, preto);
			desenharLinhaHorizontalSegmento(imagem, i * (imagem.getWidth() - 1) / 3, preto);
		}

	}

	public static int[][] alterarNumeros(int[][] tabuleiro, int x, int y, int numero) {
		tabuleiro[x][y] = numero;
		return tabuleiro;
	}

	public static void alterarNumeros(ColorImage imagem, int x, int y, int numero) {
		if (numero > 9 && numero <= 0) {
			throw new IllegalArgumentException("Tem que colocar um número entre 0 e 9");
		}
		int tamanhoCelula = (imagem.getWidth() - 1) / 9;
		int fontSize = tamanhoCelula / 2;
		//Centrar números. X*tamanhoCelula vai buscar a coluna da celula onde queremos desenhar, ou seja distancia da parte mais a esquerda da celula inicial até a parte mais a esquerda da celula final.
		//tamanhoCelula/2 calcula o centro.
		int x1 = x * tamanhoCelula + tamanhoCelula / 2;
		int y1 = y * tamanhoCelula + tamanhoCelula / 2;
        //Itera sobre os pixeis da posição da celula e mete tudo a branco incluindo as linhas.
		//Multiplico primeiro antes de dividir para evitar erros de arredondamento
		for (int i = (imagem.getWidth() - 1) * x / 9; i < (imagem.getWidth() - 1) * (x + 1) / 9; i++) {
			for (int j = (imagem.getHeight() - 1) * y / 9; j < (imagem.getHeight() - 1) * (y + 1) / 9; j++) {
				imagem.setColor(i, j, branco);

			}

		}
		//Volta a desenhar o tabuleiro e substitui o numero.
		desenharTabuleiro(imagem);
		if (numero != 0) {

			imagem.drawCenteredText(x1, y1, String.valueOf(numero), fontSize, preto);

		}

	}

	// Para as linhas horizontais entre os segmentos
	private static void desenharLinhaHorizontal(ColorImage imagem, int y, Color color) {
		int width = imagem.getWidth();
		for (int x = 0; x < width; x++) {
			imagem.setColor(x, y, color);
		}
	}

	// Para as colunas verticais entre os segmentos
	private static void desenharLinhaVertical(ColorImage imagem, int x, Color color) {
		int height = imagem.getHeight();
		for (int y = 0; y < height; y++) {
			imagem.setColor(x, y, color);
		}

	}

	// Para desenhar os contornos.
	private static void desenharLinhaVerticalContorno(ColorImage imagem, int x, int Ymin, int Ymax, Color color) {
		for (int y = Ymin; y < Ymax; y++) {
			imagem.setColor(x, y, color);
		}
	}

	private static void desenharLinhaHorizontalContorno(ColorImage imagem, int y, int Xmin, int Xmax, Color color) {

		for (int x = Xmin; x < Xmax; x++) {
			imagem.setColor(x, y, color);
		}
	}
	// Contornos mais grossos (distinguir segmentos e erros)

	private static void desenharLinhaVerticalSegmento(ColorImage imagem, int x, Color color) {
		int Xmin = x - 1;
		int Xmax = x + 1;
		if (Xmin < 0)
			Xmin = 0;
		if (Xmax > imagem.getWidth() - 1)
			Xmax = imagem.getWidth() - 1;

		for (int i = Xmin; i <= Xmax; i++) {
			for (int j = 0; j < imagem.getHeight(); j++) {
				imagem.setColor(i, j, color);
			}

		}
	}

	private static void desenharLinhaHorizontalSegmento(ColorImage imagem, int y, Color color) {
		int Ymin = y - 1;
		int Ymax = y + 1;
		if (Ymin < 0)
			Ymin = 0;
		if (Ymax > imagem.getHeight() - 1)
			Ymax = imagem.getHeight() - 1;
		for (int i = Ymin; i <= Ymax; i++) {
			for (int j = 0; j < imagem.getWidth(); j++) {
				imagem.setColor(j, i, color);

			}
		}

	}

	// Contorno dos erros

	public static void contornoLinha(ColorImage imagem, int y) {
		int Ymin = (imagem.getHeight() - 1) * y / 9;
		int Ymax = (imagem.getHeight() - 1) * (y + 1) / 9;
		desenharLinhaVerticalContorno(imagem, 0, Ymin, Ymax, vermelho);
		desenharLinhaVerticalContorno(imagem, imagem.getWidth() - 1, Ymin, Ymax, vermelho);
		desenharLinhaHorizontalSegmento(imagem, Ymin, vermelho);
		desenharLinhaHorizontalSegmento(imagem, Ymax, vermelho);

	}

	public static void contornoColuna(ColorImage imagem, int x) {
		int Xmin = (imagem.getWidth() - 1) * x / 9;
		int Xmax = (imagem.getWidth() - 1) * (x + 1) / 9;
		desenharLinhaVerticalSegmento(imagem, Xmin, vermelho);
		desenharLinhaVerticalSegmento(imagem, Xmax, vermelho);
		desenharLinhaHorizontalContorno(imagem, 0, Xmin, Xmax, vermelho);
		desenharLinhaHorizontalContorno(imagem, imagem.getHeight() - 1, Xmin, Xmax, vermelho);
	}

	public static void contornoSegmento(ColorImage imagem, int x, int y) {
		int Xmin = (imagem.getWidth() - 1) * x * 3 / 9;
		int Xmax = (imagem.getWidth() - 1) * (x + 1) * 3 / 9;
		int Ymin = (imagem.getHeight() - 1) * y * 3 / 9;
		int Ymax = (imagem.getHeight() - 1) * (y + 1) * 3 / 9;
		desenharLinhaVerticalSegmento(imagem, Xmin, vermelho);
		desenharLinhaVerticalSegmento(imagem, Xmax, vermelho);
		desenharLinhaHorizontalSegmento(imagem, Ymin, vermelho);
		desenharLinhaHorizontalSegmento(imagem, Ymax, vermelho);

	}
}
