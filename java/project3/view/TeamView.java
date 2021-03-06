package project3.view;

import project3.domain.Architect;
import project3.domain.Designer;
import project3.domain.Employee;
import project3.domain.Programmer;
import project3.service.*;

public class TeamView {
    public static void main(String[] args) {
        TeamView t = new TeamView();
        t.enterMainMenu();
    }

    private NameListService listSvc = new NameListService();
    private TeamService teamSvc = new TeamService();

    public void enterMainMenu() {
        boolean flag = true;
        while (flag) {
            listAllEmployees();
            char c = TSUtility.readMenuSelection();
            switch (c) {
                case '1': {
                    getTeam();
                    break;
                }
                case '2': {
                    addMember();
                    break;
                }
                case '3': {
                    deleteMember();
                    break;
                }
                case '4': {
                    flag = false;
                    break;
                }
                default: {
                    break;
                }
            }
            TSUtility.readReturn();
        }
    }

    private void listAllEmployees() {
        System.out.println("-----------------------------所有员工----------------------------");
        System.out.println("ID\t姓名\t年龄\t工资\t职位\t状态\t奖金\t股票\t领用设备");
        Employee[] employees = listSvc.getAllEmployees();
        for (int i = 0; i < employees.length; ++i) {
            Employee e = employees[i];
            if (e instanceof Architect) {
                Architect a = (Architect) e;
                System.out.println(a.getId() + "\t" + a.getName() + "\t" + a.getAge() + "\t" + a.getSalary() + "\t"
                        + "架构师\t" + a.getStatus() + "\t" + a.getBonus() + "\t" + a.getStock() + "\t"
                        + a.getEquipment().getDescription());
            } else if (e instanceof Designer) {
                Designer d = (Designer) e;
                System.out.println(d.getId() + "\t" + d.getName() + "\t" + d.getAge() + "\t" + d.getSalary() + "\t"
                        + "设计师\t" + d.getStatus() + "\t" + d.getBonus() + "\t" + " " + "\t"
                        + d.getEquipment().getDescription());
            } else if (e instanceof Programmer) {
                Programmer p = (Programmer) e;
                System.out.println(p.getId() + "\t" + p.getName() + "\t" + p.getAge() + "\t" + p.getSalary() + "\t"
                        + "程序员\t" + p.getStatus() + "\t" + " " + "\t" + " " + "\t" + p.getEquipment().getDescription());
            } else {
                System.out.println(e.getId() + "\t" + e.getName() + "\t" + e.getAge() + "\t" + e.getSalary());
            }
        }
        System.out.println("--------------------------------------------------------------");
        System.out.print("1-团队列表  2-添加团队成员  3-删除团队成员 4-退出   请选择(1-4):");
    }

    private void getTeam() {
        Programmer[] team = teamSvc.getTeam();
        System.out.println("--------------------团队成员列表---------------------");
        System.out.println("ID/TID\t姓名\t年龄\t工资\t职位\t奖金\t股票");
        for (int i = 0; i < team.length; ++i) {
            Programmer p = team[i];
            if (p instanceof Architect) {
                Architect a = (Architect) p;
                System.out.println(a.getId() + "/" + a.getMemberId() + "\t" + a.getName() + "\t" + a.getAge() + "\t"
                        + a.getSalary() + "\t" + "架构师\t" + a.getBonus() + "\t" + a.getStock() + "\t");
            } else if (p instanceof Designer) {
                Designer d = (Designer) p;
                System.out.println(d.getId() + "/" + d.getMemberId() + "\t" + d.getName() + "\t" + d.getAge() + "\t"
                        + d.getSalary() + "\t" + "设计师\t" + d.getBonus() + "\t" + " " + "\t");
            } else {
                System.out.println(p.getId() + "/" + p.getMemberId() + "\t" + p.getName() + "\t" + p.getAge() + "\t"
                        + p.getSalary() + "\t" + "程序员\t");
            }
        }
    }

    private void addMember() {
        System.out.println("---------------------添加成员---------------------");
        System.out.println("请输入要添加的员工ID:");
        try {
            int id = TSUtility.readInt();
            Employee[] employees = listSvc.getAllEmployees();
            if (id <= 0 || id > employees.length) {
                throw new TeamException("添加失败,id输入不正确");
            }
            teamSvc.addMember(employees[id - 1]);
        } catch (TeamException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteMember() {
        System.out.println("---------------------删除成员---------------------");
        System.out.println("请输入要删除的员工TID:");
        int tid = TSUtility.readInt();
        try {
            teamSvc.removeMember(tid);
        }
        catch (TeamException e) {
            System.out.println(e.getMessage());
        }
    }
}
