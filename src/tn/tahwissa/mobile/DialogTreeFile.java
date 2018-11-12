/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile;

import com.codename1.components.FileTree;
import com.codename1.components.FileTreeModel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.tree.TreeModel;

/**
 *
 * @author esprit
 */
public class DialogTreeFile extends Dialog {
    FileTree fileSystem;
    FileTreeModel fileModel;
    
   
    public DialogTreeFile() {
        super(new LayeredLayout());
    }
    
    public  void show() {
        System.out.println("Ya Hamma");
        fileModel = new FileTreeModel(true);
        fileSystem = new FileTree();
        fileSystem.setModel(fileModel);
        
        fileSystem.addComponent(new Button("OK"));
        Container container = new Container(BoxLayout.y());
        container.add(fileSystem);
        fileSystem.getSelectedItem();
        this.add(container);
        super.actionCommand(new Command("OK"));
        super.show();
    }
    
}
