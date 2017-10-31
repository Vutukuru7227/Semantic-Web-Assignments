import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

import org.apache.jena.query.*;

import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
public class lab3_2 {
	
	public static void main(String[] args) {
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
		
		Model model = ModelFactory.createDefaultModel();
		InputStream input = FileManager.get().open("Monterey.rdf");
		//System.out.println(input);
		Date beforeReading = new Date();
		//System.out.println(beforeReading.getTime());
		
		model.read(input, "");
		
		Date afterReading = new Date();
		System.out.println("Load of Montery.rdf took "+(afterReading.getTime() - beforeReading.getTime())/(float)1000+ " seconds");
		
//		String sparqlQuery = "prefix mon:<http://urn.monterey.org/incidents#>"
//				+ "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
//				+ "prefix bb:<http://blackbook.com/terms#>"
//				+ "prefix geo:<http://www.w3.org/2003/01/geo/wgs84_pos#>"
//				+ "prefix vCard:<http://www.w3.org/2001/vcard-rdf/3.0#>"
//				+ "prefix dc:<http://purl.org/dc/elements/1.1/>"
//				+ "prefix event:<http://ebiquity.umbc.edu/ontology/event.owl#>"
//				+ "prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
//				+ "select ?predicate ?object "
//				+ "where{"
//				+ "mon:incident1 ?predicate ?object ."
//				+ "FILTER(!isBlank(?object))"
//				+ "}"
//				+ "UNION{"
//				+ "{"
//				+ ""
//				+ "}";
		
		
//		prefix mon:<http://urn.monterey.org/incidents#>
//
		String sparqlQuery = 	"PREFIX mon:<http://urn.monterey.org/incidents#>"
		+ "SELECT ?pred ?obj WHERE {"
		+ "{" + 
		"			                              mon:incident1 ?pred ?obj .\n" + 
		"			                              FILTER (!isBlank (?obj))\n" + 
		"			                           }\n" + 
		"			                           UNION \n" + 
		"			                              {\n" + 
		"			                                 {        \n" + 
		"			                                      mon:incident1 ?p1 ?blankNode  .\n" + 
		"			                                      FILTER (isBlank (?blankNode)).\n" + 
		"			                                      ?blankNode ?pred ?obj .\n" + 
		"			                                      FILTER (!isBlank (?obj)) .\n" + 
		"			                                  }\n" + 
		"			                                  UNION\n" + 
		"			                                       {\n" + 
		"			                                           mon:incident1 ?p1 ?blankNode  .\n" + 
		"			                                           FILTER (isBlank (?blankNode)).\n" + 
		"			                                           ?blankNode ?p2 ?blankNode2 .\n" + 
		"			                                           FILTER (isBlank (?blankNode2)).\n" + 
		"			                                           ?blankNode2 ?pred ?obj .\n" + 
		"			                                       }\n" + 
		"			                               }\n" + 
		"			                            }";
			                           

		
		
		
		
		
		Query query = QueryFactory.create(sparqlQuery);
		QueryExecution queryExecution = QueryExecutionFactory.create(query, model);
		
		try {
			
			FileOutputStream fileWriter = new FileOutputStream("Lab_3_2_RVutukuru.xml");
			
			ResultSet resultSet = queryExecution.execSelect();
			ResultSetFormatter.outputAsXML(fileWriter, resultSet);	
			System.out.println("Completed");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}

}
