import javax.swing.JOptionPane;
import java.util.LinkedList;
import java.util.List;

public class ForcaBruta {
	static List<Integer> results = new LinkedList<Integer>();

	public static List<Integer> Pesquisar(String p, String t) {

		int i, j, aux;
		int m = p.length();
		int n = t.length();
		for (i = 0; i < n; i++) {
			//auxiliar
			aux = i;
			for (j = 0; j < m && aux < n; j++) {
				//se o valor dos caracteres nao for igual, pula pro proximo
				if (t.charAt(aux) != p.charAt(j) && p.charAt(j) != '?')
					break;
				//se nao verifica a proxima posição do padrao
				aux++;
			}
			//se for igual, adiciona aos resultados encontrados
			if (j == m)
				results.add(i);
		}

		return results;
	}
}
