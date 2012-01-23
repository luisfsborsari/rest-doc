package br.com.restdoc;

import java.io.File;
import java.io.IOException;

import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.codehaus.plexus.util.ReaderFactory;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

/**
 * @author Danilo Buiatti Lamounier
 * 
 *         20/06/2011 11:47:38
 */
public class RestDocProjectStub extends MavenProjectStub {

	public RestDocProjectStub() throws IOException, XmlPullParserException {
		setProjectModel();
		setProjectProperties();
		setProjectBuild();
		setCompileArtifacts(RestDocArtifact.generateArtifactsForTest());
	}

	private void setProjectModel() throws IOException, XmlPullParserException {
		MavenXpp3Reader pomReader = new MavenXpp3Reader();
		Model model = pomReader.read(ReaderFactory.newXmlReader(new File(
				getBasedir() + "/src/test/resources/", "fake-pom.xml")));
		setModel(model);
	}

	private void setProjectProperties() {
		Model model = getModel();
		setGroupId(model.getGroupId());
		setArtifactId(model.getArtifactId());
		setVersion(model.getVersion());
		setName(model.getName());
		setUrl(model.getUrl());
		setPackaging(model.getPackaging());
	}

	private void setProjectBuild() {
		Model model = getModel();
		Build build = new Build();
		build.setFinalName(model.getArtifactId());
		build.setDirectory(getBasedir() + "/target");
		build.setSourceDirectory(getBasedir() + "/src/main/java");
		build.setOutputDirectory(getBasedir().toString());
		setBuild(build);
	}

}
