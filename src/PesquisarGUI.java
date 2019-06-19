import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import javax.swing.*;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

class PesquisarGUI extends JDialog implements ActionListener {
	JPanel p1 = new JPanel(new GridLayout(4, 2));
	
	JLabel lb1 = new JLabel("Pesquisar por:  ");
	JCheckBox chkSubstituir = new JCheckBox("Substituir por:");
	JCheckBox chkCaseSensitive = new JCheckBox("Case-sensitive");
	JTextField txtPesq = new JTextField();
	JTextField txtSubs = new JTextField();
	JButton btOk = new JButton("Ok");
	JButton btCancel = new JButton("Cancelar");
	String tipoPesquisa, texto;
	List<Integer> results;
	JTextPane painelTexto;

	public PesquisarGUI(String nomeTipoPesquisa, String textoOriginal, JTextPane jTextPane) {
		painelTexto = jTextPane;
		tipoPesquisa = nomeTipoPesquisa;
		texto = textoOriginal;
		p1.add(lb1);
		lb1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		p1.add(txtPesq);
		p1.add(chkSubstituir);
		p1.add(txtSubs);
		p1.add(chkCaseSensitive);
		p1.add(new JLabel(""));
		p1.add(btOk);
		p1.add(btCancel);
		txtSubs.setEnabled(false);
		chkSubstituir.addActionListener(this);
		btOk.addActionListener(this);
		btCancel.addActionListener(this);
		getContentPane().add(p1, BorderLayout.CENTER);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setModal(true);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == chkSubstituir) {
			txtSubs.setEnabled(chkSubstituir.isSelected());
			txtSubs.setText("");
		}
		if(e.getSource() == btCancel) {
			this.dispose();
		}
		if(e.getSource() == btOk) {
			painelTexto.getStyledDocument().setCharacterAttributes(0, painelTexto.getDocument().getLength(), StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE), true);

			//se não for case sensitive, coloca em minusculo as palavras do texto pesquisado
			if(!chkCaseSensitive.isSelected()){
				texto = texto.toLowerCase();
			}
			//realiza pesquisa de acordo com o tipo selecionado
			if(tipoPesquisa == "KMP"){
				KMP kmp = new KMP();
				results = kmp.Pesquisar(txtPesq.getText(), texto);
			}
			if(tipoPesquisa == "Rabin-Karp"){
				RabinKarp rabinKarp = new RabinKarp();
				results = rabinKarp.Pesquisar(txtPesq.getText(), texto);
			}
			if(tipoPesquisa == "Boyer-Moore"){
				BoyerMoore boyerMoore = new BoyerMoore();
				results = boyerMoore.Pesquisar(txtPesq.getText(), texto);
			}
			if(tipoPesquisa == "Força Bruta"){
				ForcaBruta forcaBruta = new ForcaBruta();
				results = forcaBruta.Pesquisar(txtPesq.getText(), texto);
			}

			ListIterator<Integer> iteratorResults = results.listIterator();
			if(chkSubstituir.isSelected()){
				//se tiver sido selecionado a opção de substituição, substituir ocorrências
				painelTexto.setText(texto.replaceAll(txtPesq.getText(), txtSubs.getText()));
			}
			else{
				//se não, colore palavras encontradas de acordo com seu indice
				while(iteratorResults.hasNext()) {
					//indice do inicio da palavra encontrada
					int prox = iteratorResults.next();
					float brilho = new Random().nextFloat();

					//gera cor aleatoria com brilho não tão escuro
					Color randomColor = Color.getHSBColor(new Random().nextFloat(), new Random().nextFloat(), brilho < 0.5f ? 0.5f : brilho);
					StyleConstants.setBackground(painelTexto.getInputAttributes(), randomColor);
					//colore do indice de inicio da palavra + os indices subsequentes de acordo com o tamanho da palavra
					painelTexto.getStyledDocument().setCharacterAttributes(prox, txtPesq.getText().length(), painelTexto.getInputAttributes(), false);
				}
			}

			//fecha pop up
			this.dispose();
		}
	}
}