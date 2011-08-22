package br.com.restdoc;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;

/**
 * @author Danilo Buiatti Lamounier
 * 
 *         06/06/2011 09:43:52
 */
public enum RestAnnotation {
	GET(Get.class.getName()), POST(Post.class.getName()), PUT(Put.class.getName()), DELETE(Delete.class.getName()), PATH(
			Path.class.getName());

	private String annotationName;

	RestAnnotation(String annotation) {
		this.annotationName = annotation;
	}

	public static boolean isHttpVerb(String annotation) {
		for (RestAnnotation restAnnotation : values()) {
			if (restAnnotation.annotationName.equals(annotation) && !restAnnotation.equals(PATH)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isRestAnnotation(String annotation) {
		for (RestAnnotation restAnnotation : values()) {
			if (restAnnotation.annotationName.equals(annotation)) {
				return true;
			}
		}
		return false;
	}

	public static RestAnnotation getRequestType(String annotation) {
		for (RestAnnotation restAnnotation : values()) {
			if (restAnnotation.annotationName.equals(annotation) && !restAnnotation.equals(PATH)) {
				return restAnnotation;
			}
		}

		if (PATH.annotationName.equals(annotation)) {
			return PATH;
		}
		throw new IllegalArgumentException("The parameter is not a request type");
	}

	@Override
	public String toString() {
		return annotationName;
	}
}
