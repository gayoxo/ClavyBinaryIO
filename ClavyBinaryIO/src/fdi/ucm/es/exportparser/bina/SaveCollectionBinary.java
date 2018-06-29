/**
 * 
 */
package fdi.ucm.es.exportparser.bina;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


import fdi.ucm.server.modelComplete.ImportExportPair;
import fdi.ucm.server.modelComplete.CompleteImportRuntimeException;
import fdi.ucm.server.modelComplete.SaveCollection;
import fdi.ucm.server.modelComplete.collection.CompleteCollection;
import fdi.ucm.server.modelComplete.collection.CompleteLogAndUpdates;
import fdi.ucm.server.modelComplete.collection.document.CompleteFile;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalValueType;

/**
 * Clase que impementa el plugin de oda para Localhost
 * @author Joaquin Gayoso-Cabada
 *
 */
public class SaveCollectionBinary extends SaveCollection {

	private static final String JSON = ".clavy Collection";
	private ArrayList<ImportExportPair> Parametros;
	private String Path;
	private String FileIO;
	private String SOURCE_FOLDER = ""; // SourceFolder path

	
	/**
	 * Constructor por defecto
	 */
		public SaveCollectionBinary() {
	}

	/* (non-Javadoc)
	 * @see fdi.ucm.server.SaveCollection#processCollecccion(fdi.ucm.shared.model.collection.Collection)
	 */
	@Override
	public CompleteLogAndUpdates processCollecccion(CompleteCollection Salvar,
			String PathTemporalFiles) throws CompleteImportRuntimeException{
		try {
			
			CompleteLogAndUpdates CL=new CompleteLogAndUpdates();
			

			Path=PathTemporalFiles;
			SOURCE_FOLDER=Path+"JSON"+File.separator;
			File Dir=new File(SOURCE_FOLDER);
			Dir.mkdirs();
			FileIO = Path+System.currentTimeMillis()+".clavy";
			
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FileIO));

			
			
			oos.writeObject(Salvar);

			oos.close();
			

				

				CL.getLogLines().add("Descarga el archivo");

			return CL;


		} catch (CompleteImportRuntimeException e) {
			System.err.println("Exception JSON " +e.getGENERIC_ERROR());
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			System.err.println("Exception HTML " +e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	

	@Override
	public ArrayList<ImportExportPair> getConfiguracion() {
		if (Parametros==null)
		{
			ArrayList<ImportExportPair> ListaCampos=new ArrayList<ImportExportPair>();
//			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "Name of the export",true));
			Parametros=ListaCampos;
			return ListaCampos;
		}
		else return Parametros;
	}

	@Override
	public void setConfiguracion(ArrayList<String> DateEntrada) {
//		if (DateEntrada!=null)
//		{
//			
//
//			TextoIn=DateEntrada.get(0).trim();
//			
//
//		}
	}
		
//
//	private ArrayList<Long> generaListaDocuments(String string) {
//		String[] strings=string.split(",");
//		ArrayList<Long> Salida=new ArrayList<Long>();
//		for (String string2 : strings) {
//			try {
//				Long N=Long.parseLong(string2);
//				Salida.add(N);
//			} catch (Exception e) {
//			}
//		}
//		return Salida;
//	}

	@Override
	public String getName() {
		return JSON;
	}


	@Override
	public boolean isFileOutput() {
		return true;
	}

	@Override
	public String FileOutput() {
		return FileIO;
	}

	@Override
	public void SetlocalTemporalFolder(String TemporalPath) {
		
	}

	
	public static void main(String[] args) {
		SaveCollectionBinary SV=new SaveCollectionBinary();
		CompleteCollection CC=CreateCollectionBase();
		SV.processCollecccion(CC, "");
		System.out.println("OK");

		
	}

	private static CompleteCollection CreateCollectionBase() {
		CompleteCollection C=new CompleteCollection("nombre coleccion", "descripcion");
		C.setClavilenoid(1l);
		int n = (new Random()).nextInt(20);
		for (int i = 0; i < n; i++) 
			C.getSectionValues().add(new CompleteFile(new Long(i),"File"+ i+"path", C));
		
		@SuppressWarnings("unused")
		List<CompleteFile> Files=new LinkedList<>(C.getSectionValues());
		List<CompleteElementType> Elements=new LinkedList<CompleteElementType>();

		
		int n2 = (new Random()).nextInt(20);
		for (int i = 0; i < n2; i++) 
			{	CompleteGrammar GG=new CompleteGrammar(new Long(i), "Gram"+ i+"name","Gram"+ i+"desc",C);
				

			int n3 = (new Random()).nextInt(10);
			for (int j = 0; j < n3; j++) 
				{	CompleteOperationalValueType OP=new CompleteOperationalValueType(new Long(j),"View"+ j+"name","View"+ j+"def","View"+j+"view");
					GG.getViews().add(OP);
				}
				
			ArrayList<CompleteElementType> hijos=new ArrayList<CompleteElementType>();	
			generahijos(hijos,GG,Elements,new Integer(20));
			GG.setSons(hijos);
			
				C.getMetamodelGrammar().add(GG);
			}
		
		return C;
	}

	private static void generahijos(ArrayList<CompleteElementType> hijos, CompleteGrammar gG,
			List<CompleteElementType> elements, Integer integer) {
		int n = (new Random()).nextInt(integer);
		for (int i = 0; i < n; i++) 
			{
			CompleteElementType CET=new CompleteElementType(new Long(i), "Element"+i, gG);
			hijos.add(CET);
			elements.add(CET);
			
			CET.setBeFilter((new Random()).nextBoolean());
			CET.setMultivalued((new Random()).nextBoolean());
			CET.setSelectable((new Random()).nextBoolean());
			CET.setBrowseable((new Random()).nextBoolean());
			
			int n3 = (new Random()).nextInt(10);
			for (int j = 0; j < n3; j++) 
				{	
					CompleteOperationalValueType OP=new CompleteOperationalValueType(new Long(j),"View"+ j+"name","View"+ j+"def","View"+j+"view");
					CET.getShows().add(OP);
				}
			
			
			if ((new Random()).nextBoolean())
			{
				ArrayList<CompleteElementType> hijos1=new ArrayList<CompleteElementType>();	
				generahijos(hijos1,gG,elements,new Integer(integer/2));
				CET.setSons(hijos1);
			}
			
			
			}
		
	}


	
	
	
}
