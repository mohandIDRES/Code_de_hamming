
package hamming;

import java.util.*;

public class Hamming {

	private String trame;
	private int taille;



	/* Le constructeur de notre classe on instacie notre trame a la trame saisi */
	public Hamming() {
		System.out.println("saisir la trame binaire qui contient soit 4 bits , 11 bits ou 26 bits !");
		Scanner sc = new Scanner(System.in);
		this.trame = sc.nextLine();
		this.taille = this.trame.length();
		for (int i = 0; i < this.taille; i++) {
			if (this.trame.charAt(i) != '1' && this.trame.charAt(i) != '0') {
				System.out.println("Impossible!!! il faut saisir une trame binaire!!");
				System.exit(-1);
				;
			}
		}
		if (this.taille != 4 && this.taille != 11 && this.taille != 26) {
			System.out.println("Impossible!!! il faut saisir soit 4 bits , 11 bits ou 26 bits ");
			System.exit(-1);
			;
		}
	}

	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}

	public String getTrame() {
		return trame;
	}

	public void setTrame(String trame) {
		this.trame = trame;
	}

	/* cette méthode return le mot de hamming avec ces bits de parités */
	public Character[] codage(Hamming trame) throws InterruptedException {

		int bitParite = 0; // nombre de bits de parité du mot hamming

		if (trame.getTaille() == 4) { // si la taille de notre trame saisi est 4 alors on aura
			bitParite = 3; // 3 bits de parités
			trame.setTaille(trame.getTaille() + bitParite); // la taille de la nouvelle trame!

		} else {
			if (trame.getTaille() == 11) { // si la taille de notre trame saisi est 11 alors on aura
				bitParite = 4; // 4 bits de parités
				trame.setTaille(trame.getTaille() + bitParite); // la taille de la nouvelle trame!

			} else {
				if (trame.getTaille() == 26) { // si la taille de notre trame saisi est 4 alors on aura
					bitParite = 5; // 5 bits de parité
					trame.setTaille(trame.getTaille() + bitParite); // la taille de la nouvelle trame!
				}
			}
		}

		// dans ce tableau tabBitParite on insert les bits de parités inconnu (on ne connait pas
		// encore leurs valeurs).
		Character tabBitParite[] = new Character[bitParite];
		for (int i = 0; i < bitParite; i++) {
			tabBitParite[i] = 'C';

		}

		// dans ce tableau de character tab[] on insert le mot de hamming resultant a la
		// fin
		Character tab[] = new Character[trame.getTaille()];
		// dans ce tableau de character tra[] on insert la trame saisi au debut
		Character tra[] = new Character[trame.getTrame().length()];

		for (int w = 0; w < trame.getTrame().length(); w++) {
			tra[w] = trame.getTrame().charAt(w);

		}

		/*
		 * dans ce tableau d'entiers emplacement[] on insert les emplacement des codes
		 * de parités a ajouter pour la trame saisi 1, 2 ,4 ,8 16
		 */
		int emplacement[] = new int[bitParite];
		for (int j = 0; j < bitParite; j++) {
			emplacement[j] = (int) Math.pow(2, j);
		}

		int i = tab.length - 1;
		int j = 0;
		// int w=0;

		while (j < tabBitParite.length) { /*
									 * on insert dans ce tableau tab[] les codes de parités a leurs emplacement
									 */
			tab[i] = tabBitParite[j];

			i -= emplacement[j];
			j++;
		}

		System.out.println("placement des bits de parités dans un tableau :");

		for (int w = 0; w < tab.length; w++) {

			System.out.print(" " + tab[w] + " ");
		}

		int y = 0;
		while (y < tra.length) { /*
									 * on insert dans ce tableau tab[] (au emplacement vide) la trame saisi au debut
									 * pour former le mot de hamming
									 */
			for (int w = 0; w < tab.length; w++) {
				if (tab[w] == null) {
					tab[w] = tra[y];
					y++;

				}

			}

		}
		System.out.println();

		System.out.println("placement des bits du mot a coder dans le tableau:");

		for (int w = 0; w < tab.length; w++) {

			System.out.print(" " + tab[w] + " ");
		}

		System.out.println();

		/*
		 * on fait appel a notre méthode static calculValBP() qui calcul la valeur des bits de
		 * controle de parités qu'on placer précédément dans le tableau tab[]
		 */
		Hamming.calculValBP(bitParite, tab, tabBitParite, emplacement);

		String l = ""; /* pour afficher les codes de controle de parités */
		for (int v = 0; v < tabBitParite.length; v++) {

			l += tabBitParite[v] + " ";
		}

		String s = ""; /* pour afficher le mot de hamming resultant */
		for (int v = 0; v < tab.length; v++) {

			s += tab[v] + " ";
		}
		// s+="]";
		System.out.println("le code de hamming est" + " " + l + " " + " et le mot de Hamming résultant est" + " " + s);

		return tab;

	}

	

	/*
	 * cette méthode static calcul la valeur des bits de controle de parités qu'on
	 * placer précédément dans le tableau tab[]
	 */
	public static void calculValBP(int bitParite, Character tab[], Character tabBitParite[], int emplacement[])
			throws InterruptedException {
		for (int w = 0; w < bitParite; w++) {
			int z = tab.length - 1;
			int k = 0;
			int nb = 0;
			Character t[] = {}; /*
								 * tableau qui contient les groupes des bits de controle de parités selon leurs
								 * emplacement
								 */

			if (tab.length == 7) {
				t = new Character[4];
			} else {
				if (tab.length == 15) {
					t = new Character[8];
				} else {
					if (tab.length == 31) {
						t = new Character[16];
					}
				}
			}

			System.out.println("Formation de sous groupe de C qui est  a l'emplacement" + " " + (w + 1));
			do {

				if (emplacement[w] == 1) { /*
											 * lorsqu'on est au bit de controle de parité qui est a la premiere position
											 * alors on prend le bit en question et on saute un bit a chaque fois
											 * jusqu'a a la fin de notre tableau tab
											 */

					while (k < t.length || z >= 0) {
						t[k] = tab[z];
						z -= 2;
						System.out.print(" " + t[k] + " ");
						if (t[k] == '1') {/*
											 * si notre groupe fornit contient un 1 alors notre compteur nb incrémente
											 */
							nb++;
						}

						k++;
					}
					System.out.println();
					if (nb % 2 == 0) { /* si nb est pair alors la valeur de notre bit de parité en question sera 0 */
						tab[tab.length - emplacement[w]] = '0';
						tabBitParite[w] = '0';
						System.out.println("Comme le nombre de 1 est pair dans ce groupe alors :");
						System.out.println("C'" + (w + 1) + " " + " =" + " " + tabBitParite[w]);
					} else { /* si nb est impair alors la valeur de notre bit de parité en question sera 1 */
						tab[tab.length - emplacement[w]] = '1';
						tabBitParite[w] = '1';
						System.out.println("Comme le nombre de 1 est impair dans ce groupe alors :");
						System.out.println("C'" + (w + 1) + " " + " =" + " " + tabBitParite[w]);
					}

					System.out.println();
				} else {

					if (emplacement[w] == 2) {/*
												 * lorsqu'on est au bit de controle de parité qui est a la 2eme position
												 * alors on prend le bit en question et son suivant et on saute deux
												 * bits a chaque fois jusqu'a a la fin de notre tableau tab
												 */
						while (k < t.length && z >= 0) {
							while (z > tab.length - 3) {

								z--;
								t[k] = tab[z];
								System.out.print(" " + t[k] + " ");

								if (t[k] == '1') {
									nb++;
								}
								k++;

							}

							z -= 3;

							while (k < t.length || z >= 0) {
								// System.out.println(t.length);
								Character iz[] = new Character[2];
								int o = 0;

								while (o < iz.length && iz[o] == null) {

									iz[o] = tab[z];
									t[k] = tab[z];

									System.out.print(" " + t[k] + " ");

									z--;
									if (t[k] == '1') {
										nb++;
									}
									k++;
									o++;

								}
								z -= 2;

							}
							System.out.println();
							if (nb % 2 == 0) {/*
												 * si nb est pair alors la valeur de notre bit de parité en question
												 * sera 0
												 */
								tab[tab.length - emplacement[w]] = '0';
								tabBitParite[w] = '0';
								System.out.println("Comme le nombre de 1 est pair dans ce groupe alors :");
								System.out.println("C'" + (w + 1) + " " + " =" + " " + tabBitParite[w]);
							} else {/* si nb est impair alors la valeur de notre bit de parité en question sera 1 */
								tab[tab.length - emplacement[w]] = '1';
								tabBitParite[w] = '1';
								System.out.println("Comme le nombre de 1 est impair dans ce groupe alors :");
								System.out.println("C'" + (w + 1) + " " + " =" + " " + tabBitParite[w]);
							}
						}

						System.out.println();

					} else {

						if (emplacement[w] == 4) {/*
													 * lorsqu'on est au bit de controle de parité qui est a la 4eme
													 * position alors on prend le bit en question et ses 3 bits qui suit
													 * et on saute 4 bits et prend les 4 bits qui se trouve aprés le
													 * saut de 4 bits précedents on repete l'opération jusqu'a la fin de
													 * notre tableau tab
													 */

							z -= 3;
							while (z > tab.length - 8) {
								if (k < t.length) {

									t[k] = tab[z];
									System.out.print(" " + t[k] + " ");
									if (t[k] == '1') {
										nb++;
									}
									z--;
									k++;
								}
							}

							z -= 4;

							while (k < t.length || z > 0) {
								Character a[] = new Character[4];
								int o = 0;

								while (o < a.length && a[o] == null && z >= 0) {

									a[o] = tab[z];
									t[k] = tab[z];

									System.out.print(" " + t[k] + " ");

									z--;
									if (t[k] == '1') {
										nb++;
									}
									k++;
									o++;

								}
								z -= 3;

							}

							System.out.println();
							/* si nb est pair alors la valeur de notre bit de parité en question sera 0*/

							if (nb % 2 == 0) {		
								tab[tab.length - emplacement[w]] = '0';
								tabBitParite[w] = '0';
								System.out.println("Comme le nombre de 1 est pair dans ce groupe alors :");
								System.out.println("C'" + (w + 1) + " " + " =" + " " + tabBitParite[w]);
							} else {/* si nb est impair alors la valeur de notre bit de parité en question sera 1 */
								tab[tab.length - emplacement[w]] = '1';
								tabBitParite[w] = '1';
								System.out.println("Comme le nombre de 1 est impair dans ce groupe alors :");
								System.out.println("C'" + (w + 1) + " " + " =" + " " + tabBitParite[w]);
							}

						} else {

							/*
							 * lorsqu'on est au bit de controle de parité qui est a la 8eme position alors
							 * on prend le bit en question et ses 7 bits qui suit et on saute 8 bits et
							 * prend les 8 bits qui se trouve aprés le saut de 8 bits précedents on repete
							 * l'opération jusqu'a la fin de notre tableau tab
							 */
							if (emplacement[w] == 8) {
								z -= 7;
								System.out.println("z=" + z);
								while (z >= tab.length - 15) {
									if (k < t.length) {

										t[k] = tab[z];
										System.out.print(" " + t[k] + " ");
										if (t[k] == '1') {
											nb++;
										}
										z--;
										k++;
									}
								}

								z -= 8;

								while (k < t.length || z > 0) {
									// System.out.println(t.length);
									Character iz[] = new Character[8];
									int o = 0;

									while (o < iz.length && iz[o] == null && z >= 0) {

										iz[o] = tab[z];
										t[k] = tab[z];

										System.out.print(" " + t[k] + " ");

										z--;
										if (t[k] == '1') {
											nb++;
										}
										k++;
										o++;

									}
									z -= 8;

								}

								System.out.println();
								if (nb % 2 == 0) {
									/*si nb est pair alors la valeur de notre bit de parité en question sera 0 */
									tab[tab.length - emplacement[w]] = '0';
									tabBitParite[w] = '0';
									System.out.println("Comme le nombre de 1 est pair dans ce groupe alors :");
									System.out.println("C'" + (w + 1) + " " + " =" + " " + tabBitParite[w]);
								} else {/* si nb est impair alors la valeur de notre bit de parité en question sera 1 */
									tab[tab.length - emplacement[w]] = '1';
									tabBitParite[w] = '1';
									System.out.println("Comme le nombre de 1 est impair dans ce groupe alors :");
									System.out.println("C'" + (w + 1) + " " + " =" + " " + tabBitParite[w]);
								}

							} else {

								/*
								 * lorsqu'on est au bit de controle de parité qui est a la 16eme position alors
								 * on prend le bit en question et ses 15 bits qui suit et on saute 16 bits et on
								 * prend les 16 bits qui se trouve aprés le saut de 16 bits précedents on repete
								 * l'opération jusqu'a la fin de notre tableau tab
								 */
								if (emplacement[w] == 16) {
									z -= 15;
									System.out.println("z=" + z);
									while (z >= tab.length - 31) {
										if (k < t.length) {

											t[k] = tab[z];
											System.out.print(" " + t[k] + " ");
											if (t[k] == '1') {
												nb++;
											}
											z--;
											k++;
										}
									}

									z -= 16;

									while (k < t.length || z > 0) {
										Character iz[] = new Character[8];
										int o = 0;

										while (o < iz.length && iz[o] == null && z >= 0) {

											iz[o] = tab[z];
											t[k] = tab[z];

											System.out.print(" " + t[k] + " ");

											z--;
											if (t[k] == '1') {
												nb++;
											}
											k++;
											o++;

										}
										z -= 16;
									}

									System.out.println();
									if (nb % 2 == 0) {
									/*si nb est pair alors la valeur de notre bit de parité en question sera 0 */
										tab[tab.length - emplacement[w]] = '0';
										tabBitParite[w] = '0';
										System.out.println("Comme le nombre de 1 est pair dans ce groupe alors :");
										System.out.println("C'" + (w + 1) + " " + " =" + " " + tabBitParite[w]);
									} else {
										/* si nb est impair alors la valeur de notre bit de parité en question sera 1*/
										tab[tab.length - emplacement[w]] = '1';
										tabBitParite[w] = '1';
										System.out.println("Comme le nombre de 1 est impair dans ce groupe alors :");
										System.out.println("C'" + (w + 1) + " " + " =" + " " + tabBitParite[w]);
									}
								}
							}

						}
					}
				}
			} while (k < t.length || z > 0);
			System.out.println();
		}
	}

	
