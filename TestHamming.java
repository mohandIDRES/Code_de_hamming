package hamming ;
import java.util.*;

public class TestHamming {

	public static void main(String[] args) throws InterruptedException {
		Scanner c = new Scanner(System.in);
		
		System.out.println("QUE VOULEZ VOUS FAIRE ?  ");
		System.out.println("1 - Construire un mot de Hamming");
		System.out.println("2 - Verifier un mot");
		int choix = c.nextInt();
		

switch (choix) {
	case 1:
	Hamming a = new Hamming();

	a.codage(a);

		break;
	case 2 :
	Scanner sc = new Scanner(System.in);

	System.out.println("saisir le mot hamming (format binaire) a verifier qui contient soit 7 bits , 15 bits ou 31 bits");
	String s = sc.nextLine();
	for(int i=0;i<s.length();i++) {
		if(s.charAt(i)!='1' && s.charAt(i)!='0') {
			System.out.println("Impossible!!! il faut saisir une trame binaire!!");
			return;
		}
		}
	if(s.length()!=7 && s.length()!=15 && s.length()!=31 ) {
		System.out.println("Impossible!!! il faut saisir soit 7 bits , 15 bits ou 31 bits ");
		return;
	}

	Character tab[]=new Character[s.length()];
	for(int i=0;i<tab.length;i++) {
		tab[i]=s.charAt(i);
	}
	Hamming.verifier(tab); 

	default:
		break;
}
	

	}
}
