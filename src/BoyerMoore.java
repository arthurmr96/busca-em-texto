import javax.swing.JOptionPane;
import java.util.LinkedList;
import java.util.List;

public class BoyerMoore {
    static List<Integer> results = new LinkedList<Integer>();
    static int skip[] = new int[256];

    //calcula valor do jump
    public static void initSkip(String p) {
        int j, m = p.length();
        for (j = 0; j < 256; j++)
            skip[j] = m;
        for (j = 0; j < m; j++)
            skip[p.charAt(j)] = m - j - 1;
    }

    public static List<Integer> Pesquisar(String p, String t) {
        int i, j, a, m = p.length(), n = t.length();

        i = m - 1;
        j = m - 1;

        //realiza calculo do vetor de jump
        initSkip(p);
        while (j >= 0) {
            while (t.charAt(i) != p.charAt(j)) {
                //recebe valor de jump da chave
                a = skip[t.charAt(i)];
                i += (m - j > a) ? (m - j) : a;
                //se passou do tamanho do texto, retorna resultados encontrados
                if (i >= n)
                    return results;
                j = m - 1;
            }

            i--;
            j--;

            //se j = 0, quer dizer que bateu e encontrou
            if (j == 0) {
                results.add(i);
				i++;
            }

        }

        return results;
    }
}