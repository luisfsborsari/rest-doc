package br.com.restdoc;

/**
 * @author Danilo Buiatti Lamounier
 *
 * 21/06/2011 10:54:46
 */
public interface FakeArtifacts {
	RestDocArtifact VRAPTOR = new RestDocArtifact("vraptor", "vraptor", "3.1.1", "/fake/path/vraptor");
	RestDocArtifact GSON = new RestDocArtifact("gson", "gson", "1.6", "/fake/path/gson");
	RestDocArtifact HIBERNATE = new RestDocArtifact("hibernate", "hibernate-core", "3.3.2.GA", "/fake/path/hibernate-core");
}
