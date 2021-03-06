package project3.service;

import project3.domain.Architect;
import project3.domain.Designer;
import project3.domain.Employee;
import project3.domain.Programmer;

// getTeam(): Programmer[]
// + addMember(e: Employee) throws TeamException: void 
// + removeMember(memberId: int) throws TeamException: void 


public class TeamService {
    private static int count = 1;  //新增成员的memberId
    private final int MAX_MEMBER = 5;
    private Programmer[] team = new Programmer[MAX_MEMBER];
    private int total = 0;  //实际人数

    public Programmer[] getTeam() {
        Programmer[] teamcopy = new Programmer[total];
        for (int i = 0; i < total; ++i) {
            teamcopy[i] = team[i];
        }
        return teamcopy;
    }

    public void addMember(Employee e) throws TeamException {
        if (!(e instanceof Programmer)) {
            throw new TeamException("添加失败,该成员不是开发者");
        }
        Programmer p = (Programmer) e;

        int countarchitect = 0;
        int countdesigner = 0;
        int countprogrammer = 0;
        for (int i = 0 ; i < total; ++i) {
            if (p == team[i]) {
                throw new TeamException("添加失败,该成员已是开发团队的成员!");
            }
            if (team[i] instanceof Architect) {
                ++countarchitect;
                if (countarchitect >= 1 && p instanceof Architect) {
                    throw new TeamException("添加失败,架构师不能超过1人!");
                }
            }
            else if (team[i] instanceof Designer) {
                ++countdesigner;
                if (countdesigner >= 2 && p instanceof Designer) {
                    throw new TeamException("添加失败,设计师不能超过2人!");
                }
            }
            else {
                ++countprogrammer;
                if (countprogrammer >= 3) {
                    throw new TeamException("添加失败,程序员不能超过3人!");
                }
            }
        }
        if (total == MAX_MEMBER) {
            throw new TeamException("添加失败,团队人数不能超过五人!");
        }
        if (p.getStatus() == Status.BUSY) {
            throw new TeamException("添加失败,该成员已是某团队的成员!");
        }
        if (p.getStatus() == Status.VOCATION) {
            throw new TeamException("添加失败,该成员正在休假!");
        }

        p.setMemberId(count++);
        p.setStatus(Status.BUSY);
        team[total++] = p;
    }
    
    public void removeMember(int memberId) throws TeamException {
        for (int i = 0; i < total; ++i) {
            if (team[i].getMemberId() == memberId) {
                team[i].setStatus(Status.FREE);
                for (int j = i; j < total - 1; ++j) {
                    team[j] = team[j + 1];
                }
                team[--total] = null;
                System.out.println("删除成功!");
                return;
            }
        }
        
        throw new TeamException("移除失败,团队中不存在该成员!");
    }
}
