import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.*;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.*;

import static java.lang.System.*;

public class lab1_4d {

	//TODO: Appending default RDF Namespace.
	static String defaultNameSpace = "http://www.w3.org/1999/02/22-rdf-syntax-ns/#Ram";
	
	Model baseFriends = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
		
		
		//Lab 1:2nd Solution
		//====================================================================================
		
		//Defining Namespaces, Resources, Literals
		String personURI = "http://utdallas/semclass/#TBL";
		String fullName = "Sir Timothy John Berners-Lee";
		String email = "timbl@w3.org";
		String title = "Computer Scientist";
		String birthDate = "June 8, 1955";
		
		Model model = ModelFactory.createDefaultModel();
		
		Resource timBernersLeeModel = model.createResource(personURI).addProperty(VCARD.TITLE, title)
																	.addProperty(VCARD.EMAIL, email)
																	.addProperty(VCARD.BDAY, birthDate)
																	.addProperty(VCARD.FN, fullName);
		
		try {

			//TODO: Creating Files
			FileWriter fileWriterInRDF_XMLFormat = new FileWriter("Lab1_2_RVutukuru.xml");
			FileWriter fileWriterInNTripleFormat = new FileWriter("Lab1_2_RVutukuru.ntp");
			FileWriter fileWriterInN3Format = new FileWriter("Lab1_2_RVutukuru.n3");
			
			//TODO: Writing to files in appropriate formats
			model.write(fileWriterInRDF_XMLFormat, "RDF/XML"); // The model denoted in RDF/XML Format
			model.write(fileWriterInNTripleFormat, "N-TRIPLES");// The model denoted in N-Triples Format
			model.write(fileWriterInN3Format, "N3");// The model denoted in N3 Format
			
			
				
		}catch (IOException e) {
			// TODO: handle IOException.
			e.printStackTrace();
		}
		//======================================================================================
		
		// Lab 1: 4th Solution
		String directory = "MyDatabases/Dataset1";
		Dataset dataset = TDBFactory.createDataset(directory);
		
		dataset.begin(ReadWrite.WRITE);
		
		try {
			FileWriter Lab1_4_TDB_RDFXML_FORMAT = new FileWriter("Lab1_4_RVutukuru.xml");
			FileWriter Lab1_4_TDB_NTP_FORMAT = new FileWriter("Lab1_4_RVutukuru.ntp");
			FileWriter Lab1_4_TDB_N3_FORMAT = new FileWriter("Lab1_4_RVutukuru.n3");
			
			Model tDBModel = dataset.getNamedModel("myrdf");
			InputStream inputFOAF = FileManager.get().open("Ram_FOAFFriends.rdf");
			tDBModel.read(inputFOAF,defaultNameSpace);
			
			
			tDBModel.write(Lab1_4_TDB_RDFXML_FORMAT, "RDF/XML");
			out.println("RDF/XML - Process Comeplete");
			
			tDBModel.write(Lab1_4_TDB_NTP_FORMAT, "N-TRIPLES");
			out.println("N-TRIPLES - Process Complete");
			
			tDBModel.write(Lab1_4_TDB_N3_FORMAT,"N3");
			out.println("N3 - Process Complete");
			
			dataset.commit();
			
			}catch (Exception e) {
			// TODO: handle IOException and RiotException.
			e.printStackTrace();
		}
		finally {
			dataset.end();
		}

	}

}
