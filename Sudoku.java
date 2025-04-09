import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Sudoku {
	private SudokuBoard board;
	public ColorImage boardImage = new ColorImage(500, 500, SudokuAux.branco);

	public Sudoku(String filename, double difficulty) {
		int[][] matrizTemporaria = new int[9][9];
		loadfiletoMatrix(filename);
		matrizTemporaria = this.board.getTabuleiro();
		matrizTemporaria = SudokuAux.gerarMatriz(matrizTemporaria, difficulty);
		this.board = new SudokuBoard(matrizTemporaria);
		SudokuAux.produzirImagem(this.boardImage, this.board.getTabuleiro());

	}

	// Métodos
	public void jogada(int linha, int coluna, int numero) {

		this.board.jogada(linha, coluna, numero);

		SudokuAux.produzirImagem(this.boardImage, this.board.getTabuleiro());
		boolean[] linhas = board.linhasnaoValidas();
		for (int j = 0; j < 9; j++) {
			if (linhas[j]) {
				SudokuAux.contornoLinha(this.boardImage, j);
			}

		}
		boolean[][] segmentos = board.segmentosnaoValidos();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (segmentos[i][j]) {
					SudokuAux.contornoSegmento(this.boardImage, j, i);
				}

			}

		}
		boolean[] colunas = board.colunasnaoValidas();
		for (int i = 0; i < 9; i++) {
			if (colunas[i]) {
				SudokuAux.contornoColuna(this.boardImage, i);
			}

		}

		if (board.sudokuConcluido()) {
			System.out.println("O Sudoku está completo");
		}

	}

	public void undo() {
		try {
			this.board.undo();
		} catch (IllegalStateException e) {
			return;
		}
		SudokuAux.produzirImagem(this.boardImage, this.board.getTabuleiro());
		boolean[] linhas = board.linhasnaoValidas();
		for (int j = 0; j < 9; j++) {
			if (linhas[j]) {
				SudokuAux.contornoLinha(this.boardImage, j);
			}

		}
		boolean[][] segmentos = board.segmentosnaoValidos();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (segmentos[i][j]) {
					SudokuAux.contornoSegmento(this.boardImage, j, i);
				}

			}

		}
		boolean[] colunas = board.colunasnaoValidas();
		for (int i = 0; i < 9; i++) {
			if (colunas[i]) {
				SudokuAux.contornoColuna(this.boardImage, i);
			}

		}
		if (board.sudokuConcluido()) {
			System.out.println("O Sudoku está completo");
		}
	}

	public void jogadaAleatoria() {
		this.board.jogadaAleatoria();
		SudokuAux.produzirImagem(this.boardImage, this.board.getTabuleiro());
		boolean[] linhas = board.linhasnaoValidas();
		for (int j = 0; j < 9; j++) {
			if (linhas[j]) {
				SudokuAux.contornoLinha(this.boardImage, j);
			}

		}
		boolean[][] segmentos = board.segmentosnaoValidos();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (segmentos[i][j]) {
					SudokuAux.contornoSegmento(this.boardImage, j, i);
				}

			}

		}
		boolean[] colunas = board.colunasnaoValidas();
		for (int i = 0; i < 9; i++) {
			if (colunas[i]) {
				SudokuAux.contornoColuna(this.boardImage, i);
			}

		}

		if (board.sudokuConcluido()) {
			System.out.println("O Sudoku está completo");
		}
	}

	public void redo() {
		try {
			this.board.redo();
		} catch (IllegalStateException e) {
			System.out.println(e);
			return;
		}
		SudokuAux.produzirImagem(this.boardImage, this.board.getTabuleiro());
		boolean[] linhas = board.linhasnaoValidas();
		for (int j = 0; j < 9; j++) {
			if (linhas[j]) {
				SudokuAux.contornoLinha(this.boardImage, j);
			}

		}
		boolean[][] segmentos = board.segmentosnaoValidos();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (segmentos[i][j]) {
					SudokuAux.contornoSegmento(this.boardImage, j, i);
				}

			}

		}
		boolean[] colunas = board.colunasnaoValidas();
		for (int i = 0; i < 9; i++) {
			if (colunas[i]) {
				SudokuAux.contornoColuna(this.boardImage, i);
			}

		}
		if (board.sudokuConcluido()) {
			System.out.println("O Sudoku está completo");
		}

	}

	public void reset() {
		this.board.resetTabuleiro();
		SudokuAux.produzirImagem(this.boardImage, this.board.gettabuleiroInicial());

	}

	// Grava o tabuleiro inicial e o estado atual para .sudgame
	public void gravar(String filename) {
		try {
			PrintWriter writer = new PrintWriter(new File(filename));
			writer.print(SudokuAux.matriztoString(this.board.gettabuleiroInicial()));
			writer.print("\n");
			writer.print(SudokuAux.matriztoString(this.board.getTabuleiro()));
			writer.close();
		}

		catch (FileNotFoundException e) {
			System.out.println("O ficheiro não pode ser escrito");
		}

	}

	// Dá load ao .sud
	private void loadfiletoMatrix(String filename) {
		int i = 0;
		int j = 0;
		int[][] matriz = new int[9][9];
		try {
			Scanner scanner = new Scanner(new File(filename));
			while (scanner.hasNextLine() && i < 9) {
				j = 0;
				while (scanner.hasNextInt() && j < 9) {
					int numero = scanner.nextInt();
					matriz[i][j] = numero;
					j++;
				}
				i++;
			}
			scanner.close();

		} catch (FileNotFoundException e) {
			System.out.println("Ficheiro não encontrado");
		}
		this.board = new SudokuBoard(matriz);
		SudokuAux.produzirImagem(this.boardImage, matriz);

	}

	// Dá load ao .sudgame dando display a matrizAtual e dando load a matrizInicial
	// e matrizAtual para casos que envolvam usar o reset.
	public void load(String filename) {

		int[][] matrizInicial = new int[9][9];
		int[][] matrizAtual = new int[9][9];
		try {
			Scanner scanner = new Scanner(new File(filename));
			for (int x = 0; x < 9; x++) {
				for (int y = 0; y < 9; y++) {
					int numero = scanner.nextInt();
					matrizInicial[x][y] = numero;
				}
			}
			for (int x = 0; x < 9; x++) {
				for (int y = 0; y < 9; y++) {
					int numero = scanner.nextInt();
					matrizAtual[x][y] = numero;

				}

			}

			scanner.close();

		} catch (FileNotFoundException e) {
			System.out.println("Ficheiro não encontrado");
		}
		this.board = new SudokuBoard(matrizInicial, matrizAtual);
		SudokuAux.produzirImagem(this.boardImage, matrizAtual);
		boolean[] linhas = board.linhasnaoValidas();
		for (int j = 0; j < 9; j++) {
			if (linhas[j]) {
				SudokuAux.contornoLinha(this.boardImage, j);
			}

		}
		boolean[][] segmentos = board.segmentosnaoValidos();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (segmentos[i][j]) {
					SudokuAux.contornoSegmento(this.boardImage, j, i);
				}

			}

		}
		boolean[] colunas = board.colunasnaoValidas();
		for (int i = 0; i < 9; i++) {
			if (colunas[i]) {
				SudokuAux.contornoColuna(this.boardImage, i);
			}

		}
		if (board.sudokuConcluido()) {
			System.out.println("O Sudoku está completo");
		}

	}
}
