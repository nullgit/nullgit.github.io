package project3.service;

import project3.domain.*;

public class NameListService {    
    private Employee[] employees;

    public NameListService() {
        employees = new Employee[Data.EMPLOYEES.length];
        for (int i = 0; i < Data.EMPLOYEES.length; ++i) {
            Employee employee;
            switch (Integer.parseInt(Data.EMPLOYEES[i][0])) {
                case 10: {
                    employee = new Employee(Integer.parseInt(Data.EMPLOYEES[i][1]), Data.EMPLOYEES[i][2],
                            Integer.parseInt(Data.EMPLOYEES[i][3]), Integer.parseInt(Data.EMPLOYEES[i][4]));
                    break;
                }
                case 11: {
                    employee = new Programmer(Integer.parseInt(Data.EMPLOYEES[i][1]), Data.EMPLOYEES[i][2],
                            Integer.parseInt(Data.EMPLOYEES[i][3]), Double.parseDouble(Data.EMPLOYEES[i][4]),
                            getEquipment(Data.EQUIPMENTS[i]));
                    break;
                }
                case 12: {
                    employee = new Designer(Integer.parseInt(Data.EMPLOYEES[i][1]), Data.EMPLOYEES[i][2],
                            Integer.parseInt(Data.EMPLOYEES[i][3]), Double.parseDouble(Data.EMPLOYEES[i][4]),
                            getEquipment(Data.EQUIPMENTS[i]), Double.parseDouble(Data.EMPLOYEES[i][5]));
                    break;
                }
                case 13: {
                    employee = new Architect(Integer.parseInt(Data.EMPLOYEES[i][1]), Data.EMPLOYEES[i][2],
                            Integer.parseInt(Data.EMPLOYEES[i][3]), Double.parseDouble(Data.EMPLOYEES[i][4]),
                            getEquipment(Data.EQUIPMENTS[i]), Double.parseDouble(Data.EMPLOYEES[i][5]),
                            Integer.parseInt(Data.EMPLOYEES[i][6]));
                    break;
                }
                default:
                    employee = null;
                    break;
            }
            employees[i] = employee;
        }
    }

    public Employee[] getAllEmployees() {
        return employees;
    }

    public Employee getEmployee(int id) throws TeamException {
        for (int i = 0; i < employees.length; ++i) {
            if (employees[i].getId() == id) {
                return employees[i];
            }
        }
        throw new TeamException("找不到指定员工");
    }

    public Equipment getEquipment(String[] equipment) {
        switch (Integer.parseInt(equipment[0])) {
            case 21:
                return new PC(equipment[1], equipment[2]);
            case 22:
                return new NoteBook(equipment[1], Double.parseDouble(equipment[2]));
            case 23:
                return new Printer(equipment[1], equipment[2]);
            default:
                return null;
        }
    }
}
