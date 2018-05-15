/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.TreeSet;
import javax.swing.JTextField;
import static javax.swing.JTextField.notifyAction;

/**
 *
 * @author Marcil
 */
/*this class allow auto complete of products names*/
public class AutoComplete extends JTextField {
    private boolean deleteOnTab = false;
    /*this inner class is used to store the products in the auto complete list according to their priority*/
    private static class AutoCompleteEntry implements Comparable<AutoCompleteEntry>{
	//product name (id)
        private String name;
	//priority of the product (suggest important products first
        private int priority;

        public AutoCompleteEntry(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }
	///SETTERS AND GETTERS
        public void setName(String name) {
            this.name = name;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getName() {
            return name;
        }

        public int getPriority() {
            return priority;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 71 * hash + Objects.hashCode(this.name);
            hash = 71 * hash + this.priority;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final AutoCompleteEntry other = (AutoCompleteEntry) obj;
            if (!Objects.equals(this.name, other.name)) {
                return false;
            }
            if (this.priority != other.priority) {
                return false;
            }
            return true;
        }

        @Override
        public int compareTo(AutoCompleteEntry o) {
            int nameCompare = name.compareTo(notifyAction);
            if(nameCompare == 0)return Integer.compare(priority, o.priority);
            return nameCompare;
        }

    }
    //store the products sorted by their priority
    private TreeSet<AutoCompleteEntry> data;

    public void clearData() {
        data.clear();
    }
    //add a product to the suggestion list
    public void addEntry(String name, int priority) {
        data.add(new AutoCompleteEntry(name, priority));
    }
    //in this constructor we are creating an empty list of suggestions
    public AutoComplete() {

        super();
        data = new TreeSet<>();
        if(deleteOnTab)addFocusListener(new FocusAdapter() {
	    //when focus lost we want to complete the text that the user did not enter yet.
	    //for example if he/she entered 'ab' and the 
            //suggestion is abcd we want to replace ab with abcd) 
            @Override
            public void focusLost(FocusEvent e){
                StringBuilder innerText = new StringBuilder(getText());
                if(getSelectionEnd() == getText().length()){
                    innerText.delete(getSelectionStart(), getSelectionEnd());
                }
                setText(innerText.toString());
                
                
            }
        });

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e); //To change body of generated methods, choose Tools | Templates.
                if(e.isActionKey())return;
                String prefix = getText();
		//start auto completion only for prefixes of length 2 or more
                if(prefix.length() < 2)return;
  		//get the new best cadidate, we maybe loosing a cadidate completion after adding the new character so we 
		//have to update the current one.
                String candidate = getCompletion(prefix);
		//we also may end up with no matches after adding the new character
                if(candidate == null)return;
		//set the text of the cadidate in the text field
                int start = prefix.length();
                AutoComplete.this.setText(candidate);
                AutoComplete.this.select(start, AutoComplete.this.getText().length());
            }
	    // return the best candidate we have that starts with a given prefix
            private String getCompletion(final String prefix) {
                
                AutoCompleteEntry ret = new AutoCompleteEntry("", 0);
                data.stream().forEach((entry)->{
                    if(entry.getName().startsWith(prefix)){
                        if(ret.getPriority() < entry.getPriority()){
                            ret.setName(entry.getName());
                            ret.setPriority(entry.getPriority());
                        }
                    }
                });
                if(ret.getName().length() == 0)return null;
                return ret.getName();
            }

        });

    }

//    public static void main(String[] args) {
//        AutoComplete a = new AutoComplete();
//        a.addEntry("hello", 5);
//        a.addEntry("hello world",3);
//        a.setText("                   ");
//        JFrame f = new JFrame();
//        JPanel panel = new JPanel(new FlowLayout());
//        panel.add(a);
//        panel.setSize(500, 500);
//        panel.setVisible(true);
//        f.setSize(500, 500);
//        f.add(panel);
//        f.setVisible(true);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }

//    public void setText(String text){
//        
//    }
}
