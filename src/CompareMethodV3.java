import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CompareMethodV3 {
	static int i = 0;
	
	static ArrayList<String> identique = new ArrayList<String>();
	static ArrayList<String> ajout = new ArrayList<String>();
	static ArrayList<String> suppression = new ArrayList<String>();
	static ArrayList<String> difference = new ArrayList<String>();
	
	static ArrayList<String> listeProgiciel; 
	
	static String nomCheminFichier1;
	static String nomCheminFichier2;
	
	static String ligne;
	static String ligne2;
	static String ligne3;
	static String ligne4;
	
	static String chem;
	static String chem2;
	
    static ArrayList<String> CompositeProgiciel1 = new ArrayList<String>();
    static ArrayList<String> CompositeProgiciel2 = new ArrayList<String>();
	
	static ArrayList<String> method1 = new ArrayList<String>();
	static ArrayList<String> method2 = new ArrayList<String>();
	
	static ArrayList<String> interface1 = new ArrayList<String>();
	static ArrayList<String> interface2 = new ArrayList<String>();
	
	static ArrayList<String> recupCheminMethod1 = new ArrayList<String>();
	static ArrayList<String> recupCheminMethod2 = new ArrayList<String>();
	
	static ArrayList<String> cheminIdentique = new ArrayList<String>();
	static ArrayList<String> cheminAjout = new ArrayList<String>();
	static ArrayList<String> cheminSuppression = new ArrayList<String>();
	
	static ArrayList<String> paramMethod1 = new ArrayList<String>();
	static ArrayList<String> nomMethod1 = new ArrayList<String>();
	
	static ArrayList<String> paramMethod2 = new ArrayList<String>();
	static ArrayList<String> nomMethod2 = new ArrayList<String>();

	//Constructeur
	public CompareMethodV3(ArrayList<String> listeProgiciel) {
		super();
		CompareMethodV3.listeProgiciel = listeProgiciel;
	}

	//Récupération des fichiers ".java" dans chaque progiciels
	public void RecupFichierJava() {
		for (int a = 0; a < listeProgiciel.size(); a++){
			//String cheminV14 = "/_WORK/V14.0/20_Server/com.itnsa.assurance.client/src/main/java/com/itnsa/assurance/soa/";
			String chemin = "/_WORK/"+listeProgiciel.get(a)+"/com.itnsa.assurance.client/src/main/java/com/itnsa/assurance/soa/";
			if(a==0){
				CompareMethodV3.Checkfiles(chemin, CompositeProgiciel1, ".java");
			}
			else{
				CompareMethodV3.Checkfiles(chemin, CompositeProgiciel2, ".java");
			}
		
		}
	}
	
	//Méthode de recherche des fichiers
	public static ArrayList<String> Checkfiles (String chemin, ArrayList<String> CompositeProgiciel, String suffixe) {
		
	  	  File dir = new File(chemin);

	  	  if(!dir.isFile()){
	  	  for ( File f : dir.listFiles() )
	  	    {

			  	if (f.getName().endsWith(suffixe))
			  	{
	  	    		i++;
	  	    		CompositeProgiciel.add(f.getPath());
	  	    		System.out.println(i+" : "+f.getPath());
	  	    	}
	  	    	else{
	  	    		Checkfiles(f.getPath(),CompositeProgiciel, suffixe);
	  	    	}
	  	    }
	  	  }
	  	  return CompositeProgiciel;
	    }
	
	//Méthode qui trie les deux branches afin de garder seulement les méthodes existantes
	public void TrieBranches(ArrayList<String> CompositeProgiciel1,  ArrayList<String> CompositeProgiciel2) {
		InputStream flux;
		InputStreamReader lecture;
		BufferedReader buff;
		
		//Remplissage des fichiers
		for(int a = 0;a<CompositeProgiciel1.size() || a<CompositeProgiciel2.size();a++) {		
			try {
				
				if(a<CompositeProgiciel1.size()) {
					flux=new FileInputStream(CompositeProgiciel1.get(a));
					lecture=new InputStreamReader(flux);
					buff=new BufferedReader(lecture);
					String str = "";
					String sub = CompositeProgiciel1.get(a);
					while ((ligne=buff.readLine())!=null){
						ligne = ligne.trim();
						ligne = ligne.replaceAll("  ", " ");
						ligne = ligne.replaceAll(" ", "");
						ligne = ligne.replaceAll("	", "");
						//On réduit le nom du chemin à partir du dossier "soa"
						String chem1 = CompositeProgiciel1.get(a).substring(sub.indexOf("soa"), sub.indexOf(".java"));
						if (!ligne.contains("/") && !ligne.startsWith("import") && !ligne.startsWith("public") 
								&& !ligne.startsWith("package") && !ligne.contains("*") && !ligne.startsWith("@") && !ligne.contains("ul")  
								&& !ligne.startsWith("Class") && !ligne.startsWith("Map") && !ligne.startsWith("Object") && !ligne.startsWith("{")) {
								str += ligne;
								if (ligne.endsWith(";")) {
									method1.add(str);
									recupCheminMethod1.add(chem1);
									System.out.println("InterfaceMethodfichier1 : " + str);
									str="";
								}
						}
					}
					
					buff.close();
				}
				if(a<CompositeProgiciel2.size()) {
					flux=new FileInputStream(CompositeProgiciel2.get(a));
					lecture=new InputStreamReader(flux);
					buff=new BufferedReader(lecture);
					String str="";
					String sub = CompositeProgiciel2.get(a);
					while ((ligne=buff.readLine())!=null){
						ligne = ligne.trim();
						ligne = ligne.replaceAll("  ", " ");
						ligne = ligne.replaceAll(" ", "");
						ligne = ligne.replaceAll("	", "");
						//On réduit le nom du chemin à partir du dossier "soa"
						String chem3 = CompositeProgiciel2.get(a).substring(sub.indexOf("soa"), sub.indexOf(".java"));
						if(!ligne.contains("/") && !ligne.startsWith("import") && !ligne.startsWith("public") 
								&& !ligne.startsWith("package") && !ligne.contains("*") && !ligne.startsWith("@") && !ligne.contains("ul") 
								&& !ligne.startsWith("Class") && !ligne.startsWith("Map") && !ligne.startsWith("Object") && !ligne.startsWith("{")) {
								str += ligne;
								if (ligne.endsWith(";")) {
									method2.add(str);
									recupCheminMethod2.add(chem3);
									System.out.println("InterfaceMethodfichier2 : " + str);
									str="";
								}
						}
					}
					
					buff.close();
				}
			} 
			
			catch (IOException e) {
				e.printStackTrace();
			}

		}
		System.out.println("Nombre d'interface + méthodes dans le fichier 1 : " +method1.size());
		System.out.println("Nombre d'interface + méthodes dans le fichier 2 : " +method2.size());
	}

		//Comparaisons des méthodes --> Ajout, Suppression, Identique.
		public void CompareFichiers() {
			int nbEgalite = 0;
			int nbAjout = 0;
			int nbSuppression = 0;
			//Boucle pour l'égalité
			for (int p = 0; p<method1.size(); p++) {
				ligne = method1.get(p);
				for (String ligne2 : method2) {				
					if(ligne.compareTo(ligne2) == 0 || ligne.compareToIgnoreCase(ligne2) == 0) {
						identique.add(ligne);
						cheminIdentique.add(recupCheminMethod1.get(p));
						nbEgalite ++;
						System.out.println("Identique "+p+" : " +ligne);
						break;
					}
				}
			}
			
			//Boucle pour l'ajout
			for (int p = 0; p<method2.size(); p++) {
				ligne2 = method2.get(p);
				int compteur1 = 0;
				for (String ligne : method1) {
					if(ligne2.compareTo(ligne) < 0 || ligne2.compareTo(ligne) > 0) {
						compteur1 ++;
					}
					if(compteur1 == method1.size()) {
						ajout.add(ligne2);
						cheminAjout.add(recupCheminMethod2.get(p));
						nbAjout ++;
						System.out.println("Ajout "+p+" : " +ligne2);
						break;
					}
				}
			}
			
			//Boucle pour la suppression
			for (int p = 0; p<method1.size(); p++) {
				int compteur1 = 0;
				ligne = method1.get(p);
				for (String ligne2 : method2) {
					if(ligne.compareTo(ligne2) < 0 || ligne.compareTo(ligne2) > 0 ) {
						compteur1 ++;
					}
					if(compteur1 == method2.size()) {
						suppression.add(ligne);
						cheminSuppression.add(recupCheminMethod1.get(p));
						nbSuppression ++;
						System.out.println("Suppression "+p+" : " +ligne);
						break;
					}
				}
			}
			
			//Boucle pour les différences entre des méthodes portant le même nom
			ArrayList<String> SupprIdentique1 = new ArrayList<String>(method1);
			ArrayList<String> SupprIdentique2 = new ArrayList<String>(method2);
			//Supprime les méthodes identiques entre les deux progiciels
			for (int p = 0; p<SupprIdentique1.size(); p++) {
			    chem = recupCheminMethod1.get(p);
				ligne = SupprIdentique1.get(p);
				for (int w = 0; w<SupprIdentique2.size(); w++) {
					    chem2 = recupCheminMethod2.get(w);
						ligne2 = SupprIdentique2.get(w);
						if(ligne.compareTo(ligne2) == 0 || ligne.compareToIgnoreCase(ligne2) == 0 && chem.equals(chem2)) {
							recupCheminMethod1.remove(chem);
							recupCheminMethod2.remove(chem2);
							SupprIdentique1.remove(ligne);
							SupprIdentique2.remove(ligne2);
							p = p-1;
							break;
					}
				}
			}
			//Découpe chaque String des deux listes pour récupérer dans deux listes différentes, d'un côté le nom de la méthode et dans l'autre les paramètres pour chaque Progiciel
			//Progiciel 1 
			for (String str : SupprIdentique1) {
				if (str.contains("(") && !str.contains(")")) {
					nomMethod1.add(str);
					paramMethod1.add("");
				}
				else if (str.contains(")") && !str.contains("(")) {
					nomMethod1.add(str);
					paramMethod1.add("");
				}
				else {
				String testNom1 = str.substring(0, str.indexOf("("));
				nomMethod1.add(testNom1);
				System.out.println("Nom Méthode 1: "+testNom1);
				
				String testParam1 = str.substring(str.indexOf("(")+1, str.indexOf(")"));
				paramMethod1.add(testParam1);
				System.out.println("Param Méthode 1 : "+testParam1);
				}
			}
			//Progiciel 2
			for (String str : SupprIdentique2) {
				if (str.contains("(") && !str.contains(")")) {
					nomMethod2.add(str);
					paramMethod2.add("");
				}
				else if (str.contains(")") && !str.contains("(")) {
					nomMethod2.add(str);
					paramMethod2.add("");
				}
				else {
					String testNom2 = str.substring(0, str.indexOf("("));
					nomMethod2.add(testNom2);
					System.out.println("Nom Méthode 2: "+testNom2);
					
					String testParam2 = str.substring(str.indexOf("(")+1, str.indexOf(")"));
					paramMethod2.add(testParam2);
					System.out.println("Param Méthode 2 : "+testParam2);
				}
			}
			//Double boucle pour vérifier méthode par méthode si il existe des différences
			for (int p=0; p<SupprIdentique1.size(); p++) {
				ligne = nomMethod1.get(p);
				ligne2 = paramMethod1.get(p);
				chem = recupCheminMethod1.get(p);
				for(int w=0; w<SupprIdentique2.size(); w++) {
					ligne3 = nomMethod2.get(w);
					ligne4 = paramMethod2.get(w);
					chem2 = recupCheminMethod2.get(w);
					if(ligne.compareTo(ligne3) == 0 && !ligne4.isEmpty() && !ligne2.isEmpty()) {
						if(chem.equals(chem2)) {
							if(ligne2.compareTo(ligne4) < 0 || ligne2.compareTo(ligne4) > 0) {
								difference.add(SupprIdentique1.get(p)+" ("+ recupCheminMethod1.get(p)+") VOIR ---> "+ligne2+" / "+ligne4);
								System.out.println("Différence entre méthode 1 : "+SupprIdentique1.get(p)+ "("+chem+") et méthode 2 : "+SupprIdentique2.get(w)+"("+chem2+")");
								break;
							}
						}
					}
				}
			}
			
			
 			System.out.println("Voici le nombre d'interface + méthodes égales entre les deux branches : " + nbEgalite);
			System.out.println("Voici le nombre d'interface + méthodes ajoutées dans la nouvelle branche : " + nbAjout);
			System.out.println("Voici le nombre d'interface + méthodes supprimées dans la nouvelle branche : " + nbSuppression);
	}

}
