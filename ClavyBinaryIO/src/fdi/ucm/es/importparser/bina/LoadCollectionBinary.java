/**
 * 
 */
package fdi.ucm.es.importparser.bina;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import fdi.ucm.server.modelComplete.ImportExportDataEnum;
import fdi.ucm.server.modelComplete.ImportExportPair;
import fdi.ucm.server.modelComplete.LoadCollection;
import fdi.ucm.server.modelComplete.collection.CompleteCollection;
import fdi.ucm.server.modelComplete.collection.CompleteCollectionAndLog;

/**
 * @author Joaquin Gayoso Cabada
 *
 */
public class LoadCollectionBinary extends LoadCollection{

	
	public static boolean consoleDebug=false;
	private ArrayList<ImportExportPair> Parametros;
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LoadCollectionBinary LC=new LoadCollectionBinary();
		LoadCollectionBinary.consoleDebug=true;
		
		
		ArrayList<String> AA = new ArrayList<String>();
		AA.add("Arte.clavy");
		
		CompleteCollectionAndLog Salida=LC.processCollecccion(AA);
		if (Salida!=null)
			{
			
			System.out.println("Correcto");
			
			for (String warning : Salida.getLogLines())
				System.err.println(warning);

			
			System.exit(0);
			
			}
		else
			{
			System.err.println("Error");
			System.exit(-1);
			}
	}

	

	@Override
	public CompleteCollectionAndLog processCollecccion(ArrayList<String> dateEntrada) {
		String message="Exception .clavy-> Params Null ";
		try {
			if (dateEntrada!=null)
			{
				
				
				String fileName = dateEntrada.get(0);
				 System.out.println(fileName);
				 CompleteCollectionAndLog Salida=new CompleteCollectionAndLog();
				 
				 ArrayList<String> log = new ArrayList<>();
				 
				 Salida.setLogLines(log);

				 File file = new File(fileName);
				 FileInputStream fis = new FileInputStream(file);
				 ObjectInputStream ois = new ObjectInputStream(fis);
				 CompleteCollection object = (CompleteCollection) ois.readObject();
				 
				 Salida.setCollection(object);
				 
				 try {
					 ois.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				 try {
					 fis.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				 
				 return Salida;
			}
			else
			{
				System.err.println(message);
				throw new RuntimeException(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(message);
			throw new RuntimeException(message);
		}
		
		
//		try {
//			CompleteCollectionAndLog Salida=new CompleteCollectionAndLog();
//			CC=new CompleteCollection("MedPix", new Date()+"");
//			Salida.setCollection(CC);
//			Logs=new ArrayList<String>();
//			Salida.setLogLines(Logs);
//			encounterID=new HashMap<String,CompleteDocuments>();
//			topicID=new HashMap<String,List<CompleteDocuments>>();
//			ListImageEncounter=new ArrayList<CompleteElementTypeencounterIDImage>();
//			ListImageEncounterTopics=new ArrayList<CompleteElementTypeencounterIDImage>();
//			ListTopicID=new ArrayList<CompleteElementTypetopicIDTC>();
//			
//			ProcesaCasos();
//			ProcesaCasoID();
//			ProcesaTopics();
//			//AQUI se puede trabajar
//			
//			
//			return Salida;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
		
	}


	@Override
	public ArrayList<ImportExportPair> getConfiguracion() {
		if (Parametros==null)
		{
			ArrayList<ImportExportPair> ListaCampos=new ArrayList<ImportExportPair>();
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.File, "InputCollection in .clavy",true));
			Parametros=ListaCampos;
			return ListaCampos;
		}
		else return Parametros;
	}

	@Override
	public String getName() {
		return ".clavy Collection Import";
	}

	@Override
	public boolean getCloneLocalFiles() {
		return false;
	}

}
