package br.com.restdoc.mojos;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.codehaus.plexus.util.StringUtils;

import br.com.restdoc.FakeArtifacts;
import br.com.restdoc.mojos.RestServiceMojo;

public class RestServiceMojoTest extends AbstractMojoTestCase {

	protected void setUp() throws Exception {
		// required for mojo lookups to work
		super.setUp();
	}

	protected void tearDown() throws Exception {
		// required
		super.tearDown();

		File outputFile = RestServiceMojo.getOutputFile();
		if (outputFile != null) {
			outputFile.delete();
		}
	}

	public void testRestDocPom() throws Exception {
		RestServiceMojo mojo = getRestDocMojo();
		assertNotNull(mojo);
	}

	public void testConfigurationProperties() throws Exception {
		RestServiceMojo mojo = getRestDocMojo();
		mojo.filesTreatment();
		assertTrue(mojo.getOutputDir().exists());
		assertTrue(mojo.getSourceDir().exists());
		assertTrue(RestServiceMojo.getOutputFile().exists());
	}

	public void testGetClasspath() throws Exception {
		RestServiceMojo mojo = getRestDocMojo();
		String classpath = mojo.getClasspath();
		assertNotNull(classpath);
		assertEquals(classpath, getClasspath());
	}

	private RestServiceMojo getRestDocMojo() throws Exception {
		File testPom = new File(getBasedir() + "/src/test/resources/", "fake-pom.xml");
		return (RestServiceMojo) lookupMojo("rest-doc", testPom);

	}
	
	private String getClasspath() throws Exception {
		RestServiceMojo mojo = getRestDocMojo();

		List<String> classpath = new LinkedList<String>((Arrays.asList(mojo.getProject().getBuild()
				.getOutputDirectory(), FakeArtifacts.VRAPTOR.getFile().toString(), FakeArtifacts.HIBERNATE.getFile()
				.toString(), FakeArtifacts.GSON.getFile().toString())));
		return StringUtils.join(classpath.iterator(), File.pathSeparator);
	}
}
