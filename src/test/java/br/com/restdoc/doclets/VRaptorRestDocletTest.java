package br.com.restdoc.doclets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.restdoc.RestAnnotation;
import br.com.restdoc.RestDocAttributes;
import br.com.restdoc.RestService;
import br.com.restdoc.doclets.VRaptorRestDoclet;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationDesc.ElementValuePair;
import com.sun.javadoc.AnnotationTypeDoc;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;

/**
 * @author Danilo Buiatti Lamounier
 * 
 *         07/06/2011 11:59:33
 */
public class VRaptorRestDocletTest {

	private static VRaptorRestDoclet vraptorRestDoclet;
	private static String ELEMENT_TAG = "value";
	
	AnnotationDesc annotationDesc = mock(AnnotationDesc.class);
	ElementValuePair elementValuePair = mock(ElementValuePair.class);
	AnnotationValue annotationValue = mock(AnnotationValue.class);
	AnnotationTypeDoc annotationTypeDoc = mock(AnnotationTypeDoc.class);
	MethodDoc methodDoc = mock(MethodDoc.class);
	Tag tag = mock(Tag.class);
	RootDoc rootDoc = mock(RootDoc.class);
	ClassDoc classDoc = mock(ClassDoc.class);
	AnnotationDesc controllerAnnotationDesc = mock(AnnotationDesc.class);
	ElementValuePair controllerElementValuePair = mock(ElementValuePair.class);
	AnnotationValue controllerAnnotationValue = mock(AnnotationValue.class);
	AnnotationTypeDoc controllerAnnotationTypeDoc = mock(AnnotationTypeDoc.class);
	
	@BeforeClass
	public static void setUp() {
		vraptorRestDoclet = new VRaptorRestDoclet();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		vraptorRestDoclet = null;
	}

	@Test
	public void findURITest() {
		when(annotationDesc.elementValues()).thenReturn(new ElementValuePair[0]);
		
		String result = vraptorRestDoclet.findServiceURI(annotationDesc);
		assertEquals("", result);

		when(annotationValue.toString()).thenReturn(RestDocAttributes.PATH_URI);
		when(elementValuePair.value()).thenReturn(annotationValue);
		when(annotationDesc.elementValues()).thenReturn(new ElementValuePair[] { elementValuePair } );
		
		assertEquals(RestDocAttributes.PATH_URI, vraptorRestDoclet.findServiceURI(annotationDesc));
	}

	@Test
	public void getRequestTypeTest() {
		when(annotationDesc.annotationType()).thenReturn(annotationTypeDoc);
		
		String result = vraptorRestDoclet.getRequestType(annotationDesc);
		assertNull(result);

		when(annotationTypeDoc.qualifiedTypeName()).thenReturn(RestAnnotation.GET.toString());
		result = vraptorRestDoclet.getRequestType(annotationDesc);
		assertEquals("get", result.toLowerCase());
		
		when(annotationTypeDoc.qualifiedTypeName()).thenReturn(RestAnnotation.POST.toString());
		result = vraptorRestDoclet.getRequestType(annotationDesc);
		assertEquals("post", result.toLowerCase());
	}

	@Test
	public void getMethodRestAnnotationTest() {
		when(methodDoc.annotations()).thenReturn(new AnnotationDesc[] { annotationDesc });
		when(annotationDesc.annotationType()).thenReturn(annotationTypeDoc);
		when(annotationTypeDoc.qualifiedTypeName()).thenReturn("");
		// method isn't a service
		assertNull(vraptorRestDoclet.getMethodRestAnnotation(methodDoc));
		
		// method is a service
		when(annotationTypeDoc.qualifiedTypeName()).thenReturn(RestAnnotation.GET.toString());
		assertEquals(annotationDesc, vraptorRestDoclet.getMethodRestAnnotation(methodDoc));
	}

