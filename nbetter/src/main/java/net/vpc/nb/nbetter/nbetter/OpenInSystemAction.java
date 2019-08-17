/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vpc.nb.nbetter.nbetter;

import java.io.File;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

/**
 *
 * @author vpc
 */
@ActionID(category = "Edit", id = "net.vpc.nb.nbetter.nbetter.OpenInSystemAction")
@ActionRegistration(displayName = "Open in System", lazy = true)
@ActionReferences({
    @ActionReference(path = "Projects/Actions/Tools")
    ,
    @ActionReference(path = "Projects/package/Actions/Tools")
    ,
    @ActionReference(path = "Loaders/folder/any/Actions/Tools")
})
public class OpenInSystemAction extends NodeAction {

    private String name = "Open in System";

    public OpenInSystemAction() {
    }

    @Override
    protected boolean enable(Node[] nodes) {
        return true;
    }

    @Override
    public void performAction(org.openide.nodes.Node[] nodes) {
        try {

            for (File file : NBetterUtils.resolveFiles(nodes)) {
                try {
                    NBetterUtils.openFile(file);
                } catch (Exception ex) {
                    NotifyDescriptor desc = new NotifyDescriptor.Message("Unable to open "
                            + ((file != null && file.isDirectory()) ? " directory" : "file"),
                            NotifyDescriptor.ERROR_MESSAGE);
                    DialogDisplayer.getDefault().notify(desc);
                }
            }

        } catch (Exception ex) {
            NotifyDescriptor desc = new NotifyDescriptor.Message("Unable to open "+ex.toString(),
                    NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify(desc);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

}
