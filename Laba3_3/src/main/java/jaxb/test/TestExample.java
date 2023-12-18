package jaxb.test;

import jaxb.model.Department;
import jaxb.model.Employee;
import jaxb.model.Organisation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TestExample {
    private static final String XML_FILE = "dept-info.xml";

    public static void main(String[] args) {

        Employee emp1 = new Employee("E01", "Tom", null);
        Employee emp2 = new Employee("E02", "Mary", "E01");
        Employee emp3 = new Employee("E03", "John", null);

        List<Employee> list = new ArrayList<Employee>();
        list.add(emp1);
        list.add(emp2);
        list.add(emp3);

        Department dept1 = new Department("D01", "ACCOUNTING");
        List<Department> list1 = new ArrayList<Department>();
        list1.add(dept1);

        dept1.setEmployees(list);

        Organisation org = new Organisation("O01", "GCOM", "NEW YORK");
        List<Organisation> list2 = new ArrayList<Organisation>();
        list2.add(org);

        org.setDepartments(list1);

        try {
            // create JAXB context and instantiate marshaller
            JAXBContext context = JAXBContext.newInstance(Organisation.class);

            // (1) Marshaller : Java Object to XML content.
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            m.marshal(org, System.out);

            // Write to File
            File outFile = new File(XML_FILE);
            m.marshal(org, outFile);

            System.err.println("Write to file: " + outFile.getAbsolutePath());

            // (2) Unmarshaller : Read XML content to Java Object.
            Unmarshaller um = context.createUnmarshaller();

            // XML file create before.

            Organisation orgFromFile1 = (Organisation) um.unmarshal(new FileReader(
                    XML_FILE));

            List<Department> depts = orgFromFile1.getDepartments();
            for (Department dept : depts){
                System.out.println("Department: " + dept.getDeptName());
                for (int i = 0; i < dept.getEmployees().size(); i++) {
                    System.out.println("Employee: " + dept.getEmployees().get(i).getEmpName());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}