/*	pour vérifier si le mot de hamming passer en paramétre contient des erreurs ou non*/

	public static void verifier(Character tab[]) throws InterruptedException {

		int bitParite = 0;
		if (tab.length == 7) {
			bitParite = 3;
		} else {
			if (tab.length == 15) {
				bitParite = 4;
			} else {
				if (tab.length == 31) {
					bitParite = 5;
				}
			}
		}

		int emplacement[] = new int[bitParite];
		for (int j = 0; j < bitParite; j++) {
			emplacement[j] = (int) Math.pow(2, j);
		}

		Character trame[] = new Character[bitParite];

		/*
		 * on fait appel a notre méthode static calculValBP() qui calcul la valeur des bits de
		 * controle de Vérification C' qui se trouve a leurs emplacement dans le mot de
		 * hamming passer en parametre
		 */
		Hamming.calculValBP(bitParite, tab, trame, emplacement);

		int cmt = 0;
		for (int b = 0; b < trame.length; b++) {
			if (trame[b] == '1') {
				cmt += 1 * (int) Math.pow(2, b);
			} else {
				if (trame[b] == '0') {
					cmt += 0;
				}
			}

		}

		System.out.println("on calcul C' du mot de hamming a vérifier qui vaut:");
		for (int b = 0; b < trame.length; b++) {
			System.out.print(trame[b]);
		}

		System.out.println();

		if (cmt > 0) {
			System.out.println("La somme finale des bits de controle est  :" + cmt + " elle est differente de 0 donc :");
			System.out.println("le mot de hamming contient  une erreur a l'endroit " + " " + cmt);
		} else {
			System.out.println("La somme finale des bits de controle est  :" + cmt + " Donc : ");
			System.out.println("il y'a pas d'erreur :D");
		}

	}

}
