import java.awt.Desktop;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.io.FilenameUtils;
import com.itnsa.fwk.util.Dates;

/**
 * Compare les méthodes des classes de deux branches différentes
 * 
 * @author Jollet Alexander
 * 
 */

public class MainCompareMethodV3
{
	public static WorkbookSettings ws;
	public static WritableWorkbook workbook;
	public static WritableSheet s;
	public static WritableSheet s1;
	public static WritableSheet s2;
	public static WritableSheet s3;
    public static WritableFont ArialBold;
    public static WritableCellFormat cfArialBold;
    
    public static FileWriter fichierResultatWriter;

  public static void main(String[] args) throws RowsExceededException, WriteException, IOException
  {
	ArrayList<String> listeProgiciel = new ArrayList<String>();  
	listeProgiciel.add("201020.0");
	listeProgiciel.add("V11.8 AGPM");
	CompareMethodV3 test = new CompareMethodV3(listeProgiciel);	
	test.RecupFichierJava();
	test.TrieBranches(CompareMethodV3.CompositeProgiciel1, CompareMethodV3.CompositeProgiciel2);
	test.CompareFichiers();
	
	System.out.println("Méthodes identique dans les deux versions : ");
	for (String p1 : CompareMethodV3.identique) {
	System.out.println(p1);
	System.out.println("-------------------------------------");
	}
	
	System.out.println("Méthodes ajouter dans la deuxième version : ");
	for (String p2 : CompareMethodV3.ajout) {
		System.out.println(p2);
		System.out.println("-------------------------------------");
		}
	System.out.println("Méthodes supprimées dans la deuxième version : ");
	for (String p3 : CompareMethodV3.suppression) {
		System.out.println(p3);
		System.out.println("-------------------------------------");
		}
	  
  
  
	ws = new WorkbookSettings();
	ws.setLocale(Locale.getDefault());
	//Création du fichier excel 
	File testFLXS = new File(FilenameUtils.normalize("C:\\_WORK\\DifferencesMethodsVersions\\Differences_MéthodesV3_" + listeProgiciel.get(0) + "_et_" + listeProgiciel.get(1) + "_" + Dates.getCurrentDateAsString() + ".xls"));
	
	 try
	    {
	    	workbook = Workbook.createWorkbook(testFLXS, ws);
	    }
	    catch ( IOException e1 )
	    {
	    	e1.printStackTrace();
	    	return;
	    }
	 	//Création des différents onglets
	 	s = workbook.createSheet("PARAMETER DIFFERENCES ",0);
	 	s1 = workbook.createSheet("SAME METHODS ", 1);
	 	s2 = workbook.createSheet("NEW INTO "+ listeProgiciel.get(1), 2);
	 	s3 = workbook.createSheet("NON-EXISTENT INTO "+ listeProgiciel.get(1), 3);
	    ArialBold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
	    cfArialBold = new WritableCellFormat(ArialBold);

	    //Génération de l'affichage de toute les colonnes
	    //Colonne liste méthodes du progiciel 1 dans le fichier Excel
	    for(int p = 0; p<CompareMethodV3.difference.size();p++) {
	    	s.addCell(new Label(0,p,CompareMethodV3.difference.get(p)));
	    }
	    //Colonne liste méthodes identique + chemin associé
	    for(int p = 0; p<CompareMethodV3.identique.size();p++) {
	    	s1.addCell(new Label(0,p,CompareMethodV3.identique.get(p)+" ("+CompareMethodV3.cheminIdentique.get(p)+")"));
	    }
	    //Colonne liste méthodes ajout + chemin associé
	    for(int p = 0; p<CompareMethodV3.ajout.size();p++) {
	    	s2.addCell(new Label(0,p,CompareMethodV3.ajout.get(p)+" ("+CompareMethodV3.cheminAjout.get(p)+")"));
	    }
	    //Colonne chemin méthodes suppression + chemin associé
	    for(int p = 0; p<CompareMethodV3.suppression.size();p++) {
	    	s3.addCell(new Label(0,p,CompareMethodV3.suppression.get(p)+" ("+CompareMethodV3.cheminAjout.get(p)+")"));
	    }
		
		workbook.write();
		workbook.close();
		//Ouverture automatique du fichier excel lors de sa création
		try {
            Desktop.getDesktop().open(new File("C:\\_WORK\\DifferencesMethodsVersions\\Differences_MéthodesV3_" + listeProgiciel.get(0) + "_et_" + listeProgiciel.get(1) + "_" + Dates.getCurrentDateAsString() + ".xls"));
        } catch (IOException e) {
           e.printStackTrace();
        }
  }
  
}