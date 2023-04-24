package process;
import entities.Student;
import view.ListPanel;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Comparator;

public class ListProcess {
    
    private ListPanel listPanel;

    public ListProcess(ListPanel listPanel) {
        this.listPanel = listPanel;
    }
    
    public void exportToFile() {
        
    }
    
    public void saveToDatabase() {
        
    }

    public void sortStudentByMark(ArrayList<Student> list) {
        ArrayList<Student> listSorted = getArrayListSorted(list);
        pushDataToTable(listSorted);
    }

    public ArrayList<Student> getArrayListSorted(ArrayList<Student> list) {
        ArrayList<Student> listSorted = (ArrayList<Student>) list.clone();
        listSorted.sort((Comparator.comparingDouble(Student::getMark)));
        return listSorted;
    }
    
    public void pushDataToTable(ArrayList<Student> list) {
        DefaultTableModel model = (DefaultTableModel) listPanel.getjTable1().getModel();
        model.setRowCount(0);
        for(Student student : list) {
            model.addRow(new Object[] {
                student.getName(),
                student.getNameSchool(),
                student.getGender(),
                student.getAge(),
                student.getMark()
            });
        }
    }
    
}
