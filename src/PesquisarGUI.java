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

			if(!chkCaseSensitive.isSelected()){
				texto = texto.toLowerCase();
			}
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
				boyerMoore.Pesquisar(txtPesq.getText(), texto);
				results = boyerMoore.Pesquisar(txtPesq.getText(), texto);
			}
			if(tipoPesquisa == "Força Bruta"){
				ForcaBruta forcaBruta = new ForcaBruta();
				results = forcaBruta.Pesquisar(txtPesq.getText(), texto);
			}

			ListIterator<Integer> iteratorResults = results.listIterator();
			if(chkSubstituir.isSelected()){
				painelTexto.setText(texto.replaceAll(txtPesq.getText(), txtSubs.getText()));
			}
			else{
				while(iteratorResults.hasNext()) {
					int prox = iteratorResults.next();
					float brilho = new Random().nextFloat();

					Color randomColor = Color.getHSBColor(new Random().nextFloat(), new Random().nextFloat(), brilho < 0.5f ? 0.5f : brilho);
					StyleConstants.setBackground(painelTexto.getInputAttributes(), randomColor);
					painelTexto.getStyledDocument().setCharacterAttributes(prox, txtPesq.getText().length(), painelTexto.getInputAttributes(), false);
				}
			}

			this.dispose();
		}
	}
}