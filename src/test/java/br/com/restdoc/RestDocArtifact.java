package br.com.restdoc;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.testing.stubs.ArtifactStub;

/**
 * @author Danilo Buiatti Lamounier
 * 
 *         20/06/2011 15:40:47
 */
public class RestDocArtifact extends ArtifactStub implements Artifact {

	public RestDocArtifact(String groupId, String artifactId, String version, String filePath) {
		setGroupId(groupId);
		setArtifactId(artifactId);
		setVersion(version);
		setFile(new File(filePath));
	}

	public static List<Artifact> generateArtifactsForTest() {
		return new ArrayList<Artifact>(
				Arrays.asList(FakeArtifacts.VRAPTOR, FakeArtifacts.GSON, FakeArtifacts.HIBERNATE));
	}
}
