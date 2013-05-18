package com.artezio.rerunplugin;

import com.intellij.execution.runners.FakeRerunAction;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonShortcuts;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.impl.ToolWindowImpl;
import com.intellij.ui.content.Content;

import javax.swing.*;

/**
 * User: Galoshin Evgeniy
 * Date: 16.05.13 15:09
 */
public class RerunPlugin extends AnAction {

    public class RerunAction extends AnAction implements DumbAware {
        private Runnable myRerunAction;

        public RerunAction(JComponent consolePanel) {
            super("Rerun", "Rerun",
                    AllIcons.Actions.Restart);
            registerCustomShortcutSet(CommonShortcuts.getRerun(), consolePanel);
        }

        @Override
        public void actionPerformed(AnActionEvent e) {
            myRerunAction.run();
        }

        @Override
        public void update(AnActionEvent e) {
            e.getPresentation().setVisible(myRerunAction != null);
        }
    }

    public void actionPerformed(final AnActionEvent e) {
        ToolWindowImpl toolWindow;
        ToolWindowManager manager = ToolWindowManager.getInstance(e.getProject());
        ToolWindow window = manager.getToolWindow("Run");
        if (window == null) {
            return;
        }
        Content[] contents = window.getContentManager().getContents();

        for (Content content : contents) {
            content.getComponent().add(new JCheckBox("test checkbox"));
            if (content instanceof ConsoleView) {
                content.getComponent().add(new JCheckBox("test checkbox"));
                continue;
            }
            if (content instanceof JComponent) {
                ((JComponent) content).add(new JCheckBox("test checkbox"));
                continue;
            }
        }


//        window.getComponent()


        FakeRerunAction fakeRerunAction = new FakeRerunAction();
        fakeRerunAction.actionPerformed(e);

//        ExecutionManager.getInstance(e.getProject()).restartRunProfile(e.getProject(),                                 );

        window.show(new Runnable() {
            @Override
            public void run() {
                //nothing
            }
        });
    }
}


