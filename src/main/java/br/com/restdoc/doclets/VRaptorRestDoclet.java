package br.com.restdoc.doclets;

import br.com.restdoc.RestAnnotation;
import br.com.restdoc.RestService;
import br.com.restdoc.mojos.RestServiceMojo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.javadoc.*;
import com.sun.javadoc.AnnotationDesc.ElementValuePair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VRaptorRestDoclet {

	public static final String PATH = VRaptorRestDoclet.class.getName();
    public static final String JSON_NAME_OPTION = "-jsonName";

	static final String PARAM_TAG_ANNOTATION = "@param";
	static final String RETURN_TAG_ANNOTATION = "@return";
	static final String RETURN_EXAMPLE_TAG_ANNOTATION = "@returnExample";
	static final String THROWS_TAG_ANNOTATION = "@throws";
	static final String CALLS_TAG_ANNOTATION = "@calls";

	public static boolean start(RootDoc root) throws Exception {
		VRaptorRestDoclet doclet = new VRaptorRestDoclet();
		List<RestService> restServices = doclet.findRestServices(root);
        String servicesJson = hasJsonNameOption(root.options()) ? getJsonName(root.options()) + " = " : "";
        servicesJson += VRaptorRestDoclet.createGson().toJson(restServices).replaceAll(
				"(\\\\n|\\\\t)", "");
		VRaptorRestDoclet.writeFile(servicesJson,
				RestServiceMojo.getOutputFile());
		return true;
	}

    public static int optionLength(String option) {
        if(option.equals(JSON_NAME_OPTION)) {
            return 2;
        }
        return 0;
    }

    private static String getJsonName(String[][] options) {
        for(String option[] : options){
            if(JSON_NAME_OPTION.equals(option[0])){
                return option[1];
            }
        }
        return "";
    }

    private static boolean hasJsonNameOption(String[][] options) {
        for(String option[] : options){
            if(JSON_NAME_OPTION.equals(option[0])){
                return true;
            }
        }
        return false;
    }


    private static Gson createGson() {
		return new GsonBuilder().setPrettyPrinting().create();
	}

	public static void writeFile(String restServicesJson, File outputFile)
			throws Exception {

		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(outputFile);
			fileWriter.write(restServicesJson);
			fileWriter.flush();
		} catch (IOException e) {
			throw new Exception("Error creating file", e);
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
				}
			}
		}
	}

	List<RestService> findRestServices(RootDoc root) {
		List<RestService> restServices = new ArrayList<RestService>();

		for (ClassDoc clazz : root.classes()) {
			AnnotationDesc annotationDesc = getControllerPathAnnotation(clazz);
			String pathURI = findServiceURI(annotationDesc);

			for (MethodDoc method : clazz.methods()) {
				if (isRestService(method)) {
					restServices.add(createServiceDoc(method, pathURI));
				}
			}
		}
		return restServices;
	}

	RestService createServiceDoc(MethodDoc methodDoc, String pathURI) {
		// Get service Path
		AnnotationDesc pathAnnotationDesc = getPathAnnotation(methodDoc);
		AnnotationDesc restAnnotationDesc = getMethodRestAnnotation(methodDoc);
		String servicePath = pathURI + findServiceURI(pathAnnotationDesc)
				+ findServiceURI(restAnnotationDesc);

		// Method
		String methodTag = methodDoc.qualifiedName() + methodDoc.signature();

		// Description
		String description = methodDoc.commentText();

		// Request type
		AnnotationDesc desc = getMethodRestAnnotation(methodDoc);
		String requestType = getRequestType(desc);

		// Doc info
		List<String> parameters = getDocInfo(methodDoc, PARAM_TAG_ANNOTATION);
		String returns = getFirstItemOrNewString(getDocInfo(methodDoc,
				RETURN_TAG_ANNOTATION));
		String returnExample = getFirstItemOrNewString(getDocInfo(methodDoc,
				RETURN_EXAMPLE_TAG_ANNOTATION));
		List<String> throwsInfo = getDocInfo(methodDoc, THROWS_TAG_ANNOTATION);
		List<String> calls = getDocInfo(methodDoc, CALLS_TAG_ANNOTATION);

		return new RestService.Builder(servicePath, requestType)
				.wihtParameters(parameters).withReturns(returns)
				.withReturnExample(returnExample)
				.withExceptionThrows(throwsInfo).withCalls(calls)
				.withServiceMethod(methodTag).withDescription(description)
				.build();
	}

	private String getFirstItemOrNewString(List<String> list) {
		return list.isEmpty() ? "" : list.get(0);
	}

	List<String> getDocInfo(MethodDoc methodDoc, String paramTag) {
		List<String> params = new ArrayList<String>();
		for (Tag tag : methodDoc.tags(paramTag)) {
			params.add(tag.text());
		}
		return params;
	}

	String findServiceURI(AnnotationDesc annotationDesc) {
		try {
			for (ElementValuePair element : annotationDesc.elementValues()) {
				return element.value().toString().replace("\"", "");
			}
		} catch (Exception e) {
		}
		return "";
	}

	AnnotationDesc getPathAnnotation(MethodDoc method) {
		for (AnnotationDesc annotationDesc : method.annotations()) {
			String typeName = getAnnotationQualifiedTypeName(annotationDesc);
			if (RestAnnotation.PATH.toString().equals(typeName)) {
				return annotationDesc;
			}
		}
		return null;
	}

	AnnotationDesc getMethodRestAnnotation(MethodDoc method) {
		for (AnnotationDesc annotationDesc : method.annotations()) {
			String typeName = getAnnotationQualifiedTypeName(annotationDesc);
			if (RestAnnotation.isHttpVerb(typeName)) {
				return annotationDesc;
			}
		}
		return null;
	}

	boolean isRestService(MethodDoc method) {
		for (AnnotationDesc annotationDesc : method.annotations()) {
			String typeName = getAnnotationQualifiedTypeName(annotationDesc);
			if (RestAnnotation.isRestAnnotation(typeName)) {
				return true;
			}
		}
		return false;
	}

	String getRequestType(AnnotationDesc annotationDesc) {
		try {
			List<String> temp;
			String typeName = getAnnotationQualifiedTypeName(annotationDesc);
			RestAnnotation annotation = RestAnnotation.getRequestType(typeName);
			temp = Arrays.asList(annotation.toString().split("\\."));
			return temp.get(temp.size() - 1);
		} catch (Exception e) {
			return null;
		}
	}

	AnnotationDesc getControllerPathAnnotation(ClassDoc clazz) {
		for (AnnotationDesc annotationDesc : clazz.annotations()) {
			String typeName = getAnnotationQualifiedTypeName(annotationDesc);
			if (RestAnnotation.PATH.toString().equals(typeName)) {
				return annotationDesc;
			}
		}
		return null;
	}

	String getAnnotationQualifiedTypeName(AnnotationDesc desc) {
		try {
			return desc.annotationType().qualifiedTypeName();
		} catch (Exception e) {
			return null;
		}
	}
}
