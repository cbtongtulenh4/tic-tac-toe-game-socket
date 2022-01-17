
package javakit.minhphuc.dto;


import javakit.minhphuc.util.Avatar;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

// https://www.codejava.net/java-se/swing/jlist-custom-renderer-example
public class CustomRenderer extends JLabel implements ListCellRenderer<PlayerInGameDTO> {

    @Override
    public Component getListCellRendererComponent(JList<? extends PlayerInGameDTO> jList, PlayerInGameDTO p, int index, boolean isSelected, boolean cellHasFocus) {
        ImageIcon imageIcon = new ImageIcon(Avatar.PATH + p.getAvatar());
        setIcon(imageIcon);
        setText(p.getName());

        if (isSelected) {
            setBackground(jList.getSelectionBackground());
            setForeground(jList.getSelectionForeground());
        } else {
            setBackground(jList.getBackground());
            setForeground(jList.getForeground());
        }

        return this;
    }

}
