package INTERFACE;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Scanner;
import Classes.*;

public class main {
	public static void main(String[] args) {

		Agence ag = null;
		FileReader myFile = null;
		Scanner scanner = new Scanner(System.in);
		try {
			myFile = new FileReader("VoituresLoc.dat");
			ag = new Agence(myFile);
		} catch (FileNotFoundException e) {
			System.out.println("je peut pas lire depuis VoituresLoc.dat !!");
			ag = new Agence();
		}
		new Home2(ag);
	}

}
