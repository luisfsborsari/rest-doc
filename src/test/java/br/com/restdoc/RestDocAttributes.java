package br.com.restdoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Danilo Buiatti Lamounier
 *
 * 10/06/2011 10:21:16
 */
public interface RestDocAttributes {
	String PATH_URI = "/uri";
	String COMMENT = "comment txt";
	String DESCRIPTION = "description";
	String METHOD = "method";
	String QUALIFIED_NAME = "com.br.test.find";
	String SIGNATURE = "(String, String)";
	List<String> PARAMS = new ArrayList<String>(Arrays.asList("text"));
	String RETURN = "text";
	String RETURN_EXAMPLE = "text";
	List<String> THROWS = new ArrayList<String>(Arrays.asList("text"));;
	List<String> CALLS = new ArrayList<String>(Arrays.asList("text"));;

}
