import java.io.FileWriter;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.*;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.*;

import static java.lang.System.*;
public class Lab_2 {

	static String defaultNameSpace = "http://utdallas/semclass#";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
		out.println("Process Initialized");
		
		//2nd Part
		
		//TODO: String Declarations
		String stanleyKubrick = "Stanley Kubrick";
		String bDay = "July 26,1928";
		String role = "Movie Director";
		
		
		Model filmModel = ModelFactory.createDefaultModel();
		
		//TODO: Creating class Person
		Resource personClass = filmModel.createResource(defaultNameSpace+"person");
				
		//TODO: Define PERSON Stanley Kubrick
		Resource fStanleyKubrick = filmModel.createResource(defaultNameSpace+"StanleyKubrick");
		fStanleyKubrick.addProperty(RDF.type, personClass);
		fStanleyKubrick.addProperty(VCARD.FN, stanleyKubrick);
		fStanleyKubrick.addProperty(VCARD.ROLE, role);
		
		
		//TODO: Creating class Movie
		Resource movieClass = filmModel.createResource(defaultNameSpace+"movie");
		Property year = filmModel.createProperty(defaultNameSpace+"year");
		Property directedBy = filmModel.createProperty(defaultNameSpace+"Director");
		
		movieClass.addProperty(directedBy, fStanleyKubrick);
		
		//TODO: Define MOVIEs Dr.Strangelove and A Clockwork Orange
		Resource drStrangeLoveMovie = filmModel.createResource(defaultNameSpace+"DrStangeLoveMovie");
		Resource aClockWorkOrangeMovie = filmModel.createResource(defaultNameSpace+"AClockworkOrangeMovie");
		
		drStrangeLoveMovie.addProperty(RDF.type, movieClass);
		drStrangeLoveMovie.addProperty(year,"1964");
		//drStrangeLoveMovie.addProperty(directedBy, movieClass);
		
		aClockWorkOrangeMovie.addProperty(RDF.type, movieClass);
		aClockWorkOrangeMovie.addProperty(year,"1971");
		//aClockWorkOrangeMovie.addProperty(directedBy, movieClass);
		
		
		
		//============================================================================================
		// 3rd Part
		String directory = "MyDatabases/Dataset1";
		Dataset dataset = TDBFactory.createDataset(directory);
		
		dataset.begin(ReadWrite.WRITE);
		
		Model tdbModel = dataset.getDefaultModel();
		
		
		String authorString = "Author";
		Resource author = filmModel.createResource(defaultNameSpace+"Authors");
		Resource Books = filmModel.createResource(defaultNameSpace+"Books");
		
		//Property writtenby = filmModel.createProperty(defaultNameSpace+"Author");
		
		
		//Books.addProperty(writtenby, author);
		
		//TODO: Red Alert
		String redAlert = "Red Alert";
		String peterGeorge = "Peter George";
		String redAlertYear = "1958";
		
		Resource peterGeorgeAuthor = filmModel.createResource(defaultNameSpace+"PeterGeorge")
															.addProperty(RDF.type, author)
															.addProperty(VCARD.FN, peterGeorge)
															.addProperty(VCARD.ROLE, authorString);
		
		Resource redAlertBook = filmModel.createResource(defaultNameSpace+"RedAlertBook");
		
		//redAlertBook.addProperty(writtenby, Books);
		redAlertBook.addProperty(RDF.type, Books);
		redAlertBook.addProperty(DC.title, redAlert);
		redAlertBook.addProperty(DC.creator, peterGeorgeAuthor);	
		//redAlertBook.addProperty(writtenby, peterGeorgeAuthor);
		redAlertBook.addProperty(DC.date, redAlertYear);											
														
		
		
		//TODO: A Clockwork Orange
		String aClockWorkOrange = "A Clockwork Orange";
		String anthonyBurgess = "Anthony Burgess";
		String aClockWorkOrangeYear = "1962";
	
		Resource anthonyBurgessAuthor = filmModel.createResource(defaultNameSpace+"AnthonyBurgess")
															.addProperty(RDF.type, author)
															.addProperty(VCARD.FN, anthonyBurgess)
															.addProperty(VCARD.ROLE, authorString);

		Resource aClockworkOrangeBook = filmModel.createResource(defaultNameSpace+"AClockWorkOrangeBook")
															//.addProperty(writtenby, Books)
															.addProperty(RDF.type, Books)
															//.addProperty(writtenby, anthonyBurgessAuthor)
															.addProperty(DC.title, aClockWorkOrange)
															.addProperty(DC.creator, anthonyBurgessAuthor)
															.addProperty(DC.date, aClockWorkOrangeYear);
		
		Property basedOn = filmModel.createProperty(defaultNameSpace+"BasedOn");
		drStrangeLoveMovie.addProperty(basedOn, redAlertBook);
		aClockWorkOrangeMovie.addProperty(basedOn, aClockworkOrangeBook);
		
		tdbModel.add(filmModel);
		
		try {
			
			
			FileWriter fileWriter1 = new FileWriter("lab2_3_RVutukuru.xml");
			out.println("lab2_3_RVutukuru.xml successfully created");
			
			FileWriter fileWriter2 = new FileWriter("lab2_3_RVutukuru.n3");
			out.println("lab2_3_RVutukuru.n3 successfully created");
			tdbModel.write(fileWriter1);
			
			tdbModel.write(fileWriter2, "N3");
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			out.print("Process Completed");
		}

	}

}
