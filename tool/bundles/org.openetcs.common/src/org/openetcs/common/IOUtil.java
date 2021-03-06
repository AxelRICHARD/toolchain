package org.openetcs.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.onefile.model.IPapyrusFile;
import org.eclipse.uml2.uml.Model;

public class IOUtil {
	public static Model openUMLModel(IFile file) {
		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), false);
		Resource resource = resourceSet.getResource(uri, true);
		
		try {
			resource.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		EcoreUtil.resolveAll(resourceSet);
		EcoreUtil.resolveAll(resource);
		
		Model model = (Model) resource.getContents().get(0);
		return model;
	}
	
	public static void writeProjectFile(IProject project, IPath folder, String file, String content) throws CoreException {
		IFile projectFile = project.getFile(folder.toString() + "/" + file);
		ByteArrayInputStream stream = new ByteArrayInputStream(content.getBytes());
		projectFile.create(stream, true, null);
	}
	
	public static IFile getUMLFile(IPapyrusFile file) {
		IResource[] ressources = file.getAssociatedResources();
		
		for (IResource res : ressources) {
			if (res.getFileExtension().equals("uml")) {
				return (IFile) res;
			}
		}
		
		return null;
	}

	public void generateProjectFolder(IProject project) {
		generateProjectFolder(project.getLocation().toOSString() + "\\");
	}

	public void generateProjectFolder(String folder) {
		File file = new File(folder);
		
		if (!file.exists())	{
			file.mkdir();
		}
	}

}
