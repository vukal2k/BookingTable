/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commond;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;


public class ComboboxItemRender extends BasicComboBoxRenderer {
    public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus)
        {
            super.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);
 
            if (value != null)
            {
                ComboboxItem item = (ComboboxItem)value;
                setText( item.getValue() );
            }
 
            if (index == -1)
            {
                ComboboxItem item = (ComboboxItem)value;
                if(item!=null){
                    setText( "" + item.getValue());
                }
            }
 
 
            return this;
        }
}
