
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.rtf.RTFEditorKit;

class Main extends JFrame implements ActionListener {
	JTextPane texto; // componente que permite mudan�a de cores e outros atributos do texto
	JScrollPane painel; // painel com barra de rolagem

	JMenuBar menuBar = new JMenuBar();

	JMenu mArquivo = new JMenu("Arquivo");
	JMenuItem mNovo = new JMenuItem("Novo", KeyEvent.VK_N);
	JMenuItem mAbrir = new JMenuItem("Abrir", KeyEvent.VK_A);
	JMenuItem mSobre = new JMenuItem("Sobre", KeyEvent.VK_S);
	JMenuItem mSair = new JMenuItem("Sair", KeyEvent.VK_I);

	JMenu mPesquisar = new JMenu("Pesquisar");
	JMenuItem mKMP = new JMenuItem("KMP", KeyEvent.VK_K);
	JMenuItem mRabinKarp = new JMenuItem("Rabin-Karp", KeyEvent.VK_R);
	JMenuItem mBoyerMoore = new JMenuItem("Boyer-Moore", KeyEvent.VK_B);
	JMenuItem mForcaBruta = new JMenuItem("Força Bruta", KeyEvent.VK_F);

	public Main() {
		super("Trabalho Pratico II - Busca em Texto (2019/1)");

		mArquivo.setMnemonic(KeyEvent.VK_A);
		menuBar.add(mArquivo);

		mPesquisar.addActionListener(this);
		menuBar.add(mPesquisar);

		mSobre.setMnemonic(KeyEvent.VK_S);
		menuBar.add(mSobre);

		// Ações relativas ao arquivo
		mNovo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		mNovo.addActionListener(this);
		mArquivo.add(mNovo);

		mAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		mAbrir.addActionListener(this);
		mArquivo.add(mAbrir);

		mArquivo.addSeparator(); // separador

		mSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
		mSair.addActionListener(this);
		mArquivo.add(mSair);

		//Ações relativas à pesquisa
		mRabinKarp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		mRabinKarp.addActionListener(this);
		mPesquisar.add(mRabinKarp);

		mBoyerMoore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
		mBoyerMoore.addActionListener(this);
		mPesquisar.add(mBoyerMoore);

		mForcaBruta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		mForcaBruta.addActionListener(this);
		mPesquisar.add(mForcaBruta);

		mKMP.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
		mKMP.addActionListener(this);
		mPesquisar.add(mKMP);


		setJMenuBar(menuBar); // adiciona a barra de menus ao frame

		texto = new JTextPane();
		painel = new JScrollPane(texto);

		getContentPane().add(painel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setVisible(true);
		
		/*
		 * O c�digo abaixo mostra como colorir uma parte do texto
		 * com fundo amarelo, come�ando no 5� caractere com comprimento de 4 caracteres:
	    StyleConstants.setBackground(texto.getInputAttributes(), Color.YELLOW);
	    texto.getStyledDocument().setCharacterAttributes(5, 4, texto.getInputAttributes(), false);
	    */
	}

	public void pesquisar(String nomeTipoPesquisa) {
		try {
			PesquisarGUI pesquisarGUI = new PesquisarGUI(nomeTipoPesquisa, texto.getDocument().getText(0, texto.getDocument().getLength()), texto);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro ao pesquisar no arquivo.\n" + ex);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mSair) {
			System.exit(0);
		}

		if (e.getSource() == mNovo) {
			texto.setText("");
		}

		if (e.getSource() == mSobre) {
			JOptionPane.showMessageDialog(this,
					"BUSCA EM TEXTO\nversao 2019/1\n" +
							"Interface desenvolvida por: Arthur Mendonca e João Vitor Gomes.\n" +
							"FACULDADE COTEMIG (somente para fins didaticos)");
		}

		if (e.getSource() == mAbrir) {

			FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Documentos de texto simples", "rtf", "RTF", "txt", "TXT");
			JFileChooser chooser = new JFileChooser();

			chooser.setFileFilter(extensionFilter);
			int returnVal = chooser.showOpenDialog(this);

			try {
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String extension[] = chooser.getSelectedFile().getName().split("\\.");

					if (extension[extension.length - 1].toLowerCase().equals("rtf")) {
						texto.setContentType("text/rtf");
						texto.setEditorKit(new RTFEditorKit());
					} else {
						texto.setContentType("text/plain");
						texto.setText(texto.getText().replaceAll("\r", "")); // substitui quebra de linha padr�o "Windows" (\r\n) por somente (\n)
					}

					texto.read(new BufferedReader(new FileReader(chooser.getSelectedFile().getAbsoluteFile())), texto.getDocument());
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro ao abrir arquivo.\n" + ex);
			}
		}

		if (e.getSource() == mKMP || e.getSource() == mBoyerMoore || e.getSource() == mForcaBruta || e.getSource() == mRabinKarp) {
			pesquisar(e.getActionCommand());
		}
	}

	public static void main(String[] args) {
		new Main();
	}
}