package org.fanhongtao.swt.dialog;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Fan Hongtao
 * @created 2010-10-19
 */
public class ListInputDialog extends InputDialog
{
    private String[] items = new String[] { "" };
    
    private List list;
    
    private boolean readOnly = true;
    
    public ListInputDialog(Shell parentShell, String dialogTitle, String dialogMessage, String initialValue,
            IInputValidator validator)
    {
        super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
    }
    
    public String[] getItems()
    {
        return items;
    }
    
    public void setItems(String[] items)
    {
        this.items = items;
    }
    
    public List getList()
    {
        return list;
    }
    
    public void setList(List list)
    {
        this.list = list;
    }
    
    public boolean isReadOnly()
    {
        return readOnly;
    }
    
    public void setReadOnly(boolean readOnly)
    {
        this.readOnly = readOnly;
    }
    
    @Override
    protected Control createDialogArea(Composite parent)
    {
        Composite composite = (Composite)super.createDialogArea(parent);
        list = new List(composite, SWT.H_SCROLL | SWT.V_SCROLL);
        list.setItems(items);
        list.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetDefaultSelected(SelectionEvent e)
            {
                String[] selection = list.getSelection();
                getText().setText(selection[0]);
                buttonPressed(IDialogConstants.OK_ID);
            }
        });
        
        GridData layoutData = new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL);
        layoutData.heightHint = convertVerticalDLUsToPixels(100);
        list.setLayoutData(layoutData);
        
        getText().addModifyListener(new ModifyListener()
        {
            @Override
            public void modifyText(ModifyEvent arg0)
            {
                refreshSelectedList();
            }
        });
        
        getText().addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                switch (e.keyCode)
                {
                    case SWT.ARROW_UP:
                    {
                        int idx = list.getSelectionIndex();
                        list.setSelection(idx - 1);
                        break;
                    }
                    case SWT.ARROW_DOWN:
                    {
                        int idx = list.getSelectionIndex();
                        list.setSelection(idx - 1);
                        break;
                    }
                    default:
                    {
                        super.keyPressed(e);
                    }
                }
            }
        });
        
        return composite;
    }
    
    @Override
    protected void buttonPressed(int buttonId)
    {
        if (buttonId == IDialogConstants.OK_ID)
        {
            if (readOnly)
            {
                if (list.getSelectionCount() == 0)
                {
                    getText().setFocus();
                    return;
                }
                
                String[] selection = list.getSelection();
                getText().setText(selection[0]);
            }
        }
        super.buttonPressed(buttonId);
    }
    
    private void refreshSelectedList()
    {
        String inputText = getText().getText().toLowerCase();
        ArrayList<String> matchedList = new ArrayList<String>();
        for (String item : items)
        {
            if (item.toLowerCase().startsWith(inputText))
            {
                matchedList.add(item);
            }
        }
        
        String[] matchedItems = matchedList.toArray(new String[0]);
        list.setItems(matchedItems);
        list.setSelection(0);
    }
    
}
