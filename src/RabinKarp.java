import javax.swing.JOptionPane;
import java.util.LinkedList;
import java.util.List;

public class RabinKarp {
	static List<Integer> results = new LinkedList<Integer>();

	static final long q = 10014521L;
	static final int d = 128;

	public static List<Integer> Pesquisar(String p, String t) {
		long dm = 1, h1 = 0, h2 = 0;
		int i;
		int m = p.length();
		int n = t.length();
		if (n < m) // texto MENOR que o padrao
			return results;
		//calcula valor de cada chave do alfabeto
		for (i = 1; i < m; i++)
			dm = (d * dm) % q;
		//calcula hash
		for (i = 0; i < m; i++) {
			//hash do padrao
			h1 = (h1 * d + p.charAt(i)) % q;
			//hash do texto
			h2 = (h2 * d + t.charAt(i)) % q;
		}
		//re-hash
		for (i = 0; i < n - m; i++) {
			//se o hash for igual, padrao foi encontrado
			if(h1 == h2)
				results.add(i);
			h2 = (h2 + d * q - t.charAt(i) * dm) % q;
			h2 = (h2 * d + t.charAt(i + m)) % q;
		}

		return results;
	}
}