class SudokuBoard {
	private int[][] tabuleiro;
	private int[][] tabuleiroInicial;
	private int[][][] jogadasAnteriores = new int[729][9][9]; // Acaba por definir a memória máxima do undo e o número
																// de
																// jogadas máximas.
	private int numerodaJogada = 0;
	private int numerodeUndo = 0;
	
    //Inicializar a board inicial e a atual.
	public SudokuBoard(int[][] boardInicial, int[][] boardAtual) {
		this(boardInicial);
		this.tabuleiro = boardAtual;

	}
	// Inicialziar a board inicial e e guarda também o estado inicial da boardInicial (pelo reset)
	public SudokuBoard(int[][] boardInicial) {
		if (!SudokuAux.sudokuValido(boardInicial)) {
			throw new IllegalArgumentException("Sudoku Inválido");
		}
		this.tabuleiro = boardInicial;
		this.tabuleiroInicial = copiarTabuleiro(boardInicial);
	}

	public int numerosNasCoordenadas(int linha, int coluna) {
		return this.tabuleiro[linha][coluna];
	}
    // Moves
	public void jogada(int linha, int coluna, int numero) {
		if (linha > 9 || coluna > 9 || linha < 0 || coluna < 0) {
			throw new IllegalArgumentException("O Sudoku tem tamanho 9x9");
		}
		if (this.tabuleiroInicial[linha][coluna] != 0) {
			throw new IllegalArgumentException("A Célula já está preenchida");
		}
		if (numero < 1 || numero > 9) {
			throw new IllegalArgumentException("O valor está fora do range (1 até 9)");
		}
		if (numerodaJogada >= 729) {
			throw new IllegalStateException(
					"Não é possível realizar mais jogadas, faça reset ou de load a uma board diferente");
		}
		//Copia as diferentes matrizes para o array3d jogadasAnteriores. (Por questões de undo e redo)
		this.jogadasAnteriores[numerodaJogada] = copiarTabuleiro(this.tabuleiro);
		this.numerodaJogada++;
		numerodeUndo = numerodaJogada;
		this.tabuleiro[linha][coluna] = numero;

	}

	public void resetTabuleiro() {
		// Reset para tabuleiro inicial
		numerodaJogada = 0;
		numerodeUndo = 0;
		this.tabuleiro = copiarTabuleiro(tabuleiroInicial);
	}
	public int[][] gettabuleiroInicial() {
		return this.tabuleiroInicial;
	}

	private int[][] copiarTabuleiro(int[][] fonte) {
		int numLinhas = fonte.length;
		int numColunas = fonte[0].length;
		int[][] copiar = new int[numLinhas][numColunas];
		for (int i = 0; i < numLinhas; i++) {
			for (int j = 0; j < numColunas; j++) {
				copiar[i][j] = fonte[i][j];

			}
		}
		return copiar;
	}

	public void jogadaAleatoria() {
		if (sudokuPreenchido()) {
			throw new IllegalStateException("Não é possível fazer mais jogadas aleatórias");
		}
		if (numerodaJogada >= 729) {
			throw new IllegalStateException(
					"Não é possível realizar mais jogadas, faça reset ou de load a uma board diferente");
		}
		this.jogadasAnteriores[numerodaJogada] = copiarTabuleiro(this.tabuleiro);
		this.numerodaJogada++;
		numerodeUndo = numerodaJogada;
		while (true) {
			int x = (int) (Math.random() * 9);
			int y = (int) (Math.random() * 9);
			int numero = (int) (Math.random() * 9) + 1;
			//Verifica se a posição esta disponivel para jogar
			if (this.tabuleiro[x][y] == 0) {
				this.tabuleiro[x][y] = numero;
				if (SudokuAux.segmentoValido(this.tabuleiro, (x / 3) * 3, (y / 3) * 3)) {
					break;
				} else {
					this.tabuleiro[x][y] = 0;
				}

			}

		}

	}

	public void undo() {
		if (numerodaJogada == 0) {
			throw new IllegalStateException("Já se encontra no tabuleiro inicial");
		}
		jogadasAnteriores[numerodaJogada] = this.tabuleiro;
		numerodaJogada--;
		this.tabuleiro = jogadasAnteriores[numerodaJogada];

	}

	public void redo() {
		if (numerodeUndo <= numerodaJogada) {
			throw new IllegalStateException("Já se encontra na jogada mais recente");
		}
		this.numerodaJogada++;
		this.tabuleiro = jogadasAnteriores[numerodaJogada];

	}
    // Validações
	public boolean[][] segmentosnaoValidos() {
		boolean[][] tresportres = new boolean[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (!SudokuAux.segmentoValido(this.tabuleiro, i * 3, j * 3)) {
					tresportres[i][j] = true;

				} else {
					tresportres[i][j] = false;

				}
			}
		}
		return tresportres;
	}

	public boolean[] linhasnaoValidas() {
		boolean[] linhas = new boolean[9];
		for (int i = 0; i < 9; i++) {
			if (!SudokuAux.linhaValida(this.tabuleiro, i)) {
				linhas[i] = true;
			} else {
				linhas[i] = false;
			}
		}
		return linhas;

	}

	public boolean[] colunasnaoValidas() {
		boolean[] colunas = new boolean[9];
		for (int i = 0; i < 9; i++) {
			if (!SudokuAux.colunaValida(this.tabuleiro, i)) {
				colunas[i] = true;
			} else {
				colunas[i] = false;
			}
		}
		return colunas;
	}

	public boolean sudokuConcluido() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (this.tabuleiro[i][j] == 0) {
					return false;
				}
			}
		}
		return SudokuAux.sudokuValido(this.tabuleiro);
	}

	public boolean sudokuPreenchido() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (this.tabuleiro[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	public int[][] getTabuleiro() {
		return this.tabuleiro;
	}


	public void imprimirSudokuBoard() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(tabuleiro[i][j] + " ");
			}
			System.out.println();
		}
	}
}