	@Test
	public void createServiceTest() {
		setRootDocMock();
		
		RestService expected = new RestService.Builder(RestDocAttributes.PATH_URI, "Get")
		.wihtParameters(RestDocAttributes.PARAMS).withCalls(RestDocAttributes.CALLS)
		.withDescription(RestDocAttributes.DESCRIPTION).withExceptionThrows(RestDocAttributes.THROWS)
		.withReturnExample(RestDocAttributes.RETURN_EXAMPLE).withReturns(RestDocAttributes.RETURN)
		.withServiceMethod(RestDocAttributes.QUALIFIED_NAME + RestDocAttributes.SIGNATURE).build();
		
		RestService actual = vraptorRestDoclet.createServiceDoc(methodDoc, "");
		assertEquals(expected, actual);
	}

	@Test
	public void testFindRESTService() throws Exception {
		setRootDocMock();
		
		List<RestService> services = vraptorRestDoclet.findRestServices(rootDoc);
		assertNotNull(services);
		assertEquals(services.size(), 1);
		
		RestService expected = new RestService.Builder(RestDocAttributes.PATH_URI + RestDocAttributes.PATH_URI, "Get")
		.wihtParameters(RestDocAttributes.PARAMS).withCalls(RestDocAttributes.CALLS)
		.withDescription(RestDocAttributes.DESCRIPTION).withExceptionThrows(RestDocAttributes.THROWS)
		.withReturnExample(RestDocAttributes.RETURN_EXAMPLE).withReturns(RestDocAttributes.RETURN)
		.withServiceMethod(RestDocAttributes.QUALIFIED_NAME + RestDocAttributes.SIGNATURE).build();
		
		assertEquals(expected, services.get(0));
	}
	
	private void setRootDocMock(){
		when(rootDoc.classes()).thenReturn(new ClassDoc[] { classDoc });
		when(classDoc.annotations()).thenReturn(new AnnotationDesc[] { controllerAnnotationDesc });
		when(controllerAnnotationDesc.annotationType()).thenReturn(controllerAnnotationTypeDoc);
		when(controllerAnnotationTypeDoc.qualifiedTypeName()).thenReturn(RestAnnotation.PATH.toString());
		when(controllerAnnotationValue.toString()).thenReturn(RestDocAttributes.PATH_URI);
		when(controllerAnnotationDesc.elementValues()).thenReturn(new ElementValuePair[] { controllerElementValuePair } );
		when(controllerElementValuePair.value()).thenReturn(controllerAnnotationValue);
		when(classDoc.methods()).thenReturn(new MethodDoc[] { methodDoc });
		setMethodDocMock();
	}
	
	private void setMethodDocMock(){
		when(methodDoc.annotations()).thenReturn(new AnnotationDesc[] { annotationDesc });
		when(methodDoc.qualifiedName()).thenReturn(RestDocAttributes.QUALIFIED_NAME);
		when(methodDoc.signature()).thenReturn(RestDocAttributes.SIGNATURE);
		when(methodDoc.commentText()).thenReturn(RestDocAttributes.DESCRIPTION);
		when(methodDoc.tags(VRaptorRestDoclet.PARAM_TAG_ANNOTATION)).thenReturn(new Tag[] { tag });
		when(methodDoc.tags(VRaptorRestDoclet.RETURN_TAG_ANNOTATION)).thenReturn(new Tag[] { tag });
		when(methodDoc.tags(VRaptorRestDoclet.RETURN_EXAMPLE_TAG_ANNOTATION)).thenReturn(new Tag[] { tag });
		when(methodDoc.tags(VRaptorRestDoclet.THROWS_TAG_ANNOTATION)).thenReturn(new Tag[] { tag });
		when(methodDoc.tags(VRaptorRestDoclet.CALLS_TAG_ANNOTATION)).thenReturn(new Tag[] { tag });
		when(annotationDesc.annotationType()).thenReturn(annotationTypeDoc);
		when(annotationDesc.elementValues()).thenReturn(new ElementValuePair[] { elementValuePair } );
		when(annotationTypeDoc.qualifiedTypeName()).thenReturn(RestAnnotation.GET.toString());
		when(annotationValue.toString()).thenReturn(RestDocAttributes.PATH_URI);
		when(elementValuePair.value()).thenReturn(annotationValue);
		when(tag.text()).thenReturn("text");
	}
}
