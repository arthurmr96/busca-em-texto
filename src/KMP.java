import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class KMP {
    static int next[] = new int[1000];
    static List<Integer> results = new LinkedList<Integer>();

    //calcula os valores do vetor next
    public static void initNext(String p) {
        int i = 0, j = -1, m = p.length();
        next[0] = -1;
        while (i < m) {
            while (j >= 0 && p.charAt(i) != p.charAt(j)) {
                j = next[j];
            }

            i++;
            j++;

            next[i] = j;
        }
    }

    public static List<Integer> Pesquisar(String p, String t) {
        int i = 0, j = 0, m = p.length(), n = t.length();
        initNext(p);

        while (i < n) {
            //se caracter for igual, incrementa mais um para pegar padrao
			if (t.charAt(i) == p.charAt(j))
			{
				j++;
				i++;
			}
            //se valor da chave for igual em ambas pesquisas, adiciona resultado e vai ao next
			if (j == m) {
				j = next[j];
				results.add(i - m);
			}
			else{
				while (i < n && j >= 0 && t.charAt(i) != p.charAt(j)) {
					if (j != 0){
						j = next[j];
					}
					else {
						i += 1;
					}
				}
			}
        }

        return results;

    }

	/*
    public static void main(String args[]) {
		String t = "abracadababra";
		String p = "abracadabra";

		initNext(p);

		int pos = KMPSearch(p, t);

		if (pos == -1)
			JOptionPane.showMessageDialog(null, "Texto: " + t + "\nPadr�o: "
					+ p + "\nPadr�o nao encontrado!");
		else
			JOptionPane.showMessageDialog(null, "Texto: " + t + "\nPadr�o: "
					+ p + "\nPadr�o encontrado na posi��o: " + pos + ".");
	}*/
}