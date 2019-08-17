/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vpc.nb.nbetter.nbetter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author vpc
 */
public class NBetterUtils {

    public static void openFile(File file) throws IOException {
        final String n = System.getProperty("os.name").toLowerCase();
        if (n.contains("linux")) {
            Runtime.getRuntime().exec(new String[]{"xdg-open", file.getCanonicalPath()});
        } else if (n.contains("windows")) {
            Runtime.getRuntime().exec(new String[]{"explorer", file.getCanonicalPath()});
        } else if (n.contains("macos")) {
            Runtime.getRuntime().exec(new String[]{"open", file.getCanonicalPath()});
        } else {
            throw new IllegalArgumentException("Unsupported OS " + n);
        }
    }

    public static File[] resolveFiles(Node[] currentNodes) {
        List<File> all = new ArrayList<File>();
        for (FileObject currentNode : resolveFileObjects(currentNodes)) {
            if (currentNode != null) {
                File fo = FileUtil.toFile(currentNode);
                if (fo != null) {
                    all.add(fo);
                }
            }
        }
        return all.toArray(new File[0]);
    }

    public static FileObject[] resolveFileObjects(Node[] currentNodes) {
        List<FileObject> all = new ArrayList<FileObject>();
        if (currentNodes != null) {
            for (Node currentNode : currentNodes) {
                if (currentNode != null) {
                    FileObject fo = resolveFileObject(currentNode);
                    if (fo != null) {
                        all.add(fo);
                    }
                }
            }
        }
        return all.toArray(new FileObject[0]);
    }

    public static FileObject resolveFileObject(Node currentNode) {
        Project[] projects = null;
        try {
            projects = (Project[]) currentNode.getLookup().lookup(new Lookup.Template(Project.class)).allInstances().toArray(new Project[0]);
        } catch (Exception ex) {
            //ignore
        }
        FileObject fileObj = null;
        if (projects != null && projects.length > 0 && projects[0] != null) {
            if (projects[0].getProjectDirectory() != null) {
                try {
                    fileObj = projects[0].getProjectDirectory();
                } catch (Exception ex) {
                    //ignore
                }
            }
        }
        if (fileObj == null) {
            try {
                DataObject dataObject = currentNode.getLookup().lookup(DataObject.class);
                if (dataObject != null) {
                    fileObj = dataObject.getPrimaryFile();
                }
            } catch (Exception ex) {
                //ignore
            }
        }
        if (fileObj != null) {
            if (!fileObj.isValid() || fileObj.isVirtual()) {
                fileObj = null;
            }
        }
        return fileObj;
    }
}
