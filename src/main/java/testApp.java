import com.Debt.*;
import  com.generateDateTable.*;
import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.lang.Math;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.DecimalFormat;

public class testApp {
    public static String ceilIntWithZero(int space ,int y){
        String s = "";
        int count =-1;
        int tmp = y;
        for (int i = 0 ;i<space;i++){
            y = y/10;
            if (y == 0){
                count++;
            }
            
        }
        if (count >0){
            for (int i = 0; i < count; i++) {
                s += '0';
            }
            return s + tmp;
        }
        else return ""+tmp;
    }
    
    public static void InsertRandomRows(SqlSession session ){
        Random r1 = new Random();
        String[] TierSpeed = {"T0","T1"};
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String date = "2021-11-01";
        DecimalFormat ft = new DecimalFormat("0.00");
        DecimalFormat ft2 = new DecimalFormat("0.000");
        
        for(int i=0;i<100;i++){
            Debt debt = new Debt();
            debt.setID(10000+i);
            debt.setName("gz" + ceilIntWithZero(5,i));
            debt.setNetPrice(Float.parseFloat(ft2.format(r1.nextFloat()*5/100 +1)));
            debt.setClearSpeed(TierSpeed[r1.nextInt(2)]);
            debt.setRepayDate(date);
            debt.setYTM(Float.parseFloat(ft.format(r1.nextFloat()+1)));
            debt.setNumber(10000);
            session.insert("insertOneItem",debt);
        }
        System.out.println("success");
    }

    public static void userGenerator(SqlSession session){
        Random r1 = new Random();
        //r1.setSeed(4568L);
        Set<Integer> DupliTest= new HashSet<Integer>();
        for (int i = 0; i < 30; i++) {
            //Random r1 = new Random();
            User user = new User();
            int tmp = (int)(r1.nextDouble()*Math.pow(10,10));
            while (DupliTest.contains(tmp)){
                tmp = (int)(r1.nextDouble()*Math.pow(10,10));
            }
            DupliTest.add(tmp);
            user.setAccount(tmp+"");

            while (DupliTest.contains(tmp)){
                tmp = (int)(r1.nextDouble()*Math.pow(10,10));
            }
            user.setPassword(tmp+"");
            session.insert("insertUser",user);
        }
    }

    public static void transactionListGenerator(SqlSession session){
        List<String> calendar = new ArrayList<String>();
        String prefix = "2020-11-1";
        for (int i = 0; i < 10 ; i++) {
            calendar.add(prefix+i);
        }
        List<User> userList = session.selectList("selectAllOnRegister");
        List<Debt> debtList = session.selectList("selectAll");
//        for (int i = 0; i < debtList.size(); i++) {
//            System.out.println(debtList.get(i));
//        }
        Random r1 = new Random();
        for (int i = 0; i < calendar.size(); i++) {
            for (int j = 0; j <40; j++) {
                Trans trans = new Trans();
                trans.setBoughtScale(r1.nextInt(100)+1);
                trans.setDate(calendar.get(i));
                trans.setDebtID(debtList.get(r1.nextInt(debtList.size())).getAutoID());
                trans.setUserID(userList.get(r1.nextInt(userList.size())).getAutoID());
                session.insert("insertTransaction",trans);
            }
        }


    }

    public static void generateTableOnTime(SqlSession session){
        List<String> calendar = new ArrayList<String>();
        String prefix = "2020-11-1";
        calendar.add("bond");
        for (int i = 0; i < 10 ; i++) {
            calendar.add(prefix+i);
        }
        DecimalFormat ft2 = new DecimalFormat("0.000");
        Random r1 = new Random();
        for (int i = 1; i <calendar.size() ; i++) {
            //先获取某一天的全部交易行为 再创建前一天的表格 最后在这个基础上进行更新 完成新的表格
            List<Debt> debtList = session.selectList("selectPreviousData",calendar.get(i-1));
            List<Trans> transList = session.selectList("selectAllTransOnDate",calendar.get(i));
            for (int j = 0; j <transList.size() ; j++) {
                debtList.get(transList.get(j).getDebtID()-1).setNumber(
                        debtList.get(transList.get(j).getDebtID()-1).getNumber() - transList.get(j).getBoughtScale()
                );

                //完整逻辑应该包括购买失败报错以及购买信息的判别
            }
            session.delete("dropTable",calendar.get(i));
            session.update("createTableOnTime",calendar.get(i));

            for (int j = 0; j < debtList.size(); j++) {
//                r1.setSeed(10000+j);
                debtList.get(j).setNetPrice(Float.parseFloat(ft2.format(r1.nextFloat()*5/100 +1)));
//                debtList.get(i).setNumber(debtList.get(i).getNumber() - transList.);
//                DebtWithDate dwd = new DebtWithDate();

                DebtWithDate dwd = new DebtWithDate(debtList.get(j));
                dwd.setDate(calendar.get(i));
                session.insert("insertOnTable",dwd);
//                session.insert("insertOneItem",debtList.get(j));

            }




        }
//        session.update("createTableOnTime",)

    }

    public static void main(String[] args) throws IOException {
        //ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

        // 根据 mybatis-config.xml 配置的信息得到 sqlSessionFactory
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 然后根据 sqlSessionFactory 得到 session
        SqlSession session = sqlSessionFactory.openSession();
//        List<Debt> result =  session.selectList("selectAll");
//        for (Debt mydebt : result) {
//            System.out.println(mydebt.getID());
//        }
//        InsertRandomRows(session);
//        插入随机信息
//        userGenerator(session);
//        transactionListGenerator(session);
        generateTableOnTime(session);

//        // 增加学生
//        Student student1 = new Student();
//        student1.setId(4);
//        student1.setStudentID(4);
//        student1.setName("新增加的学生");
//        session.insert("addStudent", student1);
//
//        // 删除学生
//        Student student2 = new Student();
//        student2.setId(1);
//        session.delete("deleteStudent", student2);
//
//        // 获取学生
//        Student student3 = session.selectOne("getStudent", 2);
//
//        // 修改学生
//        student3.setName("修改的学生");
//        session.update("updateStudent", student3);
//
//        // 最后通过 session 的 selectList() 方法调用 sql 语句 listStudent
//        List<Student> listStudent = session.selectList("listStudent");
//        for (Student student : listStudent) {
//            System.out.println("ID:" + student.getId() + ",NAME:" + student.getName());
//        }

        // 提交修改
        session.commit();
        // 关闭 session
        session.close();
    }
}