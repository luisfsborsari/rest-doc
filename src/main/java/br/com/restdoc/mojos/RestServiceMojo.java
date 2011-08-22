package br.com.restdoc.mojos;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.plexus.util.StringUtils;

import br.com.restdoc.doclets.VRaptorRestDoclet;
import br.com.restdoc.util.Utils;

/**
 * Goal which generates a javaScript file with the VRaptor REST Services docs in
 * a json format.
 * 
 * @requiresDependencyResolution compile
 * @goal rest-doc
 * @phase compile
 */
public class RestServiceMojo extends AbstractMojo {
	/**
	 * The Maven Project Object
	 * 
	 * @parameter expression="${project}"
	 * @required
	 */
	protected MavenProject project;

	/**
	 * Location of the source files
	 * 
	 * @parameter default-value="${project.basedir}/src/main"
	 * @required
	 */
	private String sourceDirectory;

	/**
	 * Directory to save the generated doc file.
	 * 
	 * @parameter default-value="${project.build.directory}/"
	 * @required
	 */
	private String outputDirectory;
	
	/**
	 * Name of the generated file.
	 * 
	 * @parameter default-value="rest-doc.js"
	 * @required
	 */
	private String outputFileName;

	/**
	 * Regular expression that identifies service source files
	 * 
	 * @parameter default-value="^.*Controller\\.java$"
	 * @required
	 */
	private String serviceFileNamePattern;

	/**
	 * @parameter default-value="${project.basedir}/src/main/java"
	 * @required
	 */
	private String sourcePath;
	
	private File sourceDir;

	private File outputDir;

	private static File outputFile;

	/**
	 * Classe doclet é responsável por salvar conteudo do arquivo
	 */
	public static File getOutputFile() {
		return outputFile;
	}
	
	public static void setOutputFile(File outputFileParam) {
		outputFile = outputFileParam;
	}
	
	String getOutputFileName() {
		return outputFileName;
	}

	File getSourceDir() {
		return sourceDir;
	}

	File getOutputDir() {
		return outputDir;
	}
	
	MavenProject getProject(){
		return project;
	}
	
	private File tryToCreateDir(String filePath, String errorMessage) throws MojoExecutionException {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new MojoExecutionException(errorMessage);
		}
		return file;
	}
	
	private void deleteOuputFile(File outputFile) throws MojoExecutionException {
		if (!outputFile.delete()) {
			throw new MojoExecutionException("File " + outputFile.getAbsolutePath() + " could not be deleted");
		}
	}

	private File createOutputFileOnSuccess(File outputDir, String outputFileName) throws MojoExecutionException {
		try {
			File ouputFile = new File(outputDir, outputFileName);
			if (!new File(outputDir, outputFileName).createNewFile()) {
				throw new MojoExecutionException("File " + outputFile.getAbsolutePath() + " could not be created");
			}
			return ouputFile;
		} catch (IOException e) {
			throw new MojoExecutionException("File " + outputFile.getAbsolutePath() + " could not be created");
		}
	}

	private File createOutputFile(File outputDir, String outputFileName) throws MojoExecutionException {
		File outputFile = new File(outputDir, outputFileName);
		if (outputFile.exists()) {
			getLog().info("File " + outputFile.getAbsolutePath() + " already exists and will be overwrited");
			deleteOuputFile(outputFile);
		}
		return createOutputFileOnSuccess(outputDir, outputFileName);
	}

	void filesTreatment() throws MojoExecutionException {
		sourceDir = tryToCreateDir(sourceDirectory, "Source directory " + sourceDirectory + " doesn't exists");
		outputDir = tryToCreateDir(outputDirectory, "Output directory " + outputDirectory + " doesn't exists");
		outputFile = createOutputFile(outputDir, outputFileName);
	}

	String getClasspath() throws MavenReportException {
		List<String> classpathElements = new ArrayList<String>();
		Map<String, Artifact> compileArtifactMap = new HashMap<String, Artifact>();

		classpathElements.addAll(getProjectBuildOutputDirs(project));
		populateDependencyArtifactMap(compileArtifactMap, getCompileArtifacts(project));

		for (Artifact a : compileArtifactMap.values()) {
			classpathElements.add(a.getFile().toString());
		}
		return StringUtils.join(classpathElements.iterator(), File.pathSeparator);
	}

	private List<String> getProjectBuildOutputDirs(MavenProject p) {
		if (StringUtils.isEmpty(p.getBuild().getOutputDirectory())) {
			return Collections.emptyList();
		}
		return Collections.singletonList(p.getBuild().getOutputDirectory());
	}

	@SuppressWarnings("unchecked")
	private List<Artifact> getCompileArtifacts(MavenProject p) {
		return (p.getCompileArtifacts() == null ? Collections.<Artifact> emptyList() : new LinkedList<Artifact>(
				p.getCompileArtifacts()));
	}

	@SuppressWarnings("unchecked")
	private void populateDependencyArtifactMap(Map<String, Artifact> compileArtifactMap,
			Collection<Artifact> artifactList) throws MavenReportException {
		if (artifactList == null) {
			return;
		}

		for (Artifact newArtifact : artifactList) {
			File file = newArtifact.getFile();

			if (file == null) {
				throw new MavenReportException("Error in plugin descriptor - "
						+ "dependency was not resolved for artifact: " + newArtifact.getGroupId() + ":"
						+ newArtifact.getArtifactId() + ":" + newArtifact.getVersion());
			}
			if (compileArtifactMap.get(newArtifact.getDependencyConflictId()) != null) {
				Artifact oldArtifact = compileArtifactMap.get(newArtifact.getDependencyConflictId());

				ArtifactVersion oldVersion = new DefaultArtifactVersion(oldArtifact.getVersion());
				ArtifactVersion newVersion = new DefaultArtifactVersion(newArtifact.getVersion());
				if (newVersion.compareTo(oldVersion) > 0) {
					compileArtifactMap.put(newArtifact.getDependencyConflictId(), newArtifact);
				}
			} else {
				compileArtifactMap.put(newArtifact.getDependencyConflictId(), newArtifact);
			}
		}
	}

	public void execute() throws MojoExecutionException {
		try {
			filesTreatment();
			String classpath = getClasspath();

			getLog().info("Rest-doc generation...");
			getLog().info("Regex '" + serviceFileNamePattern + "'");
			getLog().info("Folder: " + sourceDirectory);
			getLog().info("Absolute path: " + sourceDir.getAbsolutePath());
			getLog().info("Sourcepath: " + sourcePath);

			List<String> javadocParameters = new ArrayList<String>();
			javadocParameters.add("-doclet");
			javadocParameters.add(VRaptorRestDoclet.PATH);
			javadocParameters.add("-classpath");
			javadocParameters.add(classpath);
			javadocParameters.add("-sourcepath");
			javadocParameters.add(sourcePath);
			javadocParameters.add("-docletpath");
			javadocParameters.add(".");

			FileFilter fileFilter = new RegexFileFilter(serviceFileNamePattern);
			Utils.findFiles(sourceDir, javadocParameters, fileFilter);

			try {
				com.sun.tools.javadoc.Main.execute(javadocParameters.toArray(new String[javadocParameters.size()]));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (PatternSyntaxException e) {
			throw new MojoExecutionException("Invalid regular expression", e);
		} catch (MavenReportException e) {
			throw new MojoExecutionException("Error while getting classpath", e);
		}
	}
}
